import org.gradle.internal.extensions.stdlib.capitalized
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink
import org.jetbrains.kotlin.konan.target.Family
import org.jetbrains.kotlin.konan.target.HostManager
import java.net.HttpURLConnection
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path as JavaPath
import org.jetbrains.kotlin.konan.target.Architecture as Arch

val glfwLibBaseUrl = "https://github.com/glfw/glfw/releases/download"
val glfwLibVersion = libs.versions.glfw.get()

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.kotlin.powerassert)
}

dependencies {
  commonMainImplementation(libs.kotlin.logging)
  commonTestImplementation(libs.kotlin.test)
}

kotlin {
  mingwX64()
  linuxX64()
  linuxArm64()
  @Suppress("DEPRECATION") macosX64()
  macosArm64()

  targets.withType<KotlinNativeTarget>().configureEach {
    compilations["main"].cinterops {
      @Suppress("unused")
      val glfw by registering
    }
  }

  targets.withType<KotlinNativeTarget>().matching { it.konanTarget.family == Family.LINUX }.configureEach {
    compilations["test"].cinterops {
      @Suppress("unused")
      val opengl by registering
    }
  }

  // set library search paths (absolute) and rpath for shared lib discovery at runtime
  targets.withType<KotlinNativeTarget>().configureEach {
    binaries.all {
      val depsDir = project.layout.buildDirectory.dir("deps/glfw").get().asFile.absolutePath
      when (konanTarget.family) {
        Family.MINGW -> linkerOpts("-L$depsDir/mingw/lib-mingw-w64")
        Family.OSX -> {
          val arch = if (konanTarget.architecture == Arch.ARM64) "lib-arm64" else "lib-x86_64"
          linkerOpts("-L$depsDir/macos/$arch", "-Wl,-rpath,@executable_path")
        }
        Family.LINUX -> {
          val arch = if (konanTarget.architecture == Arch.ARM64) "lib-arm64" else "lib-x64"
          linkerOpts("-L$depsDir/linux/$arch", "-rpath", $$"$ORIGIN")
        }
        else -> {}
      }
    }
  }
}

tasks {
  mapOf(
    "Mingw" to ".bin.WIN64",
    "Macos" to ".bin.MACOS",
    "Src" to ""
  ).forEach { (type, suffix) ->
    val glfwLibVersion = libs.versions.glfw.get()
    val fileName = "glfw-$glfwLibVersion$suffix.zip"

    val downloadTask = register<DownloadFile>("downloadGlfwLib${type.capitalized()}") {
      uri = uri("$glfwLibBaseUrl/$glfwLibVersion/$fileName")
      output = project.layout.buildDirectory.file("deps/glfw/$fileName")
      etag = project.layout.buildDirectory.file("deps/glfw/$fileName.etag").get().asFile.absolutePath
    }

    register<Copy>("extractGlfwLib$type") {
      dependsOn(downloadTask)
      from(zipTree(downloadTask.get().output)) {
        include("**")
        includeEmptyDirs = false
        eachFile {
          relativePath = RelativePath(true, *relativePath.segments.drop(1).toTypedArray())
        }
      }
      val dir = project.layout.buildDirectory.dir("deps/glfw/${type.lowercase()}")
      into(dir)
      outputs.dir(dir)
      outputs.cacheIf { dir.get().asFile.list()?.isNotEmpty() ?: false }
    }
  }

  data class CMakeCompileTarget(
    val family: Family,
    val architecture: Arch,
    val cmakeOptions: List<String> = emptyList()
  ) {
    val name: String get() = "$familyName$architectureName"
    val familyName: String get() = family.name.lowercase().capitalized()
    val architectureName: String get() = architecture.name.lowercase().capitalized()
    val processorName: String
      get() = when (architecture) {
        Arch.X86 -> "i686"
        Arch.X64 -> "x86_64"
        Arch.ARM32 -> "arm"
        Arch.ARM64 -> "arm64"
      }
  }

  val cMakeTargets = listOf(
    CMakeCompileTarget(Family.LINUX, Arch.X64),
    CMakeCompileTarget(
      Family.LINUX,
      Arch.ARM64,
      listOf("-DCMAKE_C_COMPILER=aarch64-linux-gnu-gcc", "-DCMAKE_CXX_COMPILER=aarch64-linux-gnu-g++")
    ),
  )

  cMakeTargets.forEach { cMakeTarget ->
    val libPath = "deps/glfw/${cMakeTarget.family.name.lowercase()}/lib-${cMakeTarget.architecture.name.lowercase()}"
    val cMakeBuildPath = "build/${cMakeTarget.family.name.lowercase()}-${cMakeTarget.architecture.name.lowercase()}"

    val cMakeGlfwConfigure = register<Exec>("cMakeGlfwConfigure${cMakeTarget.name}") {
      dependsOn("extractGlfwLibSrc")

      workingDir = project.layout.buildDirectory.dir("deps/glfw/src").get().asFile
      commandLine(
        "cmake", "-S", ".", "-B", cMakeBuildPath,
        "-DCMAKE_SYSTEM_NAME=${cMakeTarget.familyName}",
        "-DCMAKE_SYSTEM_PROCESSOR=${cMakeTarget.processorName}",
        *cMakeTarget.cmakeOptions.toTypedArray(),
        "-DCMAKE_BUILD_TYPE=Release",
        "-DBUILD_SHARED_LIBS=ON",
        "-DGLFW_BUILD_EXAMPLES=OFF",
        "-DGLFW_BUILD_TESTS=OFF",
        "-DGLFW_BUILD_DOCS=OFF",
        "-DGLFW_INSTALL=OFF"
      )
      inputs.dir(project.layout.buildDirectory.dir("deps/glfw/src/"))
      outputs.dir(project.layout.buildDirectory.dir("deps/glfw/src/$cMakeBuildPath"))
    }

    val cMakeGlfwBuild = register<Exec>("cMakeGlfwBuild${cMakeTarget.name}") {
      dependsOn(cMakeGlfwConfigure)
      workingDir = project.layout.buildDirectory.dir("deps/glfw/src").get().asFile
      commandLine("cmake", "--build", cMakeBuildPath, "-j", Runtime.getRuntime().availableProcessors().toString())
      inputs.dir(project.layout.buildDirectory.dir("deps/glfw/src/$cMakeBuildPath"))
      outputs.dir(project.layout.buildDirectory.dir("deps/glfw/src/$cMakeBuildPath/src"))
    }

    register<Copy>("copyGlfwLib${cMakeTarget.name}") {
      dependsOn(cMakeGlfwBuild)
      from(project.layout.buildDirectory.dir("deps/glfw/src/$cMakeBuildPath/src")) {
        include("libglfw.so*")
      }
      val dir = project.layout.buildDirectory.dir(libPath)
      into(dir)
      inputs.dir(project.layout.buildDirectory.dir("deps/glfw/src/$cMakeBuildPath/src"))
      outputs.dir(dir)
      outputs.cacheIf { dir.get().asFile.listFiles()?.any { it.name.startsWith("libglfw.so") } ?: false }
    }

  }

  cMakeTargets.windowed(2).forEach { (prev, next) ->
    named("cMakeGlfwConfigure${next.name}") {
      mustRunAfter("cMakeGlfwConfigure${prev.name}", "cMakeGlfwBuild${prev.name}")
    }
    named("cMakeGlfwBuild${next.name}") {
      mustRunAfter("cMakeGlfwConfigure${prev.name}", "cMakeGlfwBuild${prev.name}")
    }
  }

  // macOS: create libglfw.dylib symlink so -lglfw finds libglfw.3.dylib
  register("createGlfwDylibSymlinks") {
    dependsOn("extractGlfwLibMacos")
    val macosDir = project.layout.buildDirectory.dir("deps/glfw/macos")
    doLast {
      listOf("lib-x86_64", "lib-arm64").forEach { arch ->
        val dir = macosDir.get().asFile.resolve(arch)
        val link = dir.toPath().resolve("libglfw.dylib")
        if (!Files.exists(link)) {
          Files.createSymbolicLink(link, JavaPath.of("libglfw.3.dylib"))
        }
      }
    }
  }

  // cinterop only needs headers (no staticLibraries), so just depend on the extract task
  kotlin.targets.withType<KotlinNativeTarget> {
    val platformName = name.capitalized().removeSuffix("X64").removeSuffix("Arm64")

    named("cinteropGlfw${name.capitalized()}") {
      val extractTask = findByName("extractGlfwLib$platformName")
      if (extractTask != null) {
        dependsOn(extractTask)
      } else {
        dependsOn("extractGlfwLibSrc")
      }
    }
  }

  // link tasks: only for host platform, with shared lib dependencies
  withType<KotlinNativeLink> {
    val konanTarget = binary.compilation.konanTarget
    if (konanTarget.family != HostManager.host.family) {
      enabled = false
      return@withType
    }
    when (konanTarget.family) {
      Family.MINGW -> dependsOn("extractGlfwLibMingw")
      Family.OSX -> dependsOn("createGlfwDylibSymlinks")
      Family.LINUX -> {
        val arch = konanTarget.architecture.name.lowercase().capitalized()
        dependsOn("copyGlfwLibLinux$arch")
      }
      else -> {}
    }
    // copy shared library next to the output binary for runtime discovery
    val depsDir = project.layout.buildDirectory.dir("deps/glfw").get().asFile
    val outputDir = binary.outputDirectory
    doLast {
      when (konanTarget.family) {
        Family.MINGW -> {
          val src = File(depsDir, "mingw/lib-mingw-w64/glfw3.dll")
          if (src.exists()) src.copyTo(File(outputDir, "glfw3.dll"), overwrite = true)
        }
        Family.OSX -> {
          val arch = if (konanTarget.architecture == Arch.ARM64) "lib-arm64" else "lib-x86_64"
          val src = File(depsDir, "macos/$arch/libglfw.3.dylib")
          if (src.exists()) src.copyTo(File(outputDir, "libglfw.3.dylib"), overwrite = true)
        }
        Family.LINUX -> {
          val arch = if (konanTarget.architecture == Arch.ARM64) "lib-arm64" else "lib-x64"
          val dir = File(depsDir, "linux/$arch")
          dir.listFiles()?.filter { it.name.startsWith("libglfw.so") }?.forEach { src ->
            src.copyTo(File(outputDir, src.name), overwrite = true)
          }
        }
        else -> {}
      }
    }
  }
}

@CacheableTask
abstract class DownloadFile : DefaultTask() {
  @get:Input
  abstract val uri: Property<URI>

  @get:OutputFile
  abstract val output: RegularFileProperty

  @get:Input
  @get:Optional
  abstract val etag: Property<String>

  init {
    outputs.cacheIf { true }
  }

  @TaskAction
  fun download() {
    val dest = output.get().asFile
    dest.parentFile.mkdirs()

    val connection = uri.get().toURL().openConnection() as HttpURLConnection
    val etagFile = etag.orNull?.let { File(it) }
    etagFile
      ?.takeIf { it.exists() && dest.exists() }
      ?.readText()
      ?.let { connection.setRequestProperty("If-None-Match", it) }

    connection.connect()
    when (connection.responseCode) {
      HttpURLConnection.HTTP_NOT_MODIFIED -> return logger.lifecycle("File is up to date: ${dest.name}")
      HttpURLConnection.HTTP_OK -> {}
      else -> error("Failed to download file: HTTP ${connection.responseCode}")
    }

    dest.outputStream().use { out -> connection.inputStream.use { it.copyTo(out) } }
    connection.getHeaderField("ETag")?.let { newEtag ->
      etagFile?.also { it.parentFile.mkdirs() }?.writeText(newEtag)
    }
    logger.lifecycle("Downloaded: ${dest.name}")
  }
}
