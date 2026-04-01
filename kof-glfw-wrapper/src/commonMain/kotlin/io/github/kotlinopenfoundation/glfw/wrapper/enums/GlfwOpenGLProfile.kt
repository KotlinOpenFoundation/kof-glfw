package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_OPENGL_COMPAT_PROFILE
import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_OPENGL_CORE_PROFILE
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Specifies which OpenGL profile to use for the context.
 *
 * @property glfwValue The GLFW constant for the OpenGL profile.
 */
@OptIn(ExperimentalForeignApi::class)
enum class GlfwOpenGLProfile(
  override val glfwValue: Int
) : GlfwValue {
  /** OpenGL core profile, which removes deprecated functionality. */
  Core(GLFW_OPENGL_CORE_PROFILE),
  /** OpenGL compatibility profile, which includes deprecated functionality. */
  Compat(GLFW_OPENGL_COMPAT_PROFILE)
}
