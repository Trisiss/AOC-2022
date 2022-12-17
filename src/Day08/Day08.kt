fun main() {

    fun getNumberVisibleTree(listTree: List<Int>, element: Int, isRevert: Boolean = false): Int {
        val range = if (isRevert) listTree.indices.reversed() else listTree.indices
        var count = 0
        for (i in range) {
            if (listTree[i] >= element) {
                return count + 1
            }
            count++
        }
        return listTree.size
    }

    fun isVisible(treeMap: List<List<Int>>, position: Pair<Int, Int>): Boolean {
        if (position.first == 0 || position.first == treeMap.lastIndex) return true
        if (position.second == 0 || position.second == treeMap[position.first].lastIndex) return true
        val element = treeMap[position.first][position.second]
        val list = mutableListOf<Int>()
        for (i in treeMap.indices) {
            list.add(treeMap[i][position.second])
        }
        val sublistStart = treeMap[position.first].subList(0, position.second)
        val sublistEnd = treeMap[position.first].subList(position.second + 1, treeMap[position.first].size)
        if (sublistStart.max() < element || sublistEnd.max() < element) return true
        val sublistTop = list.subList(0, position.first)
        val sublistBottom = list.subList(position.first + 1, list.size)
        if (sublistTop.max() < element || sublistBottom.max() < element) return true
        return false
    }

    fun getScenicScore(treeMap: List<List<Int>>, position: Pair<Int, Int>): Int {
        val multipleList = mutableListOf<Int>()
        if (position.first == 0 || position.first == treeMap.lastIndex) return 0
        if (position.second == 0 || position.second == treeMap[position.first].lastIndex) return 0
        val element = treeMap[position.first][position.second]
        val list = mutableListOf<Int>()
        for (i in treeMap.indices) {
            list.add(treeMap[i][position.second])
        }
        val sublistStart = treeMap[position.first].subList(0, position.second)
        val sublistEnd = treeMap[position.first].subList(position.second + 1, treeMap[position.first].size)
        val sublistTop = list.subList(0, position.first)
        val sublistBottom = list.subList(position.first + 1, list.size)

        multipleList.add(getNumberVisibleTree(sublistStart, element, true))
        multipleList.add(getNumberVisibleTree(sublistEnd, element))
        multipleList.add(getNumberVisibleTree(sublistTop, element, true))
        multipleList.add(getNumberVisibleTree(sublistBottom, element))

        return multipleList.reduce { mult, el -> mult * el }
    }

    fun populateTreeMap(input: List<String>): List<List<Int>> {
        val treeMap = mutableListOf<MutableList<Int>>()
        input.forEachIndexed { indexLine, line ->
            val list = mutableListOf<Int>()
            val test = line.split("").filter { it.isNotEmpty() }
            test.forEachIndexed { indexColumn, column ->
                list.add(indexColumn, column.toInt())
            }
            treeMap.add(indexLine, list)
        }
        return treeMap
    }

    fun part1(input: List<String>): Int {
        val treeMap = populateTreeMap(input)
        val treeMapBool = mutableListOf<MutableList<Boolean>>()
        treeMap.forEachIndexed { indexLine, line ->
            val list = mutableListOf<Boolean>()
            line.forEachIndexed { indexCol, col ->
                val isVisible = isVisible(treeMap, indexLine to indexCol)
                list.add(indexCol, isVisible)
            }
            treeMapBool.add(indexLine, list)
        }
        return treeMapBool.sumOf { it.count { it } }
    }

    fun part2(input: List<String>): Int {
        val treeMap = populateTreeMap(input)
        val treeMapScenic = mutableListOf<List<Int>>()
        treeMap.forEachIndexed { indexLine, line ->
            val list = mutableListOf<Int>()
            line.forEachIndexed { indexCol, col ->
                val scenicScore = getScenicScore(treeMap, indexLine to indexCol)
                list.add(indexCol, scenicScore)
            }
            treeMapScenic.add(indexLine, list)
        }
        return treeMapScenic.maxOf { it.max() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08/Day08_test")
    val p1result = part1(testInput)
//    println(p1result)
//    check(p1result == 21)
    val p2result = part2(testInput)
//    println(p2result)
//    check(p2result == 8)

    val input = readInput("Day08/Day08")
    println(part1(input))
    println(part2(input))
}

