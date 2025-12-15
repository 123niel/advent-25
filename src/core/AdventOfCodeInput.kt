package core

class AdventOfCodeInput(val str: String) {
    val lines by lazy { str.lines() }
    val sections by lazy { str.split("\n\n").map { AdventOfCodeInput(it) } }
}
