@file:OptIn(ExperimentalForeignApi::class)

package io.github.kotlinopenfoundation.glfw.cinterop

import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.ExperimentalForeignApi

actual val GL_TRUE: UInt = platform.opengl32.GL_TRUE.toUInt()
actual val GL_FALSE: UInt = platform.opengl32.GL_FALSE.toUInt()
actual val GL_INFO_LOG_LENGTH: UInt = platform.opengl32.GL_INFO_LOG_LENGTH.toUInt()
actual val GL_COMPILE_STATUS: UInt = platform.opengl32.GL_COMPILE_STATUS.toUInt()
actual val GL_LINK_STATUS: UInt = platform.opengl32.GL_LINK_STATUS.toUInt()
actual val GL_VERTEX_SHADER: UInt = platform.opengl32.GL_VERTEX_SHADER.toUInt()
actual val GL_FRAGMENT_SHADER: UInt = platform.opengl32.GL_FRAGMENT_SHADER.toUInt()
actual val GL_FLOAT: UInt = platform.opengl32.GL_FLOAT.toUInt()
actual val GL_RGB: UInt = platform.opengl32.GL_RGB.toUInt()
actual val GL_UNSIGNED_BYTE: UInt = platform.opengl32.GL_UNSIGNED_BYTE.toUInt()
actual val GL_COLOR_BUFFER_BIT: UInt = platform.opengl32.GL_COLOR_BUFFER_BIT.toUInt()
actual val GL_TRIANGLES: UInt = platform.opengl32.GL_TRIANGLES.toUInt()

actual fun glViewport(x: Int, y: Int, width: Int, height: Int) =
  platform.opengl32.glViewport(x, y, width, height)

actual fun glClearColor(r: Float, g: Float, b: Float, a: Float) =
  platform.opengl32.glClearColor(r, g, b, a)

actual fun glClear(mask: UInt) =
  platform.opengl32.glClear(mask)

actual fun glDrawArrays(mode: UInt, first: Int, count: Int) =
  platform.opengl32.glDrawArrays(mode, first, count)

actual fun glReadPixels(x: Int, y: Int, width: Int, height: Int, format: UInt, type: UInt, pixels: COpaquePointer?) =
  platform.opengl32.glReadPixels(x, y, width, height, format, type, pixels)
