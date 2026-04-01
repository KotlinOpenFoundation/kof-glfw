package io.github.kotlinopenfoundation.glfw.wrapper.types

import cnames.structs.GLFWmonitor
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Type-safe handle for a GLFW [monitor](https://www.glfw.org/docs/3.4/group__monitor.html) object.
 *
 * @property pointer The native pointer to the GLFW monitor.
 */
@OptIn(ExperimentalForeignApi::class)
value class GlfwMonitorHandle(
  val pointer: CPointer<GLFWmonitor>
)
