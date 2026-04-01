package io.github.kotlinopenfoundation.glfw.wrapper

import io.github.kotlinopenfoundation.glfw.cinterop.GLFW_TRUE
import io.github.kotlinopenfoundation.glfw.cinterop.GLFWglproc
import io.github.kotlinopenfoundation.glfw.wrapper.types.GlfwWindowHandle
import io.github.kotlinopenfoundation.glfw.wrapper.exception.*
import kotlinx.cinterop.ExperimentalForeignApi

// ---------------------------------------------------------------------------
// Context reference
// [GLFW Reference](https://www.glfw.org/docs/3.4/group__context.html)
// ---------------------------------------------------------------------------

/**
 * Makes the OpenGL or OpenGL ES context of the specified window current on the calling thread.
 *
 * A context must only be made current on a single thread at a time, and each thread can have
 * only a single current context at a time.
 *
 * When moving a context between threads, you must make it non-current on the old thread
 * before making it current on the new one.
 *
 * By default, making a context non-current implicitly forces a pipeline flush.
 * On machines that support `GL_KHR_context_flush_control`, you can control this.
 *
 * The specified window must have an OpenGL or OpenGL ES context.
 * Specifying a window without a context will generate a [NoWindowContextGlfwException].
 *
 * This function may be called from any thread.
 *
 * @param window The window whose context to make current, or `null` to detach the current context.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws NoWindowContextGlfwException If the window has no OpenGL or OpenGL ES context.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwMakeContextCurrent(window: GlfwWindowHandle?) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwMakeContextCurrent(window?.pointer)
}

/**
 * Returns the window whose OpenGL or OpenGL ES context is current on the calling thread.
 *
 * This function may be called from any thread.
 *
 * @return The window whose context is current, or `null` if no window's context is current.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetCurrentContext(): GlfwWindowHandle? {
  return io.github.kotlinopenfoundation.glfw.cinterop.glfwGetCurrentContext()?.let { GlfwWindowHandle(it) }
}

/**
 * Sets the swap interval for the current OpenGL or OpenGL ES context,
 * i.e., the number of screen updates to wait from the time [glfwSwapBuffers] was called
 * before swapping the buffers and returning.
 *
 * This is sometimes called *vertical synchronization*, *vertical retrace synchronization*
 * or just *vsync*.
 *
 * A context that supports either of the `WGL_EXT_swap_control_tear` and
 * `GLX_EXT_swap_control_tear` extensions also accepts negative swap intervals, which allows
 * the driver to swap immediately when a frame arrives late.
 *
 * A context must be current on the calling thread. Calling this function without a current context
 * will cause a [NoCurrentContextGlfwException].
 *
 * This function does not apply to Vulkan. If you are rendering with Vulkan, see the present
 * mode of your swapchain instead.
 *
 * This function may be called from any thread.
 *
 * @param interval The minimum number of screen updates to wait before swapping buffers.
 *   Use 0 to disable vsync, 1 for standard vsync, or a negative value for adaptive vsync.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws NoCurrentContextGlfwException If no context is current on the calling thread.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSwapInterval(interval: Int) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSwapInterval(interval)
}

/**
 * Returns whether the specified API extension is supported by the current OpenGL or OpenGL ES context.
 *
 * It searches both for client API extension and context creation API extensions.
 *
 * A context must be current on the calling thread. Calling this function without a current context
 * will cause a [NoCurrentContextGlfwException].
 *
 * As this function retrieves and searches one or more extension strings each call,
 * it is recommended that you cache its results if it is going to be used frequently.
 * The extension strings will not change during the lifetime of a context, so there is no danger
 * in doing this.
 *
 * This function may be called from any thread.
 *
 * @param extension The ASCII-encoded name of the extension.
 * @return `true` if the extension is available, `false` otherwise.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws NoCurrentContextGlfwException If no context is current on the calling thread.
 * @throws InvalidValueGlfwException If the extension name is empty.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwExtensionSupported(extension: String): Boolean {
  return GLFW_TRUE == io.github.kotlinopenfoundation.glfw.cinterop.glfwExtensionSupported(extension)
}

/**
 * Returns the address of the specified OpenGL or OpenGL ES
 * [core or extension function](https://www.glfw.org/docs/3.4/context_guide.html#context_glext),
 * if it is supported by the current context.
 *
 * A context must be current on the calling thread. Calling this function without a current context
 * will cause a [NoCurrentContextGlfwException].
 *
 * This function does not apply to Vulkan. If you are rendering with Vulkan, see
 * [glfwGetInstanceProcAddress], `vkGetDeviceProcAddr` and `vkGetInstanceProcAddr` instead.
 *
 * This function may be called from any thread.
 *
 * @param procName The ASCII-encoded name of the function.
 * @return The address of the function, or `null` if the function is not available.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws NoCurrentContextGlfwException If no context is current on the calling thread.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetProcAddress(procName: String?): GLFWglproc? {
  return io.github.kotlinopenfoundation.glfw.cinterop.glfwGetProcAddress(procName)
}
