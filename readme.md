# Bullshredder

Automatic fallacy detection using LLMs.

## Getting Started

1. Setup on your system:

   - [devbox](https://www.jetpack.io/devbox)
   - [direnv](https://direnv.net/)

   ```bash
   # to enter the devbox dev shell loaded by direnv
   direnv allow
   ```

1. Run the development stack

   ```bash
   just dev
   ```

b1. List other useful commands by typing

   ```bash
   just
   ```

## Key Technologies

### Stack & Development

- [devbox](https://www.jetpack.io/devbox) and [nix flakes](https://zero-to-nix.com/concepts/flakes) for a reproducible dev environment: [devbox.json](devbox.json)
- [direnv](https://direnv.net/) to automatically load dev environment when entering project directory: [.envrc](.envrc)
- [just](https://github.com/casey/just) runner for common commands used in development: [justfile](justfile)
- [earthly](https://docs.earthly.dev/) Dockerfile-like CI with very good caching, locally runnable: [Earthfile](Earthfile)
- [Scala](https://www.scala-lang.org/) programming language, compiled to javascript using [ScalaJS](https://www.scala-js.org/)
- [Mill](https://mill-build.com) build tool: [build.sc](build.sc)
- [Vite](https://vitejs.dev) hot reloading and bundling: [vite.config.mts](vite.config.mts), [main.js](main.js), [index.html](index.html)
- [Mergify](https://docs.mergify.com/) for automatic PR merging with a merge queue: [.mergify.yml](.mergify.yml)
