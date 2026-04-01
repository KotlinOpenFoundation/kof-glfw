package io.github.kotlinopenfoundation.glfw.wrapper.types

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_VERSION_MAJOR
import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_VERSION_MINOR
import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_VERSION_REVISION
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Represents the version of the GLFW library.
 *
 * This data class contains the major, minor, and revision numbers of a GLFW version.
 *
 * @property major The major version number of the GLFW library.
 * @property minor The minor version number of the GLFW library.
 * @property revision The revision number of the GLFW library.
 */
data class GlfwVersion(
  val major: Int,
  val minor: Int,
  val revision: Int
) {
  companion object {
    /** The GLFW version that the library was compiled against. */
    @OptIn(ExperimentalForeignApi::class)
    val compileVersion = GlfwVersion(
      GLFW_VERSION_MAJOR,
      GLFW_VERSION_MINOR,
      GLFW_VERSION_REVISION
    )
  }
}
