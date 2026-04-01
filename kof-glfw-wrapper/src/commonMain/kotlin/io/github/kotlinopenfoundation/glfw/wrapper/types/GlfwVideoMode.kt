package io.github.kotlinopenfoundation.glfw.wrapper.types

/**
 * Describes a [video mode](https://www.glfw.org/docs/3.4/structGLFWvidmode.html) of a monitor.
 *
 * @property width The width, in screen coordinates, of the video mode.
 * @property height The height, in screen coordinates, of the video mode.
 * @property redBits The bit depth of the red channel of the video mode.
 * @property greenBits The bit depth of the green channel of the video mode.
 * @property blueBits The bit depth of the blue channel of the video mode.
 * @property refreshRate The refresh rate, in Hz, of the video mode.
 * @since GLFW 1.0
 */
data class GlfwVideoMode(
  val width: Int,
  val height: Int,
  val redBits: Int,
  val greenBits: Int,
  val blueBits: Int,
  val refreshRate: Int
)
