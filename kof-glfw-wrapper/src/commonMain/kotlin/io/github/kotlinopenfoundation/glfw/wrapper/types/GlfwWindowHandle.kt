package io.github.kotlinopenfoundation.glfw.wrapper.types

import cnames.structs.GLFWwindow
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Type-safe handle for a GLFW [window](https://www.glfw.org/docs/3.4/group__window.html) object.
 *
 * @property pointer The native pointer to the GLFW window.
 */
@OptIn(ExperimentalForeignApi::class)
value class GlfwWindowHandle(
  val pointer: CPointer<GLFWwindow>
)
