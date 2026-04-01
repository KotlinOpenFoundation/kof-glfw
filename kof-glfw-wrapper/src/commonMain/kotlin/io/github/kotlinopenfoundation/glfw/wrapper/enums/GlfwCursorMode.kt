package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.*
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Cursor mode values for the [GlfwInputMode.Cursor] input mode.
 *
 * @property glfwValue The GLFW constant for the cursor mode.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
enum class GlfwCursorMode(val glfwValue: Int) {
  /** Normal cursor behavior. The cursor is visible and behaves normally. */
  Normal(GLFW_CURSOR_NORMAL),
  /** The cursor is invisible when over the content area of the window but does not restrict it. */
  Hidden(GLFW_CURSOR_HIDDEN),
  /** Hides and grabs the cursor, providing virtual and unlimited cursor movement. */
  Disabled(GLFW_CURSOR_DISABLED),
  /** The cursor is visible but confined to the content area of the window. */
  Captured(GLFW_CURSOR_CAPTURED);

  companion object {
    /** Returns the [GlfwCursorMode] for the given GLFW constant, or `null` if not found. */
    fun fromGlfwValue(value: Int): GlfwCursorMode? = entries.find { it.glfwValue == value }
  }
}
