package io

import dijkstra.AdjacencyList
import dijkstra.Edge

class GraphHelper {

    fun parseEdgesToGraph(lines: ArrayList<String>, graph: AdjacencyList<String>): AdjacencyList<String> {
        for (line in lines) {
            try {
                val (source, destination, weightString) = line.split(",")
                val weight = weightString.toDouble()
                graph.createVertex(source)
                graph.createVertex(destination)
                graph.add(source, destination, weight)
            } catch (e: Exception) {
                println("Не вдалося розібрати рядок: $line")
            }
        }
        println(graph)
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