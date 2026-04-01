package io.github.kotlinopenfoundation.glfw.wrapper.types

/**
 * [Image data](https://www.glfw.org/docs/3.4/structGLFWimage.html) for use with GLFW functions such as window icon and cursor creation.
 *
 * The pixel data is 32-bit, little-endian, non-premultiplied RGBA (8 bits per channel)
 * with the pixels arranged in rows starting from the top-left corner.
 *
 * @property width The width of the image, in pixels.
 * @property height The height of the image, in pixels.
 * @property pixels The pixel data of the image, arranged left-to-right, top-to-bottom.
 * @since GLFW 2.1
 */
data class GlfwImage(
  val width: Int,
  val height: Int,
  val pixels: ByteArray
) {
  init {
    require(pixels.size == width * height * 4) {
      "Pixel data size must be width * height * 4 (RGBA), expected ${width * height * 4} but got ${pixels.size}"
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || this::class != other::class) return false
    other as GlfwImage
    return width == other.width && height == other.height && pixels.contentEquals(other.pixels)
  }

  override fun hashCode(): Int {
    var result = width
    result = 31 * result + height
    result = 31 * result + pixels.contentHashCode()
    return result
  }
}
