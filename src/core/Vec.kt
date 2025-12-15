package core

import kotlin.math.pow
import kotlin.math.sqrt

data class Vec(val x: Int, val y: Int) {
    override fun toString() = "($x,$y)"
}

data class Vec3d(
    val x: Int,
    val y: Int,
    val z: Int,
) {
    override fun toString() = "($x,$y,$z)"
}

fun Int.pow(exp: Int) = toDouble().pow(exp)

fun Vec3d.distance(other: Vec3d) = sqrt((x - other.x).pow(2) + (y - other.y).pow(2) + (z - other.z).pow(2))
