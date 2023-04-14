package dijkstra

class Visit<T>(
    val type: VisitType ,
    val edge: Edge<T>? = null
) {
    override fun toString(): String {
        return "Visit(type=$type, edge=$edge)"
    }
}