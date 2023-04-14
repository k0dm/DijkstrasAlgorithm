package dijkstra

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class AdjacencyList<T> : Graph<T> {

    private val adjacencies: HashMap<Vertex<T>, ArrayList<Edge<T>>> = HashMap()

    override fun createVertex(data: T): Vertex<T> {
        val vertex = Vertex(adjacencies.count(), data)
        adjacencies[vertex] = ArrayList()
        return vertex
    }


    override fun add(source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
        adjacencies[source]?.add(Edge(source, destination, weight))
        adjacencies[destination]?.add(Edge(destination, source, weight))
    }

    override fun edges(source: Vertex<T>): ArrayList<Edge<T>> =
        adjacencies[source] ?: arrayListOf()

    override fun weight(source: Vertex<T>, destination: Vertex<T>): Double? {
        return edges(source).firstOrNull { it.destination == destination }?.weight
    }

    override fun setRandomEdges(maxNumberOfEdges: Int, maxWeight: Int) {

        val vertices = adjacencies.keys

        for (currentVertex in vertices) {

            for (i in 0..Random().nextInt(maxNumberOfEdges)) {
                var randomVertex: Vertex<T>
                do {
                    randomVertex = vertices.random()
                } while (currentVertex == randomVertex)

                this.add(currentVertex, randomVertex, Random().nextInt(maxWeight) + 1.0)
            }
        }

    }

    override fun getRandomVertex() = adjacencies.keys.random()
    override fun allEdges(): ArrayList<Edge<T>> {
        val edges = ArrayList<Edge<T>>()
        adjacencies.keys.forEach {
            adjacencies[it]?.let { listOfEdges -> edges.addAll(listOfEdges) }
        }
        return edges
    }

    override fun toString(): String {
        return buildString { // 1
            adjacencies.forEach { (vertex, edges) -> // 2
                val edgeString = edges.joinToString { it.destination.data.toString() } // 3
                append("${vertex.data} ---> [ $edgeString ]\n") // 4
            }
        }
    }
}