package io.github.kotlinopenfoundation.glfw.wrapper.hints

import io.github.kotlinopenfoundation.glfw.cinterop.*
import io.github.kotlinopenfoundation.glfw.wrapper.enums.*
import kotlinx.cinterop.ExperimentalForeignApi
import kotlin.reflect.KClass

/**
 * Sealed class representing all GLFW window hints that can be set before window creation.
 *
 * Each entry carries a [glfwConstant] identifying the hint, a [valueType] for the accepted Kotlin type,
 * and a generic type parameter [T] that provides compile-time type safety when setting the hint.
 *
 * Platform-specific hints are included directly and are silently ignored on other platforms.
 *
 * See the [GLFW window hints guide](https://www.glfw.org/docs/3.4/window_guide.html#window_hints) for details.
 *
 * @param T The Kotlin type of the value accepted by this hint.
 * @property glfwConstant The GLFW integer constant identifying this hint.
 * @property valueType The [KClass] of the non-nullable value type accepted by this hint.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
sealed class GlfwWindowHint<T>(
  val glfwConstant: Int,
  val valueType: KClass<*>
) {
  // -- Window hints -----------------------------------------------------------

  /** Whether the windowed mode window will be resizable by the user. */
  data object Resizable : GlfwWindowHint<Boolean>(GLFW_RESIZABLE, Boolean::class)
  /** Whether the windowed mode window will be initially visible. */
  data object Visible : GlfwWindowHint<Boolean>(GLFW_VISIBLE, Boolean::class)
  /** Whether the windowed mode window will have window decorations. */
  data object Decorated : GlfwWindowHint<Boolean>(GLFW_DECORATED, Boolean::class)
  /** Whether the windowed mode window will be given input focus when created. */
  data object Focused : GlfwWindowHint<Boolean>(GLFW_FOCUSED, Boolean::class)
  /** Whether the full-screen window will automatically iconify on focus loss. */
  data object AutoIconify : GlfwWindowHint<Boolean>(GLFW_AUTO_ICONIFY, Boolean::class)
  /** Whether the windowed mode window will be floating above other windows. */
  data object Floating : GlfwWindowHint<Boolean>(GLFW_FLOATING, Boolean::class)
  /** Whether the windowed mode window will be maximized when created. */
  data object Maximized : GlfwWindowHint<Boolean>(GLFW_MAXIMIZED, Boolean::class)
  /** Whether the cursor should be centered over newly created full-screen windows. */
  data object CenterCursor : GlfwWindowHint<Boolean>(GLFW_CENTER_CURSOR, Boolean::class)
  /** Whether the window framebuffer will be transparent. */
  data object TransparentFramebuffer : GlfwWindowHint<Boolean>(GLFW_TRANSPARENT_FRAMEBUFFER, Boolean::class)
  /** Whether the window will be given input focus when shown. */
  data object FocusOnShow : GlfwWindowHint<Boolean>(GLFW_FOCUS_ON_SHOW, Boolean::class)
  /** Whether the window content area should be resized based on content scale changes. */
  data object ScaleToMonitor : GlfwWindowHint<Boolean>(GLFW_SCALE_TO_MONITOR, Boolean::class)
  /** Whether mouse events pass through the window. */
  data object MousePassthrough : GlfwWindowHint<Boolean>(GLFW_MOUSE_PASSTHROUGH, Boolean::class)
  /** The desired initial x-coordinate position of the window. Use `null` for any position. */
  data object PositionX : GlfwWindowHint<Int?>(GLFW_POSITION_X, Int::class)
  /** The desired initial y-coordinate position of the window. Use `null` for any position. */
  data object PositionY : GlfwWindowHint<Int?>(GLFW_POSITION_Y, Int::class)

  // -- Framebuffer hints ------------------------------------------------------

  /** Whether the framebuffer should be resized based on content scale changes. @since GLFW 3.4 */
  data object ScaleFramebuffer : GlfwWindowHint<Boolean>(GLFW_SCALE_FRAMEBUFFER, Boolean::class)
  /** The red component desired bit-depth. Use `null` for no preference. */
  data object RedBits : GlfwWindowHint<Int?>(GLFW_RED_BITS, Int::class)
  /** The green component desired bit-depth. Use `null` for no preference. */
  data object GreenBits : GlfwWindowHint<Int?>(GLFW_GREEN_BITS, Int::class)
  /** The blue component desired bit-depth. Use `null` for no preference. */
  data object BlueBits : GlfwWindowHint<Int?>(GLFW_BLUE_BITS, Int::class)
  /** The alpha component desired bit-depth. Use `null` for no preference. */
  data object AlphaBits : GlfwWindowHint<Int?>(GLFW_ALPHA_BITS, Int::class)
  /** Depth buffer desired bit depth. Use `null` for no preference. */
  data object DepthBits : GlfwWindowHint<Int?>(GLFW_DEPTH_BITS, Int::class)
  /** Stencil buffer desired bit depth. Use `null` for no preference. */
  data object StencilBits : GlfwWindowHint<Int?>(GLFW_STENCIL_BITS, Int::class)
  /** Accumulation buffer red bit depth. Use `null` for no preference. */
  @Deprecated("Accumulation buffers are a legacy OpenGL feature and should not be used in new code.")
  data object AccumRedBits : GlfwWindowHint<Int?>(GLFW_ACCUM_RED_BITS, Int::class)
  /** Accumulation buffer green bit depth. Use `null` for no preference. */
  @Deprecated("Accumulation buffers are a legacy OpenGL feature and should not be used in new code.")
  data object AccumGreenBits : GlfwWindowHint<Int?>(GLFW_ACCUM_GREEN_BITS, Int::class)
  /** Accumulation buffer blue bit depth. Use `null` for no preference. */
  @Deprecated("Accumulation buffers are a legacy OpenGL feature and should not be used in new code.")
  data object AccumBlueBits : GlfwWindowHint<Int?>(GLFW_ACCUM_BLUE_BITS, Int::class)
  /** Accumulation buffer alpha bit depth. Use `null` for no preference. */
  @Deprecated("Accumulation buffers are a legacy OpenGL feature and should not be used in new code.")
  data object AccumAlphaBits : GlfwWindowHint<Int?>(GLFW_ACCUM_ALPHA_BITS, Int::class)
  /** Desired number of auxiliary buffers. Use `null` for no preference. */
  @Deprecated("Auxiliary buffers are a legacy OpenGL feature and should not be used in new code.")
  data object AuxBuffers : GlfwWindowHint<Int?>(GLFW_AUX_BUFFERS, Int::class)
  /** Whether to use OpenGL stereoscopic rendering. */
  data object Stereo : GlfwWindowHint<Boolean>(GLFW_STEREO, Boolean::class)
  /** Desired number of samples for multisampling. Use `null` for no preference. */
  data object Samples : GlfwWindowHint<Int?>(GLFW_SAMPLES, Int::class)
  /** Whether the framebuffer should be sRGB capable. */
  data object SrgbCapable : GlfwWindowHint<Boolean>(GLFW_SRGB_CAPABLE, Boolean::class)
  /** Whether the framebuffer should be double-buffered. */
  data object DoubleBuffer : GlfwWindowHint<Boolean>(GLFW_DOUBLEBUFFER, Boolean::class)

  // -- Monitor hints ----------------------------------------------------------

  /** Desired refresh rate for full-screen windows. Use `null` for highest available. */
  data object RefreshRate : GlfwWindowHint<Int?>(GLFW_REFRESH_RATE, Int::class)

  // -- Context hints ----------------------------------------------------------

  /** Which client API to create the context for. */
  data object ClientApi : GlfwWindowHint<GlfwClientApi>(GLFW_CLIENT_API, GlfwClientApi::class)
  /** Which context creation API to use. */
  data object ContextCreationApi : GlfwWindowHint<GlfwContextCreationApi>(GLFW_CONTEXT_CREATION_API, GlfwContextCreationApi::class)
  /** Client API major version number. */
  data object ContextVersionMajor : GlfwWindowHint<Int>(GLFW_CONTEXT_VERSION_MAJOR, Int::class)
  /** Client API minor version number. */
  data object ContextVersionMinor : GlfwWindowHint<Int>(GLFW_CONTEXT_VERSION_MINOR, Int::class)
  /** Whether the OpenGL context should be forward-compatible. */
  data object OpenGLForwardCompat : GlfwWindowHint<Boolean>(GLFW_OPENGL_FORWARD_COMPAT, Boolean::class)
  /** Whether the context should be created in debug mode. */
  data object ContextDebug : GlfwWindowHint<Boolean>(GLFW_CONTEXT_DEBUG, Boolean::class)
  /** Which OpenGL profile to create the context for. Use `null` for any profile. */
  data object OpenGLProfile : GlfwWindowHint<GlfwOpenGLProfile?>(GLFW_OPENGL_PROFILE, GlfwOpenGLProfile::class)
  /** The robustness strategy to be used by the context. Use `null` for no robustness. */
  data object ContextRobustness : GlfwWindowHint<GlfwContextRobustness?>(GLFW_CONTEXT_ROBUSTNESS, GlfwContextRobustness::class)
  /** The release behavior to be used by the context. Use `null` for default behavior. */
  data object ContextReleaseBehavior : GlfwWindowHint<GlfwContextReleaseBehavior?>(GLFW_CONTEXT_RELEASE_BEHAVIOR, GlfwContextReleaseBehavior::class)
  /** Whether errors should be generated by the context. */
  data object ContextNoError : GlfwWindowHint<Boolean>(GLFW_CONTEXT_NO_ERROR, Boolean::class)

  // -- Win32 platform hints ---------------------------------------------------

  /** Whether to allow access to the window menu via Alt+Space. Ignored on other platforms. */
  data object Win32KeyboardMenu : GlfwWindowHint<Boolean>(GLFW_WIN32_KEYBOARD_MENU, Boolean::class)
  /** Whether to show the window per the program's `STARTUPINFO`. Ignored on other platforms. */
  data object Win32ShowDefault : GlfwWindowHint<Boolean>(GLFW_WIN32_SHOWDEFAULT, Boolean::class)

  // -- macOS platform hints ---------------------------------------------------

  /**
   * UTF-8 encoded name to use for autosaving the window frame.
   * Empty string disables autosaving.
   * Ignored on other platforms.
   */
  data object CocoaFrameName : GlfwWindowHint<String>(GLFW_COCOA_FRAME_NAME, String::class)
  /** Whether to enable Automatic Graphics Switching. Ignored on other platforms. */
  data object CocoaGraphicsSwitching : GlfwWindowHint<Boolean>(GLFW_COCOA_GRAPHICS_SWITCHING, Boolean::class)

  // -- Wayland platform hints -------------------------------------------------

  /** The Wayland app_id for window identification. Ignored on other platforms. */
  data object WaylandAppId : GlfwWindowHint<String>(GLFW_WAYLAND_APP_ID, String::class)

  // -- X11 platform hints -----------------------------------------------------

  /** The desired class part of the ICCCM WM_CLASS window property. Ignored on other platforms. */
  data object X11ClassName : GlfwWindowHint<String>(GLFW_X11_CLASS_NAME, String::class)
  /** The desired instance part of the ICCCM WM_CLASS window property. Ignored on other platforms. */
  data object X11InstanceName : GlfwWindowHint<String>(GLFW_X11_INSTANCE_NAME, String::class)
}
