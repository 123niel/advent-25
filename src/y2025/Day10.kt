package y2025

import core.AdventOfCode

val Day10 = AdventOfCode(2025, 10) {

    data class Machine(
        val indicators: Int,
        val buttons: List<List<Int>>,
        val joltageRequirements: List<Int>,
    )

    fun String.parseMachine(): Machine {
        val indicators = substringBefore("]").drop(1).foldRight(0) { ch, acc ->
            acc * 2 + when (ch) {
                '.' -> 0
                else -> 1
            }
        }
        val buttons = substringAfter("] ").substringBefore(" {").split(" ").map { button ->
            button.substring(1, button.length - 1).split(",").map { it.toInt() }
        }
        val joltage = substringAfter("{").dropLast(1).split(",").map { it.toInt() }

        return Machine(indicators, buttons, joltage)
    }

    fun List<Int>.toBitMask(): Int = sumOf { 1 shl it }


    part1 {

        input.lines.sumOf { line ->
            val machine = line.parseMachine()

            val buttons = machine.buttons.map { button -> button.toBitMask() }

            generateSequence(setOf(0)) { lightStates ->
                lightStates
                    .flatMap { state ->
                        buttons.map { it xor state }.toSet()
                    }.toSet()
            }.takeWhile { states ->

                states.none { it == machine.indicators }
            }.count()
        }
    }

    // https://www.reddit.com/r/adventofcode/comments/1pk87hl/2025_day_10_part_2_bifurcate_your_way_to_victory/
    part2 {
        fun Int.pressButtons(buttons: List<Int>): Int {
            return buttons.fold(this) { acc, button -> acc xor button }
        }

        fun <T> getAllCombinations(buttons: List<T>) =
            buttons.fold(listOf(emptyList<T>())) { combinations, button ->
                combinations.flatMap { previousPresses ->
                    listOf(previousPresses, previousPresses.plusElement(button))
                }
            }


        fun Machine.minPresses(): Int {
            val cache = mutableMapOf<List<Int>, Int>()

            fun minPresses(joltageRequirements: List<Int>): Int = cache.getOrPut(joltageRequirements) {

                if (joltageRequirements.all { it == 0 }) return 0
                if (joltageRequirements.any { it < 0 })
                    return 100000

                val desiredLights = joltageRequirements.foldRight(0) { req, acc ->
                    acc * 2 + when (req % 2 == 1) {
                        true -> 1
                        false -> 0
                    }
                }


                val allCombinations = getAllCombinations(buttons)

                return@getOrPut allCombinations.filter { buttons ->
                    val bitmasks = buttons.map { it.toBitMask() }
                    0.pressButtons(bitmasks) == desiredLights
                }.minOfOrNull { buttons ->

                    val counterStates = buttons.flatMap { it }.groupBy { it }.mapValues { it.value.count() }

                    val remainingJolatages =
                        joltageRequirements.mapIndexed { index, value -> (value - (counterStates[index] ?: 0)) / 2 }

                    buttons.size + 2 * minPresses(remainingJolatages)
                } ?: 100000
            }

            return minPresses(joltageRequirements)
        }


        input.lines
            .sumOf { line ->
                line.parseMachine().minPresses()
            }
    }

    solutions(532, 18387)
}

