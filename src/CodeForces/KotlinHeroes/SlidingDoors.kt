import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.util.*

fun main() {
    var N = nextInt()
    var M = nextInt()

    var widths = nextInts(N)
    var prefix = IntArray(N + 1)
    for (i in 1 until N + 1) {
        prefix[i] = prefix[i - 1] + widths[i - 1]
    }

    var Q = nextInt()
    while (Q-->0) {
        var K = nextInt()
        var doors = mutableListOf<Int>()
        doors.add(0)
        for (i in 0 until K) {
            doors.add(nextInt())
        }
        doors.add(M + 1)
        var used = 0

        for (i in 1 until doors.size) {
            var space = doors[i] - doors[i - 1] - 1
            var lo = used
            var hi = N
            // Get to the last spot with prefix sum <= space
            // 111000
            // This should be an obvious optimization.
            // Whenever we are sweeping something to search for the optimal,
            // think binary search.
            while (lo < hi) {
                var mid = (lo + hi) / 2 + 1
                if (prefix[mid] - prefix[used] <= space) {
                    lo = mid
                }
                else {
                    hi = mid - 1
                }
            }
            used = lo
        }
        out.println(if (used == N) "YES" else "NO")
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
private fun nextLong() = read().toLong()
private fun nextStrings(n: Int) = List(n) { read() }
private fun nextInts(n: Int) = List(n) { read().toInt() }
private fun nextLongs(n: Int) = List(n) { read().toLong() }
