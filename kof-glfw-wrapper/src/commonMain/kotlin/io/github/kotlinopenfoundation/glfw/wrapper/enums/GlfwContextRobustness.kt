package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_LOSE_CONTEXT_ON_RESET
import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_NO_RESET_NOTIFICATION
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Specifies the robustness strategy to be used by the context.
 *
 * @property glfwValue The GLFW constant for the robustness strategy.
 */
@OptIn(ExperimentalForeignApi::class)
enum class GlfwContextRobustness(
  override val glfwValue: Int
) : GlfwValue {
  /** No reset notification; the application will not be notified on context reset. */
  NoResetNotification(GLFW_NO_RESET_NOTIFICATION),
  /** The context is lost on a graphics reset. */
  LooseContextOnReset(GLFW_LOSE_CONTEXT_ON_RESET)
}
