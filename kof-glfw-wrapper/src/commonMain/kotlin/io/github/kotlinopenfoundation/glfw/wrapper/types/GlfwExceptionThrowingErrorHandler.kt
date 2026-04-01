package io.github.kotlinopenfoundation.glfw.wrapper.types

import io.github.kotlinopenfoundation.glfw.cinterop.*
import io.github.kotlinopenfoundation.glfw.wrapper.exception.*
import io.github.kotlinopenfoundation.glfw.wrapper.glfwSetErrorCallback
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.cinterop.ExperimentalForeignApi

private val logger = KotlinLogging.logger("GLFW")

/**
 * Handles GLFW errors by throwing corresponding exceptions.
 *
 * This ensures any GLFW-specific errors are immediately surfaced as exceptions,
 * allowing for better error handling in the application.
 *
 * **Reported errors are never fatal.**
 * As long as GLFW was successfully initialized,
 * it will remain initialized and in a safe state until terminated regardless of how many errors occur.
 * If an error occurs during initialization that causes [glfwInit] to fail,
 * any part of the library that was initialized will be safely terminated.
 */
object GlfwExceptionThrowingErrorHandler {
  /** Handles a GLFW error by throwing an appropriate exception if the error code is not `GLFW_NO_ERROR`. */
  @OptIn(ExperimentalForeignApi::class)
  fun handle(error: GlfwError) {
    logger.debug { "GLFW error ${error.code}: ${error.description}" }
    if (GLFW_NO_ERROR == error.code) {
      return
    }
    throw when (error.code) {
      GLFW_NOT_INITIALIZED -> NotInitializedGlfwException(error.description)
      GLFW_NO_CURRENT_CONTEXT -> NoCurrentContextGlfwException(error.description)
      GLFW_INVALID_ENUM -> InvalidEnumValueGlfwException(error.description)
      GLFW_INVALID_VALUE -> InvalidValueGlfwException(error.description)
      GLFW_OUT_OF_MEMORY -> OutOfMemoryGlfwException(error.description)
      GLFW_API_UNAVAILABLE -> ApiUnavailableGlfwException(error.description)
      GLFW_VERSION_UNAVAILABLE -> VersionUnavailableGlfwException(error.description)
      GLFW_PLATFORM_ERROR -> PlatformErrorGlfwException(error.description)
      GLFW_FORMAT_UNAVAILABLE -> FormatUnavailableGlfwException(error.description)
      GLFW_NO_WINDOW_CONTEXT -> NoWindowContextGlfwException(error.description)
      GLFW_CURSOR_UNAVAILABLE -> CursorUnavailableGlfwException(error.description)
      GLFW_FEATURE_UNAVAILABLE -> FeatureUnavailableGlfwException(error.description)
      GLFW_FEATURE_UNIMPLEMENTED -> FeatureNotImplementedGlfwException(error.description)
      GLFW_PLATFORM_UNAVAILABLE -> PlatformUnavailableGlfwException(error.description)
      else -> GlfwException(error.description, code = error.code)
    }
  }

  /** Registers this handler as a GLFW error callback. */
  fun register() {
    glfwSetErrorCallback(::handle)
  }
}
