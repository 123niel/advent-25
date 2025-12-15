package y2025

import core.AdventOfCode

val Day12 = AdventOfCode(2025, 12) {

    val sections = input.sections.map { it }

    val areas = sections.last().lines.map { line ->
        val size = line.substringBefore(":").split("x")
        val presentCounts = line.substringAfter(": ").split(" ").map { it.toInt() }
        (size[0].toInt() to size[1].toInt()) to presentCounts.sum()
    }

    part1 {
        areas.count { (size, count) ->
            val (width, height) = size
            (width / 3) * (height / 3) >= count
        }
    }

    solutions(599, null)
}
