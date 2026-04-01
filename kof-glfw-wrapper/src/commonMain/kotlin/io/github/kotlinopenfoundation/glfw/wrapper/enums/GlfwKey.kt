package io.github.kotlinopenfoundation.glfw.wrapper.enums

import io.github.kotlinopenfoundation.glfw.cinterop.*
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Keyboard key tokens.
 *
 * These key tokens are used to identify physical keys on the keyboard.
 * See the [GLFW key input documentation](https://www.glfw.org/docs/3.4/input_guide.html#input_key) for how these relate to key events.
 *
 * Named key tokens always correspond to the same physical key, regardless of the current keyboard layout.
 * Use [glfwGetKeyName] to get the character(s) produced by a key given the current layout.
 *
 * @property glfwValue The GLFW constant for the key.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
enum class GlfwKey(val glfwValue: Int) {
  /** An unknown key. */
  Unknown(GLFW_KEY_UNKNOWN),

  // Printable keys
  /** Space bar. */
  Space(GLFW_KEY_SPACE),
  /** Apostrophe ('). */
  Apostrophe(GLFW_KEY_APOSTROPHE),
  /** Comma (,). */
  Comma(GLFW_KEY_COMMA),
  /** Minus (-). */
  Minus(GLFW_KEY_MINUS),
  /** Period (.). */
  Period(GLFW_KEY_PERIOD),
  /** Slash (/). */
  Slash(GLFW_KEY_SLASH),
  /** Number 0. */
  Num0(GLFW_KEY_0),
  /** Number 1. */
  Num1(GLFW_KEY_1),
  /** Number 2. */
  Num2(GLFW_KEY_2),
  /** Number 3. */
  Num3(GLFW_KEY_3),
  /** Number 4. */
  Num4(GLFW_KEY_4),
  /** Number 5. */
  Num5(GLFW_KEY_5),
  /** Number 6. */
  Num6(GLFW_KEY_6),
  /** Number 7. */
  Num7(GLFW_KEY_7),
  /** Number 8. */
  Num8(GLFW_KEY_8),
  /** Number 9. */
  Num9(GLFW_KEY_9),
  /** Semicolon (;). */
  Semicolon(GLFW_KEY_SEMICOLON),
  /** Equal (=). */
  Equal(GLFW_KEY_EQUAL),
  /** Letter A. */
  A(GLFW_KEY_A),
  /** Letter B. */
  B(GLFW_KEY_B),
  /** Letter C. */
  C(GLFW_KEY_C),
  /** Letter D. */
  D(GLFW_KEY_D),
  /** Letter E. */
  E(GLFW_KEY_E),
  /** Letter F. */
  F(GLFW_KEY_F),
  /** Letter G. */
  G(GLFW_KEY_G),
  /** Letter H. */
  H(GLFW_KEY_H),
  /** Letter I. */
  I(GLFW_KEY_I),
  /** Letter J. */
  J(GLFW_KEY_J),
  /** Letter K. */
  K(GLFW_KEY_K),
  /** Letter L. */
  L(GLFW_KEY_L),
  /** Letter M. */
  M(GLFW_KEY_M),
  /** Letter N. */
  N(GLFW_KEY_N),
  /** Letter O. */
  O(GLFW_KEY_O),
  /** Letter P. */
  P(GLFW_KEY_P),
  /** Letter Q. */
  Q(GLFW_KEY_Q),
  /** Letter R. */
  R(GLFW_KEY_R),
  /** Letter S. */
  S(GLFW_KEY_S),
  /** Letter T. */
  T(GLFW_KEY_T),
  /** Letter U. */
  U(GLFW_KEY_U),
  /** Letter V. */
  V(GLFW_KEY_V),
  /** Letter W. */
  W(GLFW_KEY_W),
  /** Letter X. */
  X(GLFW_KEY_X),
  /** Letter Y. */
  Y(GLFW_KEY_Y),
  /** Letter Z. */
  Z(GLFW_KEY_Z),
  /** Left bracket (`[`). */
  LeftBracket(GLFW_KEY_LEFT_BRACKET),
  /** Backslash (`\`). */
  Backslash(GLFW_KEY_BACKSLASH),
  /** Right bracket (`]`). */
  RightBracket(GLFW_KEY_RIGHT_BRACKET),
  /** Grave accent (`). */
  GraveAccent(GLFW_KEY_GRAVE_ACCENT),
  /** Non-US #1. */
  World1(GLFW_KEY_WORLD_1),
  /** Non-US #2. */
  World2(GLFW_KEY_WORLD_2),

  // Function keys
  /** Escape key. */
  Escape(GLFW_KEY_ESCAPE),
  /** Enter/Return key. */
  Enter(GLFW_KEY_ENTER),
  /** Tab key. */
  Tab(GLFW_KEY_TAB),
  /** Backspace key. */
  Backspace(GLFW_KEY_BACKSPACE),
  /** Insert key. */
  Insert(GLFW_KEY_INSERT),
  /** Delete key. */
  Delete(GLFW_KEY_DELETE),
  /** Right arrow key. */
  Right(GLFW_KEY_RIGHT),
  /** Left arrow key. */
  Left(GLFW_KEY_LEFT),
  /** Down arrow key. */
  Down(GLFW_KEY_DOWN),
  /** Up arrow key. */
  Up(GLFW_KEY_UP),
  /** Page Up key. */
  PageUp(GLFW_KEY_PAGE_UP),
  /** Page Down key. */
  PageDown(GLFW_KEY_PAGE_DOWN),
  /** Home key. */
  Home(GLFW_KEY_HOME),
  /** End key. */
  End(GLFW_KEY_END),
  /** Caps Lock key. */
  CapsLock(GLFW_KEY_CAPS_LOCK),
  /** Scroll Lock key. */
  ScrollLock(GLFW_KEY_SCROLL_LOCK),
  /** Num Lock key. */
  NumLock(GLFW_KEY_NUM_LOCK),
  /** Print Screen key. */
  PrintScreen(GLFW_KEY_PRINT_SCREEN),
  /** Pause key. */
  Pause(GLFW_KEY_PAUSE),
  /** Function key F1. */
  F1(GLFW_KEY_F1),
  /** Function key F2. */
  F2(GLFW_KEY_F2),
  /** Function key F3. */
  F3(GLFW_KEY_F3),
  /** Function key F4. */
  F4(GLFW_KEY_F4),
  /** Function key F5. */
  F5(GLFW_KEY_F5),
  /** Function key F6. */
  F6(GLFW_KEY_F6),
  /** Function key F7. */
  F7(GLFW_KEY_F7),
  /** Function key F8. */
  F8(GLFW_KEY_F8),
  /** Function key F9. */
  F9(GLFW_KEY_F9),
  /** Function key F10. */
  F10(GLFW_KEY_F10),
  /** Function key F11. */
  F11(GLFW_KEY_F11),
  /** Function key F12. */
  F12(GLFW_KEY_F12),
  /** Function key F13. */
  F13(GLFW_KEY_F13),
  /** Function key F14. */
  F14(GLFW_KEY_F14),
  /** Function key F15. */
  F15(GLFW_KEY_F15),
  /** Function key F16. */
  F16(GLFW_KEY_F16),
  /** Function key F17. */
  F17(GLFW_KEY_F17),
  /** Function key F18. */
  F18(GLFW_KEY_F18),
  /** Function key F19. */
  F19(GLFW_KEY_F19),
  /** Function key F20. */
  F20(GLFW_KEY_F20),
  /** Function key F21. */
  F21(GLFW_KEY_F21),
  /** Function key F22. */
  F22(GLFW_KEY_F22),
  /** Function key F23. */
  F23(GLFW_KEY_F23),
  /** Function key F24. */
  F24(GLFW_KEY_F24),
  /** Function key F25. */
  F25(GLFW_KEY_F25),
  /** Keypad 0. */
  Kp0(GLFW_KEY_KP_0),
  /** Keypad 1. */
  Kp1(GLFW_KEY_KP_1),
  /** Keypad 2. */
  Kp2(GLFW_KEY_KP_2),
  /** Keypad 3. */
  Kp3(GLFW_KEY_KP_3),
  /** Keypad 4. */
  Kp4(GLFW_KEY_KP_4),
  /** Keypad 5. */
  Kp5(GLFW_KEY_KP_5),
  /** Keypad 6. */
  Kp6(GLFW_KEY_KP_6),
  /** Keypad 7. */
  Kp7(GLFW_KEY_KP_7),
  /** Keypad 8. */
  Kp8(GLFW_KEY_KP_8),
  /** Keypad 9. */
  Kp9(GLFW_KEY_KP_9),
  /** Keypad decimal. */
  KpDecimal(GLFW_KEY_KP_DECIMAL),
  /** Keypad divide. */
  KpDivide(GLFW_KEY_KP_DIVIDE),
  /** Keypad multiply. */
  KpMultiply(GLFW_KEY_KP_MULTIPLY),
  /** Keypad subtract. */
  KpSubtract(GLFW_KEY_KP_SUBTRACT),
  /** Keypad add. */
  KpAdd(GLFW_KEY_KP_ADD),
  /** Keypad Enter. */
  KpEnter(GLFW_KEY_KP_ENTER),
  /** Keypad equal. */
  KpEqual(GLFW_KEY_KP_EQUAL),
  /** Left Shift. */
  LeftShift(GLFW_KEY_LEFT_SHIFT),
  /** Left Control. */
  LeftControl(GLFW_KEY_LEFT_CONTROL),
  /** Left Alt. */
  LeftAlt(GLFW_KEY_LEFT_ALT),
  /** Left Super (Windows/Command). */
  LeftSuper(GLFW_KEY_LEFT_SUPER),
  /** Right Shift. */
  RightShift(GLFW_KEY_RIGHT_SHIFT),
  /** Right Control. */
  RightControl(GLFW_KEY_RIGHT_CONTROL),
  /** Right Alt. */
  RightAlt(GLFW_KEY_RIGHT_ALT),
  /** Right Super (Windows/Command). */
  RightSuper(GLFW_KEY_RIGHT_SUPER),
  /** Menu key. */
  Menu(GLFW_KEY_MENU);

  companion object {
    /** The last valid key token. Alias for [Menu]. */
    val Last: GlfwKey = Menu

    /** Returns the [GlfwKey] for the given GLFW constant, or `null` if not found. */
    fun fromGlfwValue(value: Int): GlfwKey? = entries.find { it.glfwValue == value }
  }
}
