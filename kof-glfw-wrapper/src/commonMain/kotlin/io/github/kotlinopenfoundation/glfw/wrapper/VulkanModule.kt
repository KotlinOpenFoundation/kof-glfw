package io.github.kotlinopenfoundation.glfw.wrapper

import io.github.kotlinopenfoundation.glfw.wrapper.enums.GlfwClientApi
import io.github.kotlinopenfoundation.glfw.wrapper.exception.ApiUnavailableGlfwException
import io.github.kotlinopenfoundation.glfw.wrapper.exception.NotInitializedGlfwException
import io.github.kotlinopenfoundation.glfw.wrapper.exception.PlatformErrorGlfwException
import io.github.kotlinopenfoundation.glfw.wrapper.types.GlfwWindowHandle
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi

// ---------------------------------------------------------------------------
// Vulkan support reference
// [GLFW Reference](https://www.glfw.org/docs/3.4/group__vulkan.html)
//
// NOTE: Full implementation requires a Vulkan cinterop dependency that does not
// yet exist in this project. All functions are stubbed with TODO.
// ---------------------------------------------------------------------------

/** Opaque pointer to a Vulkan instance. */
@OptIn(ExperimentalForeignApi::class)
typealias VulkanInstance = COpaquePointer

/** Opaque pointer to a Vulkan physical device. */
@OptIn(ExperimentalForeignApi::class)
typealias VulkanPhysicalDevice = COpaquePointer

/** Vulkan result code. */
typealias VulkanResult = Int

/** Vulkan surface handle (64-bit non-dispatchable handle). */
typealias VulkanSurfaceHandle = ULong

/** Generic Vulkan function pointer. */
@OptIn(ExperimentalForeignApi::class)
typealias GlfwVulkanFunction = CPointer<CFunction<() -> Unit>>

/**
 * Returns whether the Vulkan loader and any minimally functional ICD have been found.
 *
 * The availability of a Vulkan loader and even an ICD does not by itself guarantee that
 * surface creation or even instance creation is possible. Call [glfwGetRequiredInstanceExtensions]
 * to check whether the extensions necessary for Vulkan surface creation are available and
 * [glfwGetPhysicalDevicePresentationSupport] to check whether a queue family of a physical device
 * supports image presentation.
 *
 * This function may be called from any thread.
 *
 * @return `true` if Vulkan is minimally available, `false` otherwise.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.2
 */
fun glfwVulkanSupported(): Boolean {
  TODO("Requires Vulkan cinterop dependency")
}

/**
 * Returns an array of names of Vulkan instance extensions required by GLFW for creating
 * Vulkan surfaces for GLFW windows.
 *
 * If successful, the list will always contain `VK_KHR_surface`, so if you don't require
 * any additional extensions, you can pass this list directly to the `VkInstanceCreateInfo` struct.
 *
 * The returned array is allocated and freed by GLFW. You should not free it.
 * It is guaranteed to be valid only until the library is terminated.
 *
 * This function may be called from any thread.
 *
 * @return A list of extension names required by GLFW, or an empty list if an error occurred
 *   or Vulkan is not available.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws ApiUnavailableGlfwException If Vulkan is not available.
 * @since GLFW 3.2
 */
fun glfwGetRequiredInstanceExtensions(): List<String> {
  TODO("Requires Vulkan cinterop dependency")
}

/**
 * Returns the address of the specified Vulkan core or extension function for the specified instance.
 *
 * If `instance` is set to `null`, it can return any function exported from the Vulkan loader,
 * including at least `vkEnumerateInstanceExtensionProperties`, `vkEnumerateInstanceLayerProperties`,
 * `vkCreateInstance`, `vkGetInstanceProcAddr` and `vkGetDeviceProcAddr`.
 *
 * If Vulkan is not available on the machine, this function returns `null` and generates a
 * [ApiUnavailableGlfwException].
 *
 * This function may be called from any thread.
 *
 * @param instance The Vulkan instance to query, or `null` to retrieve functions not related to an instance.
 * @param procName The ASCII encoded the name of the function.
 * @return The address of the function, or `null` if the function is not available.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws ApiUnavailableGlfwException If Vulkan is not available.
 * @since GLFW 3.2
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetInstanceProcAddress(instance: VulkanInstance?, procName: String): GlfwVulkanFunction? {
  TODO("Requires Vulkan cinterop dependency")
}

/**
 * Returns whether the specified queue family of the specified physical device
 * supports presentation to the platform GLFW was built for.
 *
 * If Vulkan or the required window surface creation instance extensions are not available
 * on the machine, or if the specified instance was not created with the required extensions,
 * this function returns `false` and generates an [ApiUnavailableGlfwException].
 *
 * This function may be called from any thread.
 *
 * @param instance The Vulkan instance that the physical device belongs to.
 * @param device The physical device that the queue family belongs to.
 * @param queueFamily The index of the queue family to query.
 * @return `true` if the queue family supports presentation.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws ApiUnavailableGlfwException If Vulkan is not available.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.2
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetPhysicalDevicePresentationSupport(
  instance: VulkanInstance,
  device: VulkanPhysicalDevice,
  queueFamily: UInt
): Boolean {
  TODO("Requires Vulkan cinterop dependency")
}

/**
 * Creates a Vulkan surface for the specified window.
 *
 * If the Vulkan loader or at least one minimally functional ICD were not found,
 * this function returns `VK_ERROR_INITIALIZATION_FAILED` and generates an [ApiUnavailableGlfwException].
 *
 * If the required window surface creation instance extensions are not available or if the specified
 * instance was not created with these extensions enabled, this function returns
 * `VK_ERROR_EXTENSION_NOT_PRESENT` and generates an [ApiUnavailableGlfwException].
 *
 * The window surface cannot be shared with another API, so the window must have been created
 * with the [client api hint][GlfwClientApi.NoApi] set to `NoApi`.
 *
 * The window surface must be destroyed before the specified Vulkan instance.
 * It is the responsibility of the caller to destroy the window surface.
 * GLFW does not destroy it for you.
 *
 * This function may be called from any thread.
 *
 * @param instance The Vulkan instance to create the surface in.
 * @param window The window to create the surface for.
 * @return A pair of the Vulkan result code and the created surface handle.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @throws ApiUnavailableGlfwException If Vulkan is not available.
 * @throws PlatformErrorGlfwException If a platform-specific error occurred.
 * @since GLFW 3.2
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwCreateWindowSurface(instance: VulkanInstance, window: GlfwWindowHandle): Pair<VulkanResult, VulkanSurfaceHandle> {
  TODO("Requires Vulkan cinterop dependency")
}
