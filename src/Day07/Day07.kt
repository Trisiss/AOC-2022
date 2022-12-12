fun main() {

    fun populateSizes(lines: List<String>): Map<String, Int> =  buildMap {
        put("", 0)
        var cwd = ""
        for (line in lines) {
            val match = PATTERN.matchEntire(line) ?: continue
            match.groups[1]?.value?.let { dir ->
                cwd = when (dir) {
                    "/" -> ""
                    ".." -> cwd.substringBeforeLast('/', "")
                    else -> if (cwd.isEmpty()) dir else "$cwd/$dir"
                }
            } ?: match.groups[2]?.value?.toIntOrNull()?.let { size ->
                var dir = cwd
                while (true) {
                    put(dir, getOrElse(dir) { 0 } + size)
                    if (dir.isEmpty()) break
                    dir = dir.substringBeforeLast('/', "")
                }
            }
        }
    }

    fun part1(input: List<String>): Int = populateSizes(input).values.sumOf { if (it <= 10_0000) it else 0 }

    fun part2(input: List<String>): Int {
        val sizes = populateSizes(input)
        val total = sizes.getValue("")
        return sizes.values.filter { 70_000_000 - (total - it) >= 30_000_000 }.min()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07/Day07_test")
    val p1result = part1(testInput)
//    println(p1result)
    check(p1result == 95437)
    val p2result = part2(testInput)
//    println(p2result)
    check(p2result == 24933642)

    val input = readInput("Day07/Day07")
    println(part1(input))
    println(part2(input))
}

private val PATTERN = """[$] cd (.*)|(\d+).*""".toRegex()
