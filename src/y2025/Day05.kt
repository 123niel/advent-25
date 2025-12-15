package y2025

import core.AdventOfCode
import kotlin.math.max

val Day05 = AdventOfCode(2025, 5) {

    fun List<String>.parseInput(): Pair<List<LongRange>, List<Long>> {
        val ranges = takeWhile { it.isNotEmpty() }.map {
            val (start, end) = it.split("-")
            start.toLong()..end.toLong()
        }

        val ids = takeLastWhile { it.isNotEmpty() }.map {
            it.toLong()
        }

        return ranges to ids
    }

    part1 {
        val (ranges, ids) = input.lines.parseInput()
        ids.count { id -> ranges.any { range -> id in range } }
    }

    part2 {
        fun List<LongRange>.mergeOverlapping(): List<LongRange> {
            val merged = mutableListOf<LongRange>()

            sortedBy { it.first }.forEach { range ->
                val prev = merged.removeLastOrNull()
                if (prev != null) {
                    if (prev.last >= range.start) {
                        merged.add(prev.start..max(prev.last, range.last))
                    } else {
                        merged.add(prev)
                        merged.add(range)
                    }
                } else {
                    merged.add(range)
                }
            }
            return merged.toList()
        }
        input.lines
            .parseInput()
            .first
            .mergeOverlapping()
            .sumOf { it.last - it.first + 1 }
    }
    solutions(775, 350684792662845)
}
