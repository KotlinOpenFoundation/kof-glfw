package io.github.kotlinopenfoundation.glfw.wrapper.hints

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_DONT_CARE
import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_FALSE
import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_TRUE
import io.github.kotlinopenfoundation.glfw.wrapper.enums.GlfwValue
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Maps a hint value to the corresponding GLFW integer.
 *
 * - `null` -> `GLFW_DONT_CARE`
 * - `Boolean` -> `GLFW_TRUE` / `GLFW_FALSE`
 * - `Int` -> direct value
 * - [GlfwValue] -> [GlfwValue.glfwValue]
 */
@OptIn(ExperimentalForeignApi::class)
internal fun mapToGlfwInt(value: Any?): Int = when (value) {
  null -> GLFW_DONT_CARE
  is Boolean -> if (value) GLFW_TRUE else GLFW_FALSE
  is Int -> value
  is GlfwValue -> value.glfwValue
  else -> error("Unsupported hint value type: ${value::class.simpleName}")
}
