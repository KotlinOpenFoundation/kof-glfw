package io.github.kotlinopenfoundation.glfw.wrapper.enums

/**
 * Common interface for GLFW enum types that map to an integer constant.
 *
 * @property glfwValue The GLFW integer constant for this value.
 */
interface GlfwValue {
  val glfwValue: Int
}
