package dijkstra

data class Edge<T>(
    val source: T,
    val destination: T,
    val weight: Double? = null
)