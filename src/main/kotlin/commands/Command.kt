package commands

import Invoker
import dijkstra.AdjacencyList
import dijkstra.Dijkstra
import generator.DataGenerate
import io.FileEditor
import io.GraphHelper

// Абстрактний клас команди
abstract class Command(private val description: String) {
    abstract fun execute()
    override fun toString() = description

    abstract class CompositeCommand(description: String, protected val invoker: Invoker) : Command(description)

    class GenerateData(private val graph: AdjacencyList<String>) : Command("Generate data") {
        private val dataGenerator = DataGenerate.StringDataGenerator()
        override fun execute() {

            print("Enter number of Vertex: ")
            val numberOfVertex = readln().toIntOrNull()
            print("Enter number of Edges: ")
            val numberOfEdges = readln().toIntOrNull()
            println("Enter max weight: ")
            val maxWeight = readln().toIntOrNull()
            if (numberOfVertex == null || numberOfEdges == null || maxWeight == null) {
                println("Wrong input!!!")
                return
            }
            graph.clear()
            dataGenerator.generate(numberOfVertex, numberOfEdges, maxWeight, graph)
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

            val pathsFromA = dijkstra.shortestPath(firstVertex) // 1
            val path = dijkstra.shortestPath(secondVertex, pathsFromA) // 2

            println(path)

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
    }

    class StartAlgorithm(invoker: Invoker) : CompositeCommand("Start dijkstra algorithm", invoker) {

        override fun execute() {

            invoker.start()
        }
    }

}




