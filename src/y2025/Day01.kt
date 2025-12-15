package y2025

import core.AdventOfCode

val Day01 = AdventOfCode(2025, 1) {
    val steps = input.lines.map { it.first() to it.substring(1).toInt() }

    part1 {
        var dial = 50
        var zeroCount = 0

        for ((direction, amount) in steps) {
            dial = if (direction == 'R') {
                (dial + amount) % 100
            } else {
                (dial + 100 - amount) % 100
            }
            if (dial == 0) {
                zeroCount += 1
            }
        }

        zeroCount
    }

    part2 {
        var dial = 50
        var zeroCount = 0

        for ((direction, amount) in steps) {
            val rotation = Math.floorMod(amount, 100)
            val clicks = Math.floorDiv(amount, 100)

            zeroCount += clicks

            if (direction == 'R') {
                if (dial + rotation >= 100) {
                    zeroCount++
                }
                dial = (dial + rotation) % 100
            } else {
                if (dial != 0 && dial - rotation <= 0) {
                    zeroCount++
                }
                dial = Math.floorMod(dial - rotation, 100)
            }
        }

        zeroCount
    }

    solutions(1089, 6530)
}
