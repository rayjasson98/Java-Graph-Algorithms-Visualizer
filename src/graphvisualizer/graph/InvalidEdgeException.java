package graphvisualizer.graph;

public class InvalidEdgeException extends RuntimeException {
    public InvalidEdgeException() {
        super("The edge is invalid or does not belong to this graph.");
    }

    public InvalidEdgeException(String string) {
        super(string);
    }
}
