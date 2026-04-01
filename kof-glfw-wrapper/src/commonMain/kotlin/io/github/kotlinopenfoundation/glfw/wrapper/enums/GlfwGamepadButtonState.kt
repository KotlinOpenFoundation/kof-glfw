package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.*
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Gamepad button states.
 *
 * @property glfwValue The GLFW constant for the button state.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
enum class GlfwGamepadButtonState(val glfwValue: Int) {
  /** The button is released. */
  Released(GLFW_RELEASE),
  /** The button is pressed. */
  Pressed(GLFW_PRESS);

  companion object {
    /** Returns the [GlfwGamepadButtonState] for the given GLFW constant, or `null` if not found. */
    fun fromGlfwValue(value: Int): GlfwGamepadButtonState? = entries.find { it.glfwValue == value }
  }
}
