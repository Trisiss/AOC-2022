fun main() {

    fun initListCalories(input: List<String>): List<Int> {
        val listCalories = mutableListOf<Int>()
        var sum = 0
        input.forEachIndexed { index, element ->
            element.toIntOrNull()?.let { num ->
                sum += num
                if (index == input.lastIndex) listCalories.add(sum)
            } ?: listCalories.add(sum).also { sum = 0 }
        }
        return listCalories
    }

    fun part1(input: List<String>): Int = initListCalories(input).max()

    fun part2(input: List<String>): Int {
        val listCalories = initListCalories(input)
        return listCalories.sorted().takeLast(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01/Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01/Day01")
    println(part1(input))
    println(part2(input))
}
