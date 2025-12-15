package y2025

import core.AdventOfCode

val Day07 = AdventOfCode(2025, 7) {

    val lines = input.lines

    part1 {
        lines
            .drop(1)
            .fold(setOf(lines.first().indexOf('S')) to 0) { (beams, splitCount), line ->
                val splitters = line.indices.filter { line[it] == '^' && it in beams }

                beams
                    .flatMap { if (it in splitters) setOf(it - 1, it + 1) else setOf(it) }
                    .toSet() to splitCount + splitters.size
            }.second
    }

    part2 {
        val start = buildMap {
            lines.first().indices.forEach { put(it, 0L) }
            put(lines.first().indexOf('S'), 1)
        }

        lines
            .drop(1)
            .fold(start) { beams, line ->
                buildMap {
                    putAll(beams)
                    val splitters = line.indices.filter { line[it] == '^' && it in beams }
                    splitters.forEach { splitterIndex ->

                        this[splitterIndex - 1] = this[splitterIndex - 1]!! + beams[splitterIndex]!!
                        this[splitterIndex + 1] = this[splitterIndex + 1]!! + beams[splitterIndex]!!
                        put(splitterIndex, 0)
                    }
                }
            }.values
            .sum()
    }

    solutions(1602, 135656430050438)
}
