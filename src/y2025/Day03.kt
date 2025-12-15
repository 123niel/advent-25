package y2025

import core.AdventOfCode

val Day03 = AdventOfCode(2025, 3) {

    fun String.getMaxDigit(): Int = map { it.digitToInt() }.max()

    part1 {
        input.lines.sumOf {
            val tens = it.substring(0, it.length - 1).getMaxDigit()
            val ones = it.substringAfter(tens.digitToChar()).getMaxDigit()
            tens * 10 + ones
        }
    }

    part2 {
        fun String.highestJoltage(minLenght: Int): String = if (minLenght == 0) {
            ""
        } else if (minLenght == 1) {
            "${this.maxBy { it.digitToInt() }}"
        } else {
            val max = maxBy { it.toChar() }
            if (this.first() == max()) {
                max + this.drop(1).highestJoltage(minLenght - 1)
            } else {
                val prefix = this.substringBefore(max)
                val substringAfter = substringAfter(prefix)
                if (substringAfter.length <= minLenght) {
                    prefix.highestJoltage(minLenght - substringAfter.length) + substringAfter
                } else if (prefix.length >= length - minLenght) {
                    substringAfter.highestJoltage(minLenght)
                } else {
                    substringAfter.highestJoltage(minLenght)
                }
            }
        }

        input.lines.sumOf { line ->
            line.highestJoltage(12).toLong()
        }
    }

    solutions(17452, 173300819005913)
}
