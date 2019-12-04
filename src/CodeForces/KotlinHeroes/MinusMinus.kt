import java.io.*

fun main() {
    val out = PrintWriter(System.out)

    var Q = nextInt()
    while (Q-->0) {
        var s = nextLine()
        var t = nextLine()
        var poss = true
        var j = 0
        for (i in t.indices) {
            if (j < s.length && s[j] == t[i]) {
                j++
                continue
            }
            else {
                if (j + 1 < s.length && s[j] == '-' && s[j + 1] == '-' && t[i] == '+') {
                    j += 2
                }
                else {
                    poss = false
                }
            }
        }
        if (j < s.length) {
            // We didn't finish using s
            poss = false
        }
        out.println(if (poss) "YES" else "NO")
    }
    out.close()
}

private fun nextLine() = readLine()!!
private fun nextInt() = nextLine().toInt()
private fun nextLong() = nextLine().toLong()
private fun readArray() = nextLine().split(" ")
private fun readInts() = readArray().map{ it.toInt() }
private fun readLongs() = readArray().map{ it.toLong() }