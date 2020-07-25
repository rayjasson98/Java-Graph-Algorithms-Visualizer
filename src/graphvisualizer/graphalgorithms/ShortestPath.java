package graphvisualizer.graphalgorithms;

import graphvisualizer.graph.Edge;
import graphvisualizer.graph.*;
import graphvisualizer.graphview.SmartGraphPanel;
import java.util.*;

/**
 * A shortest path algorithm which implements Dijkstra’s algorithm
 * that can be used on a directed graph that is either strongly connected
 * or not strongly connected with random generation of directed edges
 */

public class ShortestPath {
    /**
     * Construct a visualization of shortest path algorithm in a directed graph which is
     * either strongly connected not strongly connected referenced by <code>digraph</code>.
     * Random directed edges will be generated until a path exists between the starting vertex
     * and ending vertex
     *
     * @param digraph Directed graph
     * @param graphView Graph visualization object
     * @param startVertex Starting vertex of the shortest path algorithm
     * @param endVertex Ending vertex of the shortest path algorithm
     * @return The weight count from the starting vertex to ending vertex of the Dijkstra’s algorithm
     */

    public static String start(AdjacencyMapDigraph<String, Integer> digraph, SmartGraphPanel<String, Integer> graphView,
                               Vertex<String> startVertex, Vertex<String> endVertex) {
        LinkedHashMap<Vertex<String>, Integer> d = new LinkedHashMap<>();
        int[] weight = {1};
        StringBuilder sb = new StringBuilder();

        //Generate random edges between random vertices until the path exists
        while (!dijkstra(digraph, startVertex, endVertex, d, weight)) {
            //generate a random edge
            sb.append(digraph.generateRandomEdge(new Random().nextInt(20) + 1));
        }

        return sb.append("\n[Shortest Path]\n\n").append(generatePath(digraph, startVertex, endVertex, d, graphView)).append("\nWeight count from ").
                append(startVertex).append(" to ").append(endVertex).append(" is ").append(weight[0]).append(".\n").toString();
    }


    /**
     * An implementation of Dijkstra’s algorithm to determine and pass the weight of shortest path between
     * the starting vertex and the ending vertex through array weight. This is also to detect whether a path
     * exists between the starting and ending vertices and return boolean value.
     * This should be called repeatedly by <code>start(AdjacencyMapDigraph, SmartGraphPanel, Vertex<String>, Vertex<String>)</code>.
     *
     * @param digraph Directed graph
     * @param startVertex Starting vertex of the shortest path algorithm
     * @param endVertex Ending vertex of the shortest path algorithm
     * @param d Map to store the distance/weight of each vertex from the starting vertex
     * @param weight The weight count of the shortest path
     */

    private static boolean dijkstra(AdjacencyMapDigraph<String, Integer> digraph, Vertex<String> startVertex, Vertex<String> endVertex,
                                    LinkedHashMap <Vertex<String>, Integer> d, int[] weight) {

        Map<Vertex<String>, Integer> cloud = new LinkedHashMap<>();
        HeapAdaptablePriorityQueue<Integer, Vertex<String>> pq = new HeapAdaptablePriorityQueue<>();
        Map<Vertex<String>, Entry<Integer, Vertex<String>>> pqTokens = new LinkedHashMap<>();

        //for each vertex of the graph, add an entry to the priority queue with the source having distance 0 and all others having infinite distance
        for (Vertex<String> v : digraph.vertices()) {
            if (v.equals(startVertex)) {
                d.put(v, 0);
            } else {
                d.put(v, Integer.MAX_VALUE);
            }
            //save the entry for future updates
            pqTokens.put(v, pq.insert(d.get(v), v));
        }

        //Add all the reachable vertices to the Map cloud
        while (!pq.isEmpty()) {
            Entry<Integer, Vertex<String>> entry = pq.removeMin();
            int key = entry.getKey();
            Vertex<String> u = entry.getValue();
            cloud.put(u, key);      //the actual distance to u
            pqTokens.remove(u);     //remove u from pq

            for (Edge<Integer, String> edge : digraph.outgoingEdges(u)) {
                Vertex<String> v = digraph.opposite(u, edge);

                if (cloud.get(v) == null) {
                    //perform the relaxation step on edge (u,v)
                    int wgt = edge.element();
                    if ((d.get(u) + wgt) < d.get(v)) {              //check if there is any better/shorter path to v
                        d.put(v, (d.get(u) + wgt));                 //update the distance in Map d
                        pq.replaceKey(pqTokens.get(v), d.get(v));   //update the pq entry
                    }
                }
            }
        }
        weight[0] = cloud.get(endVertex);                           //Store the weight of shortest path in array weight

        //check if there is any path can be reach by starting vertex to ending vertex and return a boolean value
        if (cloud.get(endVertex) >= Integer.MAX_VALUE) {
            return false;
        } else return Integer.signum(cloud.get(endVertex)) != -1;
    }

    /**
     * Reconstruct a shortest-path tree rooted at starting vertex, the tree is represented as a map
     * from each reachable vertex other than the starting vertex to the edge that is used to reach
     * a vertex from its parent u in the tree. Then, this method return the String value of the vertex or
     * vertices on the path from starting vertex to ending vertex. This should be called repeatedly by
     * <code>start(AdjacencyMapDigraph, SmartGraphPanel, Vertex<String>, Vertex<String>)</code>.
     *
     * @param digraph Directed graph
     * @param startVertex Starting vertex of the shortest path algorithm
     * @param endVertex Ending vertex of the shortest path algorithm
     * @param d Map to store the distance/weight of each vertex from the starting vertex
     * @param graphView  Graph visualization object
     */

    private static String generatePath (AdjacencyMapDigraph<String, Integer> digraph, Vertex<String> startVertex, Vertex<String> endVertex,
                                        LinkedHashMap<Vertex<String>, Integer> d, SmartGraphPanel<String, Integer> graphView) {
        Map<Vertex<String>, Edge<Integer, String>> tree = new LinkedHashMap<>();
        Map<Vertex<String>, Vertex<String>> parentsOfVertices = new HashMap<>();
        StringBuilder writtenPath = new StringBuilder();

        for (Vertex<String> vertex : d.keySet()) {
            if (vertex != startVertex) {
                for (Edge<Integer, String> edge : digraph.incomingEdges(vertex)) {  //consider the incoming edges
                    Vertex<String> u = digraph.opposite(vertex, edge);
                    int wgt = edge.element();
                    if (d.get(vertex) == d.get(u) + wgt) {
                        tree.put(vertex, edge);             //The vertices and edges are stored
                        parentsOfVertices.put(vertex, u);   //The parents of vertices are stored
                    }
                }
            }
        }
        Stack<Vertex<String>> path = new Stack<>();     //set of vertices on the path
        Vertex<String> vertexInPath;

        //push the vertex or vertices on path into the stack
        for (vertexInPath = endVertex; !vertexInPath.equals(startVertex); vertexInPath = parentsOfVertices.get(vertexInPath)) {
            path.push(vertexInPath);
        }

        //push the starting vertex into the stack
        path.push(startVertex);

        //Set the style class of the vertex or vertices on path and update the graphview
        while(!path.isEmpty()) {
            vertexInPath = path.pop();
            graphView.getStylableVertex(vertexInPath).setStyleClass("highlightedVertex");
            graphView.update();

            if(!vertexInPath.equals(startVertex))
                writtenPath.append(tree.get(vertexInPath)).append("\n");
        }
        return writtenPath.toString();
    }
}
