package io.github.kotlinopenfoundation.glfw.wrapper.exception

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_NOT_INITIALIZED
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * GLFW has not been initialized (`GLFW_NOT_INITIALIZED`).
 *
 * This occurs if a GLFW function was called that must not be called unless the library is initialized.
 *
 * @param message human-readable description of the error
 * @param cause optional underlying cause
 */
@OptIn(ExperimentalForeignApi::class)
class NotInitializedGlfwException(
  message: String? = null,
  cause: Throwable? = null
) : GlfwException(message, cause, GLFW_NOT_INITIALIZED)
