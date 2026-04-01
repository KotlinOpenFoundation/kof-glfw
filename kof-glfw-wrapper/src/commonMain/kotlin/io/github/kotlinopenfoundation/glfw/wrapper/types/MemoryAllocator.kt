package io.github.kotlinopenfoundation.glfw.wrapper.types

import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Provides custom heap memory allocation, reallocation, and deallocation.
 *
 * @param T The type of user data that is passed to allocation methods, which extends [CPointed].
 * @since GLFW 3.4
 */
@OptIn(ExperimentalForeignApi::class)
interface MemoryAllocator<T : CPointed> {
  /** Returns a memory block at least [size] bytes long, or `null` if allocation failed. */
  fun allocate(size: ULong, userData: CPointer<T>?): COpaquePointer?

  /** Returns a memory block at least size-bytes-long, or `null` if allocation failed. */
  fun reallocate(block: COpaquePointer?, size: ULong, userData: CPointer<T>?): COpaquePointer?

  /** Deallocates the specified memory block */
  fun deallocate(block: COpaquePointer?, userData: CPointer<T>?): COpaquePointer?
}
