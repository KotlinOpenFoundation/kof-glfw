package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_WAYLAND_DISABLE_LIBDECOR
import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_WAYLAND_PREFER_LIBDECOR
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Wayland-specific init hint for libdecor configuration.
 *
 * Corresponds to the `GLFW_WAYLAND_LIBDECOR` init hint.
 *
 * @property glfwValue The GLFW constant for the libdecor setting.
 */
@OptIn(ExperimentalForeignApi::class)
enum class GlfwWaylandLibDecor(
  override val glfwValue: Int
) : GlfwValue {
  /** Prefer using libdecor for window decorations. */
  Prefer(GLFW_WAYLAND_PREFER_LIBDECOR),
  /** Disable libdecor; use the built-in decoration implementation. */
  Disable(GLFW_WAYLAND_DISABLE_LIBDECOR)
}
