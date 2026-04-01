package io.github.kotlinopenfoundation.glfw.wrapper.exception

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_INVALID_ENUM
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * One of the arguments to the function was an invalid enum value (`GLFW_INVALID_ENUM`).
 *
 * @param message human-readable description of the error
 * @param cause optional underlying cause
 */
@OptIn(ExperimentalForeignApi::class)
class InvalidEnumValueGlfwException(
  message: String? = null,
  cause: Throwable? = null
) : GlfwException(message, cause, GLFW_INVALID_ENUM)
