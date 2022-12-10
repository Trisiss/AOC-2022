fun main() {

    fun numbersOfStacks(lines: List<String>): Int =
        lines
            .dropWhile { it.contains("[") }
            .first()
            .split(" ")
            .filter { it.isNotBlank() }
            .maxOf { it.toInt() }

    fun populateStacks(lines: List<String>, onCharacterAdd: (numberOfStack: Int, element: Char) -> Unit) {
        lines
            .filter { it.contains("[") }
            .forEach { line ->
                line.forEachIndexed { index, char ->
                    if (char.isLetter()) {
                        onCharacterAdd(index / 4, char)
                    }
                }
            }
    }

    fun executeMoves(moves: List<Move>, stacks: List<ArrayDeque<Char>>) {
        moves.forEach { move ->
            repeat(move.quantity) { stacks[move.target].addFirst(stacks[move.source].removeFirst()) }
        }
    }

    fun executeMovesGroup(moves: List<Move>, stacks: List<ArrayDeque<Char>>) {
        moves.forEach { move ->
            val movedElements = stacks[move.source].subList(0, move.quantity)
            stacks[move.target].addAll(0, movedElements)
            repeat(move.quantity) { stacks[move.source].removeFirst() }
        }
    }

    fun part1(input: List<String>): String {

        val numberOfStacks = numbersOfStacks(input)
        val stacks = List(numberOfStacks) { ArrayDeque<Char>() }

        populateStacks(input) { numberOfStack, element ->
            stacks[numberOfStack].addLast(element)
        }

        val moves = input.filter { it.contains("move") }.map { it.toMove() }

        executeMoves(moves, stacks)

        return stacks.map { it.first() }.joinToString("")
    }

    fun part2(input: List<String>): String {

        val numberOfStacks = numbersOfStacks(input)
        val stacks = List(numberOfStacks) { ArrayDeque<Char>() }

        populateStacks(input) { numberOfStack, element ->
            stacks[numberOfStack].addLast(element)
        }

        val moves = input.filter { it.contains("move") }.map { it.toMove() }

        executeMovesGroup(moves, stacks)

        return stacks.map { it.first() }.joinToString("")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05/Day05_test")
    val p1result = part1(testInput)
    println(p1result)
    check(p1result == "CMZ")
    val p2result = part2(testInput)
    println(p2result)
    check(p2result == "MCD")

    val input = readInput("Day05/Day05")
    println(part1(input))
    println(part2(input))
}

data class Move(val quantity: Int, val source: Int, val target: Int)

private fun String.toMove(): Move =
    this
    .split(' ')
    .map { it.toIntOrNull() }
    .filterNotNull()
    .run {
        Move(get(0), get(1) - 1, get(2) - 1)
    }