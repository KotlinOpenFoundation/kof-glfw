import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
  alias(libs.plugins.kotlin.multiplatform)
}

dependencies {
  commonMainImplementation(projects.kofGlfwInterop)
}

kotlin {
  mingwX64()
  linuxX64()
  linuxArm64()
  macosX64()
  macosArm64()
}

tasks {
  withType<KotlinNativeLink> {
    if (binary.compilation.konanTarget.family != HostManager.host.family) {
      enabled = false
      return@withType
    }
  }
}
