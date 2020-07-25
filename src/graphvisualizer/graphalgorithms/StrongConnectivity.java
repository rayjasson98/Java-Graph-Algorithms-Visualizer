package graphvisualizer.graphalgorithms;

import graphvisualizer.graph.AdjacencyMapDigraph;
import graphvisualizer.graph.Edge;
import graphvisualizer.graph.Vertex;
import graphvisualizer.graphview.SmartGraphPanel;
import java.util.*;

import static java.lang.Integer.min;

/**
 * This class is used to determine the strong connectivity of a directed graph.
 * This method {@link #checkStronglyConnected(AdjacencyMapDigraph, Vertex, SmartGraphPanel)} implements DFS
 * to determine strong connectivity.
 * New edges are generated when the graph is not strongly connected until it is strongly connected.
 */
public class StrongConnectivity {
    private static final int UNVISITED = -1;

    private static HashMap<Vertex<String>,Integer> ref ;
    private static LinkedList<Vertex<String>> verticesList;
    private static StringBuilder sb;
    private static Deque<Integer> stack;
    private static int id;
    private static int sccCount;
    private static int[] ids, low;
    private static boolean[] onStack;

    /**
     * Generate a visualization for the DFS algorithm for a directed graph.
     * This method calls the DFS algorithm {@link #checkStronglyConnected(AdjacencyMapDigraph, Vertex, SmartGraphPanel)}
     * to determine the connectivity.
     * This method will generate a new edge when the graph is not strongly connected.
     *
     * @param digraph Directed graph
     * @param graphView Graph visualization object
     * @return description of edges that are newly added, result of the DFS algorithm {@link #checkStronglyConnected(AdjacencyMapDigraph, Vertex, SmartGraphPanel)}
     */
    public static String start(AdjacencyMapDigraph<String, Integer> digraph, SmartGraphPanel<String, Integer> graphView) {
        ref = new HashMap<Vertex<String>,Integer>();
        setRef(digraph, ref);

        boolean isStronglyConnected = false;
        Vertex<String> obj;
        verticesList = new LinkedList<>(digraph.vertices());
        StringBuilder sb = new StringBuilder();

        while(!isStronglyConnected) {
            init(digraph);
            while (!verticesList.isEmpty()){
                obj = verticesList.remove();
                if (ids[ref.get(obj)]==UNVISITED){
                    checkStronglyConnected(digraph,obj, graphView);
                }
            }

            isStronglyConnected = sccCount == 1;

            if (isStronglyConnected) {
                sb.append("\n[Strong Connectivity] [Graph is strongly connected!]\n");
                break;
            }
            else {
                sb.append(digraph.generateRandomEdge(new Random().nextInt(20) + 1));
                verticesList.addAll(digraph.vertices());
            }
        }
        return sb.append(digraph).toString();
    }

    /**
     * This method is a DFS algorithm used to determine strong connectivity of a directed graph.
     * This method is repeatedly called by {@link #start(AdjacencyMapDigraph, SmartGraphPanel)}
     *
     * @param digraph Directed graph
     * @param startVertex The starting vertex that will be used for DFS
     * @param graphView Graph visualization object
     */
    private static void checkStronglyConnected(AdjacencyMapDigraph<String, Integer> digraph, Vertex<String> startVertex, SmartGraphPanel<String, Integer> graphView) {
        int at = ref.get(startVertex);

        stack.push(at);
        onStack[at] = true;
        ids[at] = low [at] = id++;

        graphView.getStylableVertex(startVertex).setStyleClass("highlightedVertex");
        graphView.update();

        for (Edge<Integer, String> edge : digraph.outgoingEdges(startVertex)) {
            Vertex<String> vertex = digraph.opposite(startVertex, edge);

            if (ids[ref.get(vertex)]==UNVISITED)
                checkStronglyConnected(digraph, vertex, graphView);

            if (onStack[ref.get(vertex)]) low[at] = min(low[at], low[ref.get(vertex)]);
        }

        if (ids[at]==low[at]){
            for (int node = stack.pop(); ; node=stack.pop()){
                onStack[node] = false;
                low[node] = ids[at];
                if (node==at) break;
            }
            sccCount++;
        }
    }

    /**
     * This method is used to initialize the hashmap named 'ref'.
     * The hashmap is used as a index reference of the vertex when accessing to arrays.
     *
     * @param digraph Directed graph
     * @param ref A hashmap that stores the vertex as key and Integer as value.
     */
    public static void setRef(AdjacencyMapDigraph<String, Integer> digraph, HashMap<Vertex<String>, Integer> ref) {
        verticesList = new LinkedList<>(digraph.vertices());
        int count=0;

        while (!verticesList.isEmpty()){
            ref.put(verticesList.remove(),count);
            count++;
        }
    }

    /**
     * Initialize the members of the class before {@link #checkStronglyConnected(AdjacencyMapDigraph, Vertex, SmartGraphPanel)} is executed.
     * The members can be found declared as global variables of this class.
     *
     * @param digraph Directed graph
     */
    private static void init(AdjacencyMapDigraph<String, Integer> digraph){
        int graphSize = digraph.numVertices();

        id = 0;
        ids = new int[graphSize];
        low = new int[graphSize];
        onStack = new boolean[graphSize];
        stack = new ArrayDeque<>();
        Arrays.fill(ids, UNVISITED);
        sccCount=0;
    }
}
