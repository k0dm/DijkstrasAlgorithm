package dijkstra

interface Graph<T> {
    fun createVertex(data: T):T

    fun add(
        source: T,
        destination: T,
        weight: Double?
    )

    fun setRandomEdges(maxNumberOfEdges: Int, maxWeight: Int)

    fun getRandomVertex(): T

    fun edges(source: T): ArrayList<Edge<T>>
    fun allEdges(): ArrayList<Edge<T>>

    fun weight(
        source: T,
        destination: T
    ): Double?

    fun clear()
}

