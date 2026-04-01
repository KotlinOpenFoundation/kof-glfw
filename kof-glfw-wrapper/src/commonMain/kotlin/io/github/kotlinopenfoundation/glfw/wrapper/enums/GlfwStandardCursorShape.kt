package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.*
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Standard system cursor shapes.
 *
 * These can be used with [glfwCreateStandardCursor].
 * See the [GLFW cursor shape documentation](https://www.glfw.org/docs/3.4/group__shapes.html) for details.
 *
 * @property glfwValue The GLFW constant for the cursor shape.
 * @since GLFW 3.1
 */
@OptIn(ExperimentalForeignApi::class)
enum class GlfwStandardCursorShape(val glfwValue: Int) {
  /** The regular arrow cursor shape. */
  Arrow(GLFW_ARROW_CURSOR),
  /** The text input I-beam cursor shape. */
  IBeam(GLFW_IBEAM_CURSOR),
  /** The crosshair cursor shape. */
  Crosshair(GLFW_CROSSHAIR_CURSOR),
  /** The pointing hand cursor shape. */
  PointingHand(GLFW_POINTING_HAND_CURSOR),
  /** The horizontal resize/move arrow shape. */
  ResizeEW(GLFW_RESIZE_EW_CURSOR),
  /** The vertical resize/move arrow shape. */
  ResizeNS(GLFW_RESIZE_NS_CURSOR),
  /** The top-left to bottom-right diagonal resize/move arrow shape. */
  ResizeNWSE(GLFW_RESIZE_NWSE_CURSOR),
  /** The top-right to bottom-left diagonal resize/move arrow shape. */
  ResizeNESW(GLFW_RESIZE_NESW_CURSOR),
  /** The omnidirectional resize/move cursor shape. */
  ResizeAll(GLFW_RESIZE_ALL_CURSOR),
  /** The operation-not-allowed cursor shape. */
  NotAllowed(GLFW_NOT_ALLOWED_CURSOR)
}
