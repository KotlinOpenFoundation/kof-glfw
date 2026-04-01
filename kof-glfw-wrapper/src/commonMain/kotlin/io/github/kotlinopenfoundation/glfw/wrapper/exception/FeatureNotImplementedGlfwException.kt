package io.github.kotlinopenfoundation.glfw.wrapper.exception

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_FEATURE_UNIMPLEMENTED
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * The requested feature has not yet been implemented in GLFW for this platform
 * (`GLFW_FEATURE_UNIMPLEMENTED`).
 *
 * @param message human-readable description of the error
 * @param cause optional underlying cause
 */
@OptIn(ExperimentalForeignApi::class)
class FeatureNotImplementedGlfwException(
  message: String? = null,
  cause: Throwable? = null
) : GlfwException(message, cause, GLFW_FEATURE_UNIMPLEMENTED)
