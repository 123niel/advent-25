package y2025

import core.AdventOfCode

val Day10 = AdventOfCode(2025, 10) {

    data class Machine(
        val indicators: Int,
        val buttons: List<List<Int>>,
        val joltage: List<Int>,
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

    part1 {

        input.lines.sumOf { line ->
            val machine = line.parseMachine()

            val buttons = machine.buttons.map { button -> button.sumOf { 1 shl it } }

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

    xpart2 {
        input.lines
            .sumOf { line ->

                val machine = line.parseMachine()

                val start = List(machine.joltage.size) { 0 }

                generateSequence(listOf(start) to false) { (states, _) ->
                    val nextStates = states
                        .flatMap { counters ->
                            machine.buttons.map { button ->
                                counters.mapIndexed { index, counter ->
                                    if (index in button) {
                                        counter + 1
                                    } else {
                                        counter
                                    }
                                }
                            }
                        }.distinct()
                        .filter {
                            (machine.joltage zip it).all { (joltageReq, count) -> count <= joltageReq }
                        }
                    nextStates to nextStates.any { (machine.joltage zip it).all { (joltageReq, count) -> count == joltageReq } }
                }.takeWhile { !it.second }.count()
            }
    }

    solutions(532, null)
}
