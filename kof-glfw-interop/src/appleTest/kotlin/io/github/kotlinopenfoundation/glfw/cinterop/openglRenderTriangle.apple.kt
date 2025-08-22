@file:OptIn(ExperimentalForeignApi::class)

package io.github.kotlinopenfoundation.glfw.cinterop

import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.ExperimentalForeignApi

actual val GL_TRUE: UInt = platform.OpenGL3.GL_TRUE.toUInt()
actual val GL_FALSE: UInt = platform.OpenGL3.GL_FALSE.toUInt()
actual val GL_INFO_LOG_LENGTH: UInt = platform.OpenGL3.GL_INFO_LOG_LENGTH.toUInt()
actual val GL_COMPILE_STATUS: UInt = platform.OpenGL3.GL_COMPILE_STATUS.toUInt()
actual val GL_LINK_STATUS: UInt = platform.OpenGL3.GL_LINK_STATUS.toUInt()
actual val GL_VERTEX_SHADER: UInt = platform.OpenGL3.GL_VERTEX_SHADER.toUInt()
actual val GL_FRAGMENT_SHADER: UInt = platform.OpenGL3.GL_FRAGMENT_SHADER.toUInt()
actual val GL_FLOAT: UInt = platform.OpenGL3.GL_FLOAT.toUInt()
actual val GL_RGB: UInt = platform.OpenGL3.GL_RGB.toUInt()
actual val GL_UNSIGNED_BYTE: UInt = platform.OpenGL3.GL_UNSIGNED_BYTE.toUInt()
actual val GL_COLOR_BUFFER_BIT: UInt = platform.OpenGL3.GL_COLOR_BUFFER_BIT.toUInt()
actual val GL_TRIANGLES: UInt = platform.OpenGL3.GL_TRIANGLES.toUInt()

actual fun glViewport(x: Int, y: Int, width: Int, height: Int) =
  platform.OpenGL3.glViewport(x, y, width, height)

actual fun glClearColor(r: Float, g: Float, b: Float, a: Float) =
  platform.OpenGL3.glClearColor(r, g, b, a)

actual fun glClear(mask: UInt) =
  platform.OpenGL3.glClear(mask)

actual fun glDrawArrays(mode: UInt, first: Int, count: Int) =
  platform.OpenGL3.glDrawArrays(mode, first, count)

actual fun glReadPixels(x: Int, y: Int, width: Int, height: Int, format: UInt, type: UInt, pixels: COpaquePointer?) =
  platform.OpenGL3.glReadPixels(x, y, width, height, format, type, pixels)
