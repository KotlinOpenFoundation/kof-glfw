package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_HAT_DOWN
import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_HAT_LEFT
import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_HAT_RIGHT
import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_HAT_UP
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Joystick hat directions based on bitmask values.
 *
 * @property value The bitmask value for the hat direction.
 */
@OptIn(ExperimentalForeignApi::class)
enum class JoystickHatDirection(
  val value: UByte
) {
  /** Hat is pointing up (bitmask 1). */
  Up(GLFW_HAT_UP),
  /** Hat is pointing right (bitmask 2). */
  Right(GLFW_HAT_RIGHT),
  /** Hat is pointing down (bitmask 4). */
  Down(GLFW_HAT_DOWN),
  /** Hat is pointing left (bitmask 8). */
  Left(GLFW_HAT_LEFT);

  constructor(glfwValue: Int) : this(glfwValue.toUByte())
}
