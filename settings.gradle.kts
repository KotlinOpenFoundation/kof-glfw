enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
  id("com.gradle.develocity") version("4.4.0")
}

dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      from(files("./libs.versions.toml"))
    }
  }

  @Suppress("UnstableApiUsage")
  repositories {
    mavenCentral()
  }
}

rootProject.name = "kof-glfw"

include(
  ":kof-glfw-interop",
//  ":kof-glfw-wrapper",
//  ":kof-glfw"
)

develocity {
  buildScan {
    termsOfUseUrl.set("https://gradle.com/help/legal-terms-of-use")
    termsOfUseAgree.set("yes")
    publishing.onlyIf { false }
  }
}
