import java.util.ArrayList
import java.util.LinkedList

class MaximumFlow {

  private var V = 0
  private var adj = ArrayList<LinkedList<IntArray>>()
  private var level = IntArray(0)

  private fun addEdge(u: Int, v: Int, w: Int) {
    adj[u].add(intArrayOf(v, w))
    adj[v].add(intArrayOf(u, 0))
  }

  private fun bfs(s: Int, t: Int): Boolean {
    for (i in level.indices) {
      level[i] = -1
    }
    val queue = LinkedList<Int>()
    queue.add(s)
    level[s] = 0
    while (queue.isNotEmpty()) {
      val u = queue.poll()
      for (p in adj[u]) {
        val v = p[0]
        val weight = p[1]
        if (level[v] < 0 && weight > 0) {
          queue.add(v)
          level[v] = level[u] + 1
        }
      }
    }
    return level[t] > 0
  }

  private fun dfs(u: Int, t: Int, flow: IntArray): Int {
    if (u == t) return flow[0]
    var flowChange = 0
    var f: Int
    for (p in adj[u]) {
      val v = p[0]
      val weight = p[1]
      if (level[v] == level[u] + 1 && weight > 0) {
        f = dfs(v, t, intArrayOf(Math.min(flow[0] - flowChange, weight)))
        if (f == 0) {
          level[v] = -1
        }
        flowChange += f
        p[1] -= f
        adj[v][p[0]][1] += f
        if (flowChange == flow[0]) return flowChange
      }
    }
    return flowChange
  }

  fun dinicMaxFlow(s: Int, t: Int): Int {
    var maxFlow = 0
    while (bfs(s, t)) {
      maxFlow += dfs(s, t, intArrayOf(Int.MAX_VALUE))
    }
    return maxFlow
  }

  fun getMaxFlow(graph: ArrayList<IntArray>, s: Int, t: Int): Int {
    V = graph.size
    adj = ArrayList()
    for (i in 0 until V) {
      adj.add(LinkedList())
    }
    level = IntArray(V)
    for (i in graph.indices) {
      addEdge(graph[i][0], graph[i][1], graph[i][2])
    }
    return dinicMaxFlow(s, t)
  }

}
