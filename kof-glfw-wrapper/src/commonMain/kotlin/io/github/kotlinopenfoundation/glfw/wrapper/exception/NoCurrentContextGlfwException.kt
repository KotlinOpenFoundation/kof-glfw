package io.github.kotlinopenfoundation.glfw.wrapper.exception

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_NOT_INITIALIZED
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * No OpenGL or OpenGL ES context is current on the calling thread (`GLFW_NO_CURRENT_CONTEXT`).
 *
 * This occurs if a GLFW function was called that needs and operates on the current OpenGL or
 * OpenGL ES context, but no context is current on the calling thread.
 *
 * @param message human-readable description of the error
 * @param cause optional underlying cause
 */
@OptIn(ExperimentalForeignApi::class)
class NoCurrentContextGlfwException(
  message: String? = null,
  cause: Throwable? = null
) : GlfwException(message, cause, GLFW_NOT_INITIALIZED)
