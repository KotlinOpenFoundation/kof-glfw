package io.github.kotlinopenfoundation.glfw.wrapper.types

/**
 * A two-component vector with [Int] components.
 *
 * Used for values such as window positions, sizes, and monitor physical dimensions.
 *
 * @property x The x component.
 * @property y The y component.
 */
data class IntVector2(
  val x: Int,
  val y: Int
)
