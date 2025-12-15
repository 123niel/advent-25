package core

import com.toldoven.aoc.notebook.AocClient

class AdventOfCode<T : Number>(
    val year: Int,
    val day: Int,
    private val block: AdventOfCodeBuilder<T>.() -> Unit,
) {
    private val client by lazy { AocClient.fromEnv().interactiveDay(year, day) }
    private val input by lazy { AdventOfCodeInput(client.input()) }

    suspend fun run() {
        val builder = AdventOfCodeBuilder<T>(input).apply { block() }

        println("$year - $day")

        if (builder.part1 != null) {
            if (builder.expected1 != null) {
                checkWithTimer("Part1", builder.expected1!!, builder.part1 as suspend () -> Number)
            } else {
                withTimer("Part 1", builder.part1 as suspend () -> Number)
            }
        }
        if (builder.part2 != null) {
            if (builder.expected2 != null) {
                checkWithTimer("Part2", builder.expected2!!, builder.part2 as suspend () -> Number)
            } else {
                withTimer("Part 2", builder.part2 as suspend () -> Number)
            }
        }
    }
}

class AdventOfCodeBuilder<T>(val input: AdventOfCodeInput) {
    var part1: (suspend () -> T)? = null
    var part2: (suspend () -> T)? = null

    var expected1: T? = null
    var expected2: T? = null

    fun part1(block: suspend () -> T) {
        part1 = block
    }

    fun part2(block: suspend () -> T) {
        part2 = block
    }

    fun solutions(part1: T?, part2: T?) {
        expected1 = part1
        expected2 = part2
    }

    fun xpart1(block: suspend () -> T) = Unit

    fun xpart2(block: suspend () -> T) = Unit
}
