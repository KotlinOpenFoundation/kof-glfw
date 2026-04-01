package io.github.kotlinopenfoundation.glfw.wrapper.types

/**
 * The size, in screen coordinates, of the frame (window decorations) around the content area of a window.
 *
 * @property left The size of the left edge of the window frame.
 * @property top The size of the top edge of the window frame.
 * @property right The size of the right edge of the window frame.
 * @property bottom The size of the bottom edge of the window frame.
 * @since GLFW 3.1
 */
data class GlfwWindowFrameSize(
  val left: Int,
  val top: Int,
  val right: Int,
  val bottom: Int
)
