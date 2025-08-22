package io.github.kotlinopenfoundation.glfw.cinterop

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.posix.fclose
import platform.posix.fopen
import platform.posix.fread
import platform.posix.fwrite

@OptIn(ExperimentalForeignApi::class)
object BitMapIO {
  fun read(path: String): BitMapImage {
    val file = fopen(path, "rb") ?: error("Cannot open $path for reading")
    try {
      val header = ByteArray(54)
      header.usePinned { fread(it.addressOf(0), 1u.toULong(), 54u.toULong(), file) }

      require(header[0] == 0x42.toByte() && header[1] == 0x4D.toByte()) { "Not a BMP file" }

      val width = header.readIntLE(18)
      val height = header.readIntLE(22)
      val dataOffset = header.readIntLE(10)

      // Seek to pixel data
      val skip = ByteArray(dataOffset - 54)
      if (skip.isNotEmpty()) {
        skip.usePinned { fread(it.addressOf(0), 1u.toULong(), skip.size.toULong(), file) }
      }

      val rowSize = (width * 3 + 3) / 4 * 4
      val row = ByteArray(rowSize)
      val pixels = ByteArray(width * height * 3)

      for (y in 0 until height) {
        row.usePinned { fread(it.addressOf(0), 1u.toULong(), rowSize.toULong(), file) }
        for (x in 0 until width) {
          val srcIdx = x * 3
          val dstIdx = (y * width + x) * 3
          pixels[dstIdx] = row[srcIdx + 2]     // R (from BGR)
          pixels[dstIdx + 1] = row[srcIdx + 1] // G
          pixels[dstIdx + 2] = row[srcIdx]     // B
        }
      }

      return BitMapImage(width, height, pixels)
    } finally {
      fclose(file)
    }
  }

  fun write(path: String, image: BitMapImage) {
    val rowSize = (image.width * 3 + 3) / 4 * 4
    val imageSize = rowSize * image.height
    val fileSize = 54 + imageSize

    val file = fopen(path, "wb") ?: error("Cannot open $path for writing")
    try {
      // BMP File Header (14 bytes) + Info Header (40 bytes)
      val header = ByteArray(54)
      header[0] = 0x42 // 'B'
      header[1] = 0x4D // 'M'
      header.writeIntLE(2, fileSize)
      header.writeIntLE(10, 54)     // pixel data offset
      header.writeIntLE(14, 40)     // info header size
      header.writeIntLE(18, image.width)
      header.writeIntLE(22, image.height)
      header.writeShortLE(26, 1)    // planes
      header.writeShortLE(28, 24)   // bits per pixel
      header.writeIntLE(34, imageSize)

      header.usePinned { fwrite(it.addressOf(0), 1u.toULong(), 54u.toULong(), file) }

      // Pixel data: convert RGB to BGR, already bottom-up from OpenGL
      val row = ByteArray(rowSize)
      for (y in 0 until image.height) {
        for (x in 0 until image.width) {
          val srcIdx = (y * image.width + x) * 3
          val dstIdx = x * 3
          row[dstIdx] = image.pixels[srcIdx + 2]     // B
          row[dstIdx + 1] = image.pixels[srcIdx + 1] // G
          row[dstIdx + 2] = image.pixels[srcIdx]     // R
        }
        row.usePinned { fwrite(it.addressOf(0), 1u.toULong(), rowSize.toULong(), file) }
      }
    } finally {
      fclose(file)
    }
  }
}
