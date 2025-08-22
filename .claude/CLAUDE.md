# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Kotlin Multiplatform Native bindings for GLFW 3.4. Provides type-safe Kotlin/Native wrappers around the GLFW C library for windowing and input. Part of the Kotlin Open Foundation.

## Build Commands

```bash
./gradlew build # Full build + tests for the current platform
```

GLFW native libraries are downloaded/built automatically during the Gradle build (pre-built for Windows/macOS, compiled from source via CMake on Linux).

## Architecture

### Module Structure

- **`kof-glfw-interop`** — Active module. Low-level C interop bindings generated from `glfw.def`. Package: `platform.glfw`.
- **`kof-glfw-wrapper`** — Planned (commented out). Thin type-safe wrapper around C bindings.
- **`kof-glfw-kotlin`** — Planned (commented out). High-level idiomatic Kotlin API.

### Native Targets

MinGW x64 (Windows), macOS x64/arm64, Linux x64/arm64.
All configured in `kof-glfw-interop/build.gradle.kts`.

### C Interop

- Definition file: `kof-glfw-interop/src/nativeInterop/cinterop/glfw.def`
- Forward declarations: `kof-glfw-interop/src/commonMain/kotlin/cnames/structs/ForwardDeclarations.kt`
- Platform-specific compiler/linker options are set per target in the `.def` file

### Build Configuration

- Kotlin 2.3.20, JDK 25, Gradle 9.4+
- Version catalog: `libs.versions.toml`
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
