import com.palantir.gradle.gitversion.VersionDetails
import groovy.lang.Closure
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinTest
import org.jetbrains.kotlin.gradle.tasks.UsesKotlinJavaToolchain

val versionDetails: Closure<VersionDetails> by extra

plugins {
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.dokka)
  alias(libs.plugins.git.version)
  `jvm-toolchains`
}

group = "io.github.kotlinopenfoundation.glfw"
version = versionDetails().determineVersion()

val service = project.extensions.getByType<JavaToolchainService>()
val customLauncher = service.launcherFor {
  languageVersion.set(JavaLanguageVersion.of(libs.versions.jdk.get().toInt()))
  vendor.set(JvmVendorSpec.ADOPTIUM)
}

subprojects {
  group = rootProject.group
  version = rootProject.version

  apply(plugin = rootProject.libs.plugins.kotlin.multiplatform.get().pluginId)
  apply(plugin = rootProject.libs.plugins.dokka.get().pluginId)
  apply(plugin = "maven-publish")

  dokka {
    dokkaSourceSets.configureEach {
      sourceLink {
        localDirectory = projectDir
        val modulePath = projectDir.relativeTo(rootDir).path.replace('\\', '/')
        remoteUrl("https://github.com/KotlinOpenFoundation/kotlin-glfw/blob/main/$modulePath")
        remoteLineSuffix = "#L"
      }
    }
  }

  configure<PublishingExtension> {
    repositories {
      maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/KotlinOpenFoundation/glfw")
        credentials {
          username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
          password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
        }
      }
    }

    publications {
      register<MavenPublication>("gpr") {
        from(components.findByName("kotlin"))
      }
    }
  }

  tasks {
    withType<UsesKotlinJavaToolchain> {
      kotlinJavaToolchain.toolchain.use(customLauncher)
    }
    withType<KotlinTest> {
      testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
        showExceptions = true
        showStackTraces = true
        exceptionFormat = TestExceptionFormat.FULL
      }
    }
  }
}

dokka {
  moduleName = project.name

  dokkaPublications.html {
    suppressInheritedMembers = true
  }

  pluginsConfiguration.html {
    footerMessage = "(c) Kotlin Open Foundation"
  }
}

dependencies {
  dokka(projects.kofGlfwInterop)
}

fun VersionDetails.determineVersion(): String {
  val first = gitHash.startsWith(lastTag)
  val base = if (first) "0.0.0" else lastTag.removePrefix("v")
  val next = if (first)
    base
  else
    base.split(".")
      .map(String::toInt)
      .let { (major, minor, patch) -> listOf(major, minor, patch + 1) }
      .joinToString(".")

  return if (!isCleanTag)
    "$next-local"
  else if (commitDistance > 0)
    "$next-dev"
  else
    base
}
