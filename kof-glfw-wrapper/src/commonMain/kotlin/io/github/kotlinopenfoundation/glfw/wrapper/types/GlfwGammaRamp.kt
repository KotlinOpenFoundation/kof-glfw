package io.github.kotlinopenfoundation.glfw.wrapper.types

/**
 * Describes the [gamma ramp](https://www.glfw.org/docs/3.4/structGLFWgammaramp.html) for a monitor.
 *
 * Each channel (red, green, blue) contains [size] entries mapping input to output values.
 *
 * @property red The red channel values.
 * @property green The green channel values.
 * @property blue The blue channel values.
 * @since GLFW 3.0
 */
data class GlfwGammaRamp(
  val red: UShortArray,
  val green: UShortArray,
  val blue: UShortArray
) {
  init {
    require(red.size == green.size && green.size == blue.size) { "Gamma ramps must have the same number of channels" }
  }

  /** The number of entries in each channel. */
  val size: Int get() = red.size

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || this::class != other::class) return false

    other as GlfwGammaRamp

    if (!red.contentEquals(other.red)) return false
    if (!green.contentEquals(other.green)) return false
    if (!blue.contentEquals(other.blue)) return false
    if (size != other.size) return false

    return true
  }

  override fun hashCode(): Int {
    var result = red.contentHashCode()
    result = 31 * result + green.contentHashCode()
    result = 31 * result + blue.contentHashCode()
    result = 31 * result + size
    return result
  }
}
