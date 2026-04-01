package io.github.kotlinopenfoundation.glfw.wrapper

import cnames.structs.GLFWwindow
import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_TRUE
import io.github.kotlinopenfoundation.glfw.cinterop.GLFWgamepadstate
import io.github.kotlinopenfoundation.glfw.cinterop.GLFWimage
import io.github.kotlinopenfoundation.glfw.wrapper.enums.*
import io.github.kotlinopenfoundation.glfw.wrapper.exception.*
import io.github.kotlinopenfoundation.glfw.wrapper.types.*
import kotlinx.cinterop.*
import kotlin.concurrent.AtomicReference

// ---------------------------------------------------------------------------
// Input reference
// [GLFW Reference](https://www.glfw.org/docs/3.4/group__input.html)
// ---------------------------------------------------------------------------

// -- Callback typealiases ---------------------------------------------------

/**
 * Callback for key events.
 *
 * @see glfwSetKeyCallback
 */
typealias GlfwKeyCallback = (window: GlfwWindowHandle, key: GlfwKey, scancode: Int, action: GlfwKeyAction, mods: GlfwModifierKey) -> Unit

/**
 * Callback for Unicode character input events.
 *
 * @see glfwSetCharCallback
 */
typealias GlfwCharCallback = (window: GlfwWindowHandle, codepoint: UInt) -> Unit

/**
 * Callback for mouse button events.
 *
 * @see glfwSetMouseButtonCallback
 */
typealias GlfwMouseButtonCallback = (window: GlfwWindowHandle, button: GlfwMouseButton, action: GlfwKeyAction, mods: GlfwModifierKey) -> Unit

/**
 * Callback for cursor position events.
 *
 * @see glfwSetCursorPosCallback
 */
typealias GlfwCursorPosCallback = (window: GlfwWindowHandle, xPos: Double, yPos: Double) -> Unit

/**
 * Callback for cursor enter/leave events.
 *
 * @see glfwSetCursorEnterCallback
 */
typealias GlfwCursorEnterCallback = (window: GlfwWindowHandle, entered: Boolean) -> Unit

/**
 * Callback for scroll events.
 *
 * @see glfwSetScrollCallback
 */
typealias GlfwScrollCallback = (window: GlfwWindowHandle, xOffset: Double, yOffset: Double) -> Unit

/**
 * Callback for file drop events.
 *
 * @see glfwSetDropCallback
 */
typealias GlfwDropCallback = (window: GlfwWindowHandle, paths: List<String>) -> Unit

/**
 * Callback for joystick connection and disconnection events.
 *
 * @see glfwSetJoystickCallback
 */
typealias GlfwJoystickCallback = (joystick: Joystick, event: GlfwConnectionEvent) -> Unit

// -- Callback stores --------------------------------------------------------

@OptIn(ExperimentalForeignApi::class)
private val keyCallbackStore = CallbackStore<GLFWwindow, GlfwKeyCallback>()
@OptIn(ExperimentalForeignApi::class)
private val charCallbackStore = CallbackStore<GLFWwindow, GlfwCharCallback>()
@OptIn(ExperimentalForeignApi::class)
private val mouseButtonCallbackStore = CallbackStore<GLFWwindow, GlfwMouseButtonCallback>()
@OptIn(ExperimentalForeignApi::class)
private val cursorPosCallbackStore = CallbackStore<GLFWwindow, GlfwCursorPosCallback>()
@OptIn(ExperimentalForeignApi::class)
private val cursorEnterCallbackStore = CallbackStore<GLFWwindow, GlfwCursorEnterCallback>()
@OptIn(ExperimentalForeignApi::class)
private val scrollCallbackStore = CallbackStore<GLFWwindow, GlfwScrollCallback>()
@OptIn(ExperimentalForeignApi::class)
private val dropCallbackStore = CallbackStore<GLFWwindow, GlfwDropCallback>()

@OptIn(kotlin.experimental.ExperimentalNativeApi::class)
private val joystickCallbackReference = AtomicReference<GlfwJoystickCallback?>(null)

// -- Input mode -------------------------------------------------------------

/**
 * Returns the value of an input mode of the specified window.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to query.
 * @param mode The input mode whose value to return.
 * @return The current value of the input mode.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidEnumValueGlfwException If the mode is not a valid input mode.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetInputMode(window: GlfwWindowHandle, mode: GlfwInputMode): Int =
  io.github.kotlinopenfoundation.glfw.cinterop.glfwGetInputMode(window.pointer, mode.glfwValue)

/**
 * Sets an input mode option for the specified window.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose input mode to set.
 * @param mode The input mode to set.
 * @param value The new value of the input mode.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidEnumValueGlfwException If the mode is not a valid input mode.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetInputMode(window: GlfwWindowHandle, mode: GlfwInputMode, value: Int) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetInputMode(window.pointer, mode.glfwValue, value)
}

/**
 * Returns whether raw mouse motion is supported on the current system.
 *
 * This function must only be called from the main thread.
 *
 * @return `true` if raw mouse motion is supported.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwRawMouseMotionSupported(): Boolean =
  GLFW_TRUE == io.github.kotlinopenfoundation.glfw.cinterop.glfwRawMouseMotionSupported()

// -- Keyboard ---------------------------------------------------------------

/**
 * Returns the layout-specific name of the specified printable key.
 *
 * If the key is `GLFW_KEY_UNKNOWN`, the scancode is used to identify the key, otherwise the scancode is ignored.
 *
 * This function must only be called from the main thread.
 *
 * @param key The key to query, or [GlfwKey.Unknown] for scancode lookup.
 * @param scancode The scancode of the key to query.
 * @return The UTF-8 encoded, layout-specific name of the key, or `null` if the key is not printable.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.2
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetKeyName(key: GlfwKey, scancode: Int): String? =
  io.github.kotlinopenfoundation.glfw.cinterop.glfwGetKeyName(key.glfwValue, scancode)?.toKString()

/**
 * Returns the platform-specific scancode of the specified key.
 *
 * If the specified key token corresponds to a physical key not supported on the current platform,
 * this function returns -1.
 *
 * This function may be called from any thread.
 *
 * @param key The key to query.
 * @return The platform-specific scancode for the key, or -1 if the key is not supported.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidEnumValueGlfwException If the key is not a valid key token.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetKeyScancode(key: GlfwKey): Int =
  io.github.kotlinopenfoundation.glfw.cinterop.glfwGetKeyScancode(key.glfwValue)

/**
 * Returns the last state reported for the specified key to the specified window.
 *
 * The returned state is one of [GlfwKeyAction.Press] or [GlfwKeyAction.Release].
 *
 * The higher-level action [GlfwKeyAction.Repeat] is only reported to the key callback.
 *
 * This function must only be called from the main thread.
 *
 * @param window The desired window.
 * @param key The desired key.
 * @return The last key action.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidEnumValueGlfwException If the key is not a valid key token.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetKey(window: GlfwWindowHandle, key: GlfwKey): GlfwKeyAction =
  GlfwKeyAction.fromGlfwValue(io.github.kotlinopenfoundation.glfw.cinterop.glfwGetKey(window.pointer, key.glfwValue))
    ?: GlfwKeyAction.Release

// -- Mouse ------------------------------------------------------------------

/**
 * Returns the last state reported for the specified mouse button to the specified window.
 *
 * This function must only be called from the main thread.
 *
 * @param window The desired window.
 * @param button The desired mouse button.
 * @return The last button action.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidEnumValueGlfwException If the button is not a valid mouse button.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetMouseButton(window: GlfwWindowHandle, button: GlfwMouseButton): GlfwKeyAction =
  GlfwKeyAction.fromGlfwValue(
    io.github.kotlinopenfoundation.glfw.cinterop.glfwGetMouseButton(window.pointer, button.glfwValue)
  ) ?: GlfwKeyAction.Release

/**
 * Retrieves the position of the cursor, in screen coordinates, relative to the upper-left
 * corner of the content area of the specified window.
 *
 * If the cursor is disabled, the cursor position is unbounded and limited only by
 * the minimum and maximum values of a `Double`.
 *
 * This function must only be called from the main thread.
 *
 * @param window The desired window.
 * @return The cursor position as (x, y).
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetCursorPos(window: GlfwWindowHandle): Pair<Double, Double> = memScoped {
  val x = alloc<DoubleVar>()
  val y = alloc<DoubleVar>()
  io.github.kotlinopenfoundation.glfw.cinterop.glfwGetCursorPos(window.pointer, x.ptr, y.ptr)
  Pair(x.value, y.value)
}

/**
 * Sets the position of the cursor, in screen coordinates, relative to the upper-left
 * corner of the content area of the specified window.
 *
 * The window must have input focus. If the window does not have input focus when this function is called,
 * it fails silently.
 *
 * This function must only be called from the main thread.
 *
 * @param window The desired window.
 * @param xPos The desired x-coordinate, relative to the left edge of the content area.
 * @param yPos The desired y-coordinate, relative to the top edge of the content area.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @throws FeatureUnavailableGlfwException If the platform does not support it.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetCursorPos(window: GlfwWindowHandle, xPos: Double, yPos: Double) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetCursorPos(window.pointer, xPos, yPos)
}

// -- Cursor -----------------------------------------------------------------

/**
 * Creates a new custom cursor image that can be set for a window with [glfwSetCursor].
 *
 * The cursor can be destroyed with [glfwDestroyCursor]. Any remaining cursors are destroyed by [glfwTerminate].
 *
 * The pixels are 32-bit, little-endian, non-premultiplied RGBA.
 * The cursor hotspot is specified in pixels, relative to the upper-left corner of the cursor image.
 *
 * This function must only be called from the main thread.
 *
 * @param image The desired cursor image.
 * @param xHotSpot The desired x-coordinate, in pixels, of the cursor hotspot.
 * @param yHotSpot The desired y-coordinate, in pixels, of the cursor hotspot.
 * @return The handle of the created cursor.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.1
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwCreateCursor(image: GlfwImage, xHotSpot: Int, yHotSpot: Int): GlfwCursorHandle {
  val pinned = image.pixels.pin()
  try {
    return memScoped {
      val cImage = alloc<GLFWimage>()
      cImage.width = image.width
      cImage.height = image.height
      cImage.pixels = pinned.addressOf(0).reinterpret()
      val cursor = io.github.kotlinopenfoundation.glfw.cinterop.glfwCreateCursor(cImage.ptr, xHotSpot, yHotSpot)
        ?: throw PlatformErrorGlfwException("Failed to create cursor")
      GlfwCursorHandle(cursor)
    }
  } finally {
    pinned.unpin()
  }
}

/**
 * Creates a cursor with a standard shape that can be set for a window with [glfwSetCursor].
 *
 * The images for these cursors come from the system cursor theme, and their exact appearance will vary
 * between platforms.
 *
 * This function must only be called from the main thread.
 *
 * @param shape One of the standard cursor shapes.
 * @return The handle of the created cursor.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidEnumValueGlfwException If the shape is not a valid standard cursor shape.
 * @throws CursorUnavailableGlfwException If the cursor shape is not available.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.1
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwCreateStandardCursor(shape: GlfwStandardCursorShape): GlfwCursorHandle {
  val cursor = io.github.kotlinopenfoundation.glfw.cinterop.glfwCreateStandardCursor(shape.glfwValue)
    ?: throw PlatformErrorGlfwException("Failed to create standard cursor")
  return GlfwCursorHandle(cursor)
}

/**
 * Destroys a cursor previously created with [glfwCreateCursor] or [glfwCreateStandardCursor].
 *
 * Any remaining cursors will be destroyed by [glfwTerminate].
 *
 * If the specified cursor is current for any window, that window will be reverted to the default cursor.
 * This does not affect the cursor mode.
 *
 * This function must only be called from the main thread.
 * This function must not be called from a callback.
 *
 * @param cursor The cursor to destroy.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.1
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwDestroyCursor(cursor: GlfwCursorHandle) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwDestroyCursor(cursor.pointer)
}

/**
 * Sets the cursor image to be used when the cursor is over the content area of the specified window.
 *
 * The set cursor will only be visible when the cursor mode of the window is [GlfwCursorMode.Normal].
 *
 * On some platforms, the set cursor may not be visible unless the window also has input focus.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to set the cursor for.
 * @param cursor The cursor to set, or `null` to switch back to the default arrow cursor.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.1
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetCursor(window: GlfwWindowHandle, cursor: GlfwCursorHandle?) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetCursor(window.pointer, cursor?.pointer)
}

// -- Input callbacks --------------------------------------------------------

/**
 * Sets the key callback of the specified window, which is called when a key is pressed,
 * repeated, or released.
 *
 * The key functions deal with physical keys, with layout-independent key tokens named after their
 * values in the standard US keyboard layout.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose callback to set.
 * @param callback The new key callback, or `null` to remove the currently set callback.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetKeyCallback(window: GlfwWindowHandle, callback: GlfwKeyCallback?) {
  if (callback == null) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetKeyCallback(window.pointer, null)
    keyCallbackStore.remove(window.pointer)
    return
  }
  keyCallbackStore[window.pointer] = callback
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetKeyCallback(
    window.pointer,
    staticCFunction { win, key, scancode, action, mods ->
      val cb = keyCallbackStore[win!!] ?: return@staticCFunction
      val glfwKey = GlfwKey.fromGlfwValue(key) ?: GlfwKey.Unknown
      val glfwAction = GlfwKeyAction.fromGlfwValue(action) ?: return@staticCFunction
      cb(GlfwWindowHandle(win), glfwKey, scancode, glfwAction, GlfwModifierKey(mods))
    }
  )
}

/**
 * Sets the Unicode character callback of the specified window, which is called when a
 * Unicode character is input.
 *
 * The character callback is intended for Unicode text input. It behaves like a text input
 * callback that returns the character itself rather than a key token.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose callback to set.
 * @param callback The new callback, or `null` to remove the currently set callback.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 2.4
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetCharCallback(window: GlfwWindowHandle, callback: GlfwCharCallback?) {
  if (callback == null) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetCharCallback(window.pointer, null)
    charCallbackStore.remove(window.pointer)
    return
  }
  charCallbackStore[window.pointer] = callback
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetCharCallback(
    window.pointer,
    staticCFunction { win, codepoint ->
      charCallbackStore[win!!]?.invoke(GlfwWindowHandle(win), codepoint)
    }
  )
}

/**
 * Sets the mouse button callback of the specified window, which is called when a
 * mouse button is pressed or released.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose callback to set.
 * @param callback The new callback, or `null` to remove the currently set callback.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetMouseButtonCallback(window: GlfwWindowHandle, callback: GlfwMouseButtonCallback?) {
  if (callback == null) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetMouseButtonCallback(window.pointer, null)
    mouseButtonCallbackStore.remove(window.pointer)
    return
  }
  mouseButtonCallbackStore[window.pointer] = callback
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetMouseButtonCallback(
    window.pointer,
    staticCFunction { win, button, action, mods ->
      val cb = mouseButtonCallbackStore[win!!] ?: return@staticCFunction
      val glfwButton = GlfwMouseButton.fromGlfwValue(button) ?: return@staticCFunction
      val glfwAction = GlfwKeyAction.fromGlfwValue(action) ?: return@staticCFunction
      cb(GlfwWindowHandle(win), glfwButton, glfwAction, GlfwModifierKey(mods))
    }
  )
}

/**
 * Sets the cursor position callback of the specified window, which is called
 * when the cursor is moved.
 *
 * The callback is provided with the position, in screen coordinates, relative to
 * the upper-left corner of the content area of the window.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose callback to set.
 * @param callback The new callback, or `null` to remove the currently set callback.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetCursorPosCallback(window: GlfwWindowHandle, callback: GlfwCursorPosCallback?) {
  if (callback == null) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetCursorPosCallback(window.pointer, null)
    cursorPosCallbackStore.remove(window.pointer)
    return
  }
  cursorPosCallbackStore[window.pointer] = callback
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetCursorPosCallback(
    window.pointer,
    staticCFunction { win, xPos, yPos ->
      cursorPosCallbackStore[win!!]?.invoke(GlfwWindowHandle(win), xPos, yPos)
    }
  )
}

/**
 * Sets the cursor enter/leave callback of the specified window, which is called
 * when the cursor enters or leaves the content area of the window.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose callback to set.
 * @param callback The new callback, or `null` to remove the currently set callback.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetCursorEnterCallback(window: GlfwWindowHandle, callback: GlfwCursorEnterCallback?) {
  if (callback == null) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetCursorEnterCallback(window.pointer, null)
    cursorEnterCallbackStore.remove(window.pointer)
    return
  }
  cursorEnterCallbackStore[window.pointer] = callback
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetCursorEnterCallback(
    window.pointer,
    staticCFunction { win, entered ->
      cursorEnterCallbackStore[win!!]?.invoke(GlfwWindowHandle(win), entered == GLFW_TRUE)
    }
  )
}

/**
 * Sets the scroll callback of the specified window, which is called when a
 * scrolling device is used, such as a mouse wheel or scrolling area of a touchpad.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose callback to set.
 * @param callback The new callback, or `null` to remove the currently set callback.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetScrollCallback(window: GlfwWindowHandle, callback: GlfwScrollCallback?) {
  if (callback == null) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetScrollCallback(window.pointer, null)
    scrollCallbackStore.remove(window.pointer)
    return
  }
  scrollCallbackStore[window.pointer] = callback
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetScrollCallback(
    window.pointer,
    staticCFunction { win, xOffset, yOffset ->
      scrollCallbackStore[win!!]?.invoke(GlfwWindowHandle(win), xOffset, yOffset)
    }
  )
}

/**
 * Sets the file drop callback of the specified window, which is called when one or more
 * dragged paths are dropped on the window.
 *
 * The path array and its strings are valid only until the callback returns.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose callback to set.
 * @param callback The new callback, or `null` to remove the currently set callback.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.1
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetDropCallback(window: GlfwWindowHandle, callback: GlfwDropCallback?) {
  if (callback == null) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetDropCallback(window.pointer, null)
    dropCallbackStore.remove(window.pointer)
    return
  }
  dropCallbackStore[window.pointer] = callback
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetDropCallback(
    window.pointer,
    staticCFunction { win, count, paths ->
      val cb = dropCallbackStore[win!!] ?: return@staticCFunction
      val pathList = (0 until count).map { paths!![it]!!.toKString() }
      cb(GlfwWindowHandle(win), pathList)
    }
  )
}

// -- Joystick ---------------------------------------------------------------

/**
 * Returns whether the specified joystick is present.
 *
 * There is no need to call this function before other functions that accept a joystick ID,
 * as they all check for presence before performing any other work.
 *
 * This function must only be called from the main thread.
 *
 * @param joystick The joystick to query.
 * @return `true` if the joystick is present.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidEnumValueGlfwException If the joystick ID is not valid.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwJoystickPresent(joystick: Joystick): Boolean =
  GLFW_TRUE == io.github.kotlinopenfoundation.glfw.cinterop.glfwJoystickPresent(joystick.glfwValue)

/**
 * Returns the values of all axes of the specified joystick.
 *
 * Each element in the returned list is a value between -1.0 and 1.0.
 *
 * If the specified joystick is not present, this function will return an empty list.
 *
 * This function must only be called from the main thread.
 *
 * @param joystick The joystick to query.
 * @return The list of axis values.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidEnumValueGlfwException If the joystick ID is not valid.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetJoystickAxes(joystick: Joystick): List<Float> = memScoped {
  val count = alloc<IntVar>()
  val axes = io.github.kotlinopenfoundation.glfw.cinterop.glfwGetJoystickAxes(joystick.glfwValue, count.ptr)
    ?: return emptyList()
  (0 until count.value).map { axes[it] }
}

/**
 * Returns the state of all buttons of the specified joystick.
 *
 * Each element in the returned list is either [JoystickButtonState.Pressed] or
 * [JoystickButtonState.Released].
 *
 * If the specified joystick is not present, this function will return an empty list.
 *
 * This function must only be called from the main thread.
 *
 * @param joystick The joystick to query.
 * @return The list of button states.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidEnumValueGlfwException If the joystick ID is not valid.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 2.2
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetJoystickButtons(joystick: Joystick): List<JoystickButtonState> {
  val buttonStates = memScoped {
    val count = alloc<IntVar>()
    val buttons = io.github.kotlinopenfoundation.glfw.cinterop.glfwGetJoystickButtons(joystick.glfwValue, count.ptr)
      ?: return emptyList()
    buttons.readBytes(count.value)
  }
  return buttonStates.map { state -> JoystickButtonState.entries.first { it.glfwValue == state.toInt() } }
}

/**
 * Returns the state of all hats of the specified joystick.
 *
 * Hat values use bitmask constants for directional combinations.
 *
 * If the specified joystick is not present, this function will return an empty list.
 *
 * This function must only be called from the main thread.
 *
 * @param joystick The joystick to query.
 * @return The list of hat states.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidEnumValueGlfwException If the joystick ID is not valid.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetJoystickHats(joystick: Joystick): List<JoystickHatState> = memScoped {
  val count = alloc<IntVar>()
  val hats = io.github.kotlinopenfoundation.glfw.cinterop.glfwGetJoystickHats(joystick.glfwValue, count.ptr)
    ?: return emptyList()
  (0 until count.value).map { JoystickHatState(hats[it]) }
}

/**
 * Returns the name, encoded as UTF-8, of the specified joystick.
 *
 * If the specified joystick is not present, this function will return `null`.
 *
 * This function must only be called from the main thread.
 *
 * @param joystick The joystick to query.
 * @return The UTF-8 encoded name of the joystick, or `null` if the joystick is not present.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidEnumValueGlfwException If the joystick ID is not valid.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetJoystickName(joystick: Joystick): String? =
  io.github.kotlinopenfoundation.glfw.cinterop.glfwGetJoystickName(joystick.glfwValue)?.toKString()

/**
 * Returns the SDL compatible GUID, as a UTF-8 encoded hexadecimal string, of the specified joystick.
 *
 * The GUID is what connects a joystick to a gamepad mapping.
 *
 * If the specified joystick is not present, this function will return `null`.
 *
 * This function must only be called from the main thread.
 *
 * @param joystick The joystick to query.
 * @return The UTF-8 encoded GUID of the joystick, or `null` if the joystick is not present or has no GUID.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidEnumValueGlfwException If the joystick ID is not valid.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetJoystickGUID(joystick: Joystick): String? =
  io.github.kotlinopenfoundation.glfw.cinterop.glfwGetJoystickGUID(joystick.glfwValue)?.toKString()

/**
 * Sets the user pointer of the specified joystick.
 *
 * The current value is retained until the joystick is disconnected. The initial value is `null`.
 *
 * This function may be called from any thread. Access is not synchronized.
 *
 * @param joystick The joystick whose pointer to set.
 * @param pointer The new value.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetJoystickUserPointer(joystick: Joystick, pointer: CPointer<*>?) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetJoystickUserPointer(joystick.glfwValue, pointer)
}

/**
 * Returns the current value of the user pointer of the specified joystick.
 *
 * This function may be called from any thread. Access is not synchronized.
 *
 * @param joystick The joystick whose pointer to return.
 * @return The user pointer, or `null` if not set.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetJoystickUserPointer(joystick: Joystick): CPointer<*>? =
  io.github.kotlinopenfoundation.glfw.cinterop.glfwGetJoystickUserPointer(joystick.glfwValue)

/**
 * Returns whether the specified joystick is both present and has a gamepad mapping.
 *
 * If the specified joystick is present but does not have a gamepad mapping, this function will return `false`
 * but will not generate an error.
 *
 * This function must only be called from the main thread.
 *
 * @param joystick The joystick to query.
 * @return `true` if the joystick has a gamepad mapping.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidEnumValueGlfwException If the joystick ID is not valid.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwJoystickIsGamepad(joystick: Joystick): Boolean =
  GLFW_TRUE == io.github.kotlinopenfoundation.glfw.cinterop.glfwJoystickIsGamepad(joystick.glfwValue)

/**
 * Sets the joystick configuration callback, which is called when a joystick is connected to
 * or disconnected from the system.
 *
 * For joystick connection and disconnection events to be delivered on all platforms,
 * you need to call one of the event processing functions.
 *
 * This function must only be called from the main thread.
 *
 * @param callback The new callback, or `null` to remove the currently set callback.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.2
 */
@OptIn(ExperimentalForeignApi::class, kotlin.experimental.ExperimentalNativeApi::class)
fun glfwSetJoystickCallback(callback: GlfwJoystickCallback?) {
  joystickCallbackReference.value = callback
  if (callback == null) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetJoystickCallback(null)
    return
  }
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetJoystickCallback(
    staticCFunction { jid, event ->
      val cb = joystickCallbackReference.value ?: return@staticCFunction
      val joystick = Joystick.entries.find { it.glfwValue == jid } ?: return@staticCFunction
      val connectionEvent = GlfwConnectionEvent.fromGlfwValue(event) ?: return@staticCFunction
      cb(joystick, connectionEvent)
    }
  )
}

// -- Gamepad ----------------------------------------------------------------

/**
 * Parses the specified ASCII encoded string and updates the internal list with any gamepad
 * mappings it finds.
 *
 * This string may contain either a single gamepad mapping or many mappings separated by newlines.
 *
 * If there is already a gamepad mapping for a given GUID in the internal list, it will be replaced.
 *
 * This function must only be called from the main thread.
 *
 * @param string The string containing the gamepad mappings.
 * @return `true` if successful.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidValueGlfwException If the string format is invalid.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwUpdateGamepadMappings(string: String): Boolean =
  GLFW_TRUE == io.github.kotlinopenfoundation.glfw.cinterop.glfwUpdateGamepadMappings(string)

/**
 * Returns the human-readable name of the gamepad from the gamepad mapping assigned to
 * the specified joystick.
 *
 * If the specified joystick is not present or does not have a gamepad mapping, this function
 * will return `null`.
 *
 * This function must only be called from the main thread.
 *
 * @param joystick The joystick to query.
 * @return The UTF-8 encoded name of the gamepad, or `null` if the joystick is not present or has no mapping.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidEnumValueGlfwException If the joystick ID is not valid.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetGamepadName(joystick: Joystick): String? =
  io.github.kotlinopenfoundation.glfw.cinterop.glfwGetGamepadName(joystick.glfwValue)?.toKString()

/**
 * Retrieves the state of the specified joystick remapped to an Xbox-like gamepad.
 *
 * If the specified joystick is not present or does not have a gamepad mapping, this function will
 * return `null`.
 *
 * This function must only be called from the main thread.
 *
 * @param joystick The joystick to query.
 * @return The gamepad state, or `null` if the joystick has no mapping.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidEnumValueGlfwException If the joystick ID is not valid.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetGamepadState(joystick: Joystick): GlfwGamepadState? = memScoped {
  val state = alloc<GLFWgamepadstate>()
  val success = io.github.kotlinopenfoundation.glfw.cinterop.glfwGetGamepadState(joystick.glfwValue, state.ptr)
  if (GLFW_TRUE != success) return null
  val buttons = (0 until 15).map { GlfwGamepadButtonState.fromGlfwValue(state.buttons[it].toInt()) ?: GlfwGamepadButtonState.Released }
  val axes = (0 until 6).map { state.axes[it] }
  GlfwGamepadState(buttons, axes)
}

// -- Clipboard --------------------------------------------------------------

/**
 * Sets the system clipboard to the specified, UTF-8 encoded string.
 *
 * This function must only be called from the main thread.
 *
 * @param string The UTF-8 encoded string.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetClipboardString(string: String) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetClipboardString(null, string)
}

/**
 * Returns the contents of the system clipboard if it contains or is convertible to a UTF-8 encoded string.
 *
 * This function must only be called from the main thread.
 *
 * @return The contents of the clipboard as a UTF-8 encoded string, or `null` if the clipboard is empty or unavailable.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws FormatUnavailableGlfwException If the clipboard content could not be converted to a string.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetClipboardString(): String? =
  io.github.kotlinopenfoundation.glfw.cinterop.glfwGetClipboardString(null)?.toKString()

// -- Time -------------------------------------------------------------------

/**
 * Returns the current GLFW time, in seconds.
 *
 * Unless the time has been set using [glfwSetTime], measures elapsed time since GLFW was initialized.
 * Uses the highest-resolution monotonic time source on each platform.
 *
 * This function may be called from any thread.
 *
 * @return The current time, in seconds.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 1.0
 *
 * @see glfwSetTime
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetTime(): Double =
  io.github.kotlinopenfoundation.glfw.cinterop.glfwGetTime()

/**
 * Sets the current GLFW time, in seconds.
 *
 * The value must be a positive finite number less than or equal to 18446744073.0 (approximately 584.5 years).
 *
 * This function may be called from any thread but is not atomic with respect to [glfwGetTime].
 *
 * @param time The new value, in seconds.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidValueGlfwException If the time value is invalid.
 * @since GLFW 2.2
 *
 * @see glfwGetTime
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetTime(time: Double) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetTime(time)
}

/**
 * Returns the current value of the raw timer.
 *
 * This function returns the current value of the raw timer, measured in 1 / frequency seconds.
 * To get the frequency, call [glfwGetTimerFrequency].
 *
 * This function may be called from any thread.
 *
 * @return The value of the timer.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.2
 *
 * @see glfwGetTimerFrequency
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetTimerValue(): ULong =
  io.github.kotlinopenfoundation.glfw.cinterop.glfwGetTimerValue()

/**
 * Returns the frequency, in Hz, of the raw timer.
 *
 * This function may be called from any thread.
 *
 * @return The frequency of the timer, in Hz.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.2
 *
 * @see glfwGetTimerValue
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetTimerFrequency(): ULong =
  io.github.kotlinopenfoundation.glfw.cinterop.glfwGetTimerFrequency()
