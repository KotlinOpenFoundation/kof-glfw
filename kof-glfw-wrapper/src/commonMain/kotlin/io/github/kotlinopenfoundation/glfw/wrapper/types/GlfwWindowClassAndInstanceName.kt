package io.github.kotlinopenfoundation.glfw.wrapper.types

/**
 * Represents the class name and instance name associated with an X11 GLFW window.
 *
 * Both properties must be set to non-empty string to have an effect.
 *
 * @property className The name of the window class.
 * @property instanceName The name of the window instance.
 */
data class GlfwWindowClassAndInstanceName(
  val className: String,
  val instanceName: String
) {
  /** Returns `true` if either [className] or [instanceName] is blank. */
  fun isBlank(): Boolean = className.isBlank() || instanceName.isBlank()
}
