package io.github.kotlinopenfoundation.glfw.wrapper.types

/**
 * Represents a rectangular region defined by its position and dimensions.
 *
 * Used to describe areas such as the monitor work area.
 *
 * @property x The x-coordinate of the upper-left corner.
 * @property y The y-coordinate of the upper-left corner.
 * @property width The width of the rectangle.
 * @property height The height of the rectangle.
 */
data class Bounds(
  val x: Int,
  val y: Int,
  val width: Int,
  val height: Int
)
