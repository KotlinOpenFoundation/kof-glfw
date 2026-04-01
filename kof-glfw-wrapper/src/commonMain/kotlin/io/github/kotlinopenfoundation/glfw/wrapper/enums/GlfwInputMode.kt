package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.*
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Input mode tokens for [glfwGetInputMode] and [glfwSetInputMode].
 *
 * See the [GLFW input guide](https://www.glfw.org/docs/3.4/input_guide.html) for details.
 *
 * @property glfwValue The GLFW constant for the input mode.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
enum class GlfwInputMode(val glfwValue: Int) {
  /** Cursor mode. Value should be one of [GlfwCursorMode] constants. */
  Cursor(GLFW_CURSOR),
  /** Whether sticky keys are enabled. */
  StickyKeys(GLFW_STICKY_KEYS),
  /** Whether sticky mouse buttons are enabled. */
  StickyMouseButtons(GLFW_STICKY_MOUSE_BUTTONS),
  /** Whether lock key modifiers are enabled. */
  LockKeyMods(GLFW_LOCK_KEY_MODS),
  /** Whether raw mouse motion is enabled (only when the cursor is disabled). */
  RawMouseMotion(GLFW_RAW_MOUSE_MOTION)
}
