package dijkstra

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class AdjacencyList<T>(private val adjacencies: HashMap<T, ArrayList<Edge<T>>> = HashMap()) : Graph<T> {

    override fun createVertex(data: T): T {
        if (adjacencies[data] == null)
            adjacencies[data] = ArrayList()
        return data
    }

    override fun add(source: T, destination: T, weight: Double?) {
        adjacencies[source]?.add(Edge(source, destination, weight))
        adjacencies[destination]?.add(Edge(destination, source, weight))
    }

    override fun edges(source: T): ArrayList<Edge<T>> =
        adjacencies[source] ?: arrayListOf()

    override fun weight(source: T, destination: T): Double? {
        return edges(source).firstOrNull { it.destination == destination }?.weight
    }

    override fun setRandomEdges(maxNumberOfEdges: Int, maxWeight: Int) {

        val vertices = adjacencies.keys

        for (currentVertex in vertices) {

            for (i in 0..Random().nextInt(maxNumberOfEdges)) {
                var randomVertex: T
                do {
                    randomVertex = vertices.random()
                } while (currentVertex == randomVertex)

                this.add(currentVertex, randomVertex, Random().nextInt(maxWeight) + 1.0)
            }
        }

    }

    override fun getRandomVertex() = adjacencies.keys.random()

    override fun getVertex(name: T): T? {
        return if (adjacencies[name] != null) name
        else null
    }

    override fun allEdges(): ArrayList<Edge<T>> {
        val edges = ArrayList<Edge<T>>()
        adjacencies.keys.forEach {
            adjacencies[it]?.let { listOfEdges -> edges.addAll(listOfEdges) }
        }
        return edges
    }

    override fun size() = adjacencies.size
    override fun clear() {
        adjacencies.clear()
    }

    override fun toString(): String {
        return buildString { // 1
            adjacencies.forEach { (vertex, edges) -> // 2
                val edgeString = edges.joinToString { it.destination.toString() } // 3
                append("$vertex ---> [ $edgeString ]\n") // 4
            }
        }
    }
}