fun main() {

    fun rangeEntry(firstRange: Pair<Int, Int>, secondRange: Pair<Int, Int>): Boolean =
        firstRange.first >= secondRange.first && firstRange.second <= secondRange.second
    || secondRange.first >= firstRange.first && secondRange.second <= firstRange.second

    fun rangeOverlap(firstRange: Pair<Int, Int>, secondRange: Pair<Int, Int>): Boolean =
        firstRange.first >= secondRange.first && firstRange.first <= secondRange.second
    || secondRange.first >= firstRange.first && secondRange.first <= firstRange.second

    fun part1(input: List<String>): Int = input.count { line ->
        line.split(',').map {
            it.split('-').map { it.toInt() }
        }.run { rangeEntry(get(0)[0] to get(0)[1], get(1)[0] to get(1)[1]) }
    }

    fun part2(input: List<String>): Int = input.count { line ->
        line.split(',').map {
            it.split('-').map { it.toInt() }
        }.run { rangeOverlap(get(0)[0] to get(0)[1], get(1)[0] to get(1)[1]) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04/Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04/Day04")
    println(part1(input))
    println(part2(input))
}

