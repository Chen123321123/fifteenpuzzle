# Fifteen Puzzle Solver

A high-performance Fifteen Puzzle (sliding puzzle) solver implemented in Java.

## Features
- **Efficient implementation**: Pure Java, stable runtime, minimal dependencies.
- **Heuristic search**: Uses a fixed, module-level heuristic to guide the search and **avoid full state-space traversal**, drastically reducing the search space.
- **Significant speedup**: Under the same hardware and settings, the average solve time for random **9×9** puzzles drops from **~30 seconds** to **under 0.5 seconds**.
