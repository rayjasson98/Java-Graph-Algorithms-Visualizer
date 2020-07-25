# Java Graph Algorithms Visualizer
*[Ray Jasson](mailto:holmesqueen2070@yahoo.com)*<br>
*26/07/2020*<br>

<br>

## :notebook: Background

This is a dynamic and interactive graph algorithm visualizer written in Java that demonstrates the solution of the following problems:

- Strong Connectivity
- Cycle Detection
- Shortest Path

This visualizer is developed using [JavaFX SmartGraph library](https://github.com/brunomnsilva/JavaFXSmartGraph) written by [Bruno Silva](https://github.com/brunomnsilva).

:unlock: ***The visualizer was implemented in Java 8 which includes JavaFX as bundle.***

<br>

## :speech_balloon: Graph Representation in Java

The data structure for graph is represented using an adjacency map. The adjacency map has a primary and a secondary structure. In the primary structure represented by the hash-based map, the names or IDs of vertices serve as keys and the associated vertices as values. The secondary structure maintains the incidence collection of the edges using two different map references: an *Outgoing Edges* hash-based map and an *Incoming Edges* hash-based map. In both hash-based maps, the opposite end vertices serve as the keys and the edges serve as the values.

<p align="center"><img src="/docs/pics/AdjacencyMap.png" width=75% height=75%></p>
<p align="center"><i>Schematic Representation of an Adjacency Map</i></p>

Refer to [AdjacencyMapDigraph.java](/src/graphvisualizer/graph/AdjacencyMapDigraph.java) for more details.

<br>

## :computer: Program Execution

The visualizer has 5 functions:

- Strong Connectivity
- Cycle Detection
- Shortest Path
- Add Vertex
- Reset Graph

<p align="center"><img src="/docs/pics/interface.png"></p>
<p align="center"><i>User Interface of the Graph Algorithms Visualizer</i></p>

<br>

### :arrow_down_small: Strong Connectivity

[Tarjan's algorithm](https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm) is used to determine whether the directed graph is strongly connected by finding the strongly connected components (SCCs) of the graph. The implementation of Tarjan's algorithm is as follows:

- **If the graph is strongly connected**<br>
Print the resulting graph
- **Else**<br>
Generate random edges between vertices until the graph is strongly connected

<p align="center"><img src="/docs/gifs/StrongConnectivity.gif"></p>
<p align="center"><i>Random edges are generated until the graph is strongly connected</i></p>

Refer to [StrongConnectivity.java](/src/graphvisualizer/graphalgorithms/StrongConnectivity.java) for more details.

<br>

### :arrow_down_small: Cycle Detection

[Depth-First Search (DFS)](https://en.wikipedia.org/wiki/Depth-first_search) is used to detect the presence of a cycle in the graph. The implementation of DFS cycle detection algorithm is as follows:

- **If the graph has a cycle**<br>
Print the resulting cycle and its length
- **Else**<br>
Generate random edges between vertices until the graph has a cycle

<p align="center"><img src="/docs/gifs/CycleDetection.gif"></p>
<p align="center"><i>Random edges are generated until the graph has a cycle</i></p>

Refer to [CycleDetection.java](/src/graphvisualizer/graphalgorithms/CycleDetection.java) for more details.

<br>

### :arrow_down_small: Shortest Path

[Dijkstra's algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm) is used to determine the shortest path between two end vertices in the graph. You can select a start vertex and an end vertex of the shortest path by double-clicking the vertices. The selected end vertices are shown in yellow. The implementation of Dijkstra's algorithm is as follows:

- **If there is a path between the start vertex and the end vertex**<br>
Print the shortest path between the end vertices
- **Else**<br>
Generate random edges between vertices until a path is formed between the end vertices

<p align="center"><img src="/docs/gifs/ShortestPath.gif"></p>
<p align="center"><i>Random edges are generated until the graph has a path between two end vertices</i></p>

Refer to [ShortestPath.java](/src/graphvisualizer/graphalgorithms/ShortestPath.java) for more details.

<br>

### :arrow_down_small: Add Vertex and Reset Graph

- You can add up to maximum 5 additional vertices to the graph for testing and viewing the solution of the algorithms.

<p align="center"><img src="/docs/gifs/Add.gif"></p>
<p align="center"><i>An example of adding 2 additional vertices to the graph, and performing strong connectivity algorithm</i></p>

- You can reset the graph to its default state.

Refer to [Main.java](/src/graphvisualizer/graphalgorithms/Main.java) for more details.

<br>

## :black_nib: Reference

- Cormen, T. H., Leiserson, C., Rivest, R., & Stein, C. (2009). Introduction to Algorithms (3rd ed.). Cambridge, Massachusetts, United States of America: MIT Press.
- Goodrich, M. T., & Tamassia, R. (2014). Data Structures and Algorithms in Java (6th ed.). New Jersey: John Wiley.
- [A generic (Java FX) graph visualization library](https://github.com/brunomnsilva/JavaFXSmartGraph)
