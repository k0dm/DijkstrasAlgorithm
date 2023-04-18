package generator

import dijkstra.AdjacencyList

interface DataGenerate {

    fun generate(numberOfVertex: Int, numberOfEdges:Int, maxWeight:Int, graph: AdjacencyList<String>): AdjacencyList<String>

    class StringDataGenerator() : DataGenerate {

        override fun generate(
            numberOfVertex: Int,
            numberOfEdges: Int,
            maxWeight: Int,
            graph: AdjacencyList<String>
        ): AdjacencyList<String> {
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