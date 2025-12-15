package y2025

import core.AdventOfCode

val Day02 = AdventOfCode(2025, 2) {
    val ranges = input.str.split(",").map {
        val parts = it.split("-")
        (parts[0].toLong()..parts[1].toLong())
    }

    part1 {
        fun LongRange.invalidIds(): List<Long> = filter {
            val str = it.toString()

            str.substring(str.length / 2) == str.substring(0, str.length / 2)
        }
        ranges.sumOf { it.invalidIds().sum() }
    }

    part2 {
        fun LongRange.invalidIds(): List<Long> = filter {
            val str = it.toString()
            for (i in 1..str.length / 2) {
                val regex = Regex("(${str.substring(0, i)})+")
                if (str.matches(regex)) {
                    return@filter true
                }
            }

            false
        }

        ranges.sumOf { it.invalidIds().sum() }
    }

    solutions(28846518423, 31578210022)
}
