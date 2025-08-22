package io.github.kotlinopenfoundation.glfw.cinterop

import io.github.kotlinopenfoundation.glfw.cinterop.ImageAssertions.assertPixelsMatch
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.toKString
import platform.posix.getenv
import kotlin.experimental.ExperimentalNativeApi
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

private val logger = KotlinLogging.logger {}

@OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class)
class GlfwTest {
  @OptIn(ExperimentalNativeApi::class)
  private val isMacOSCI: Boolean = Platform.osFamily == OsFamily.MACOSX
      && getenv("CI")?.toKString() == "true"

  @BeforeTest
  fun setUp() {
    glfwSetErrorCallback(staticCFunction { error, description ->
      logger.error { "GLFW Error $error: ${description?.toKString()}" }
    })
  }

  @AfterTest
  fun cleanUp() {
    glfwTerminate()
    glfwSetErrorCallback(null)
  }

  @Test
  fun initAndTerminate() {
    assert(GLFW_TRUE == glfwInit()) { "Failed to initialize GLFW" }
    val version = glfwGetVersionString()?.toKString()
    assert(version != null) { "Failed to get GLFW version string" }
    logger.info { "GLFW version: $version" }
    glfwTerminate()
  }

  @Test
  fun openAndCloseWindow() {
    if (isMacOSCI) return // GLFW NSGL hardcodes NSOpenGLPFAAccelerated — no SW fallback on macOS CI
    assert(GLFW_TRUE == glfwInit()) { "Failed to initialize GLFW" }

    // reset widow hints
    glfwDefaultWindowHints()

    // OpenGL Core Profile
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1)
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE) // Required on macOS

    // Headless-friendly defaults
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)

    // Strip the framebuffer down to the bare minimum the SW renderer accepts
    glfwWindowHint(GLFW_RED_BITS, 8)
    glfwWindowHint(GLFW_GREEN_BITS, 8)
    glfwWindowHint(GLFW_BLUE_BITS, 8)
    glfwWindowHint(GLFW_ALPHA_BITS, 0)   // <-- default is 8, SW renderer may reject this
    glfwWindowHint(GLFW_DEPTH_BITS, 0)   // no depth buffer needed (use FBO anyway)
    glfwWindowHint(GLFW_STENCIL_BITS, 0) // no stencil buffer needed
    glfwWindowHint(GLFW_SAMPLES, 0)      // no MSAA

    // Help the SW renderer: allow integrated/fallback GPU selection
    glfwWindowHint(GLFW_COCOA_GRAPHICS_SWITCHING, GLFW_TRUE)

    val window = glfwCreateWindow(640, 480, "Test Window", null, null)
    assert(window != null) { "Failed to create GLFW window" }
    glfwMakeContextCurrent(window)
    assert(window == glfwGetCurrentContext()) { "Failed to make GLFW window current" }
    glfwDestroyWindow(window)

    glfwTerminate()
  }

  @Test
  fun renderBasicGeometryInWindow() {
    if (isMacOSCI) return // GLFW NSGL hardcodes NSOpenGLPFAAccelerated — no SW fallback on macOS CI
    assertEquals(GLFW_TRUE, glfwInit(), "Failed to initialize GLFW")

    glfwDefaultWindowHints()

    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1)
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE) // Required on macOS

    // Headless-friendly defaults
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)

    // Strip the framebuffer down to the bare minimum the SW renderer accepts
    glfwWindowHint(GLFW_RED_BITS, 8)
    glfwWindowHint(GLFW_GREEN_BITS, 8)
    glfwWindowHint(GLFW_BLUE_BITS, 8)
    glfwWindowHint(GLFW_ALPHA_BITS, 0)   // <-- default is 8, SW renderer may reject this
    glfwWindowHint(GLFW_DEPTH_BITS, 0)   // no depth buffer needed (use FBO anyway)
    glfwWindowHint(GLFW_STENCIL_BITS, 0) // no stencil buffer needed
    glfwWindowHint(GLFW_SAMPLES, 0)      // no MSAA

    // Help the SW renderer: allow integrated/fallback GPU selection
    glfwWindowHint(GLFW_COCOA_GRAPHICS_SWITCHING, GLFW_TRUE)

    val width = 256
    val height = 256
    val window = glfwCreateWindow(width, height, "Triangle Test", null, null)
    assert(window != null) { "Failed to create GLFW window" }
    glfwMakeContextCurrent(window)

    val pixels = openglRenderTriangle(width, height)

    glfwDestroyWindow(window)
    glfwTerminate()

    // Compare rendered output with the reference image
    val reference = BitMapIO.read("src/commonTest/resources/triangle.bmp")
    assertEquals(width, reference.width, "Reference image width mismatch")
    assertEquals(height, reference.height, "Reference image height mismatch")
    assertPixelsMatch(pixels, reference.pixels, 2, 0.01)
  }
}
