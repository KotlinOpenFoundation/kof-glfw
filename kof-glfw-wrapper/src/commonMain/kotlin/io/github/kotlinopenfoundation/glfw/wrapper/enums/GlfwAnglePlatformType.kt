package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.*
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * ANGLE rendering backend init hint. Controls which ANGLE backend is used for OpenGL ES.
 *
 * Corresponds to the `GLFW_ANGLE_PLATFORM_TYPE` hint.
 *
 * @property glfwValue The GLFW constant for the ANGLE platform type.
 */
@OptIn(ExperimentalForeignApi::class)
enum class GlfwAnglePlatformType(
  override val glfwValue: Int
) : GlfwValue {
  /** No specific ANGLE platform type. */
  None(GLFW_ANGLE_PLATFORM_TYPE_NONE),
  /** Use the OpenGL backend. */
  OpenGL(GLFW_ANGLE_PLATFORM_TYPE_OPENGL),
  /** Use the OpenGL ES backend. */
  OpenGLES(GLFW_ANGLE_PLATFORM_TYPE_OPENGLES),
  /** Use the Direct3D 9 backend. */
  DirectX9(GLFW_ANGLE_PLATFORM_TYPE_D3D9),
  /** Use the Direct3D 11 backend. */
  DirectX11(GLFW_ANGLE_PLATFORM_TYPE_D3D11),
  /** Use the Vulkan backend. */
  Vulkan(GLFW_ANGLE_PLATFORM_TYPE_VULKAN),
  /** Use the Metal backend. */
  Metal(GLFW_ANGLE_PLATFORM_TYPE_METAL)
}
