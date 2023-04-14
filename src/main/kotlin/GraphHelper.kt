import dijkstra.AdjacencyList
import dijkstra.Edge

class GraphHelper {

    fun parseEdgesToGraph(edges: ArrayList<Edge<String>>): AdjacencyList<String> {
        val graph = AdjacencyList<String>()
        for (edge in edges) {
            graph.add(edge.source, edge.destination, edge.weight)
        }
        return graph
    }

    fun convertToStringList(edges: ArrayList<Edge<String>>): ArrayList<String> {
        val result = ArrayList<String>()
        for (edge in edges) {
            val str = "${edge.source},${edge.destination},${edge.weight}"
            result.add(str)
        }
        return result
    }

}