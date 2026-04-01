package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.*
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Connection event types for monitors and joysticks.
 *
 * @property glfwValue The GLFW constant for the connection event.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
enum class GlfwConnectionEvent(val glfwValue: Int) {
  /** The device was connected. */
  Connected(GLFW_CONNECTED),
  /** The device was disconnected. */
  Disconnected(GLFW_DISCONNECTED);

  companion object {
    /** Returns the [GlfwConnectionEvent] for the given GLFW constant, or `null` if not found. */
    fun fromGlfwValue(value: Int): GlfwConnectionEvent? = entries.find { it.glfwValue == value }
  }
}
