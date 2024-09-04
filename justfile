# https://github.com/casey/just

# List available recipes in the order in which they appear in this file
_default:
  @just --list --unsorted

# start local development environment
dev:
  mill -w lib.jvm.run

# generate BSP (build server protocol) project
gen-bsp:
  mill mill.bsp.BSP/install

# generate IntelliJ IDEA project
gen-idea:
  mill mill.idea.GenIdea/idea

# format source code
format:
  scalafmt lib

# count lines of code in repo
cloc:
  # ignores generated code
  cloc --vcs=git --fullpath
