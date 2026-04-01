package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.*
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Mouse button identifiers.
 *
 * See the [GLFW mouse button documentation](https://www.glfw.org/docs/3.4/group__buttons.html) for details.
 *
 * @property glfwValue The GLFW constant for the mouse button.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
enum class GlfwMouseButton(val glfwValue: Int) {
  /** Mouse button 1 (left). */
  Button1(GLFW_MOUSE_BUTTON_1),
  /** Mouse button 2 (right). */
  Button2(GLFW_MOUSE_BUTTON_2),
  /** Mouse button 3 (middle). */
  Button3(GLFW_MOUSE_BUTTON_3),
  /** Mouse button 4. */
  Button4(GLFW_MOUSE_BUTTON_4),
  /** Mouse button 5. */
  Button5(GLFW_MOUSE_BUTTON_5),
  /** Mouse button 6. */
  Button6(GLFW_MOUSE_BUTTON_6),
  /** Mouse button 7. */
  Button7(GLFW_MOUSE_BUTTON_7),
  /** Mouse button 8. */
  Button8(GLFW_MOUSE_BUTTON_8);

  companion object {
    /** The left mouse button. Alias for [Button1]. */
    val Left: GlfwMouseButton = Button1
    /** The right mouse button. Alias for [Button2]. */
    val Right: GlfwMouseButton = Button2
    /** The middle mouse button. Alias for [Button3]. */
    val Middle: GlfwMouseButton = Button3
    /** The last valid mouse button. */
    val Last: GlfwMouseButton = Button8

    /** Returns the [GlfwMouseButton] for the given GLFW constant, or `null` if not found. */
    fun fromGlfwValue(value: Int): GlfwMouseButton? = entries.find { it.glfwValue == value }
  }
}
