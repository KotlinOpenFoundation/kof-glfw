package io.github.kotlinopenfoundation.glfw.cinterop

import kotlin.experimental.ExperimentalNativeApi
import kotlin.math.abs
import kotlin.math.round

@OptIn(ExperimentalNativeApi::class)
object ImageAssertions {
  fun assertPixelsMatch(
    actual: ByteArray,
    expected: ByteArray,
    pixelComponentTolerance: Int,
    mismatchRatioTolerance: Double
  ) {
    assert(expected.size == actual.size) { "Pixel array size mismatch" }
    val mismatchCount = actual.asSequence().zip(expected.asSequence())
      .count { (actualByte, expectedByte) -> pixelComponentTolerance <= abs(actualByte - expectedByte) }
    val totalComponents = expected.size
    val mismatchRatio = mismatchCount.toDouble() / totalComponents
    assert(mismatchRatio <= mismatchRatioTolerance) {
      val mismatchPercent = round(mismatchRatio * 10_000) / 100
      "Image mismatch: $mismatchCount of $totalComponents components differ (${mismatchPercent}%) beyond the tolerance of $pixelComponentTolerance"
    }
  }
}
