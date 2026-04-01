package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_NO_API
import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_OPENGL_API
import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_OPENGL_ES_API
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Represents the client APIs supported by GLFW.
 *
 * @property glfwValue The corresponding numeric constant value for the client API as defined by GLFW.
 */
@OptIn(ExperimentalForeignApi::class)
enum class GlfwClientApi(
  override val glfwValue: Int
) : GlfwValue {
  /** Use the OpenGL client API. */
  OpenGL(GLFW_OPENGL_API),
  /** Use the OpenGL ES client API. */
  OpenGLES(GLFW_OPENGL_ES_API),
  /** Do not create an API context. */
  NoApi(GLFW_NO_API)
}
