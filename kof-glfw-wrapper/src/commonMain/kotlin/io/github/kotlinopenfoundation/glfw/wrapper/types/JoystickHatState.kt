package io.github.kotlinopenfoundation.glfw.wrapper.types

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_HAT_CENTERED
import io.github.kotlinopenfoundation.glfw.wrapper.enums.JoystickHatDirection
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Wraps a GLFW joystick hat bitmask value.
 *
 * Hat states are bitmasks where individual direction bits can be combined to represent diagonal
 * positions (e.g., up and right). The raw GLFW hat constants are:
 * - `GLFW_HAT_CENTERED`
 * - `GLFW_HAT_UP`
 * - `GLFW_HAT_RIGHT`
 * - `GLFW_HAT_DOWN`
 * - `GLFW_HAT_LEFT`
 *
 * @property value The raw bitmask value of the hat state.
 */
value class JoystickHatState(private val value: UByte) {
  /** `true` if the hat is pushed to the right. */
  val right: Boolean
    get() = JoystickHatDirection.Right in this
  /** `true` if the hat is pushed to the left. */
  val left: Boolean
    get() = JoystickHatDirection.Left in this
  /** `true` if the hat is pushed up. */
  val up: Boolean
    get() = JoystickHatDirection.Up in this
  /** `true` if the hat is pushed down. */
  val down: Boolean
    get() = JoystickHatDirection.Down in this

  /** `true` if the hat is in the centered (neutral) position. */
  @OptIn(ExperimentalForeignApi::class)
  val centered: Boolean
    get() = GLFW_HAT_CENTERED.toUByte() == value

  /** Checks whether the given [direction] is present in this hat bitmask. */
  operator fun contains(direction: JoystickHatDirection): Boolean =
    (value and direction.value) != 0.toUByte()
}
