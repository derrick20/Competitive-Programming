import java.util.*
import kotlin.math.max

fun main() {
    val jin = Scanner(System.`in`)
    val n = jin.nextInt()
    val m = jin.nextInt()
    val root = jin.nextInt() - 1
    val adj = Array(n) { mutableListOf<Int>() }
    for (j in 1 until n) {
        val a = jin.nextInt() - 1
        val b = jin.nextInt() - 1
        adj[a].add(b)
        adj[b].add(a)
    }
    val colors = IntArray(n) { jin.nextInt() }
    val parent = IntArray(n) { -1 }
    val dfs = mutableListOf<Int>()
    val stack = Stack<Int>()
    stack.push(root)
    while (!stack.isEmpty()) {
        val node = stack.pop()
        dfs.add(node)
        for (child in adj[node]) {
            if (child != parent[node]) {
                parent[child] = node
                stack.push(child)
            }
        }
    }
    val left = IntArray(n)
    val right = IntArray(n)
    // DFS Numbering to get left and right range (subtree covering)
    for (j in n - 1 downTo 0) {
        left[dfs[j]] = j
        right[dfs[j]] = max(right[dfs[j]], j)
        if (j != 0) {
            right[parent[dfs[j]]] = max(right[parent[dfs[j]]], right[dfs[j]])
        }
    }
    println("dfs = $dfs")
    println("left = ${left.contentToString()}")
    println("right = ${right.contentToString()}")
    val queries = Array(m) { j -> Pair(j, jin.nextInt() - 1) }
    queries.sortBy { right[it.second] }
    val bit = BIT(0, n - 1)
    val lastOccurrence = mutableMapOf<Int, Int>()
    var k = 0
    val answer = IntArray(m)
    for (j in 0 until n) {
        val color = colors[dfs[j]]
        // Greedily plae the least occurrence of any given color as far back
        // as possible so that they will be part of the upcoming intervals
        // to be queried. This ensures that if it were to be part of any future
        // query, then it will be properly accounted for.
        if (lastOccurrence.contains(color)) {
            // Decrement the past values of that color
            // Repeated the same color later on, so we delete that subtree
            // since we are the ones who now possess it
            bit.update(lastOccurrence[color]!!, -1)
        }
        // store the node at which this color was painted
        lastOccurrence[color] = j
        // Since we have sorted by j, now we know that the nearest possible
        // location of that color will be j. So, we have greedily made it so
        // that if that value is gonna affect our sum, it will as much as it can
        // by being as near as possible.
        bit.update(j, 1)
        while (k < m && right[queries[k].second] == j) {
            // All queries ending at the current position we've checked up too can
            // be answered here.
            answer[queries[k].first] = bit.query(left[queries[k].second], right[queries[k].second])
            k++
        }
    }
    val builder = StringBuilder()
    for (j in 0 until m) {
        builder.append(answer[j])
        builder.append('\n')
    }
    print(builder)
}

class BIT(val treeFrom: Int, treeTo: Int) {
    val `val`: IntArray

    init {
        `val` = IntArray(treeTo - treeFrom + 2)
    }

    fun update(index: Int, delta: Int) {
        var i = index + 1 - treeFrom
        while (i < `val`.size) {
            `val`[i] += delta
            i += i and -i
        }
    }

    fun query(to: Int): Int {
        var res: Int = 0
        var i = to + 1 - treeFrom
        while (i > 0) {
            res += `val`[i]
            i -= i and -i
        }
        return res
    }

    fun query(from: Int, to: Int): Int {
        return if (to < from) {
            0
        } else query(to) - query(from - 1)
    }
}