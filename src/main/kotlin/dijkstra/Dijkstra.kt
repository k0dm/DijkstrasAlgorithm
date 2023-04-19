package dijkstra


class Dijkstra<T>(private val graph: AdjacencyList<T> = AdjacencyList()) {

    private fun route(destination: T, paths: HashMap<T, Visit<T>>): ArrayList<Edge<T>> {
        var vertex = destination
        val path = arrayListOf<Edge<T>>()
        loop@ while (true) {
            val visit = paths[vertex] ?: break
            when (visit.type) {
                VisitType.EDGE -> visit.edge?.let {
                    path.add(it)
                    vertex = it.source
                }

                VisitType.START -> break@loop
            }
        }
        return path
    }

    private fun distance(destination: T, paths: HashMap<T, Visit<T>>): Double {
        val path = route(destination, paths) // 1
        return path.sumOf { it.weight ?: 0.0 }
    }

    fun shortestPath(start: T, destination: T): HashMap<T, Visit<T>> {
        val paths: HashMap<T, Visit<T>> = HashMap()
        paths[start] = Visit(VisitType.START)

        val distanceComparator = Comparator<T> { first, second ->
            (distance(second, paths) - distance(first, paths)).toInt()
        }
        // Use the previous Comparator and create a min-priority queue to store the
        //vertices that must be visited.
        val priorityQueue = ComparatorPriorityQueueImpl(distanceComparator)
        // Enqueue the start vertex as the first vertex to visit.
        priorityQueue.enqueue(start)
        while (true) {
            val vertex = priorityQueue.dequeue() ?: break
            if (vertex == destination)
                return paths

            val edges = graph.edges(vertex)
            edges.forEach {
                val weight = it.weight ?: return@forEach
                if (paths[it.destination] == null ||
                    distance(vertex, paths) + weight < distance(it.destination, paths)
                ) {
                    paths[it.destination] = Visit(VisitType.EDGE, it)
                    priorityQueue.enqueue(it.destination)
                }
            }
        }
        return paths
    }

    fun shortestPath(destination: T, paths: HashMap<T, Visit<T>>): Path<T> {
        return Path(route(destination, paths), distance(destination, paths))
    }
}