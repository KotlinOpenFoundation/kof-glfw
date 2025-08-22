# Contributing to kof-glfw

Contributions are welcome! 
Whether you're fixing a bug, adding a feature, or improving documentation, we appreciate your help.

## Getting Started

1. Fork the repository and clone your fork.
2. Create a new branch for your changes.
3. Make your changes, following the guidelines below.
4. Run the full build and tests locally before submitting:
   ```bash
   ./gradlew build
   ```
5. Open a pull request against `main`.

## Code Guidelines

- Follow existing Kotlin and GLFW coding conventions used in the project.
- Keep changes focused — one PR per concern.
- Write meaningful commit messages that explain *why*, not just *what*.
- Add or update tests for any new or changed functionality.
- Do not introduce unnecessary dependencies.

## Code Review

All contributions go through code review before being merged:
- At least one human reviewer must approve every PR.
- Reviewers verify correctness, readability, security, and test coverage.
- Address review feedback before merging.

## Reporting Issues

- Use GitHub Issues to report bugs or request features.
- Include steps to reproduce for bug reports.
- Check existing issues before opening a new one.

## AI-Generated Code Disclaimer

This project allows contributions authored with the aid of AI coding assistants.

The following rules apply:
- **All AI-generated code must be carefully reviewed by a human developer before being submitted.**
- AI-generated output MUST NOT be committed or merged without human review and approval.
- AI agents MUST NOT push code, create PRs, or merge branches without explicit human approval.
- AI-generated code HAVE TO meet the same quality, security, and testing standards as human-written code.
- AI-generated output SHALL NOT be used as the primary design decision parameter.
