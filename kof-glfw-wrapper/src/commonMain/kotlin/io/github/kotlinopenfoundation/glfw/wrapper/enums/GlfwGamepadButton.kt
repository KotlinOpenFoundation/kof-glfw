package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.*
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Gamepad button identifiers.
 *
 * Corresponds to the Xbox controller layout.
 * See the [GLFW gamepad button documentation](https://www.glfw.org/docs/3.4/group__gamepad__buttons.html) for details.
 *
 * @property glfwValue The GLFW constant for the gamepad button.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
enum class GlfwGamepadButton(val glfwValue: Int) {
  /** The `A` button (Xbox) / Cross (PlayStation). */
  A(GLFW_GAMEPAD_BUTTON_A),
  /** The `B` button (Xbox) / Circle (PlayStation). */
  B(GLFW_GAMEPAD_BUTTON_B),
  /** The `X` button (Xbox) / Square (PlayStation). */
  X(GLFW_GAMEPAD_BUTTON_X),
  /** The `Y` button (Xbox) / Triangle (PlayStation). */
  Y(GLFW_GAMEPAD_BUTTON_Y),
  /** The left bumper. */
  LeftBumper(GLFW_GAMEPAD_BUTTON_LEFT_BUMPER),
  /** The right bumper. */
  RightBumper(GLFW_GAMEPAD_BUTTON_RIGHT_BUMPER),
  /** The back/select button. */
  Back(GLFW_GAMEPAD_BUTTON_BACK),
  /** The start button. */
  Start(GLFW_GAMEPAD_BUTTON_START),
  /** The guide/home button. */
  Guide(GLFW_GAMEPAD_BUTTON_GUIDE),
  /** The left thumbstick button. */
  LeftThumb(GLFW_GAMEPAD_BUTTON_LEFT_THUMB),
  /** The right thumbstick button. */
  RightThumb(GLFW_GAMEPAD_BUTTON_RIGHT_THUMB),
  /** The D-pad up button. */
  DpadUp(GLFW_GAMEPAD_BUTTON_DPAD_UP),
  /** The D-pad right button. */
  DpadRight(GLFW_GAMEPAD_BUTTON_DPAD_RIGHT),
  /** The D-pad down button. */
  DpadDown(GLFW_GAMEPAD_BUTTON_DPAD_DOWN),
  /** The D-pad left button. */
  DpadLeft(GLFW_GAMEPAD_BUTTON_DPAD_LEFT);

  companion object {
    /** Alias for [A]. */
    val Cross: GlfwGamepadButton = A
    /** Alias for [B]. */
    val Circle: GlfwGamepadButton = B
    /** Alias for [X]. */
    val Square: GlfwGamepadButton = X
    /** Alias for [Y]. */
    val Triangle: GlfwGamepadButton = Y
    /** The last valid gamepad button. */
    val Last: GlfwGamepadButton = DpadLeft

    /** Returns the [GlfwGamepadButton] for the given GLFW constant, or `null` if not found. */
    fun fromGlfwValue(value: Int): GlfwGamepadButton? = entries.find { it.glfwValue == value }
  }
}
