fun main() {

    fun Char.getPriorities() = when {
        isUpperCase() -> code - 38
        isLowerCase() -> code - 96
        else -> error("Unknown char!!")
    }

    fun part1(input: List<String>): Int = input.sumOf { line ->
        line.chunked(line.length/2).map { it.toSet() }.reduce { acc, chars -> acc.intersect(chars) }.sumOf { it.getPriorities() }
    }

    fun part2(input: List<String>): Int = input.chunked(3).sumOf { chunk ->
        chunk.windowed(2).map { it[0].toSet().intersect(it[1].toSet()) }
            .reduce { acc, chars -> acc.intersect(chars) }
            .sumOf { it.getPriorities() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03/Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03/Day03")
    println(part1(input))
    println(part2(input))
}

