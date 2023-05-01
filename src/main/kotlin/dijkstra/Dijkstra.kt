package dijkstra

import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.PriorityBlockingQueue
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


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

    private fun routeMT(destination: T, paths: ConcurrentHashMap<T, Visit<T>>): ArrayList<Edge<T>> {
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

    private fun distanceMT(destination: T, paths: ConcurrentHashMap<T, Visit<T>>): Double {

        val path = routeMT(destination, paths) // 1
        return path.sumOf { it.weight ?: 0.0 }
    }


    fun shortestPath(start: T, destination: T): HashMap<T, Visit<T>> {
        val paths: HashMap<T, Visit<T>> = HashMap()
        paths[start] = Visit(VisitType.START)

        val distanceComparator = Comparator<T> { first, second ->
            (distance(first,paths) - distance(second, paths)).toInt()
        }
//        val priorityQueue = ComparatorPriorityQueueImpl(distanceComparator)
        val priorityQueue = PriorityQueue(distanceComparator)
        priorityQueue.add(start)

        while (true) {
            val vertex = priorityQueue.poll() ?: break
            if (vertex == destination)
                return paths

            val edges = this.graph.edges(vertex)
           println("Vertex: $vertex")
           println("edges: ${edges.size}")
            edges.forEach {
                val weight = it.weight ?: return@forEach
                if (paths[it.destination] == null ||
                    distance(vertex, paths) + weight < distance(it.destination, paths)
                ) {
                    paths[it.destination] = Visit(VisitType.EDGE, it)
                    priorityQueue.add(it.destination)

                }
            }

             println("queue:  $priorityQueue")
            println("queue size:  ${priorityQueue.size}")
        }
        return paths
    }


     suspend fun shortestPathParallel(start: T, destination: T): HashMap<T, Visit<T>>  {

        val paths = ConcurrentHashMap<T, Visit<T>>()
        paths[start] = Visit(VisitType.START)

        val distanceComparator = Comparator<T> { first, second ->

            val s1 = distanceMT(first, (paths))
            val s2 = distanceMT(second, (paths))
//            println("$first - $second = $s1-$s2 = ${s1 - s2}")
            (s1 - s2).toInt()
        }
        val priorityQueue = PriorityBlockingQueue(1000000, distanceComparator)
        priorityQueue.add(start)


        while (true) {
            val vertex = priorityQueue.poll() ?: break

            if (vertex == destination) {
                return HashMap(paths)
            }

            val edges = graph.edges(vertex)
//            println("Vertex: $vertex")
//            println("edges: ${edges.size}")

//            val executorService = Executors.newFixedThreadPool(16)
            val coroutines = mutableListOf<Job>()

            edges.forEach {
                coroutines.add(CoroutineScope(Job()).launch {

                    val weight = it.weight ?: return@launch
                    if (paths[it.destination] == null ||
                        distanceMT(vertex, paths) + weight < distanceMT(it.destination, paths)
                    ) {
                        paths[it.destination] = Visit(VisitType.EDGE, it)
                        priorityQueue.add(it.destination)
                    }
                })
            }
            coroutines.forEach { it.join() }

//            executorService.shutdown()
//            withContext(Dispatchers.IO) {
//                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)
//            }

//            println("queue:  ${priorityQueue}")
//            println("queue size:  ${priorityQueue.size}")
//            println("set size:  ${priorityQueue.toArray().toSet().size}\n")

        }

        return HashMap(paths)
    }


    fun shortestPath(destination: T, paths: HashMap<T, Visit<T>>): Path<T> {
        return Path(route(destination, paths), distance(destination, paths))
    }
}


































































