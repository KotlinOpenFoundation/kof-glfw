package io.github.kotlinopenfoundation.glfw.wrapper.exception

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_INVALID_VALUE
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * One of the arguments to the function was an invalid value (`GLFW_INVALID_VALUE`).
 *
 * For example, requesting a non-existent OpenGL or OpenGL ES version like 2.7.
 *
 * @param message human-readable description of the error
 * @param cause optional underlying cause
 */
@OptIn(ExperimentalForeignApi::class)
class InvalidValueGlfwException(
  message: String? = null,
  cause: Throwable? = null
) : GlfwException(message, cause, GLFW_INVALID_VALUE)
