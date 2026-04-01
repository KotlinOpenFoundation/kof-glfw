package io.github.kotlinopenfoundation.glfw.wrapper.types

import io.github.kotlinopenfoundation.glfw.wrapper.glfwGetError

/**
 * Represents an error retrieved from the GLFW library.
 *
 * This class encapsulates the error code and its corresponding description.
 *
 * @property code The error code representing the specific type of error encountered.
 * @property description A human-readable string describing the error.
 * @since 3.3
 *
 * @see glfwGetError
 */
data class GlfwError(val code: Int, val description: String)
