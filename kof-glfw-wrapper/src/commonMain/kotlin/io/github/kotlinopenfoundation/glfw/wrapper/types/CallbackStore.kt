package io.github.kotlinopenfoundation.glfw.wrapper.types

import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Stores per-pointer callback associations for GLFW objects (e.g., per-window callbacks).
 *
 * @param T The native struct type of the pointer key.
 * @param C The callback function type.
 */
@OptIn(ExperimentalForeignApi::class)
internal class CallbackStore<T : CPointed, C> {
  private val callbacks = mutableMapOf<CPointer<T>, C>()

  operator fun set(pointer: CPointer<T>, callback: C): C? = callbacks.put(pointer, callback)
  operator fun get(pointer: CPointer<T>): C? = callbacks[pointer]
  fun remove(pointer: CPointer<T>): C? = callbacks.remove(pointer)
}
