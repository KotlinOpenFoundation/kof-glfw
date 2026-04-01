package io.github.kotlinopenfoundation.glfw.wrapper.exception

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_CURSOR_UNAVAILABLE
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * The specified standard cursor shape is not available (`GLFW_CURSOR_UNAVAILABLE`).
 *
 * @param message human-readable description of the error
 * @param cause optional underlying cause
 */
@OptIn(ExperimentalForeignApi::class)
class CursorUnavailableGlfwException(
  message: String? = null,
  cause: Throwable? = null
) : GlfwException(message, cause, GLFW_CURSOR_UNAVAILABLE)
