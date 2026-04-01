package io.github.kotlinopenfoundation.glfw.wrapper

import io.github.kotlinopenfoundation.glfw.cinterop.GLFWgammaramp
import io.github.kotlinopenfoundation.glfw.wrapper.enums.GlfwConnectionEvent
import io.github.kotlinopenfoundation.glfw.wrapper.exception.FeatureUnavailableGlfwException
import io.github.kotlinopenfoundation.glfw.wrapper.exception.InvalidValueGlfwException
import io.github.kotlinopenfoundation.glfw.wrapper.exception.NotInitializedGlfwException
import io.github.kotlinopenfoundation.glfw.wrapper.exception.PlatformErrorGlfwException
import io.github.kotlinopenfoundation.glfw.wrapper.types.*
import kotlinx.cinterop.*
import platform.posix.memcpy
import kotlin.concurrent.AtomicReference
import kotlin.experimental.ExperimentalNativeApi

// ---------------------------------------------------------------------------
// Monitor reference
// [GLFW Reference](https://www.glfw.org/docs/3.4/group__monitor.html)
// ---------------------------------------------------------------------------

/**
 * Callback for monitor connection and disconnection events.
 *
 * @see glfwSetMonitorCallback
 */
typealias GlfwMonitorCallback = (monitor: GlfwMonitorHandle, event: GlfwConnectionEvent) -> Unit

@OptIn(ExperimentalNativeApi::class)
private val monitorCallbackReference = AtomicReference<GlfwMonitorCallback?>(null)

/**
 * Returns the currently connected monitors.
 *
 * The primary monitor is always first in the returned list.
 * If no monitors were found, this function returns an empty list.
 *
 * This function must only be called from the main thread.
 *
 * @return A list of monitor handles.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetMonitors(): List<GlfwMonitorHandle> {
  return memScoped {
    val count = alloc<IntVar>()
    val monitors = io.github.kotlinopenfoundation.glfw.cinterop.glfwGetMonitors(count.ptr) ?: return emptyList()
    (0 until count.value).map { GlfwMonitorHandle(monitors[it]!!) }
  }
}

/**
 * Returns the primary monitor. This is usually the monitor where elements like the task bar
 * or global menu bar are located.
 *
 * This function must only be called from the main thread.
 *
 * @return The primary monitor, or `null` if no monitors were found.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetPrimaryMonitor(): GlfwMonitorHandle? {
  return io.github.kotlinopenfoundation.glfw.cinterop.glfwGetPrimaryMonitor()?.let { GlfwMonitorHandle(it) }
}

/**
 * Returns the position, in screen coordinates, of the upper-left corner of the specified monitor.
 *
 * This function must only be called from the main thread.
 *
 * @param monitor The monitor to query.
 * @return The position of the monitor's viewport on the virtual screen.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetMonitorPos(monitor: GlfwMonitorHandle): IntVector2 {
  return memScoped {
    val xPos = alloc<IntVar>()
    val yPos = alloc<IntVar>()
    io.github.kotlinopenfoundation.glfw.cinterop.glfwGetMonitorPos(monitor.pointer, xPos.ptr, yPos.ptr)
    IntVector2(xPos.value, yPos.value)
  }
}

/**
 * Returns the position, in screen coordinates, of the upper-left corner of the work area of the
 * specified monitor along with its size.
 *
 * The work area is defined as the area of the monitor not occluded by the operating system task bar
 * where present. If no task bar exists, then the work area is the monitor resolution in screen
 * coordinates.
 *
 * This function must only be called from the main thread.
 *
 * @param monitor The monitor to query.
 * @return The work area bounds.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetMonitorWorkArea(monitor: GlfwMonitorHandle): Bounds {
  return memScoped {
    val xPos = alloc<IntVar>()
    val yPos = alloc<IntVar>()
    val width = alloc<IntVar>()
    val height = alloc<IntVar>()
    io.github.kotlinopenfoundation.glfw.cinterop.glfwGetMonitorWorkarea(monitor.pointer, xPos.ptr, yPos.ptr, width.ptr, height.ptr)
    Bounds(xPos.value, yPos.value, width.value, height.value)
  }
}

/**
 * Returns the size, in millimetres, of the display area of the specified monitor.
 *
 * Some platforms do not provide accurate monitor size information, either because the monitor
 * [EDID](https://en.wikipedia.org/wiki/Extended_display_identification_data) data is incorrect
 * or because the driver does not report it accurately.
 *
 * This function must only be called from the main thread.
 *
 * @param monitor The monitor to query.
 * @return The physical size of the monitor in millimetres.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetMonitorPhysicalSize(monitor: GlfwMonitorHandle): IntVector2 {
  return memScoped {
    val xSize = alloc<IntVar>()
    val ySize = alloc<IntVar>()
    io.github.kotlinopenfoundation.glfw.cinterop.glfwGetMonitorPhysicalSize(monitor.pointer, xSize.ptr, ySize.ptr)
    IntVector2(xSize.value, ySize.value)
  }
}

/**
 * Returns the content scale for the specified monitor.
 *
 * The content scale is the ratio between the current DPI and the platform's default DPI.
 * This is especially important for text rendering and any UI elements.
 *
 * The content scale may depend on both the monitor resolution and pixel density,
 * and on user settings. It may be very different from the raw DPI calculated from
 * the physical size and current resolution.
 *
 * This function must only be called from the main thread.
 *
 * @param monitor The monitor to query.
 * @return The content scale as (xScale, yScale).
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetMonitorContentScale(monitor: GlfwMonitorHandle): FloatVector2 {
  return memScoped {
    val xScale = alloc<FloatVar>()
    val yScale = alloc<FloatVar>()
    io.github.kotlinopenfoundation.glfw.cinterop.glfwGetMonitorContentScale(monitor.pointer, xScale.ptr, yScale.ptr)
    FloatVector2(xScale.value, yScale.value)
  }
}

/**
 * Returns the human-readable, UTF-8 encoded name of the specified monitor.
 * The name typically reflects the make and model of the monitor and is not guaranteed to be unique.
 *
 * This function must only be called from the main thread.
 *
 * @param monitor The monitor to query.
 * @return The UTF-8 encoded name of the monitor, or `null` if an error occurred.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetMonitorName(monitor: GlfwMonitorHandle): String? {
  return io.github.kotlinopenfoundation.glfw.cinterop.glfwGetMonitorName(monitor.pointer)?.toKString()
}

/**
 * Returns the current value of the user pointer of the specified monitor.
 *
 * This function may be called from any thread. Access is not synchronized.
 *
 * @param monitor The monitor whose pointer to return.
 * @return The user pointer, or `null` if not set.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetMonitorUserPointer(monitor: GlfwMonitorHandle): CPointer<*>? {
  return io.github.kotlinopenfoundation.glfw.cinterop.glfwGetMonitorUserPointer(monitor.pointer)
}

/**
 * Sets the user pointer of the specified monitor.
 *
 * The current value is retained until the monitor is disconnected. The initial value is `null`.
 *
 * This function may be called from any thread. Access is not synchronized.
 *
 * @param monitor The monitor whose pointer to set.
 * @param userPointer The new value.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetMonitorUserPointer(monitor: GlfwMonitorHandle, userPointer: CPointer<*>?) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetMonitorUserPointer(monitor.pointer, userPointer)
}

/**
 * Sets the monitor configuration callback, which is called when a monitor is connected to
 * or disconnected from the system.
 *
 * This function must only be called from the main thread.
 *
 * @param callback The new callback, or `null` to remove the currently set callback.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class)
fun glfwSetMonitorCallback(callback: GlfwMonitorCallback? = null) {
  monitorCallbackReference.value = callback
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetMonitorCallback(staticCFunction { monitor, event ->
    val cb = monitorCallbackReference.value ?: return@staticCFunction
    val handle = GlfwMonitorHandle(monitor!!)
    cb(handle, GlfwConnectionEvent.entries.first { it.glfwValue == event })
  })
}

/**
 * Returns an array of all video modes supported by the specified monitor.
 *
 * The returned list is sorted in ascending order, first by color bit depth (the sum of all channel depths),
 * then by resolution area (the product of width and height), then by resolution width, and finally by
 * refresh rate.
 *
 * This function must only be called from the main thread.
 *
 * @param monitor The monitor to query.
 * @return A list of video modes supported by the monitor.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetVideoModes(monitor: GlfwMonitorHandle): List<GlfwVideoMode> {
  return memScoped {
    val count = alloc<IntVar>()
    val modes = io.github.kotlinopenfoundation.glfw.cinterop.glfwGetVideoModes(monitor.pointer, count.ptr)
      ?: return emptyList()
    (0 until count.value).map {
      val mode = modes[it]
      GlfwVideoMode(mode.width, mode.height, mode.redBits, mode.greenBits, mode.blueBits, mode.refreshRate)
    }
  }
}

/**
 * Returns the current video mode of the specified monitor.
 * If you have created a full-screen window for that monitor, the return value will depend on
 * whether that window is iconified.
 *
 * This function must only be called from the main thread.
 *
 * @param monitor The monitor to query.
 * @return The current video mode of the monitor, or `null` if an error occurred.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetVideoMode(monitor: GlfwMonitorHandle): GlfwVideoMode? {
  return io.github.kotlinopenfoundation.glfw.cinterop.glfwGetVideoMode(monitor.pointer)
    ?.pointed
    ?.let { GlfwVideoMode(it.width, it.height, it.redBits, it.greenBits, it.blueBits, it.refreshRate) }
}

/**
 * Generates an appropriately sized gamma ramp from the specified exponent and then calls
 * [glfwSetGammaRamp] with it.
 * The value must be a finite number greater than zero.
 *
 * The software-controlled gamma ramp is applied in addition to the hardware gamma correction,
 * which today is usually an approximation of sRGB gamma.
 *
 * This function must only be called from the main thread.
 *
 * @param monitor The monitor whose gamma ramp to set.
 * @param gamma The desired exponent.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws InvalidValueGlfwException If the gamma value is invalid.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @throws FeatureUnavailableGlfwException If the platform does not support gamma ramp control.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetGamma(monitor: GlfwMonitorHandle, gamma: Float) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetGamma(monitor.pointer, gamma)
}

/**
 * Returns the current gamma ramp of the specified monitor.
 *
 * This function must only be called from the main thread.
 *
 * @param monitor The monitor to query.
 * @return The current gamma ramp, or `null` if an error occurred.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @throws FeatureUnavailableGlfwException If the platform does not support gamma ramp retrieval.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetGammaRamp(monitor: GlfwMonitorHandle): GlfwGammaRamp? {
  return io.github.kotlinopenfoundation.glfw.cinterop.glfwGetGammaRamp(monitor.pointer)
    ?.pointed
    ?.let {
      val size = it.size.toInt()
      val byteCount = (size * UShort.SIZE_BYTES).toULong()
      val red = UShortArray(size)
      val green = UShortArray(size)
      val blue = UShortArray(size)
      red.usePinned { pinned -> memcpy(pinned.addressOf(0), it.red, byteCount) }
      green.usePinned { pinned -> memcpy(pinned.addressOf(0), it.green, byteCount) }
      blue.usePinned { pinned -> memcpy(pinned.addressOf(0), it.blue, byteCount) }
      GlfwGammaRamp(red, green, blue)
    }
}

/**
 * Sets the current gamma ramp for the specified monitor.
 *
 * The original gamma ramp for that monitor is saved by GLFW the first time this function is called
 * and is restored by [glfwTerminate].
 *
 * The software-controlled gamma ramp is applied in addition to the hardware gamma correction.
 *
 * The specified gamma ramp size must be 256.
 *
 * This function must only be called from the main thread.
 *
 * @param monitor The monitor whose gamma ramp to set.
 * @param ramp The gamma ramp to use.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @throws FeatureUnavailableGlfwException If the platform does not support gamma ramp control.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetGammaRamp(monitor: GlfwMonitorHandle, ramp: GlfwGammaRamp) {
  val redPinned = ramp.red.pin()
  val greenPinned = ramp.green.pin()
  val bluePinned = ramp.blue.pin()
  try {
    memScoped {
      val cRamp = alloc<GLFWgammaramp>()
      cRamp.size = ramp.size.toUInt()
      cRamp.red = redPinned.addressOf(0)
      cRamp.green = greenPinned.addressOf(0)
      cRamp.blue = bluePinned.addressOf(0)
      io.github.kotlinopenfoundation.glfw.cinterop.glfwSetGammaRamp(monitor.pointer, cRamp.ptr)
    }
  } finally {
    redPinned.unpin()
    greenPinned.unpin()
    bluePinned.unpin()
  }
}
