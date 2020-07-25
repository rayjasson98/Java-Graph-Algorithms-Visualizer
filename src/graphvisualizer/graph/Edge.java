package graphvisualizer.graph;

public interface Edge<E, V> {
    /* return element stored in edge */
    E element();
    /* return 2 end vertices connected by edge */
    Vertex<V>[] vertices();
}
