package io.github.kotlinopenfoundation.glfw.wrapper.exception

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_PLATFORM_UNAVAILABLE
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * No compatible platform was detected during initialization (`GLFW_PLATFORM_UNAVAILABLE`).
 *
 * @param message human-readable description of the error
 * @param cause optional underlying cause
 */
@OptIn(ExperimentalForeignApi::class)
class PlatformUnavailableGlfwException(
  message: String? = null,
  cause: Throwable? = null
) : GlfwException(message, cause, GLFW_PLATFORM_UNAVAILABLE)
