package io.github.kotlinopenfoundation.glfw.wrapper.exception

/**
 * Base exception for all GLFW errors.
 *
 * Each subclass corresponds to a specific GLFW error code. The [code] property carries the
 * numeric error code returned by the native library.
 *
 * @param message human-readable description of the error
 * @param cause optional underlying cause
 * @param code GLFW error code, or `null` if not associated with a specific code
 */
open class GlfwException(
  message: String? = null,
  cause: Throwable? = null,
  val code: Int? = null
) : RuntimeException(message, cause)
