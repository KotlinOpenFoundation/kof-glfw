@file:OptIn(ExperimentalForeignApi::class)

package io.github.kotlinopenfoundation.glfw.cinterop

import kotlinx.cinterop.*
import kotlinx.cinterop.invoke

// language=glsl
const val VERT_SRC = """
#version 450 core
layout(location = 0) in vec2 a_pos;
layout(location = 1) in vec3 a_color;
out vec3 v_color;
void main() {
    gl_Position = vec4(a_pos, 0.0, 1.0);
    v_color = a_color;
}
"""

// language=glsl
const val FRAG_SRC = """
#version 450 core
in  vec3 v_color;
out vec4 frag_color;
void main() {
    frag_color = vec4(v_color, 1.0);
}
"""

val VERTICES = floatArrayOf(
  /*    x       y      r     g     b  */
  0.0f, 0.6f, 1.0f, 0.0f, 0.0f,  // top - red
  -0.6f, -0.4f, 0.0f, 1.0f, 0.0f, // left - green
  0.6f, -0.4f, 0.0f, 0.0f, 1.0f,  // right - blue
)

expect val GL_TRUE: UInt
expect val GL_FALSE: UInt
expect val GL_INFO_LOG_LENGTH: UInt
expect val GL_COMPILE_STATUS: UInt
expect val GL_LINK_STATUS: UInt
expect val GL_VERTEX_SHADER: UInt
expect val GL_FRAGMENT_SHADER: UInt
expect val GL_FLOAT: UInt
expect val GL_RGB: UInt
expect val GL_UNSIGNED_BYTE: UInt
expect val GL_COLOR_BUFFER_BIT: UInt
expect val GL_TRIANGLES: UInt

expect fun glViewport(x: Int, y: Int, width: Int, height: Int)
expect fun glClearColor(r: Float, g: Float, b: Float, a: Float)
expect fun glClear(mask: UInt)
expect fun glDrawArrays(mode: UInt, first: Int, count: Int)
expect fun glReadPixels(x: Int, y: Int, width: Int, height: Int, format: UInt, type: UInt, pixels: COpaquePointer?)

object GL {

  // --- Function pointer types ---
  private typealias GlCreateShaderFunc = CPointer<CFunction<(UInt) -> UInt>>
  private typealias GlDeleteShaderFunc = CPointer<CFunction<(UInt) -> Unit>>
  private typealias GlShaderSourceFunc = CPointer<CFunction<(UInt, Int, CPointer<CPointerVar<ByteVar>>?, CPointer<IntVar>?) -> Unit>>
  private typealias GlCompileShaderFunc = CPointer<CFunction<(UInt) -> Unit>>
  private typealias GlAttachShaderFunc = CPointer<CFunction<(UInt, UInt) -> Unit>>

  private typealias GlGetShaderivFunc = CPointer<CFunction<(UInt, UInt, CPointer<IntVar>?) -> Unit>>
  private typealias GlGetShaderInfoLogFunc = CPointer<CFunction<(UInt, Int, CPointer<IntVar>?, CPointer<ByteVar>?) -> Unit>>

  private typealias GlCreateProgramFunc = CPointer<CFunction<() -> UInt>>
  private typealias GlLinkProgramFunc = CPointer<CFunction<(UInt) -> Unit>>
  private typealias GlUseProgramFunc = CPointer<CFunction<(UInt) -> Unit>>
  private typealias GlDeleteProgramFunc = CPointer<CFunction<(UInt) -> Unit>>

  private typealias GlGetProgramivFunc = CPointer<CFunction<(UInt, UInt, CPointer<IntVar>?) -> Unit>>
  private typealias GlGetProgramInfoLogFunc = CPointer<CFunction<(UInt, Int, CPointer<IntVar>?, CPointer<ByteVar>?) -> Unit>>

  private typealias GlCreateVertexArraysFunc = CPointer<CFunction<(Int, CPointer<UIntVar>?) -> Unit>>
  private typealias GlBindVertexArrayFunc = CPointer<CFunction<(UInt) -> Unit>>
  private typealias GlDeleteVertexArraysFunc = CPointer<CFunction<(Int, CPointer<UIntVar>?) -> Unit>>
  private typealias GlVertexArrayVertexBufferFunc = CPointer<CFunction<(UInt, UInt, UInt, Long, Int) -> Unit>>
  private typealias GlEnableVertexArrayAttribFunc = CPointer<CFunction<(UInt, UInt) -> Unit>>
  private typealias GlVertexArrayAttribFormatFunc = CPointer<CFunction<(UInt, UInt, Int, UInt, UInt, UInt) -> Unit>>
  private typealias GlVertexArrayAttribBindingFunc = CPointer<CFunction<(UInt, UInt, UInt) -> Unit>>
  private typealias GlCreateBuffersFunc = CPointer<CFunction<(Int, CPointer<UIntVar>?) -> Unit>>
  private typealias GlNamedBufferStorageFunc = CPointer<CFunction<(UInt, Long, COpaquePointer?, UInt) -> Unit>>
  private typealias GlDeleteBuffersFunc = CPointer<CFunction<(Int, CPointer<UIntVar>?) -> Unit>>

  // --- Loaded functions ---
  private val _glCreateShader: GlCreateShaderFunc by lazy { load("glCreateShader") }
  private val _glDeleteShader: GlDeleteShaderFunc by lazy { load("glDeleteShader") }
  private val _glShaderSource: GlShaderSourceFunc by lazy { load("glShaderSource") }
  private val _glCompileShader: GlCompileShaderFunc by lazy { load("glCompileShader") }
  private val _glAttachShader: GlAttachShaderFunc by lazy { load("glAttachShader") }

  private val _glGetShaderiv: GlGetShaderivFunc by lazy { load("glGetShaderiv") }
  private val _glGetShaderInfoLog: GlGetShaderInfoLogFunc by lazy { load("glGetShaderInfoLog") }

  private val _glCreateProgram: GlCreateProgramFunc by lazy { load("glCreateProgram") }
  private val _glLinkProgram: GlLinkProgramFunc by lazy { load("glLinkProgram") }
  private val _glUseProgram: GlUseProgramFunc by lazy { load("glUseProgram") }
  private val _glDeleteProgram: GlDeleteProgramFunc by lazy { load("glDeleteProgram") }

  private val _glGetProgramiv: GlGetProgramivFunc by lazy { load("glGetProgramiv") }
  private val _glGetProgramInfoLog: GlGetProgramInfoLogFunc by lazy { load("glGetProgramInfoLog") }

  private val _glCreateVertexArrays: GlCreateVertexArraysFunc by lazy { load("glCreateVertexArrays") }
  private val _glBindVertexArray: GlBindVertexArrayFunc by lazy { load("glBindVertexArray") }
  private val _glDeleteVertexArrays: GlDeleteVertexArraysFunc by lazy { load("glDeleteVertexArrays") }
  private val _glVertexArrayVertexBuffer: GlVertexArrayVertexBufferFunc by lazy { load("glVertexArrayVertexBuffer") }
  private val _glEnableVertexArrayAttrib: GlEnableVertexArrayAttribFunc by lazy { load("glEnableVertexArrayAttrib") }
  private val _glVertexArrayAttribFormat: GlVertexArrayAttribFormatFunc by lazy { load("glVertexArrayAttribFormat") }
  private val _glVertexArrayAttribBinding: GlVertexArrayAttribBindingFunc by lazy { load("glVertexArrayAttribBinding") }
  private val _glCreateBuffers: GlCreateBuffersFunc by lazy { load("glCreateBuffers") }
  private val _glNamedBufferStorage: GlNamedBufferStorageFunc by lazy { load("glNamedBufferStorage") }
  private val _glDeleteBuffers: GlDeleteBuffersFunc by lazy { load("glDeleteBuffers") }

  // --- Generic loader helper ---
  private fun <T : CPointed> load(name: String): CPointer<T> {
    val ptr = glfwGetProcAddress(name) ?: error("Failed to load OpenGL function: $name")
    return ptr.reinterpret()
  }

  // --- Public wrappers ---
  fun createShader(type: UInt): UInt {
    return _glCreateShader(type)
  }

  fun shaderSource(shader: UInt, source: String) {
    memScoped {
      val strings = allocArrayOf(source.cstr.ptr)
      _glShaderSource(shader, 1, strings, null)
    }
  }

  fun compileShader(shader: UInt) {
    _glCompileShader(shader)
  }

  fun getShaderiv(shader: UInt, pname: UInt): Int {
    return memScoped {
      val params = alloc<IntVar>()
      _glGetShaderiv(shader, pname, params.ptr)
      params.value
    }
  }

  fun getShaderInfoLog(shader: UInt): String {
    memScoped {
      val length = getShaderiv(shader, GL_INFO_LOG_LENGTH)
      if (length <= 1) return ""

      val actualLength = alloc<IntVar>()
      val buffer = allocArray<ByteVar>(length)
      _glGetShaderInfoLog(shader, length, actualLength.ptr, buffer)
      return buffer.readBytes(actualLength.value).decodeToString()
    }
  }

  fun getProgramiv(shader: UInt, pname: UInt): Int {
    return memScoped {
      val params = alloc<IntVar>()
      _glGetProgramiv(shader, pname, params.ptr)
      params.value
    }
  }

  fun getProgramInfoLog(program: UInt): String {
    memScoped {
      val length = getProgramiv(program, GL_INFO_LOG_LENGTH)
      if (length <= 1) return ""

      val actualLength = alloc<IntVar>()
      val buffer = allocArray<ByteVar>(length)
      _glGetProgramInfoLog(program, length, actualLength.ptr, buffer)
      return buffer.readBytes(actualLength.value).decodeToString()
    }
  }

  fun createProgram(): UInt {
    return _glCreateProgram()
  }

  fun deleteShader(shader: UInt) {
    _glDeleteShader(shader)
  }

  fun attachShader(program: UInt, shader: UInt) {
    _glAttachShader(program, shader)
  }

  fun linkProgram(program: UInt) {
    _glLinkProgram(program)
  }

  fun useProgram(program: UInt) {
    _glUseProgram(program)
  }

  fun createVertexArray(): UInt {
    memScoped {
      val vao = alloc<UIntVar>()
      _glCreateVertexArrays(1, vao.ptr)
      return vao.value
    }
  }

  fun createBuffer(): UInt {
    memScoped {
      val buffer = alloc<UIntVar>()
      _glCreateBuffers(1, buffer.ptr)
      return buffer.value
    }
  }

  fun namedBufferStorage(buffer: UInt, size: Long, data: COpaquePointer?, flags: UInt) {
    _glNamedBufferStorage(buffer, size, data, flags)
  }

  fun vertexArrayVertexBuffer(vao: UInt, bindingIndex: UInt, buffer: UInt, offset: Long, stride: Int) {
    _glVertexArrayVertexBuffer(vao, bindingIndex, buffer, offset, stride)
  }

  fun enableVertexArrayAttrib(vao: UInt, index: UInt) {
    _glEnableVertexArrayAttrib(vao, index)
  }

  fun vertexArrayAttribFormat(
    vao: UInt,
    attribIndex: UInt,
    size: Int,
    type: UInt,
    normalized: Boolean,
    relativeOffset: UInt
  ) {
    val normalized: UInt = if (normalized) GL_TRUE else GL_FALSE
    _glVertexArrayAttribFormat(vao, attribIndex, size, type, normalized, relativeOffset)
  }

  fun vertexArrayAttribBinding(vao: UInt, attribIndex: UInt, bindingIndex: UInt) {
    _glVertexArrayAttribBinding(vao, attribIndex, bindingIndex)
  }

  fun bindVertexArray(vao: UInt) {
    _glBindVertexArray(vao)
  }

  fun deleteVertexArray(vao: UInt) {
    memScoped {
      _glDeleteVertexArrays(1, alloc<UIntVar>().apply { value = vao }.ptr)
    }
  }

  fun deleteBuffer(buffer: UInt) {
    memScoped {
      _glDeleteBuffers(1, alloc<UIntVar>().apply { value = buffer }.ptr)
    }
  }

  fun deleteProgram(program: UInt) {
    _glDeleteProgram(program)
  }
}

fun compileShader(type: UInt, src: String): UInt {
  val sharedHandle = GL.createShader(type)
  GL.shaderSource(sharedHandle, src)
  GL.compileShader(sharedHandle)
  if (GL_TRUE.toInt() != GL.getShaderiv(sharedHandle, GL_COMPILE_STATUS)) {
    throw Error("Shader compile error: ${GL.getShaderInfoLog(sharedHandle)}")
  }
  return sharedHandle
}

fun linkProgram(vertSrc: String, fragSrc: String): UInt {
  val vert = compileShader(GL_VERTEX_SHADER, vertSrc)
  val frag = compileShader(GL_FRAGMENT_SHADER, fragSrc)

  val prog = GL.createProgram()
  GL.attachShader(prog, vert)
  GL.attachShader(prog, frag)
  GL.linkProgram(prog)

  GL.deleteShader(vert)
  GL.deleteShader(frag)

  if (GL_TRUE.toInt() != GL.getProgramiv(prog, GL_LINK_STATUS)) {
    throw Error("Program link error: ${GL.getProgramInfoLog(prog)}")
  }
  return prog
}

fun openglRenderTriangle(width: Int, height: Int): ByteArray {
  val prog = linkProgram(VERT_SRC, FRAG_SRC)
  val vao = GL.createVertexArray()
  val vbo = GL.createBuffer()

  // Upload vertex data
  VERTICES.usePinned { pinned ->
    GL.namedBufferStorage(vbo, VERTICES.size * sizeOf<FloatVar>(), pinned.addressOf(0).reinterpret(), 0u)
  }
  // Bind VBO to VAO binding point 0
  GL.vertexArrayVertexBuffer(vao, 0u, vbo, 0, 5 * sizeOf<FloatVar>().toInt())

  // Attribute 0: position (xy)
  GL.enableVertexArrayAttrib(vao, 0u)
  GL.vertexArrayAttribFormat(vao, 0u, 2, GL_FLOAT, false, 0u)
  GL.vertexArrayAttribBinding(vao, 0u, 0u)

  // Attribute 1: color (rgb)
  GL.enableVertexArrayAttrib(vao, 1u)
  GL.vertexArrayAttribFormat(vao, 1u, 3, GL_FLOAT, false, 2u * sizeOf<FloatVar>().toUInt())
  GL.vertexArrayAttribBinding(vao, 1u, 0u)

  // Render
  glViewport(0, 0, width, height)
  glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
  glClear(GL_COLOR_BUFFER_BIT)

  GL.useProgram(prog)
  GL.bindVertexArray(vao)
  glDrawArrays(GL_TRIANGLES, 0, 3)

  // Cleanup
  GL.deleteVertexArray(vao)
  GL.deleteBuffer(vbo)
  GL.deleteProgram(prog)

  // Read pixels
  val pixels = ByteArray(width * height * 3)
  pixels.usePinned { pinned ->
    glReadPixels(
      0,
      0,
      width,
      height,
      GL_RGB,
      GL_UNSIGNED_BYTE,
      pinned.addressOf(0)
    )
  }
  return pixels
}
