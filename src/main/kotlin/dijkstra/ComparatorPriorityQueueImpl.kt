package dijkstra

class ComparatorPriorityQueueImpl<T>(
    private val comparator: Comparator<T>
) : AbstractPriorityQueue<T>() {
    override val heap = ComparatorHeapImpl(comparator)
}