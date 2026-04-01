package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.*
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Actions for key and mouse button events.
 *
 * @property glfwValue The GLFW constant for the action.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
enum class GlfwKeyAction(val glfwValue: Int) {
  /** The key or mouse button was released. */
  Release(GLFW_RELEASE),
  /** The key or mouse button was pressed. */
  Press(GLFW_PRESS),
  /** The key was held down until it repeated. */
  Repeat(GLFW_REPEAT);

  companion object {
    /** Returns the [GlfwKeyAction] for the given GLFW constant, or `null` if not found. */
    fun fromGlfwValue(value: Int): GlfwKeyAction? = entries.find { it.glfwValue == value }
  }
}
