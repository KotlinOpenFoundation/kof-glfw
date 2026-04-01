package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.*
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Modifier key flags as a bitmask.
 *
 * Modifier keys are combined into a single bitmask value passed to key and mouse button callbacks.
 * See the [GLFW modifier key documentation](https://www.glfw.org/docs/3.4/group__mods.html) for details.
 *
 * @property value The raw bitmask value.
 * @since GLFW 3.1
 */
@OptIn(ExperimentalForeignApi::class)
value class GlfwModifierKey(val value: Int) {
  /** Whether the Shift key is held. */
  val shift: Boolean get() = (value and GLFW_MOD_SHIFT) != 0
  /** Whether the Control key is held. */
  val control: Boolean get() = (value and GLFW_MOD_CONTROL) != 0
  /** Whether the Alt key is held. */
  val alt: Boolean get() = (value and GLFW_MOD_ALT) != 0
  /** Whether the Super key (Windows/Command) is held. */
  val superKey: Boolean get() = (value and GLFW_MOD_SUPER) != 0
  /** Whether CapsLock is active. */
  val capsLock: Boolean get() = (value and GLFW_MOD_CAPS_LOCK) != 0
  /** Whether NumLock is active. */
  val numLock: Boolean get() = (value and GLFW_MOD_NUM_LOCK) != 0

  companion object {
    /** No modifier keys. */
    val None = GlfwModifierKey(0)
  }
}
