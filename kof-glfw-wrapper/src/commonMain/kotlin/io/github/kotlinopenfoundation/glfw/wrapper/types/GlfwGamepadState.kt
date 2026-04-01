package io.github.kotlinopenfoundation.glfw.wrapper.types

import io.github.kotlinopenfoundation.glfw.wrapper.enums.GlfwGamepadAxis
import io.github.kotlinopenfoundation.glfw.wrapper.enums.GlfwGamepadButton
import io.github.kotlinopenfoundation.glfw.wrapper.enums.GlfwGamepadButtonState

/**
 * [State of a gamepad](https://www.glfw.org/docs/3.4/structGLFWgamepadstate.html), including all buttons and axes.
 *
 * The button and axis indices correspond to the [GlfwGamepadButton] and [GlfwGamepadAxis] enum values.
 *
 * @property buttons The states of each gamepad button, indexed by [GlfwGamepadButton] ordinal.
 * @property axes The values of each gamepad axis, indexed by [GlfwGamepadAxis] ordinal. Values range from -1.0 to 1.0 inclusive.
 * @since GLFW 3.3
 */
data class GlfwGamepadState(
  val buttons: List<GlfwGamepadButtonState>,
  val axes: List<Float>
)
