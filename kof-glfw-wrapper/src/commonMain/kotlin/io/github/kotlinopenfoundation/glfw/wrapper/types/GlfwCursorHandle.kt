package io.github.kotlinopenfoundation.glfw.wrapper.types

import cnames.structs.GLFWcursor
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Type-safe handle for a GLFW cursor object.
 *
 * @property pointer The native pointer to the GLFW cursor.
 */
@OptIn(ExperimentalForeignApi::class)
value class GlfwCursorHandle(
  val pointer: CPointer<GLFWcursor>
)
