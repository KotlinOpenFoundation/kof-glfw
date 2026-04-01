package io.github.kotlinopenfoundation.glfw.wrapper.exception

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_NO_WINDOW_CONTEXT
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * A window that does not have an OpenGL or OpenGL ES context was passed to a function that
 * requires it (`GLFW_NO_WINDOW_CONTEXT`).
 *
 * @param message human-readable description of the error
 * @param cause optional underlying cause
 */
@OptIn(ExperimentalForeignApi::class)
class NoWindowContextGlfwException(
  message: String? = null,
  cause: Throwable? = null
) : GlfwException(message, cause, GLFW_NO_WINDOW_CONTEXT)
