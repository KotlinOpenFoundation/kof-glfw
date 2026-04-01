package io.github.kotlinopenfoundation.glfw.wrapper.hints

import io.github.kotlinopenfoundation.glfw.cinterop.*
import io.github.kotlinopenfoundation.glfw.wrapper.enums.GlfwAnglePlatformType
import io.github.kotlinopenfoundation.glfw.wrapper.enums.GlfwPlatform
import io.github.kotlinopenfoundation.glfw.wrapper.enums.GlfwWaylandLibDecor
import kotlinx.cinterop.ExperimentalForeignApi
import kotlin.reflect.KClass

/**
 * Sealed class representing all GLFW [initialization hints](https://www.glfw.org/docs/3.4/intro_guide.html#init_hints)
 * set before calling `glfwInit`.
 *
 * Each entry carries a [glfwConstant] identifying the hint, a [valueType] for the accepted Kotlin type,
 * and a generic type parameter [T] that provides compile-time type safety when setting the hint.
 *
 * Platform-specific hints are included directly and are silently ignored on other platforms.
 *
 * @param T The Kotlin type of the value accepted by this hint.
 * @property glfwConstant The GLFW integer constant identifying this init hint.
 * @property valueType The [KClass] of the non-nullable value type accepted by this hint.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
sealed class GlfwInitHint<T>(
  val glfwConstant: Int,
  val valueType: KClass<*>
) {
  /** Platform selection. Use `null` to select any platform. */
  data object Platform : GlfwInitHint<GlfwPlatform?>(GLFW_PLATFORM, GlfwPlatform::class)
  /** Whether to expose joystick hats as buttons. */
  data object JoystickHatButtons : GlfwInitHint<Boolean>(GLFW_JOYSTICK_HAT_BUTTONS, Boolean::class)
  /** ANGLE rendering backend. */
  data object AnglePlatformType : GlfwInitHint<GlfwAnglePlatformType>(GLFW_ANGLE_PLATFORM_TYPE, GlfwAnglePlatformType::class)
  /** macOS: whether to change the working directory to app bundle Resources. Ignored on other platforms. */
  data object CocoaChdirResources : GlfwInitHint<Boolean>(GLFW_COCOA_CHDIR_RESOURCES, Boolean::class)
  /** macOS: whether to create a main menu and dock icon. Ignored on other platforms. */
  data object CocoaMenubar : GlfwInitHint<Boolean>(GLFW_COCOA_MENUBAR, Boolean::class)
  /** Wayland: libdecor configuration. Ignored on other platforms. */
  data object WaylandLibDecor : GlfwInitHint<GlfwWaylandLibDecor>(GLFW_WAYLAND_LIBDECOR, GlfwWaylandLibDecor::class)
  /** X11: whether to use XCB for Vulkan surface creation. Ignored on other platforms. */
  data object X11XcbVulkanSurface : GlfwInitHint<Boolean>(GLFW_X11_XCB_VULKAN_SURFACE, Boolean::class)
}
