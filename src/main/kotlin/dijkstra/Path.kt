package dijkstra

import java.awt.DisplayMode

class Path<T>(private val path: ArrayList<Edge<T>>, private val distance: Double) {

    fun showPath() {
        path.apply {
            reverse()
            forEach { // 3
                println(
                    "${it.source} --|${it.weight ?: 0.0}|--> + " +
                            "${it.destination}"
                )
            }
        }
    }

    fun showDistance() {
        println("Distance: $distance")
    }
}