package io.github.kotlinopenfoundation.glfw.wrapper.exception

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_FEATURE_UNAVAILABLE
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * The platform does not provide the requested feature (`GLFW_FEATURE_UNAVAILABLE`).
 *
 * @param message human-readable description of the error
 * @param cause optional underlying cause
 */
@OptIn(ExperimentalForeignApi::class)
class FeatureUnavailableGlfwException(
  message: String? = null,
  cause: Throwable? = null
) : GlfwException(message, cause, GLFW_FEATURE_UNAVAILABLE)
