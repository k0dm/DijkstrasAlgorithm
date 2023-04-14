package generator

import dijkstra.AdjacencyList

interface DataGenerate<T> {

    fun generate(numberOfVertex: Int, numberOfEdges:Int, maxWeight:Int): AdjacencyList<T>

    class StringDataGenerator() : DataGenerate<String> {

        override fun generate(numberOfVertex: Int, numberOfEdges:Int, maxWeight:Int): AdjacencyList<String> {
            val graph = AdjacencyList<String>()
            if (numberOfVertex < 1 || numberOfEdges < 1) return graph

            val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

            for (i in 0..numberOfVertex) {
                graph.createVertex(List(10) { charPool.random() }.joinToString(""))
            }

            graph.setRandomEdges(numberOfEdges, maxWeight)

            return graph
        }

    }
}