package dijkstra

interface Graph<T> {
    fun createVertex(data: T): Vertex<T>

    fun add(
        source: Vertex<T>,
        destination: Vertex<T>,
        weight: Double?
    )

    fun setRandomEdges(maxNumberOfEdges: Int, maxWeight: Int)

    fun getRandomVertex(): Vertex<T>

    fun edges(source: Vertex<T>): ArrayList<Edge<T>>

    fun weight(
        source: Vertex<T>,
        destination: Vertex<T>
    ): Double?
}

