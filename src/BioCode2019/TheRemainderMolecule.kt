import java.util.*
import kotlin.math.max
import kotlin.math.min

fun main() {
    val jin = Scanner(System.`in`)
    val n = jin.nextInt()
    val atps = Array(n) { jin.nextInt() }
    atps.sort()
    val floor = IntArray(200001)
    var j = 0
    for (k in 1..200000) {
        if (j < n && k == atps[j]) {
            floor[k] = k
            while (j < n && k == atps[j]) {
                j++
            }
        } else {
            floor[k] = floor[k - 1]
        }
    }
    var answer = 0
    for (atp in atps) {
        for (m in 2..min(atp - 1, 320)) {
            if (floor[m] == m) {
                answer = max(answer, atp % m)
            }
        }
        if (atp > 320) {
            var beta = atp - 1
            while (beta <= 100000) {
                beta += atp
                if (floor[beta] > atp) {
                    answer = max(answer, floor[beta] % atp)
                }
            }
        }
    }
    println(answer)
}