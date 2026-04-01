package io.github.kotlinopenfoundation.glfw.wrapper.types

/**
 * A two-component vector with [Float] components.
 *
 * Used for values such as content scale factors.
 *
 * @property x The x component.
 * @property y The y component.
 */
data class FloatVector2(
  val x: Float,
  val y: Float
)
