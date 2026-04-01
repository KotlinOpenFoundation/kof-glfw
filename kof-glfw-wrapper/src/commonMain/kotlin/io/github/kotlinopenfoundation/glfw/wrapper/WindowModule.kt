package io.github.kotlinopenfoundation.glfw.wrapper

import cnames.structs.GLFWwindow
import io.github.kotlinopenfoundation.glfw.cinterop.*
import io.github.kotlinopenfoundation.glfw.wrapper.exception.*
import io.github.kotlinopenfoundation.glfw.wrapper.types.*
import io.github.kotlinopenfoundation.glfw.wrapper.hints.GlfwWindowHint
import io.github.kotlinopenfoundation.glfw.wrapper.hints.mapToGlfwInt
import kotlinx.cinterop.*
import kotlin.time.Duration
import kotlin.time.DurationUnit

// ---------------------------------------------------------------------------
// Window reference
// [GLFW Reference](https://www.glfw.org/docs/3.4/group__window.html)
// ---------------------------------------------------------------------------

// -- Types ------------------------------------------------------------------

/**
 * Represents a GLFW window attribute with a type-safe value mapper.
 *
 * @param T the Kotlin type that the raw GLFW integer attribute value is mapped to.
 * @property glfwValue the GLFW constant identifying this attribute.
 * @property valueMapper function that converts the raw integer value to [T].
 */
data class GlfwWindowAttribute<T>(
  val glfwValue: Int,
  val valueMapper: (Int) -> T
)

/**
 * Predefined GLFW window attributes for use with [glfwGetWindowAttrib].
 */
@OptIn(ExperimentalForeignApi::class)
object GlfwWindowAttributes {
  /** Whether the window is visible. */
  val visible = GlfwWindowAttribute(GLFW_VISIBLE, GLFW_TRUE::equals)

  /** Whether the window has input focus. */
  val focused = GlfwWindowAttribute(GLFW_FOCUSED, GLFW_TRUE::equals)

  /** Whether the window is iconified (minimized). */
  val iconified = GlfwWindowAttribute(GLFW_ICONIFIED, GLFW_TRUE::equals)

  /** Whether the window is maximized. */
  val maximized = GlfwWindowAttribute(GLFW_MAXIMIZED, GLFW_TRUE::equals)

  /** Whether the cursor is hovering over the content area. */
  val hovered = GlfwWindowAttribute(GLFW_HOVERED, GLFW_TRUE::equals)

  /** Whether the window is resizable by the user. */
  val resizable = GlfwWindowAttribute(GLFW_RESIZABLE, GLFW_TRUE::equals)

  /** Whether the window has decorations (title bar, border, etc.). */
  val decorated = GlfwWindowAttribute(GLFW_DECORATED, GLFW_TRUE::equals)

  /** Whether the window is floating (always on top). */
  val floating = GlfwWindowAttribute(GLFW_FLOATING, GLFW_TRUE::equals)

  /** Whether the window has a transparent framebuffer. */
  val transparentFramebuffer = GlfwWindowAttribute(GLFW_TRANSPARENT_FRAMEBUFFER, GLFW_TRUE::equals)

  /** Whether the window gains input focus when shown. */
  val focusOnShow = GlfwWindowAttribute(GLFW_FOCUS_ON_SHOW, GLFW_TRUE::equals)

  /** Whether mouse events pass through the window. */
  val mousePassthrough = GlfwWindowAttribute(GLFW_MOUSE_PASSTHROUGH, GLFW_TRUE::equals)

  /** Whether the window auto-iconifies on focus loss in fullscreen. */
  val autoIconify = GlfwWindowAttribute(GLFW_AUTO_ICONIFY, GLFW_TRUE::equals)
}

// -- Callback typealiases ---------------------------------------------------

/** Callback for window position changes. */
typealias GlfwWindowPosCallback = (window: GlfwWindowHandle, xPos: Int, yPos: Int) -> Unit

/** Callback for window size changes. */
typealias GlfwWindowSizeCallback = (window: GlfwWindowHandle, width: Int, height: Int) -> Unit

/** Callback for window close requests. */
typealias GlfwWindowCloseCallback = (window: GlfwWindowHandle) -> Unit

/** Callback for window content refresh requests. */
typealias GlfwWindowRefreshCallback = (window: GlfwWindowHandle) -> Unit

/** Callback for window focus changes. */
typealias GlfwWindowFocusCallback = (window: GlfwWindowHandle, focused: Boolean) -> Unit

/** Callback for window iconify (minimize) state changes. */
typealias GlfwWindowIconifyCallback = (window: GlfwWindowHandle, iconified: Boolean) -> Unit

/** Callback for the window `maximize` state changes. */
typealias GlfwWindowMaximizeCallback = (window: GlfwWindowHandle, maximized: Boolean) -> Unit

/** Callback for framebuffer size changes. */
typealias GlfwFramebufferSizeCallback = (window: GlfwWindowHandle, width: Int, height: Int) -> Unit

/** Callback for window content scale changes. */
typealias GlfwWindowContentScaleCallback = (window: GlfwWindowHandle, xScale: Float, yScale: Float) -> Unit

// -- Callback stores (per-window) -------------------------------------------

@OptIn(ExperimentalForeignApi::class)
private val windowPosCallbackStore = CallbackStore<GLFWwindow, GlfwWindowPosCallback>()

@OptIn(ExperimentalForeignApi::class)
private val windowSizeCallbackStore = CallbackStore<GLFWwindow, GlfwWindowSizeCallback>()

@OptIn(ExperimentalForeignApi::class)
private val windowCloseCallbackStore = CallbackStore<GLFWwindow, GlfwWindowCloseCallback>()

@OptIn(ExperimentalForeignApi::class)
private val windowRefreshCallbackStore = CallbackStore<GLFWwindow, GlfwWindowRefreshCallback>()

@OptIn(ExperimentalForeignApi::class)
private val windowFocusCallbackStore = CallbackStore<GLFWwindow, GlfwWindowFocusCallback>()

@OptIn(ExperimentalForeignApi::class)
private val windowIconifyCallbackStore = CallbackStore<GLFWwindow, GlfwWindowIconifyCallback>()

@OptIn(ExperimentalForeignApi::class)
private val windowMaximizeCallbackStore = CallbackStore<GLFWwindow, GlfwWindowMaximizeCallback>()

@OptIn(ExperimentalForeignApi::class)
private val framebufferSizeCallbackStore = CallbackStore<GLFWwindow, GlfwFramebufferSizeCallback>()

@OptIn(ExperimentalForeignApi::class)
private val windowContentScaleCallbackStore = CallbackStore<GLFWwindow, GlfwWindowContentScaleCallback>()

// -- Window hints -----------------------------------------------------------

/**
 * Resets all window hints to their default values.
 *
 * This function must only be called from the main thread.
 *
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 *
 * @see glfwWindowHint
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwDefaultWindowHints() {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwDefaultWindowHints()
}

/**
 * Sets a window hint for the next call to [glfwCreateWindow].
 *
 * The hints, once set, retain their values until changed by a call to this function
 * or [glfwDefaultWindowHints], or until the library is terminated.
 *
 * The type of [value] is enforced at compile time via the [hint]'s type parameter.
 * For nullable hint types (e.g. `GlfwWindowHint.RedBits` typed as `Int?`),
 * `null` maps to `GLFW_DONT_CARE` for int hints or empty string for string hints.
 *
 * This function must only be called from the main thread.
 *
 * @param T the value type determined by the hint.
 * @param hint the window hint to set.
 * @param value the new value of the window hint.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidEnumValueGlfwException If the hint is not a valid window hint.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun <T> glfwWindowHint(hint: GlfwWindowHint<T>, value: T) {
  if (hint.valueType == String::class) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwWindowHintString(
      hint.glfwConstant,
      value as? String ?: ""
    )
  } else {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwWindowHint(
      hint.glfwConstant,
      mapToGlfwInt(value)
    )
  }
}

// -- Window lifecycle -------------------------------------------------------

/**
 * Creates a window and its associated OpenGL or OpenGL ES context.
 *
 * Most options are specified through window hints set via [glfwWindowHint].
 *
 * The created window, framebuffer, and context may differ from what you requested,
 * as not all parameters and hints are hard constraints.
 *
 * This function must only be called from the main thread.
 *
 * @param size Desired size in screen coordinates (must be greater than zero).
 * @param title Initial UTF-8 encoded window title.
 * @param monitor Monitor for full-screen mode, or `null` for windowed mode.
 * @param share Window whose context to share resources with, or `null`.
 * @return Handle to the created window.
 * @throws GlfwException If window creation fails.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidValueGlfwException If an invalid size was provided.
 * @throws ApiUnavailableGlfwException If the requested client API is not available.
 * @throws VersionUnavailableGlfwException If the requested OpenGL/ES version is not available.
 * @throws FormatUnavailableGlfwException If the requested pixel format is not available.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwCreateWindow(
  size: IntVector2,
  title: String,
  monitor: GlfwMonitorHandle? = null,
  share: GlfwWindowHandle? = null
): GlfwWindowHandle {
  val ptr = io.github.kotlinopenfoundation.glfw.cinterop.glfwCreateWindow(
    size.x, size.y, title, monitor?.pointer, share?.pointer
  ) ?: throw GlfwException("Failed to create GLFW window")
  return GlfwWindowHandle(ptr)
}

/**
 * Destroys the specified window and its context.
 *
 * On calling this function, no further callbacks will be called for that window.
 *
 * If the context of the specified window is current on the main thread, it is detached before being destroyed.
 *
 * This function must only be called from the main thread.
 * This function must not be called from a callback.
 *
 * @param window The window to destroy.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwDestroyWindow(window: GlfwWindowHandle) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwDestroyWindow(window.pointer)
}

/**
 * Returns the value of the close flag of the specified window.
 *
 * @param window The window to query.
 * @return `true` if the window should close, `false` otherwise.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwWindowShouldClose(window: GlfwWindowHandle): Boolean {
  return GLFW_TRUE == io.github.kotlinopenfoundation.glfw.cinterop.glfwWindowShouldClose(window.pointer)
}

/**
 * Sets the value of the close flag of the specified window.
 *
 * Can be used to override user closure attempts or signal that a window should be closed.
 *
 * @param window The window whose flag to change.
 * @param shouldClose The new value of the close flag.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowShouldClose(window: GlfwWindowHandle, shouldClose: Boolean) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowShouldClose(
    window.pointer,
    if (shouldClose) GLFW_TRUE else GLFW_FALSE
  )
}

// -- Window title -----------------------------------------------------------

/**
 * Returns the title of the specified window.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to query.
 * @return The UTF-8 encoded window title, or `null` if an error occurred.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.4
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetWindowTitle(window: GlfwWindowHandle): String? {
  return io.github.kotlinopenfoundation.glfw.cinterop.glfwGetWindowTitle(window.pointer)?.toKString()
}

/**
 * Sets the title of the specified window.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose title to change.
 * @param title The new title as UTF-8 encoded string.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowTitle(window: GlfwWindowHandle, title: String) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowTitle(window.pointer, title)
}

// -- Window icon ------------------------------------------------------------

/**
 * Sets the icon of the specified window.
 *
 * If passed a list of candidate images, those of or closest to the sizes desired by the system are selected.
 * If no images are specified, the window reverts to its default icon.
 *
 * The pixels are 32-bit, little-endian, non-premultiplied RGBA.
 * The images are not copied; their data must be valid until the next call to [glfwSetWindowIcon] or [glfwDestroyWindow].
 *
 * **macOS:** Regular windows do not have icons on macOS. GLFW will emit [FeatureUnavailableGlfwException].
 * The dock icon will be the same as the application bundle's icon. For more information, see the macOS-specific docs.
 *
 * **Wayland:** There is no existing protocol to change an icon; the window will inherit the one defined in the desktop file.
 * GLFW will emit [FeatureUnavailableGlfwException].
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose icon to set.
 * @param images The list of images to set, or an empty list to revert to the default.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidValueGlfwException If the image data was invalid.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @throws FeatureUnavailableGlfwException If the platform does not support window icons.
 * @since GLFW 3.2
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowIcon(window: GlfwWindowHandle, images: List<GlfwImage> = emptyList()) {
  if (images.isEmpty()) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowIcon(window.pointer, 0, null)
    return
  }
  memScoped {
    val cImages = allocArray<GLFWimage>(images.size)
    val pinnedArrays = images.map { it.pixels.pin() }
    try {
      images.forEachIndexed { index, image ->
        cImages[index].width = image.width
        cImages[index].height = image.height
        cImages[index].pixels = pinnedArrays[index].addressOf(0).reinterpret()
      }
      io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowIcon(window.pointer, images.size, cImages)
    } finally {
      pinnedArrays.forEach { it.unpin() }
    }
  }
}

// -- Window position & size -------------------------------------------------

/**
 * Retrieves the position, in screen coordinates, of the upper-left corner of the content area
 * of the specified window.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to query.
 * @return The position of the upper-left corner of the content area.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @throws FeatureUnavailableGlfwException If called on a platform that does not support it.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetWindowPos(window: GlfwWindowHandle): IntVector2 {
  return memScoped {
    val x = alloc<IntVar>()
    val y = alloc<IntVar>()
    io.github.kotlinopenfoundation.glfw.cinterop.glfwGetWindowPos(window.pointer, x.ptr, y.ptr)
    IntVector2(x.value, y.value)
  }
}

/**
 * Sets the position, in screen coordinates, of the upper-left corner of the content area
 * of the specified windowed mode window.
 *
 * It is very rarely a good idea to move an already visible window, as it will confuse
 * and annoy the user.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to reposition.
 * @param pos The new position of the upper-left corner of the content area.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @throws FeatureUnavailableGlfwException If called on a platform that does not support it.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowPos(window: GlfwWindowHandle, pos: IntVector2) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowPos(window.pointer, pos.x, pos.y)
}

/**
 * Retrieves the size, in screen coordinates, of the content area of the specified window.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to query.
 * @return The size of the content area.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetWindowSize(window: GlfwWindowHandle): IntVector2 {
  return memScoped {
    val width = alloc<IntVar>()
    val height = alloc<IntVar>()
    io.github.kotlinopenfoundation.glfw.cinterop.glfwGetWindowSize(window.pointer, width.ptr, height.ptr)
    IntVector2(width.value, height.value)
  }
}

/**
 * Sets the size limits of the content area of the specified window.
 *
 * If the window is full screen, the size limits only take effect once it is made windowed.
 * If the window is not resizable, this function does nothing.
 *
 * Use `null` for any parameter to leave that limit unchanged.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to set limits for.
 * @param minWidth The minimum width of the content area, or `null` for no minimum.
 * @param minHeight The minimum height of the content area, or `null` for no minimum.
 * @param maxWidth The maximum width of the content area, or `null` for no maximum.
 * @param maxHeight The maximum height of the content area, or `null` for no maximum.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidValueGlfwException If the limits are invalid.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.2
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowSizeLimits(
  window: GlfwWindowHandle,
  minWidth: Int? = null,
  minHeight: Int? = null,
  maxWidth: Int? = null,
  maxHeight: Int? = null
) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowSizeLimits(
    window.pointer,
    minWidth ?: GLFW_DONT_CARE,
    minHeight ?: GLFW_DONT_CARE,
    maxWidth ?: GLFW_DONT_CARE,
    maxHeight ?: GLFW_DONT_CARE
  )
}

/**
 * Sets the required aspect ratio of the content area of the specified window.
 *
 * If the window is full screen, the aspect ratio only takes effect once it is made windowed.
 * If the window is not resizable, this function does nothing.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to set the aspect ratio for.
 * @param number The numerator of the desired aspect ratio, or `null` to disable.
 * @param denominator The denominator of the desired aspect ratio, or `null` to disable.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidValueGlfwException If the aspect ratio values are invalid.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.2
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowAspectRatio(window: GlfwWindowHandle, number: Int? = null, denominator: Int? = null) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowAspectRatio(
    window.pointer,
    number ?: GLFW_DONT_CARE,
    denominator ?: GLFW_DONT_CARE
  )
}

/**
 * Sets the size, in screen coordinates, of the content area of the specified window.
 *
 * For full-screen windows, this function updates the resolution of the desired video mode
 * and switches to the closest matching one.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to resize.
 * @param size The desired size of the content area.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowSize(window: GlfwWindowHandle, size: IntVector2) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowSize(window.pointer, size.x, size.y)
}

/**
 * Retrieves the size, in pixels, of the framebuffer of the specified window.
 *
 * The framebuffer size may differ from the window size on high-DPI displays.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose framebuffer to query.
 * @return The framebuffer size in pixels.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetFramebufferSize(window: GlfwWindowHandle): IntVector2 {
  return memScoped {
    val width = alloc<IntVar>()
    val height = alloc<IntVar>()
    io.github.kotlinopenfoundation.glfw.cinterop.glfwGetFramebufferSize(window.pointer, width.ptr, height.ptr)
    IntVector2(width.value, height.value)
  }
}

/**
 * Retrieves the size, in screen coordinates, of each edge of the frame
 * of the specified window.
 *
 * This size includes the title bar if the window has one.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose frame size to query.
 * @return The frame edge sizes.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.1
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetWindowFrameSize(window: GlfwWindowHandle): GlfwWindowFrameSize {
  return memScoped {
    val left = alloc<IntVar>()
    val top = alloc<IntVar>()
    val right = alloc<IntVar>()
    val bottom = alloc<IntVar>()
    io.github.kotlinopenfoundation.glfw.cinterop.glfwGetWindowFrameSize(
      window.pointer, left.ptr, top.ptr, right.ptr, bottom.ptr
    )
    GlfwWindowFrameSize(left.value, top.value, right.value, bottom.value)
  }
}

/**
 * Retrieves the content scale for the specified window.
 *
 * The content scale is the ratio between the current DPI and the platform's default DPI.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to query.
 * @return The content scale as (x-scale, y-scale).
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetWindowContentScale(window: GlfwWindowHandle): FloatVector2 {
  return memScoped {
    val xScale = alloc<FloatVar>()
    val yScale = alloc<FloatVar>()
    io.github.kotlinopenfoundation.glfw.cinterop.glfwGetWindowContentScale(window.pointer, xScale.ptr, yScale.ptr)
    FloatVector2(xScale.value, yScale.value)
  }
}

/**
 * Returns the opacity of the whole window, including any decorations.
 *
 * @param window The window to query.
 * @return The opacity value, between 0.0 and 1.0.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetWindowOpacity(window: GlfwWindowHandle): Float =
  io.github.kotlinopenfoundation.glfw.cinterop.glfwGetWindowOpacity(window.pointer)

/**
 * Sets the opacity of the whole window, including any decorations.
 *
 * A window created with framebuffer transparency may not use the whole window transparency.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to set opacity for.
 * @param opacity The desired opacity value, between 0.0 and 1.0.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @throws FeatureUnavailableGlfwException If the platform does not support it.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowOpacity(window: GlfwWindowHandle, opacity: Float) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowOpacity(window.pointer, opacity)
}

// -- Window state -----------------------------------------------------------

/**
 * Iconifies (minimizes) the specified window if it was previously restored.
 * If the window is already iconified, this function does nothing.
 *
 * If the specified window is a full-screen window, GLFW restores the original video mode of the monitor.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to iconify.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 2.1
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwIconifyWindow(window: GlfwWindowHandle) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwIconifyWindow(window.pointer)
}

/**
 * Restores the specified window if it was previously iconified (minimized) or maximized.
 * If the window is already restored, this function does nothing.
 *
 * If the specified window is an iconified full-screen window,
 * its desired video mode is set on the monitor again.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to restore.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 2.1
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwRestoreWindow(window: GlfwWindowHandle) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwRestoreWindow(window.pointer)
}

/**
 * Maximizes the specified window if it was previously not maximized.
 * If the window is already maximized, this function does nothing.
 *
 * If the specified window is a full-screen window, this function does nothing.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to maximize.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.2
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwMaximizeWindow(window: GlfwWindowHandle) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwMaximizeWindow(window.pointer)
}

/**
 * Makes the specified window visible if it was previously hidden.
 * If the window is already visible or is in full-screen mode, this function does nothing.
 *
 * By default, windowed mode windows are focused when shown.
 * Set the [GLFW_FOCUS_ON_SHOW] window hint to change this.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to make visible.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwShowWindow(window: GlfwWindowHandle) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwShowWindow(window.pointer)
}

/**
 * Hides the specified window if it was previously visible.
 * If the window is already hidden or is in full-screen mode, this function does nothing.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to hide.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwHideWindow(window: GlfwWindowHandle) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwHideWindow(window.pointer)
}

/**
 * Brings the specified window to the front and sets input focus.
 * The window should already be visible and not iconified.
 *
 * Do not use this function to steal focus from other applications unless you are certain that is
 * what the user wants. Focus stealing can be extremely disruptive.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to give input focus.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.2
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwFocusWindow(window: GlfwWindowHandle) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwFocusWindow(window.pointer)
}

/**
 * Requests user attention to the specified window.
 *
 * On platforms where this is not supported, attention is requested to the application as a whole.
 *
 * Once the user has given attention, usually by focusing on the window or application,
 * the system will end the request automatically.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to request attention for.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwRequestWindowAttention(window: GlfwWindowHandle) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwRequestWindowAttention(window.pointer)
}

// -- Monitor association ----------------------------------------------------

/**
 * Returns the handle of the monitor that the specified window is in full screen on.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to query.
 * @return The monitor handle, or `null` if the window is in windowed mode.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetWindowMonitor(window: GlfwWindowHandle): GlfwMonitorHandle? =
  io.github.kotlinopenfoundation.glfw.cinterop.glfwGetWindowMonitor(window.pointer)?.let { GlfwMonitorHandle(it) }

/**
 * Sets the monitor that the window uses for full-screen mode or,
 * if the monitor is `null`, makes it windowed mode.
 *
 * When setting a monitor, this function updates the width, height, and refresh rate
 * of the desired video mode and switches to the video mode closest to it.
 *
 * When the monitor is `null`, the position, width, and height are used to place the window content area.
 * The refresh rate is ignored when no monitor is specified.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose monitor, size, or video mode to set.
 * @param monitor The desired monitor, or `null` to set windowed mode.
 * @param pos The desired position of the upper-left corner of the content area.
 * @param size The desired size of the content area or video mode.
 * @param refreshRate The desired refresh rate in Hz, or `null` for don't care.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.2
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowMonitor(
  window: GlfwWindowHandle,
  monitor: GlfwMonitorHandle?,
  pos: IntVector2,
  size: IntVector2,
  refreshRate: Int? = null
) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowMonitor(
    window.pointer, monitor?.pointer, pos.x, pos.y, size.x, size.y, refreshRate ?: GLFW_DONT_CARE
  )
}

// -- Window attributes ------------------------------------------------------

/**
 * Returns an attribute of the specified window or its OpenGL or OpenGL ES context.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to query.
 * @param attribute The window attribute to retrieve.
 * @return The value of the attribute, mapped to the appropriate type.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidEnumValueGlfwException If the attribute is not a valid window attribute.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun <T> glfwGetWindowAttrib(window: GlfwWindowHandle, attribute: GlfwWindowAttribute<T>): T {
  val value = io.github.kotlinopenfoundation.glfw.cinterop.glfwGetWindowAttrib(window.pointer, attribute.glfwValue)
  return attribute.valueMapper(value)
}

/**
 * Sets an attribute of the specified window.
 *
 * Some of these attributes are ignored for full-screen windows. The new value will take effect
 * if the window is later made windowed.
 *
 * Some of these attributes are ignored for windowed mode windows. The new value will take effect
 * if the window is later made full screen.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window to modify.
 * @param attribute The attribute to set (use the GLFW constant directly).
 * @param value The new value of the attribute.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidEnumValueGlfwException If the attribute is not a settable window attribute.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowAttrib(window: GlfwWindowHandle, attribute: Int, value: Int) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowAttrib(window.pointer, attribute, value)
}

// -- User pointer -----------------------------------------------------------

/**
 * Sets the user pointer of the specified window.
 *
 * The current value is retained until the window is destroyed. The initial value is `null`.
 *
 * This function may be called from any thread. Access is not synchronized.
 *
 * @param window The window whose pointer to set.
 * @param pointer The new value.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowUserPointer(window: GlfwWindowHandle, pointer: CPointer<*>?) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowUserPointer(window.pointer, pointer)
}

/**
 * Returns the current value of the user pointer of the specified window.
 *
 * The initial value is `null`.
 *
 * This function may be called from any thread. Access is not synchronized.
 *
 * @param window The window whose pointer to return.
 * @return The user pointer, or `null` if not set.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetWindowUserPointer(window: GlfwWindowHandle): CPointer<*>? =
  io.github.kotlinopenfoundation.glfw.cinterop.glfwGetWindowUserPointer(window.pointer)

// -- Callbacks --------------------------------------------------------------

/**
 * Sets the position callback for the specified window.
 *
 * This callback is called when the window is moved.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose callback to set.
 * @param callback The new callback, or `null` to remove the currently set callback.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowPosCallback(window: GlfwWindowHandle, callback: GlfwWindowPosCallback?) {
  if (callback == null) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowPosCallback(window.pointer, null)
    windowPosCallbackStore.remove(window.pointer)
    return
  }
  windowPosCallbackStore[window.pointer] = callback
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowPosCallback(
    window.pointer,
    staticCFunction { win, x, y ->
      windowPosCallbackStore[win!!]?.invoke(GlfwWindowHandle(win), x, y)
    }
  )
}

/**
 * Sets the size callback for the specified window.
 *
 * This callback is called when the window is resized.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose callback to set.
 * @param callback The new callback, or `null` to remove the currently set callback.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowSizeCallback(window: GlfwWindowHandle, callback: GlfwWindowSizeCallback?) {
  if (callback == null) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowSizeCallback(window.pointer, null)
    windowSizeCallbackStore.remove(window.pointer)
    return
  }
  windowSizeCallbackStore[window.pointer] = callback
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowSizeCallback(
    window.pointer,
    staticCFunction { win, width, height ->
      windowSizeCallbackStore[win!!]?.invoke(GlfwWindowHandle(win), width, height)
    }
  )
}

/**
 * Sets the close callback for the specified window.
 *
 * This callback is called when the user attempts to close the window,
 * for example, by clicking the close widget in the title bar.
 *
 * The close flag is set before this callback is called, but you can modify it from the callback.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose callback to set.
 * @param callback The new callback, or `null` to remove the currently set callback.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 2.5
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowCloseCallback(window: GlfwWindowHandle, callback: GlfwWindowCloseCallback?) {
  if (callback == null) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowCloseCallback(window.pointer, null)
    windowCloseCallbackStore.remove(window.pointer)
    return
  }
  windowCloseCallbackStore[window.pointer] = callback
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowCloseCallback(
    window.pointer,
    staticCFunction { win ->
      windowCloseCallbackStore[win!!]?.invoke(GlfwWindowHandle(win))
    }
  )
}

/**
 * Sets the refresh callback for the specified window.
 *
 * This callback is called when the content area of the window needs to be redrawn,
 * for example, if the window has been exposed after having been covered by another window.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose callback to set.
 * @param callback The new callback, or `null` to remove the currently set callback.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 2.5
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowRefreshCallback(window: GlfwWindowHandle, callback: GlfwWindowRefreshCallback?) {
  if (callback == null) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowRefreshCallback(window.pointer, null)
    windowRefreshCallbackStore.remove(window.pointer)
    return
  }
  windowRefreshCallbackStore[window.pointer] = callback
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowRefreshCallback(
    window.pointer,
    staticCFunction { win ->
      windowRefreshCallbackStore[win!!]?.invoke(GlfwWindowHandle(win))
    }
  )
}

/**
 * Sets the focus callback for the specified window.
 *
 * This callback is called when the window gains or loses input focus.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose callback to set.
 * @param callback The new callback, or `null` to remove the currently set callback.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowFocusCallback(window: GlfwWindowHandle, callback: GlfwWindowFocusCallback?) {
  if (callback == null) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowFocusCallback(window.pointer, null)
    windowFocusCallbackStore.remove(window.pointer)
    return
  }
  windowFocusCallbackStore[window.pointer] = callback
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowFocusCallback(
    window.pointer,
    staticCFunction { win, focused ->
      windowFocusCallbackStore[win!!]?.invoke(GlfwWindowHandle(win), focused == GLFW_TRUE)
    }
  )
}

/**
 * Sets the iconify callback for the specified window.
 *
 * This callback is called when the window is iconified (minimized) or restored.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose callback to set.
 * @param callback The new callback, or `null` to remove the currently set callback.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowIconifyCallback(window: GlfwWindowHandle, callback: GlfwWindowIconifyCallback?) {
  if (callback == null) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowIconifyCallback(window.pointer, null)
    windowIconifyCallbackStore.remove(window.pointer)
    return
  }
  windowIconifyCallbackStore[window.pointer] = callback
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowIconifyCallback(
    window.pointer,
    staticCFunction { win, iconified ->
      windowIconifyCallbackStore[win!!]?.invoke(GlfwWindowHandle(win), iconified == GLFW_TRUE)
    }
  )
}

/**
 * Sets the `maximize` callback for the specified window.
 *
 * This callback is called when the window is maximized or restored.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose callback to set.
 * @param callback The new callback, or `null` to remove the currently set callback.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowMaximizeCallback(window: GlfwWindowHandle, callback: GlfwWindowMaximizeCallback?) {
  if (callback == null) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowMaximizeCallback(window.pointer, null)
    windowMaximizeCallbackStore.remove(window.pointer)
    return
  }
  windowMaximizeCallbackStore[window.pointer] = callback
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowMaximizeCallback(
    window.pointer,
    staticCFunction { win, maximized ->
      windowMaximizeCallbackStore[win!!]?.invoke(GlfwWindowHandle(win), maximized == GLFW_TRUE)
    }
  )
}

/**
 * Sets the framebuffer resize callback for the specified window.
 *
 * This callback is called when the framebuffer of the specified window is resized.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose callback to set.
 * @param callback The new callback, or `null` to remove the currently set callback.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetFramebufferSizeCallback(window: GlfwWindowHandle, callback: GlfwFramebufferSizeCallback?) {
  if (callback == null) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetFramebufferSizeCallback(window.pointer, null)
    framebufferSizeCallbackStore.remove(window.pointer)
    return
  }
  framebufferSizeCallbackStore[window.pointer] = callback
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetFramebufferSizeCallback(
    window.pointer,
    staticCFunction { win, width, height ->
      framebufferSizeCallbackStore[win!!]?.invoke(GlfwWindowHandle(win), width, height)
    }
  )
}

/**
 * Sets the content scale callback for the specified window.
 *
 * This callback is called when the content scale of the specified window changes.
 *
 * This function must only be called from the main thread.
 *
 * @param window The window whose callback to set.
 * @param callback The new callback, or `null` to remove the currently set callback.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetWindowContentScaleCallback(window: GlfwWindowHandle, callback: GlfwWindowContentScaleCallback?) {
  if (callback == null) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowContentScaleCallback(window.pointer, null)
    windowContentScaleCallbackStore.remove(window.pointer)
    return
  }
  windowContentScaleCallbackStore[window.pointer] = callback
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetWindowContentScaleCallback(
    window.pointer,
    staticCFunction { win, xScale, yScale ->
      windowContentScaleCallbackStore[win!!]?.invoke(GlfwWindowHandle(win), xScale, yScale)
    }
  )
}

// -- Events -----------------------------------------------------------------

/**
 * Processes all pending events.
 *
 * This function processes only those events that are already in the event queue and then returns immediately.
 * Processing events will cause the window and input callbacks associated with those events to be called.
 *
 * On some platforms, a window move, resize, or menu operation will cause event processing to block.
 * This is due to how event processing is designed on those platforms.
 *
 * This function must only be called from the main thread.
 *
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 1.0
 *
 * @see glfwWaitEvents
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwPollEvents() {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwPollEvents()
}

/**
 * Waits until events are queued and processes them.
 *
 * If [timeout] is `null`, blocks indefinitely until events arrive, then processes them.
 * If [timeout] is provided, blocks for the specified duration or until events arrive, then processes them.
 *
 * This function must only be called from the main thread.
 *
 * @param timeout The maximum time to wait, or `null` to wait indefinitely.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 2.5
 *
 * @see glfwPollEvents
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwWaitEvents(timeout: Duration? = null) {
  if (null == timeout) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwWaitEvents()
  } else {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwWaitEventsTimeout(timeout.toDouble(DurationUnit.SECONDS))
  }
}

/**
 * Posts an empty event to the event queue, causing [glfwWaitEvents] to return.
 *
 * This function may be called from any thread.
 *
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.1
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwPostEmptyEvent() {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwPostEmptyEvent()
}

// -- Buffer swapping --------------------------------------------------------

/**
 * Swaps the front and back buffers of the specified window when rendering with OpenGL or OpenGL ES.
 *
 * If the swap interval is greater than zero, the GPU driver waits for the specified number of
 * screen updates before swapping the buffers.
 *
 * This function may be called from any thread.
 *
 * @param window The window whose buffers to swap.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws NoCurrentContextGlfwException If no context is current on the calling thread.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSwapBuffers(window: GlfwWindowHandle) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSwapBuffers(window.pointer)
}
