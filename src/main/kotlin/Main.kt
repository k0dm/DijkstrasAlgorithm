import commands.Command
import dijkstra.AdjacencyList
import dijkstra.Dijkstra

suspend fun main(args: Array<String>) {

    val graph = AdjacencyList<String>()
    val dijkstra = Dijkstra(graph)
    val commands = HashMap<Int, Command>()
    commands[1] = Command.GenerateData(graph)
    commands[2] = Command.LoadData(graph)
    commands[4] = Command.SaveData(graph)

    commands[3] = Command.StartAlgorithm(
        Invoker(HashMap<Int, Command>().apply {
            put(1, Command.ChooseRandomVertices(graph,dijkstra))
            put(2, Command.ChooseTwoVertices(graph,dijkstra))
        }),
        graph
    )



    val invoker = Invoker(commands)
    invoker.start()

}
