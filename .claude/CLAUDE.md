# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Kotlin Multiplatform Native bindings for GLFW 3.4. Provides type-safe Kotlin/Native wrappers around the GLFW C library for windowing and input. Part of the Kotlin Open Foundation.

Three-tier API design:
1. **C interop** — 1:1 bindings to the GLFW C API
2. **Wrapper** — Thin type-safe wrapper around the C bindings
3. **Kotlin API** — Kotlin-first idiomatic API (planned)

## Build Commands

```bash
./gradlew build                    # Full build + tests for the current platform
./gradlew :kof-glfw-wrapper:check  # Build + test the wrapper module only
./gradlew :kof-glfw-interop:check  # Build + test the interop module only
```

GLFW native libraries are downloaded/built automatically during the Gradle build (pre-built for Windows/macOS, compiled from source via CMake on Linux).

## Architecture

### Module Structure

- **`kof-glfw-interop`** — Low-level C interop bindings generated from `glfw.def`. 
  - Package: `io.github.kotlinopenfoundation.glfw.cinterop`.
  - Handles GLFW library downloading/compilation per platform.
- **`kof-glfw-wrapper`** — Thin type-safe wrapper around C bindings.
  - Package: `io.github.kotlinopenfoundation.glfw.wrapper`.
  - Depends on `kof-glfw-interop`.
- **`kof-glfw-kotlin`** — High-level idiomatic Kotlin API.
  - Planned (not yet created).
  - Package: `io.github.kotlinopenfoundation.glfw.kotlin`.
  - Depends on `kof-glfw-wrapper`.

### Native Targets

MinGW x64 (Windows), macOS x64/arm64, Linux x64/arm64.

### C Interop

- Definition file: `kof-glfw-interop/src/nativeInterop/cinterop/glfw.def`
- Forward declarations: `kof-glfw-interop/src/commonMain/kotlin/cnames/structs/ForwardDeclarations.kt`
- Platform-specific compiler/linker options are set per target in the `.def` file

### Wrapper Module Patterns

The wrapper lives under `kof-glfw-wrapper/src/commonMain/kotlin/.../wrapper/` and is organized into subpackages:

- **`enums/`** — Enum classes wrapping GLFW integer constants. Each enum has a `glfwValue: Int` property and a `fromGlfwValue()` companion for reverse lookup. Exception: `GlfwModifierKey` is a `value class` (bitmask flags, not an enum).
- **`exception/`** — `GlfwException` base class with 14 typed subclasses (one per GLFW error code). All extend `RuntimeException`.
- **`functions/`** — Top-level GLFW functions grouped into modules: `CoreModule`, `WindowModule`, `MonitorModule`, `InputModule`, `ContextModule`, `VulkanModule`.
- **`hints/`** — Window and init hints using sealed interfaces for type-safe builder patterns. Subpackages: `window/`, `init/`.
- **Root** — Handle types (`GlfwWindowHandle`, `GlfwMonitorHandle`, `GlfwCursorHandle` as zero-cost value classes wrapping `CPointer`), data classes (`GlfwVideoMode`, `GlfwImage`, `GlfwGamepadState`, `GlfwGammaRamp`), and utility types (`IntVector2`, `FloatVector2`, `Bounds`).

Key conventions:
- All wrapper types are prefixed with `Glfw`
- `@OptIn(ExperimentalForeignApi::class)` on all FFI-touching code
- KDoc on all public APIs with `@property`, `@since`, and GLFW doc references
- Error handler (`GlfwExceptionThrowingErrorHandler`) maps GLFW error codes to typed exceptions, logs at DEBUG level via `kotlin-logging`

### Build Configuration

- Versions managed in `libs.versions.toml` (Kotlin, JDK, GLFW, etc.)
- Git-based versioning via `com.palantir.git-version`
- Power assert plugin enabled for test assertions
- Configuration cache and build cache enabled

### CI

GitHub Actions (`.github/workflows/build.yaml`)
- Windows and Linux tests use Mesa for software OpenGL rendering in headless mode

## For AI Assistants

All contributions go through code review before being merged:
- Do not push, merge, or create PRs without explicit human instruction.
- Flag uncertainty rather than guessing.
- Prefer minimal, focused changes over broad refactoring.
- Follow existing code conventions and patterns.
- Cover all code paths with meaningful tests.
