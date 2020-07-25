package graphvisualizer.graphalgorithms;

import graphvisualizer.graph.AdjacencyMapDigraph;
import graphvisualizer.graph.Edge;
import graphvisualizer.graph.Vertex;
import graphvisualizer.graphview.SmartGraphPanel;
import java.util.*;

/**
 * A cycle detection algorithm that can be used on a directed graph
 * that is strongly connected or not strongly connected with random
 * generation of directed edges.
 */
public class CycleDetection {
    /*
    These 2 variables are declared as global variables since it's hard to
    pass primitive datatype by reference as they are immutable. The only
    way to pass them by reference without using other datatype in external
    libraries is to create a class to wrap the primitive datatype, however,
    since this is not our primary concern in detecting a cycle, both variables
    are merely declared as global variables.
     */
    private static boolean isCyclic;
    private static int cycleCount;

    /**
     * Construct a visualization of cycle detection algorithm in a directed graph (strongly connected/not
     * strongly connected) referenced by <code>digraph</code>. Random directed edges will be generated until
     * a cycle is found.
     *
     * @param digraph Directed graph
     * @param graphView Graph visualization object
     * @return A general description of the process and result of the cycle detection algorithm
     */
    public static String start(AdjacencyMapDigraph<String, Integer> digraph, SmartGraphPanel<String, Integer> graphView) {
        LinkedList<Vertex<String>> vertices = new LinkedList<>();
        Set<Vertex<String>> visitedVertices = new HashSet<>();
        Set<Vertex<String>> onStackVertices = new HashSet<>();
        Map<Vertex<String>, Vertex<String>> parentsOfVertices = new HashMap<>();
        LinkedList<Set<Vertex<String>>> foundCycles = new LinkedList<>();
        isCyclic = false;
        cycleCount = 0;
        StringBuilder sb = new StringBuilder();
        StringBuilder buf = new StringBuilder();

        while(!isCyclic) {
            vertices.addAll(digraph.vertices());

            while (!vertices.isEmpty()) { //continue perform DFS if there are unvisited vertices, e.g. when there are several strongly connected components
                checkCycle(digraph, vertices.remove(), vertices, visitedVertices, onStackVertices, parentsOfVertices, foundCycles, buf, graphView);
                visitedVertices.clear(); parentsOfVertices.clear();
            }
            if(!isCyclic) {
                sb.append(digraph.generateRandomEdge(new Random().nextInt(20) + 1)); //generate a random edge
            }
        }
        return sb.append(String.format("\n[Cycle Detection] [%d cycle(s) found.]", cycleCount)).append(buf).append("\n").toString();
    }

    /**
     * A DFS method to detect the existence of a cycle in the directed graph. Random directed edges will be generated until
     * a cycle is found. This should be called repeatedly by <code>start(AdjacencyMapDigraph, SmartGraphPanel)</code>.
     *
     * @param digraph Directed graph
     * @param startVertex Starting vertex of a DFS search
     * @param vertices List of vertices of the directed graph
     * @param visitedVertices Set of visited vertices
     * @param onStackVertices Set of vertices on the recursion stack
     * @param parentsOfVertices Map that maps visited vertices to their parents
     * @param foundCycles List of found cycles
     * @param buf StringBuilder object to print the resulting cycles
     * @param graphView Graph visualization object
     */
    private static void checkCycle(AdjacencyMapDigraph<String, Integer> digraph, Vertex<String> startVertex, LinkedList<Vertex<String>> vertices,
                                   Set<Vertex<String>> visitedVertices, Set<Vertex<String>> onStackVertices, Map<Vertex<String>, Vertex<String>> parentsOfVertices,
                                   LinkedList<Set<Vertex<String>>> foundCycles, StringBuilder buf, SmartGraphPanel<String, Integer> graphView)
    {
        vertices.remove(startVertex);
        visitedVertices.add(startVertex);
        onStackVertices.add(startVertex);

        for (Edge<Integer, String> edge : digraph.outgoingEdges(startVertex)) {
            Vertex<String> vertex = digraph.opposite(startVertex, edge); //obtain the child of the vertex

            if(!visitedVertices.contains(vertex)) {
                parentsOfVertices.put(vertex, startVertex); //store the tree edge connecting the vertex
                checkCycle(digraph, vertex, vertices, visitedVertices, onStackVertices, parentsOfVertices, foundCycles, buf, graphView); //recursively perform DFS search
            }
            else if (onStackVertices.contains(vertex)) {
                Vertex<String> vertexInCycle;
                Stack<Vertex<String>> cycleStack = new Stack<>();
                boolean isRepeated = false;
                isCyclic = true;

                for (vertexInCycle = startVertex; !vertexInCycle.equals(vertex); vertexInCycle = parentsOfVertices.get(vertexInCycle)) {
                    cycleStack.push(vertexInCycle);
                }
                cycleStack.push(vertex);
                Set<Vertex<String>> newCycle = new HashSet<>(cycleStack);

                for (Set<Vertex<String>> foundCycle : foundCycles) {
                    if (newCycle.equals(foundCycle)) { //Check if this is a repeated cycle found before
                        isRepeated = true;
                        break;
                    }
                }
                if(!isRepeated) { //Not a repeated cycle found before
                    ++cycleCount;
                    foundCycles.add(newCycle);
                    buf.append("\nCycle of length ").append(cycleStack.size()).append(": ");

                    while(!cycleStack.isEmpty()) {
                        vertexInCycle = cycleStack.pop();
                        graphView.getStylableVertex(vertexInCycle).setStyleClass("highlightedVertex");
                        graphView.update();
                        buf.append(vertexInCycle).append(" --> ");
                    }
                    buf.append(vertex).append("\n");
                }
            }
        }
        onStackVertices.remove(startVertex);
    }
}
