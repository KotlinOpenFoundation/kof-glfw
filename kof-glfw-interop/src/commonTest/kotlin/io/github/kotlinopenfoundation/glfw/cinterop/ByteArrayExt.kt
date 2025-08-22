package io.github.kotlinopenfoundation.glfw.cinterop

fun ByteArray.readIntLE(offset: Int): Int =
  (this[offset].toInt() and 0xFF) or
      ((this[offset + 1].toInt() and 0xFF) shl 8) or
      ((this[offset + 2].toInt() and 0xFF) shl 16) or
      ((this[offset + 3].toInt() and 0xFF) shl 24)

fun ByteArray.writeIntLE(offset: Int, value: Int) {
  this[offset] = (value and 0xFF).toByte()
  this[offset + 1] = ((value shr 8) and 0xFF).toByte()
  this[offset + 2] = ((value shr 16) and 0xFF).toByte()
  this[offset + 3] = ((value shr 24) and 0xFF).toByte()
}

fun ByteArray.writeShortLE(offset: Int, value: Int) {
  this[offset] = (value and 0xFF).toByte()
  this[offset + 1] = ((value shr 8) and 0xFF).toByte()
}
