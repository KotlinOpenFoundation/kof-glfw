package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.*
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Gamepad axis identifiers.
 *
 * See the [GLFW gamepad axis documentation](https://www.glfw.org/docs/3.4/group__gamepad__axes.html) for details.
 *
 * @property glfwValue The GLFW constant for the gamepad axis.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
enum class GlfwGamepadAxis(val glfwValue: Int) {
  /** Left stick X axis. */
  LeftX(GLFW_GAMEPAD_AXIS_LEFT_X),
  /** Left stick Y axis. */
  LeftY(GLFW_GAMEPAD_AXIS_LEFT_Y),
  /** Right stick X axis. */
  RightX(GLFW_GAMEPAD_AXIS_RIGHT_X),
  /** Right stick Y axis. */
  RightY(GLFW_GAMEPAD_AXIS_RIGHT_Y),
  /** Left trigger axis. */
  LeftTrigger(GLFW_GAMEPAD_AXIS_LEFT_TRIGGER),
  /** Right trigger axis. */
  RightTrigger(GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER);

  companion object {
    /** The last valid gamepad axis. */
    val Last: GlfwGamepadAxis = RightTrigger
  }
}
