package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.*
import io.github.kotlinopenfoundation.glfw.wrapper.glfwPlatformSupported
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Represents a supported platform by the GLFW library.
 *
 * GLFW can be compiled for more than one platform (window system) at once.
 * This lets a single library binary support both Wayland and X11 on Linux and other Unix-like systems.
 *
 * @property glfwValue The internal GLFW constant associated with the platform.
 */
@OptIn(ExperimentalForeignApi::class)
enum class GlfwPlatform(
  override val glfwValue: Int
) : GlfwValue {
  /** The Win32 platform (Windows). */
  Win32(GLFW_PLATFORM_WIN32),
  /** The Cocoa platform (macOS). */
  Cocoa(GLFW_PLATFORM_COCOA),
  /** The Wayland platform (Linux). */
  Wayland(GLFW_PLATFORM_WAYLAND),
  /** The X11 platform (Linux/Unix). */
  X11(GLFW_PLATFORM_X11),
  /** Headless platform for off-screen rendering. */
  Null(GLFW_PLATFORM_NULL)
  ;

  /** Whether the library was compiled with support for the platform. */
  val supported: Boolean
    get() = glfwPlatformSupported(this)
}
