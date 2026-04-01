package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_RELEASE_BEHAVIOR_FLUSH
import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_RELEASE_BEHAVIOR_NONE
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Specifies the release behavior to be used by the context.
 *
 * @property glfwValue The GLFW constant for the release behavior.
 */
@OptIn(ExperimentalForeignApi::class)
enum class GlfwContextReleaseBehavior(
  override val glfwValue: Int
) : GlfwValue {
  /** The pipeline is flushed when the context is released. */
  Flush(GLFW_RELEASE_BEHAVIOR_FLUSH),
  /** The pipeline is not flushed on release. */
  None(GLFW_RELEASE_BEHAVIOR_NONE)
}
