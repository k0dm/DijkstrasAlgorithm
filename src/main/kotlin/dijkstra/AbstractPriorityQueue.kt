package dijkstra

abstract class AbstractPriorityQueue<T> : Queue<T> {
    // 2
    abstract val heap: Heap<T>
        get
    // 1
    override fun enqueue(element: T): Boolean {
        heap.insert(element)
        return true
    }
    // 2
    override fun dequeue() = heap.remove()
    // 3
    override val count: Int
        get() = heap.count
    // 4
    override fun peek() = heap.peek()}