package io.github.kotlinopenfoundation.glfw.wrapper.exception

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_PLATFORM_ERROR
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * A platform-specific error occurred that does not match any of the more specific categories
 * (`GLFW_PLATFORM_ERROR`).
 *
 * @param message human-readable description of the error
 * @param cause optional underlying cause
 */
@OptIn(ExperimentalForeignApi::class)
class PlatformErrorGlfwException(
  message: String? = null,
  cause: Throwable? = null
) : GlfwException(message, cause, GLFW_PLATFORM_ERROR)
