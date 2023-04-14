import commands.MenuCommand
import dijkstra.AdjacencyList
import dijkstra.Dijkstra
import dijkstra.Vertex
import generator.DataGenerate

fun main(args: Array<String>) {


    val graph = AdjacencyList<String>()
    val singapore = graph.createVertex("Singapore")
    val tokyo = graph.createVertex("Tokyo")
    val hongKong = graph.createVertex("Hong Kong")
    val detroit = graph.createVertex("Detroit")
    val sanFrancisco = graph.createVertex("San Francisco")
    val washingtonDC = graph.createVertex("Washington DC")
    val austinTexas = graph.createVertex("Austin Texas")
    val seattle = graph.createVertex("Seattle")
    graph.add(singapore, hongKong, 300.0)
    graph.add(singapore, tokyo, 500.0)
    graph.add(hongKong, tokyo, 250.0)
    graph.add(tokyo, detroit, 450.0)
    graph.add(tokyo, washingtonDC, 300.0)
    graph.add(hongKong, sanFrancisco, 600.0)
    graph.add(detroit, austinTexas, 50.0)
    graph.add(austinTexas, washingtonDC, 292.0)
    graph.add(sanFrancisco, washingtonDC, 337.0)
    graph.add(washingtonDC, seattle, 277.0)
    graph.add(sanFrancisco, seattle, 218.0)
    graph.add(austinTexas, sanFrancisco, 297.0)


    val dijkstra = Dijkstra(graph)
    val pathsFromA = dijkstra.shortestPath(austinTexas) // 1
    val path = dijkstra.shortestPath(seattle, pathsFromA) // 2

////////////////////////////////////////////////////////////////

//    val stringDataGenerator = DataGenerate.StringDataGenerator()
//    val graph = stringDataGenerator.generate(10000,5,1000)
//
//    val source = graph.getRandomVertex()
//    var destination: Vertex<String>
//
//    do {
//        destination = graph.getRandomVertex()
//    }while (source == destination)
//
//    val dijkstra = Dijkstra(graph)
//    val pathsFromA = dijkstra.shortestPath(source) // 1
//    val path = dijkstra.shortestPath(destination, pathsFromA) // 2
//
    println(path)

    path.apply {
        reverse()
        forEach { // 3
            println(
                "${it.source.data} --|${it.weight ?: 0.0}|--> + " +
                        "${it.destination.data}"
            )
        }
    }


}
