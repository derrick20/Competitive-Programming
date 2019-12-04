import java.io.*
import java.util.*
import kotlin.math.max
import kotlin.math.min

fun main() {
    val out = PrintWriter(System.out)

    var Q = nextInt()
    while (Q-->0) {
        // a >= b >= c
        val (x, y) = readLongs(2)
        // max(x, y) = a + b
        // min(x, y) = a + c or b + c
        val c = 1
        val sum = max(x, y) + c
        val b = sum - min(x, y)
        // if min is a + c, then we are b,
        // but if min is b + c, we are a

        val a = max(x, y) - b
        out.println("$a $b $c")
    }
    out.close()
}

private val _reader = BufferedReader(InputStreamReader(System.`in`))
private fun readLine(): String? = _reader.readLine()
private fun readLn() = _reader.readLine()!!
private var _tokenizer: StringTokenizer = StringTokenizer("")
private fun read(): String {
    while (_tokenizer.hasMoreTokens().not()) _tokenizer = StringTokenizer(_reader.readLine() ?: return "", " ")
    return _tokenizer.nextToken()
}
private fun nextInt() = read().toInt()
private fun nextDouble() = read().toDouble()
private fun nextLong() = read().toLong()
private fun readStrings(n: Int) = List(n) { read() }
private fun readInts(n: Int) = List(n) { read().toInt() }
private fun readDoubles(n: Int) = List(n) { read().toDouble() }
private fun readLongs(n: Int) = List(n) { read().toLong() }