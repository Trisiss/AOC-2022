fun main() {

    operator fun String.component1() = this[0]
    operator fun String.component2() = this[1]
    operator fun String.component3() = this[2]

    fun Char.toShape(): Shape = when (this) {
        'A', 'X' -> Shape.Rock()
        'B', 'Y' -> Shape.Paper()
        'C', 'Z' -> Shape.Scissors()
        else -> error("Unexpected char $this")
    }

    fun Pair<Shape, Shape>.getScore(): Int = second.getWinnerScore(first) + second.selectScore

    fun part1(input: List<String>): Int = input.sumOf { s ->
            val (theirShape, _, myShape) = s
            (theirShape.toShape() to myShape.toShape()).getScore()
        }

    fun part2(input: List<String>): Int = input.sumOf { s ->
        val (therShape, _, resultRound) = s
        therShape.toShape().getWinnerScore(resultRound)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02/Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02/Day02")
    println(part1(input))
    println(part2(input))
}

private sealed interface Shape {
    fun getWinnerScore(other: Shape): Int

    fun getWinnerScore(resultRound: Char): Int

    val selectScore: Int

    class Rock : Shape {
        override val selectScore = 1

        override fun getWinnerScore(other: Shape): Int = when (other) {
            is Rock -> 3
            is Scissors -> 6
            else -> 0
        }

        override fun getWinnerScore(resultRound: Char): Int = when (resultRound) {
            'X' -> Scissors().run { selectScore + this.getWinnerScore(this@Rock) }
            'Y' -> Rock().run { selectScore + this.getWinnerScore(this@Rock) }
            else -> Paper().run { selectScore + this.getWinnerScore(this@Rock) }
        }
    }

    class Paper : Shape {
        override val selectScore = 2

        override fun getWinnerScore(other: Shape): Int = when (other) {
            is Paper -> 3
            is Rock -> 6
            else -> 0
        }

        override fun getWinnerScore(resultRound: Char): Int = when (resultRound) {
            'X' -> Rock().run { selectScore + this.getWinnerScore(this@Paper) }
            'Y' -> Paper().run { selectScore + this.getWinnerScore(this@Paper) }
            else -> Scissors().run { selectScore + this.getWinnerScore(this@Paper) }
        }
    }

    class Scissors : Shape {
        override val selectScore = 3

        override fun getWinnerScore(other: Shape): Int = when (other) {
            is Scissors -> 3
            is Paper -> 6
            else -> 0
        }

        override fun getWinnerScore(resultRound: Char): Int = when (resultRound) {
            'X' -> Paper().run { selectScore + this.getWinnerScore(this@Scissors) }
            'Y' -> Scissors().run { selectScore + this.getWinnerScore(this@Scissors) }
            else -> Rock().run { selectScore + this.getWinnerScore(this@Scissors) }
        }
    }
}
