package io.github.kotlinopenfoundation.glfw.cinterop

data class BitMapImage(
  val width: Int,
  val height: Int,
  val pixels: ByteArray
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || this::class != other::class) return false

    other as BitMapImage

    if (width != other.width) return false
    if (height != other.height) return false
    if (!pixels.contentEquals(other.pixels)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = width
    result = 31 * result + height
    result = 31 * result + pixels.contentHashCode()
    return result
  }
}

