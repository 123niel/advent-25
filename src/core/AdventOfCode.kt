package core

import com.toldoven.aoc.notebook.AocClient

fun <T : Number> adventOfCode(
    year: Int,
    day: Int,
    block: AdventOfCodeBuilder<T>.() -> Unit,
): () -> Unit {
    val client by lazy { AocClient.fromEnv().interactiveDay(year, day) }

    val input by lazy { AdventOfCodeInput(client.input()) }

    val builder = AdventOfCodeBuilder<T>(input).apply { block() }
    return {
        if (builder.expected1 != null) {
            checkWithTimer("Part1", builder.expected1!!, builder.part1)
        } else {
            withTimer("Part 1", builder.part1)
        }

        if (builder.expected2 != null) {
            checkWithTimer("Part2", builder.expected2!!, builder.part2)
        } else {
            withTimer("Part 2", builder.part2)
        }
    }
}

class AdventOfCodeBuilder<T>(val input: AdventOfCodeInput) {
    var part1: () -> T = { TODO() }
    var part2: () -> T = { TODO() }

    var expected1: T? = null
    var expected2: T? = null

    fun part1(expected: T? = null, block: () -> T) {
        part1 = block
        expected1 = expected
    }

    fun part2(expected: T? = null, block: () -> T) {
        part2 = block
        expected2 = expected
    }
}
