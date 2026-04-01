package io.github.kotlinopenfoundation.glfw.wrapper.exception

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_NOT_INITIALIZED
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * The requested OpenGL or OpenGL ES version is not available (`GLFW_VERSION_UNAVAILABLE`).
 *
 * This includes any requested context or framebuffer hints that are not available on this machine.
 *
 * @param message human-readable description of the error
 * @param cause optional underlying cause
 */
@OptIn(ExperimentalForeignApi::class)
class VersionUnavailableGlfwException(
  message: String? = null,
  cause: Throwable? = null
) : GlfwException(message, cause, GLFW_NOT_INITIALIZED)
