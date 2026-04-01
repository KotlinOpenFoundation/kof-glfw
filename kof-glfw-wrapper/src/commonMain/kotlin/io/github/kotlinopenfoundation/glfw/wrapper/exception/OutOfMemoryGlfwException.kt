package io.github.kotlinopenfoundation.glfw.wrapper.exception

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_NOT_INITIALIZED
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * A memory allocation failed (`GLFW_OUT_OF_MEMORY`).
 *
 * @param message human-readable description of the error
 * @param cause optional underlying cause
 */
@OptIn(ExperimentalForeignApi::class)
class OutOfMemoryGlfwException(
  message: String? = null,
  cause: Throwable? = null
) : GlfwException(message, cause, GLFW_NOT_INITIALIZED)
