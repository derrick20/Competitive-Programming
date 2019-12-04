import java.util.*

fun main() {
    val jin = Scanner(System.`in`)
    val n = jin.nextInt()
    val m = jin.nextInt()
    val adj = Array(n + 1) { mutableSetOf<Int>() }
    for (j in 0 until m) {
        val a = jin.nextInt()
        val b = jin.nextInt()
        adj[a].add(b)
        adj[b].add(a)
    }
    val curr = mutableSetOf<Int>()
    for (j in 1..n) {
        var parent = 0
        val toRemove = mutableListOf<Int>()
        // Starting from any of the components we have right now,
        // see if we can join with the node j. If possible,
        // steal their connections, and we won't add it.
        // If it happens to be that j is the one that connects
        // BACK to another one of the components in curr,
        // then we need j to steal all the connections of something
        // in our curr components. So, we delete the curr component,
        // and give our connections to the newly added j, which
        // will then give those connections to the first compoonent
        // which stole its connections
        for (k in curr) {
            if (!adj[k].contains(j)) {
                if (parent == 0) {
                    parent = k
                    adj[parent].retainAll(adj[j])
                } else {
                    adj[parent].retainAll(adj[k])
                    toRemove.add(k)
                }
            }
        }
        for (k in toRemove) {
            // We found something previously placed that is
            // connected, so delete it.
            curr.remove(k)
        }
        if (parent == 0) {
            // Couldn't connect to anything, so add it in
            curr.add(j)
        }
    }
    println(curr.size - 1)
}