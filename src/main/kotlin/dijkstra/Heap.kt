package dijkstra

interface Heap<Element> : Collection<Element> {
    fun peek(): Element?
}