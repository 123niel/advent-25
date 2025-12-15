package y2025

import core.AdventOfCode

val Day11 = AdventOfCode(2025, 11) {

    val devices = input.lines
        .associate {
            val name = it.substringBefore(":")
            val outputs = it.substringAfter(": ").split(" ")
            name to outputs
        }

    part1 {
        fun String.countPaths(destination: String): Int = if (destination == this) {
            1
        } else {
            devices[this]?.sumOf { nextName -> nextName.countPaths(destination) } ?: 0
        }
        "you".countPaths("out")
    }
    part2 {
        val cache = mutableMapOf<Triple<String, Boolean, Boolean>, Long>()

        fun String.countPaths(
            destination: String,
            hasFFT: Boolean = false,
            hasDAC: Boolean = false,
        ): Long = cache.getOrPut(Triple(this, hasFFT, hasDAC)) {
            if (destination == this) {
                if (hasFFT && hasDAC) 1 else 0
            } else {
                devices[this]!!.sumOf { nextName ->
                    nextName.countPaths(
                        destination = destination,
                        hasFFT = hasFFT || this == "fft",
                        hasDAC = hasDAC || this == "dac",
                    )
                }
            }
        }

        "svr".countPaths("out")
    }

    solutions(534, 499645520864100)
}
