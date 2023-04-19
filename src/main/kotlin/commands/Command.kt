package commands

import Invoker
import dijkstra.AdjacencyList
import dijkstra.Dijkstra
import dijkstra.Path
import dijkstra.Visit
import generator.DataGenerate
import io.FileEditor
import io.GraphHelper
import kotlin.system.measureTimeMillis

// Абстрактний клас команди
abstract class Command(private val description: String) {
    abstract fun execute()
    override fun toString() = description

    class GenerateData(private val graph: AdjacencyList<String>) : Command("Generate data") {
        private val dataGenerator = DataGenerate.StringDataGenerator()
        override fun execute() {

            print("Enter number of Vertex: ")
            val numberOfVertex = readln().toIntOrNull()
            print("Enter number of Edges: ")
            val numberOfEdges = readln().toIntOrNull()
            print("Enter max weight: ")
            val maxWeight = readln().toIntOrNull()
            if (numberOfVertex == null || numberOfEdges == null || maxWeight == null) {
                println("Wrong input!!!")
                return
            }
            graph.clear()
            dataGenerator.generate(numberOfVertex, numberOfEdges, maxWeight, graph)
            println("Generated successfully, number of vertices: ${graph.size()}")
        }
    }

    class SaveData(private val graph: AdjacencyList<String>) : Command("Save data") {

        private val fileEditor = FileEditor.Base()
        override fun execute() {
            print("Enter a name of file: ")
            val fileName = readln()
            fileEditor.saveDataToFile(fileName, GraphHelper().convertToStringList(graph.allEdges()))
        }
    }

    class LoadData(private val graph: AdjacencyList<String>) : Command("Load data from file") {
        private val fileEditor = FileEditor.Base()

        override fun execute() {
            print("Enter a name of file or path: ")
            val file = readln()
            if (!fileEditor.openFile(file)) {
                println("This file doesn`t exist")
                return
            }
            graph.clear()
            GraphHelper().parseEdgesToGraph(fileEditor.readDataFromFile(), graph)
        }
    }

    class ChooseRandomVertices(private val graph: AdjacencyList<String>, val dijkstra: Dijkstra<String>) :
        Command("Get 2 random vertices") {
        override fun execute() {
            val firstVertex = graph.getRandomVertex()
            var secondVertex: String
            do {
                secondVertex = graph.getRandomVertex()
            } while (firstVertex == secondVertex)

            println("First vertex: $firstVertex")
            println("Second vertex: $secondVertex")

            ShortestPath(firstVertex,secondVertex,dijkstra).execute()

        }
    }

    class ChooseTwoVertices(private val graph: AdjacencyList<String>, val dijkstra: Dijkstra<String>) :
        Command("Enter 2 vertices") {
        override fun execute() {
            print("Enter the name of the first vertex: ")
            var firstVertex: String
            do {
                firstVertex = readln()
            } while (graph.getVertex(firstVertex) == null)

            print("Enter the name of the second vertex: ")

            var secondVertex: String
            do {
                secondVertex = readln()
            } while (firstVertex == secondVertex || graph.getVertex(secondVertex) == null)

            ShortestPath(firstVertex,secondVertex,dijkstra).execute()
        }
    }

    class ShortestPath(
        private val firstVertex: String,
        private val secondVertex: String,
        private val dijkstra: Dijkstra<String>
    ): Command("Shortest path") {
        override fun execute() {
            val pathsFromSource:HashMap<String, Visit<String>>
            val path:Path<String>
            val executionTime = measureTimeMillis {
                pathsFromSource = dijkstra.shortestPath(firstVertex, secondVertex) // 1
                path = dijkstra.shortestPath(secondVertex, pathsFromSource) // 2
            }
            println("Execution time: $executionTime ms")
            path.showPath()
            path.showDistance()
        }
    }

    class StartAlgorithm(private val invoker: Invoker, private val graph: AdjacencyList<String>) :
        Command("Start dijkstra algorithm") {

        override fun execute() {
            if (graph.size() < 2) {
                println("Graph doesn`t have at least 2 vertices")
                return
            }

            invoker.start()
        }
    }

}




