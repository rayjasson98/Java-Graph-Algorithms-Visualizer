package graphvisualizer.graph;

import java.util.Collection;

public interface Graph<V, E> {
    /* return total number of vertices */
    int numVertices();
    /* return total number of edges */
    int numEdges();
    /* return iteration of all vertices */
    Collection<Vertex<V>> vertices();
    /* return iteration of all edges */
    Collection<Edge<E, V>> edges();
    /* return iteration of all incoming edges */
    Collection<Edge<E, V>> incomingEdges(Vertex<V> v) throws InvalidVertexException;
    /* return iteration of all outgoing edges */
    Collection<Edge<E, V>> outgoingEdges(Vertex<V> v) throws InvalidVertexException;
    /* return opposite vertex */
    Vertex<V> opposite(Vertex<V> v, Edge<E, V> e) throws InvalidVertexException, InvalidEdgeException;
    /* create and return a new vertex */
    Vertex<V> insertVertex(V element) throws InvalidVertexException;
    /* return a new edge from u to v or null if not exists */
    Edge<E, V> getEdge(Vertex<V> u, Vertex<V> v) throws InvalidVertexException, InvalidEdgeException;
    /* create and return a new edge using vertex instance */
    Edge<E, V> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws InvalidVertexException, InvalidEdgeException;
    /* create and return a new edge using vertex element */
    Edge<E, V> insertEdge(V uElement, V vElement, E eElement) throws InvalidVertexException, InvalidEdgeException;
    /* remove a vertex and all incident edges */
    V removeVertex(Vertex<V> v) throws InvalidVertexException;
    /* remove an edge */
    E removeEdge(Edge<E, V> e) throws InvalidEdgeException;
}