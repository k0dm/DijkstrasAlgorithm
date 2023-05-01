package dijkstra

enum class VisitType {
    START,                //The vertex is the starting vertex
    EDGE                  //The vertex has an associated edge that leads to a path back to the starting vertex.
}