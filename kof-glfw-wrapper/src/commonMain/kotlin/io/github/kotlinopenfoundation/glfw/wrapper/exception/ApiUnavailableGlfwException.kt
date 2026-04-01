package io.github.kotlinopenfoundation.glfw.wrapper.exception

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_NOT_INITIALIZED
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * GLFW could not find support for the requested API on the system (`GLFW_API_UNAVAILABLE`).
 *
 * @param message human-readable description of the error
 * @param cause optional underlying cause
 */
@OptIn(ExperimentalForeignApi::class)
class ApiUnavailableGlfwException(
  message: String? = null,
  cause: Throwable? = null
) : GlfwException(message, cause, GLFW_NOT_INITIALIZED)
