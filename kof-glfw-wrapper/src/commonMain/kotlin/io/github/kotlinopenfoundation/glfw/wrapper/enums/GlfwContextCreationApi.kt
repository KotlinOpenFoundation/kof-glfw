package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_EGL_CONTEXT_API
import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_NATIVE_CONTEXT_API
import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_OSMESA_CONTEXT_API
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Represents the available context creation API options for initializing an OpenGL context with GLFW.
 *
 * @property glfwValue The corresponding GLFW constant associated with the context creation API.
 */
@OptIn(ExperimentalForeignApi::class)
enum class GlfwContextCreationApi(
  override val glfwValue: Int
) : GlfwValue {
  /** Use the native platform context creation API. */
  Native(GLFW_NATIVE_CONTEXT_API),
  /** Use the EGL context creation API. */
  EGL(GLFW_EGL_CONTEXT_API),
  /** Off-Screen rendering Mesa */
  OSMesa(GLFW_OSMESA_CONTEXT_API)
}
