package core

import kotlin.time.measureTimedValue

fun String.green(): String = green + this + reset

fun String.red() = red + this + reset

fun String.cyan() = cyan + this + reset

private const val red = "\u001B[31m"
private const val green = "\u001B[92m"
private const val cyan = "\u001b[36m"
private const val reset = "\u001B[0m"

fun <T : Number> withTimer(msg: String, block: () -> T) {
    val (value, time) = measureTimedValue { block() }

    val timeStr = "${time.inWholeMilliseconds}ms".cyan()
    println("$msg\t$timeStr\t$value")
}

fun <T : Number> checkWithTimer(
    msg: String,
    expected: T,
    block: () -> T,
) {
    val (value, time) = measureTimedValue { block() }

    val passed = value == expected

    val timeInMs = "${time.inWholeMilliseconds}ms".cyan()

    val valueStr =
        if (passed) {
            "✅ $value".green()
        } else {
            "❌ $value should be $expected".red()
        }

    println("$msg\t$timeInMs\t$valueStr")
}
