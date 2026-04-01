package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_PRESS
import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_RELEASE
import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_REPEAT
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Joystick button states.
 *
 * @property glfwValue The GLFW constant for the button state.
 */
@OptIn(ExperimentalForeignApi::class)
enum class JoystickButtonState(
  val glfwValue: Int
) {
  /** The button is pressed. */
  Pressed(GLFW_PRESS),
  /** The button is released. */
  Released(GLFW_RELEASE),
  /** The button action is repeated (held down). */
  Repeated(GLFW_REPEAT)
}
