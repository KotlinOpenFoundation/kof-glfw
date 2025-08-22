@file:OptIn(ExperimentalForeignApi::class)

package io.github.kotlinopenfoundation.glfw.cinterop

import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.ExperimentalForeignApi

actual val GL_TRUE: UInt = platform.opengl.GL_TRUE.toUInt()
actual val GL_FALSE: UInt = platform.opengl.GL_FALSE.toUInt()
actual val GL_INFO_LOG_LENGTH: UInt = platform.opengl.GL_INFO_LOG_LENGTH.toUInt()
actual val GL_COMPILE_STATUS: UInt = platform.opengl.GL_COMPILE_STATUS.toUInt()
actual val GL_LINK_STATUS: UInt = platform.opengl.GL_LINK_STATUS.toUInt()
actual val GL_VERTEX_SHADER: UInt = platform.opengl.GL_VERTEX_SHADER.toUInt()
actual val GL_FRAGMENT_SHADER: UInt = platform.opengl.GL_FRAGMENT_SHADER.toUInt()
actual val GL_FLOAT: UInt = platform.opengl.GL_FLOAT.toUInt()
actual val GL_RGB: UInt = platform.opengl.GL_RGB.toUInt()
actual val GL_UNSIGNED_BYTE: UInt = platform.opengl.GL_UNSIGNED_BYTE.toUInt()
actual val GL_COLOR_BUFFER_BIT: UInt = platform.opengl.GL_COLOR_BUFFER_BIT.toUInt()
actual val GL_TRIANGLES: UInt = platform.opengl.GL_TRIANGLES.toUInt()

actual fun glViewport(x: Int, y: Int, width: Int, height: Int) =
  platform.opengl.glViewport(x, y, width, height)

actual fun glClearColor(r: Float, g: Float, b: Float, a: Float) =
  platform.opengl.glClearColor(r, g, b, a)

actual fun glClear(mask: UInt) =
  platform.opengl.glClear(mask)

actual fun glDrawArrays(mode: UInt, first: Int, count: Int) =
  platform.opengl.glDrawArrays(mode, first, count)

actual fun glReadPixels(x: Int, y: Int, width: Int, height: Int, format: UInt, type: UInt, pixels: COpaquePointer?) =
  platform.opengl.glReadPixels(x, y, width, height, format, type, pixels)
