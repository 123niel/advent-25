package y2025

import core.AdventOfCode
import core.Vec3d
import core.distance

val Day08 = AdventOfCode(2025, 8) {
    val boxes = input.lines.map { line ->
        val (x, y, z) = line.split(",")
        Vec3d(x.toInt(), y.toInt(), z.toInt())
    }

    data class State(
        val circuits: Set<Set<Vec3d>>,
        val possibleConnections: Map<Set<Vec3d>, Double>,
        val lastPair: Set<Vec3d>,
    )

    fun solve(boxes: List<Vec3d>, fastMode: Boolean = true) = sequence {
        val circuits = boxes.map { setOf(it) }.toMutableSet()

        val possibleConnections = boxes
            .allPairs()
            .associate {
                setOf(it.first, it.second) to it.first.distance(it.second)
            }.toMutableMap()

        while (true) {
            val shortestConnection = possibleConnections.minBy { it.value }

            val nearest = shortestConnection.key
            val firstCircuit = circuits.find { nearest.toList().toList()[0] in it }!!
            val secondCircuit = circuits.find { nearest.toList().toList()[1] in it }!!

            val newCircuit = buildSet {
                addAll(firstCircuit)
                addAll(secondCircuit)
            }
            circuits.apply {
                remove(firstCircuit)
                remove(secondCircuit)
                add(newCircuit)
            }

            possibleConnections.apply {
                val newAsList = newCircuit.toList()

                if (fastMode) {
                    for (i in newAsList.indices) {
                        for (j in i + 1..newAsList.lastIndex) {
                            remove(setOf(newAsList[i], newAsList[j]))
                        }
                    }
                } else {
                    remove(nearest)
                }
            }

            yield(State(circuits as Set<Set<Vec3d>>, possibleConnections, nearest))
        }
    }

    part1 {
        val last = solve(boxes, fastMode = false).take(1000).last()
        last.circuits
            .map { it.size }
            .sortedDescending()
            .take(3)
            .reduce(Int::times)
    }

    part2 {
        val (first, second) = solve(boxes).first { it.circuits.size == 1 }.lastPair.toList()

        first.x * second.x
    }

    solutions(123420, 673096646)
}
