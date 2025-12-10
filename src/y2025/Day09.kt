package y2025

import core.adventOfCode
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

val Day09 = adventOfCode(2025, 9, {
    val vecs = input.lines.map { line ->
        val (x, y) = line.split(",")
        Vec(x.toInt(), y.toInt())
    }

    val rectangles = vecs.allPairs().map { it.sort() }
    val polygonLines = vecs.plus(vecs.first()).zipWithNext().map { it.sort() }

    part1(4776487744) {
        rectangles.maxOf { it.area() }
    }

    part2(1560299548) {
        rectangles
            .filter { (point1, point2) ->
                polygonLines.none { (line1, line2) ->
                    point1.x < line2.x && point2.x > line1.x && point1.y < line2.y && point2.y > line1.y
                }
            }.maxOf { it.area() }
    }
})

data class Vec(val x: Int, val y: Int) {
    override fun toString() = "($x,$y)"
}

fun Pair<Vec, Vec>.area() = ((second.x - first.x).absoluteValue + 1).toLong() * ((second.y - first.y).absoluteValue + 1).toLong()

fun Pair<Vec, Vec>.sort() = Vec(min(first.x, second.x), min(first.y, second.y)) to Vec(max(first.x, second.x), max(first.y, second.y))

fun <T> List<T>.allPairs() = buildList {
    for (i in this@allPairs.indices) {
        for (j in i + 1..this@allPairs.lastIndex) {
            add(this@allPairs[i] to this@allPairs[j])
        }
    }
}
