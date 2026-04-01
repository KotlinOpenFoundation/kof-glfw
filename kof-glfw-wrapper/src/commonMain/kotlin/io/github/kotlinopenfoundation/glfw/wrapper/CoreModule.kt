package io.github.kotlinopenfoundation.glfw.wrapper

import io.github.kotlinopenfoundation.glfw.cinterop.*
import io.github.kotlinopenfoundation.glfw.wrapper.enums.GlfwPlatform
import io.github.kotlinopenfoundation.glfw.wrapper.exception.*
import io.github.kotlinopenfoundation.glfw.wrapper.hints.GlfwInitHint
import io.github.kotlinopenfoundation.glfw.wrapper.hints.mapToGlfwInt
import io.github.kotlinopenfoundation.glfw.wrapper.types.*
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.cinterop.*
import kotlin.concurrent.AtomicReference
import kotlin.concurrent.atomics.ExperimentalAtomicApi

private val logger = KotlinLogging.logger("GLFW")

// ---------------------------------------------------------------------------
// Initialization, version and error reference
// [GLFW Reference](https://www.glfw.org/docs/3.4/group__init.html)
// ---------------------------------------------------------------------------

/**
 * Initializes the GLFW library.
 *
 * Before most GLFW functions can be used, GLFW must be initialized, and before an application terminates,
 * GLFW should be terminated to free any resources allocated during or after initialization.
 *
 * If initialization fails, [glfwTerminate] is called before returning.
 *
 * This function must only be called from the main thread.
 *
 * @return `true` if initialization was successful.
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwInit(): Boolean {
  GlfwExceptionThrowingErrorHandler.register()
  return GLFW_TRUE == io.github.kotlinopenfoundation.glfw.cinterop.glfwInit()
}

/**
 * Terminates the GLFW library.
 *
 * This function destroys all remaining windows and cursors,
 * restores any modified gamma ramps, and frees any other allocated resources.
 *
 * Once this function is called, you must again call [glfwInit] successfully
 * before you will be able to use most GLFW functions.
 *
 * If GLFW has been successfully initialized, this function should be called before the application exits.
 * If initialization fails, there is no need to call this function,
 * as it is called by [glfwInit] before it returns failure.
 *
 * This function has no effect if GLFW is not initialized.
 *
 * **Notes:**
 * - The contexts of any remaining windows must not be current on any other thread when this function is called.
 * - This function must not be called from a callback.
 * - This function must only be called from the main thread.
 *
 * @since GLFW 1.0
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwTerminate() {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwTerminate()
}

/**
 * Sets hints for the next initialization of GLFW.
 *
 * The values you set hints to are never reset by GLFW, but they only take effect during initialization.
 * Once GLFW has been initialized,
 * any values you set will be ignored until the library is terminated and initialized again.
 *
 * Some hints are platform-specific.
 * These may be set on any platform, but they will only affect their specific platform.
 * Other platforms will ignore them.
 * Setting these hints requires no platform-specific headers or functions.
 *
 * Can be called before GLFW is initialized.
 *
 * This function may be called from any thread.
 *
 * @param hint The init hint to set.
 * @param value The new value of the init hint.
 * @since GLFW 3.3
 */
@OptIn(ExperimentalForeignApi::class)
fun <T> glfwInitHint(hint: GlfwInitHint<T>, value: T) {
  io.github.kotlinopenfoundation.glfw.cinterop.glfwInitHint(hint.glfwConstant, mapToGlfwInt(value))
}

@OptIn(ExperimentalForeignApi::class, ExperimentalAtomicApi::class)
private var storedAllocator = AtomicReference<MemoryAllocator<*>?>(null)

/**
 * Sets a custom memory allocator for GLFW.
 *
 * The allocator will be used for all heap memory allocation by GLFW after this function is called.
 * The allocator is used during [glfwInit] and must outlive the library.
 *
 * To use the default allocator, call this function with a `null` argument.
 *
 * Can be called before GLFW is initialized.
 *
 * This function must only be called from the main thread.
 *
 * @param allocator The new memory allocator, or `null` for the default.
 * @param userData The user data pointer to pass to the allocator.
 * @since GLFW 3.4
 */
@OptIn(ExperimentalForeignApi::class)
fun <T : CPointed> glfwInitAllocator(
  allocator: MemoryAllocator<T>?,
  userData: CPointer<T>? = null
) {
  val oldAllocator = storedAllocator.getAndSet(allocator)
  if (oldAllocator == allocator) return

  if (null == allocator) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwInitAllocator(null)
    logger.debug { "GLFW memory allocator unregistered" }
    return
  }

  memScoped {
    val struct = alloc<GLFWallocator> {
      allocate = staticCFunction { size, userData ->
        storedAllocator.value?.allocate(size, userData?.reinterpret())
      }
      reallocate = staticCFunction { block, size, userData ->
        storedAllocator.value?.reallocate(block, size, userData?.reinterpret())
      }
      deallocate = staticCFunction { block, userData ->
        storedAllocator.value?.deallocate(block, userData?.reinterpret())
      }
      user = userData
    }
    io.github.kotlinopenfoundation.glfw.cinterop.glfwInitAllocator(struct.ptr)
  }
  logger.debug { "GLFW memory allocator registered" }
}

/**
 * Sets the desired Vulkan
 * [`vkGetInstanceProcAddr`](https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetInstanceProcAddr.html)
 * function that GLFW will use for all Vulkan related entry point queries.
 *
 * This feature is mostly useful:
 * - On macOS,
 * - If your copy of the Vulkan loader is in a location where GLFW cannot find it through dynamic loading,
 * - If you are still using the static library version of the loader.
 *
 * If set to `null`,
 * GLFW will try to load the Vulkan loader dynamically by its standard name and get this function from there.
 * This is the default behavior.
 *
 * The function address you set is never reset by GLFW, but it only takes effect during initialization.
 * Once GLFW has been initialized, any updates will be ignored until the library is terminated and initialized again.
 *
 * Can be called before GLFW is initialized.
 *
 * This function must only be called from the main thread.
 *
 * @param loader The Vulkan `vkGetInstanceProcAddr` function, or `null` for the default.
 * @since GLFW 3.4
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwInitVulkanLoader(loader: PFN_vkGetInstanceProcAddr?) {
  TODO("Not implemented — requires Vulkan cinterop dependency")
}

/** Function pointer type that retrieves instance-specific function pointers for Vulkan API commands. */
@OptIn(ExperimentalForeignApi::class)
typealias PFN_vkGetInstanceProcAddr = CPointer<CFunction<(VulkanInstance, CPointer<ByteVar>) -> PFN_vkVoidFunction>>

/** Placeholder function pointer type returned by queries. */
@OptIn(ExperimentalForeignApi::class)
typealias PFN_vkVoidFunction = CPointer<CFunction<() -> Unit>>

// ---------------------------------------------------------------------------
// Version
// ---------------------------------------------------------------------------

/**
 * Retrieves the major, minor, and revision numbers of the GLFW library.
 *
 * It is intended for when you are using GLFW as a shared library
 * and want to ensure that you are using the minimum required version.
 *
 * Can be called before GLFW is initialized.
 *
 * This function may be called from any thread.
 *
 * @return A [GlfwVersion] describing semantic version of the GLFW library.
 * @since GLFW 1.0
 *
 * @see glfwGetVersionString
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetVersion(): GlfwVersion = memScoped {
  val major = alloc<IntVar>()
  val minor = alloc<IntVar>()
  val revision = alloc<IntVar>()
  io.github.kotlinopenfoundation.glfw.cinterop.glfwGetVersion(major.ptr, minor.ptr, revision.ptr)
  GlfwVersion(major.value, minor.value, revision.value)
}

/**
 * Returns the compile-time generated version string of the GLFW library binary.
 *
 * It describes the version, platforms, compiler and any platform or operating system specific compile-time options.
 * It should not be confused with the OpenGL or OpenGL ES version string, queried with `glGetString`.
 *
 * Do not use the version string to parse the GLFW library version.
 * The [glfwGetVersion] function provides the version of the running library binary in numerical format.
 *
 * Do not use the version string to parse what platforms are supported.
 * The [glfwPlatformSupported] function lets you query platform support.
 *
 * Can be called before GLFW is initialized.
 *
 * This function may be called from any thread.
 *
 * @return The GLFW version string.
 * @since GLFW 3.0
 *
 * @see glfwGetVersion
 * @see glfwPlatformSupported
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetVersionString(): String =
  io.github.kotlinopenfoundation.glfw.cinterop.glfwGetVersionString()!!.toKString()

// ---------------------------------------------------------------------------
// Error handling
// ---------------------------------------------------------------------------

/**
 * Callback function signature for error events.
 *
 * @see glfwSetErrorCallback
 */
typealias GlfwErrorCallback = (error: GlfwError) -> Unit

private val errorCallbackReference = AtomicReference<GlfwErrorCallback?>(null)

/**
 * Returns and clears the error code and human-readable description
 * of the last error that occurred on the calling thread.
 *
 * Can be called before GLFW is initialized.
 *
 * This function may be called from any thread.
 *
 * @return The last error that occurred on the calling thread, or `null` if no error has occurred.
 * @since GLFW 3.3
 *
 * @see glfwSetErrorCallback
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetError(): GlfwError? = memScoped {
  val message = allocPointerTo<ByteVar>()
  val code = io.github.kotlinopenfoundation.glfw.cinterop.glfwGetError(message.ptr)
  if (GLFW_NO_ERROR == code)
    null
  else
    GlfwError(code, message.value!!.toKString())
}

/**
 * Sets the error callback, which is called with an error code
 * and a human-readable description each time a GLFW error occurs.
 *
 * The error code is set before the callback is called.
 * Calling [glfwGetError] from the error callback will return the same value as the error code argument.
 *
 * The error callback is called on the thread where the error occurred.
 * If you are using GLFW from multiple threads, your error callback needs to be written accordingly.
 *
 * Once set, the error callback remains set even after the library has been terminated.
 *
 * Can be called before GLFW is initialized.
 *
 * This function must only be called from the main thread.
 *
 * **Reported errors are never fatal.**
 * As long as GLFW was successfully initialized,
 * it will remain initialized and in a safe state until terminated regardless of how many errors occur.
 *
 * @param callback The new callback, or `null` to remove the currently set callback.
 * @since GLFW 3.0
 *
 * @see glfwGetError
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwSetErrorCallback(callback: GlfwErrorCallback?) {
  if (null == callback) {
    io.github.kotlinopenfoundation.glfw.cinterop.glfwSetErrorCallback(null)
    errorCallbackReference.value = null
    logger.debug { "GLFW error handler unregistered" }
    return
  }

  errorCallbackReference.value = callback
  io.github.kotlinopenfoundation.glfw.cinterop.glfwSetErrorCallback(staticCFunction { errorCode, description ->
    val error = GlfwError(errorCode, description!!.toKString())
    errorCallbackReference.value?.invoke(error)
  })
  logger.debug { "GLFW error handler registered" }
}

// ---------------------------------------------------------------------------
// Platform
// ---------------------------------------------------------------------------

/**
 * Returns the platform selected during initialization.
 *
 * The returned platform will always be one of the active platforms in the [GlfwPlatform] enum.
 *
 * This function may be called from any thread.
 *
 * @return The currently selected platform.
 * @throws NotInitializedGlfwException If GLFW has not been initialized.
 * @since GLFW 3.4
 *
 * @see glfwPlatformSupported
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwGetPlatform(): GlfwPlatform {
  val platform = io.github.kotlinopenfoundation.glfw.cinterop.glfwGetPlatform()
  return GlfwPlatform.entries.first { it.glfwValue == platform }
}

/**
 * Returns whether the library was compiled with support for the specified [platform].
 *
 * Can be called before GLFW is initialized.
 *
 * This function may be called from any thread.
 *
 * @param platform The platform to query.
 * @return `true` if the platform is supported, `false` otherwise.
 * @since GLFW 3.4
 *
 * @see glfwGetPlatform
 */
@OptIn(ExperimentalForeignApi::class)
fun glfwPlatformSupported(platform: GlfwPlatform): Boolean {
  return GLFW_TRUE == io.github.kotlinopenfoundation.glfw.cinterop.glfwPlatformSupported(platform.glfwValue)
}
