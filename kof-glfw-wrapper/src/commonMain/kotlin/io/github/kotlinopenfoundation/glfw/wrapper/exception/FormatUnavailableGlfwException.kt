package io.github.kotlinopenfoundation.glfw.wrapper.exception

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_FORMAT_UNAVAILABLE
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * The requested pixel format is not supported, or the contents of the clipboard could not be
 * converted to the requested format (`GLFW_FORMAT_UNAVAILABLE`).
 *
 * @param message human-readable description of the error
 * @param cause optional underlying cause
 */
@OptIn(ExperimentalForeignApi::class)
class FormatUnavailableGlfwException(
  message: String? = null,
  cause: Throwable? = null
) : GlfwException(message, cause, GLFW_FORMAT_UNAVAILABLE)
