fun main() {


    fun part1(input: String): Int = input
        .windowed(4) {
            it.groupingBy { it }.eachCount().values.max()
        }.indexOfFirst { it == 1 } + 4

    fun part2(input: String): Int = input
        .windowed(14) {
            it.groupingBy { it }.eachCount().values.max()
        }.indexOfFirst { it == 1 } + 14

    // test if implementation meets criteria from the description, like:
    val testInput = readInputText("Day06/Day06_test")
    val p1result = part1(testInput)
    println(p1result)
    check(p1result == 7)
    val p2result = part2(testInput)
    println(p2result)
    check(p2result == 19)

    val input = readInputText("Day06/Day06")
    println(part1(input))
    println(part2(input))
}
