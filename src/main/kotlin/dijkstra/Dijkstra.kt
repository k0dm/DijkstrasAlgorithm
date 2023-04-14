package dijkstra


class Dijkstra<T>(private val graph: AdjacencyList<T>) {


    private fun route(destination: Vertex<T>, paths: HashMap<Vertex<T>, Visit<T>>): ArrayList<Edge<T>> {
        var vertex = destination // Start at the destination vertex.
        val path = arrayListOf<Edge<T>>() // Create a list of edges to store the path.
        loop@ while (true) {
            val visit = paths[vertex] ?: break
            when (visit.type) {
                VisitType.EDGE -> visit.edge?.let { // As long as you've not reached the start case, continue to extract the next edge.
                    path.add(it) // Add this edge to the path.
                    vertex = it.source //Set the current vertex to the edge’s source vertex. This moves you closer to the start vertex.
                }
                VisitType.START -> break@loop // Once the while loop reaches the start case, you've completed the path and return it.
            }
        }
        return path
    }

    private fun distance(destination: Vertex<T>, paths: HashMap<Vertex<T>, Visit<T>>): Double {
        val path = route(destination, paths) // 1
        return path.sumOf { it.weight ?: 0.0 }
    }

    fun shortestPath(start: Vertex<T>): HashMap<Vertex<T>, Visit<T>> {
        val paths: HashMap<Vertex<T>, Visit<T>> = HashMap()
        paths[start] = Visit(VisitType.START) // Define paths and initialize it with the start vertex.
        // Create a Comparator which uses distances between vertices for sorting
        val distanceComparator = Comparator<Vertex<T>> { first, second ->
            (distance(second, paths) - distance(first, paths)).toInt()
        }
        // Use the previous Comparator and create a min-priority queue to store the
        //vertices that must be visited.
        val priorityQueue =
            ComparatorPriorityQueueImpl(distanceComparator)
        // Enqueue the start vertex as the first vertex to visit.
        priorityQueue.enqueue(start)

         while (true) {
            val vertex = priorityQueue.dequeue() ?: break
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

    fun shortestPath(destination: Vertex<T>, paths: HashMap<Vertex<T>, Visit<T>>): ArrayList<Edge<T>> {
        return route(destination, paths)
    }


//    fun getAllShortestPath(source: Vertex<T>, graph: AdjacencyList<T>): HashMap<Vertex<T>,
//            ArrayList<Edge<T>>> {
//        val paths = HashMap<Vertex<T>, ArrayList<Edge<T>>>() // 1
//        val pathsFromSource = shortestPath(source) // 2
//        graph.edges().forEach { // 3
//            val path = shortestPath(it, pathsFromSource)
//            paths[it] = path
//        }
//        return paths // 4
//    }
}