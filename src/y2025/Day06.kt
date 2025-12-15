package y2025

import core.AdventOfCode

val Day06 = AdventOfCode(2025, 6) {

    fun List<String>.parseInput(): List<List<String>> = map { it.split("""\s+""".toRegex()) }

    part1 {

        val parsedInput = input.lines.parseInput()
        val results = mutableListOf<Long>()

        for (i in parsedInput.first().indices) {
            val op = if (parsedInput.last()[i] == "+") {
                { a: Long, b: Long -> a + b }
            } else {
                { a: Long, b: Long -> a * b }
            }

            val values = parsedInput.dropLast(1).map { it[i].toLong() }
            results.add(values.drop(1).fold(values.first(), op))
        }

        results.sum()
    }

    part2 {
        fun String.splitAtAll(indicies: List<Int>): List<String> = indicies.windowed(size = 2, step = 1, partialWindows = true) { window ->

            val startIndex = window[0]
            val endIndex = window.getOrNull(1)?.let { it - 1 } ?: this@splitAtAll.length
            this@splitAtAll.substring(startIndex, endIndex)
        }

        fun List<String>.chunkedAtOperators(): List<List<String>> {
            val operations = this.last()
            val startIndicies = operations.indices.filter { operations[it] != ' ' }

            return map { it.splitAtAll(startIndicies) }
        }

        val evenSizedChunks = input.lines.chunkedAtOperators()

        data class Problem(val values: List<Long>, val operationType: Char)

        val problems = evenSizedChunks.first().indices.map { i ->
            val chunk = evenSizedChunks.map { it[i] }
            val numberLines = chunk.dropLast(1)

            Problem(
                values = numberLines.first().indices.map { x ->
                    numberLines.indices
                        .map { y ->
                            chunk[y][x]
                        }.joinToString("")
                        .trim()
                        .toLong()
                },
                operationType = chunk.last().first(),
            )
        }

        problems.sumOf { (numbers, operator) ->
            numbers.drop(1).fold(numbers.first(), if (operator == '+') Long::plus else Long::times)
        }
    }

    solutions(5733696195703, 10951882745757)
}
