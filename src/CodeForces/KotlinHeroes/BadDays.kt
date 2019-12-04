import java.io.*

fun main() {
    val out = PrintWriter(System.out)

    var N = nextInt()
    var arr = readInts()
    var ultimate = 0
    var penultimate = 0
    var ct = 0
    for (i in 0 until N) {
        if (arr[i] < penultimate) {
            ct++
        }
        else if (arr[i] >= ultimate) {
            // Both shift up too get arr[i]
            penultimate = ultimate
            ultimate = arr[i]
        }
        else if (arr[i] >= penultimate) {
            // Otherwise, only one can shift up
            penultimate = arr[i]
        }
    }
    out.println(ct)
    out.close()
}

private fun nextLine() = readLine()!!
private fun nextInt() = nextLine().toInt()
private fun nextLong() = nextLine().toLong()
private fun readArray() = nextLine().split(" ")
private fun readInts() = readArray().map{ it.toInt() }
private fun readLongs() = readArray().map{ it.toLong() }