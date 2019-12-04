import java.io.*
import java.util.*
import kotlin.collections.ArrayList

fun main() {
    var M = nextInt()
    var arr = readInts(M)
    var ans = ArrayDeque<ArrayList<Int>>()
    for (v in arr.reversed()) {
        if (v == -1) {
            ans.addLast(ArrayList())
        }
        else {
            var front = ans.removeFirst()
            front.add(v)
            ans.addLast(front)
        }
    }
    out.println(ans.size)
    for (list in ans.reversed()) {
        out.print("${list.size} ${list.reversed().joinToString(" ")}")
        out.println()
    }
    out.close()
}

private val out = PrintWriter(System.out)
private val reader = BufferedReader(InputStreamReader(System.`in`))
private fun readLine(): String? = reader.readLine()
private fun readLn() = reader.readLine()!!
private var tokenizer: StringTokenizer = StringTokenizer("")
private fun read(): String {
    while (tokenizer.hasMoreTokens().not()) tokenizer = StringTokenizer(reader.readLine() ?: return "", " ")
    return tokenizer.nextToken()
}
private fun nextInt() = read().toInt()
private fun nextDouble() = read().toDouble()
private fun nextLong() = read().toLong()
private fun readStrings(n: Int) = List(n) { read() }
private fun readInts(n: Int) = List(n) { read().toInt() }
private fun readDoubles(n: Int) = List(n) { read().toDouble() }
private fun readLongs(n: Int) = List(n) { read().toLong() }
