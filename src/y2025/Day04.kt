package y2025

import core.AdventOfCode

val Day04 = AdventOfCode(2025, 4) {

    data class Point(val x: Int, val y: Int)

    fun List<String>.charAt(point: Point) = get(point.y)[point.x]

    fun Point.allNeighbours() = listOf(
        Point(x - 1, y - 1),
        Point(x - 1, y),
        Point(x - 1, y + 1),
        Point(x, y - 1),
        Point(x, y + 1),
        Point(x + 1, y - 1),
        Point(x + 1, y),
        Point(x + 1, y + 1),
    )

    fun List<String>.forEach2D(body: (point: Point) -> Unit) = mapIndexed { y, line ->
        line.mapIndexed { x, _ ->
            body(Point(x, y))
        }
    }

    fun List<String>.asMap(): Map<Point, Char> = buildMap {
        forEach2D { put(it, charAt(it)) }
    }

    fun predicate(map: Map<Point, Char>): (Map.Entry<Point, Char>) -> Boolean =
        { (point, char) -> char == '@' && point.allNeighbours().count { map[it] == '@' } < 4 }

    fun Map<Point, Char>.removeAccessible(): Pair<Map<Point, Char>, Int> {
        val accessible = filter(predicate(this))
        return this - accessible.keys to accessible.size
    }

    val inputMap = input.lines.asMap()
    part1 {

        inputMap.removeAccessible().second
    }

    part2 {
        generateSequence(inputMap to null as Int?) { (map, _) -> map.removeAccessible() }
            .takeWhile { it.second != 0 }
            .mapNotNull { it.second }
            .sum()
    }

    solutions(1551, 9784)
}
