
fun main() {
    var M = nextInt()
    var arr = readInts()

    var N = 0
    for (v in arr) {
        if (v == -1) {
            N++
        }
    }

    var ans = Array(N) { mutableListOf<Int>()}
//    var available = mutableMapOf
    var done = BooleanArray(N)
    var next = IntArray(N)
    var prev = IntArray(N)
    for (i in 0 until N) {
        next[i] = (i + 1) % N
        prev[next[i]] = i
    }
    var curr = 0
    for (v in arr) {

        // With that invariant maintained, we can now place the value somewhere
        // correctly
        if (v == -1) {
            done[curr] = true
            // LinkedList idea:
            next[prev[curr]] = next[curr]
            prev[next[curr]] = prev[curr]
        }
        else {
            ans[curr].add(v)
        }
        curr = next[curr]
    }
    println(N)
    for (list in ans) {
        print("${list.size} ${list.joinToString(" ")}")
        println()
    }
}

private fun nextLine() = readLine()!!
private fun nextInt() = nextLine().toInt()
private fun nextLong() = nextLine().toLong()
private fun readArray() = nextLine().split(" ")
private fun readInts() = readArray().map{ it.toInt() }
private fun readLongs() = readArray().map{ it.toLong() }