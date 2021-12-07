package hw8.spp;

import hw8.graph.Edge;
import hw8.graph.Graph;
import hw8.graph.Vertex;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class DijkstraStreetSearcher extends StreetSearcher {


  private Map<Vertex<String>, Double> distance; // hashmap of (vertex, distance)
  private Set<Vertex<String>> explored; // hashset of explored vertices
  private Queue<Vertex<String>> vertexPriorityQueue;

  /**
   * Creates a StreetSearcher object.
   *
   * @param graph an implementation of Graph ADT.
   */
  public DijkstraStreetSearcher(Graph<String, String> graph) {
    super(graph);
  }

  private class VertexByDistanceComparator implements Comparator<Vertex<String>> {
    @Override
    public int compare(Vertex<String> vertex1, Vertex<String> vertex2) {
      return (distance.get(vertex1)).compareTo(distance.get(vertex2));
    }
  }

  // if improved, stepForwardUpdate updates distance and PQ
  private void stepForwardUpdate(Vertex<String> vertex, Edge<String> edge, Vertex<String> neighbor) {
    double d = distance.get(vertex) + (double) graph.label(edge); // d = distance + weight
    if (d < distance.get(neighbor)) { // if < current distance, update distance and add to PQ
      distance.replace(neighbor, d);
      graph.label(neighbor, edge);
      vertexPriorityQueue.add(neighbor);
    }
  }

  // Dijkstra's search algorithm
  private void search(Vertex<String> start, Vertex<String> end) {
    // set up
    distance = new HashMap<>();
    explored = new HashSet<>();
    vertexPriorityQueue = new PriorityQueue<>(new VertexByDistanceComparator()); // by order of distance
    for (Vertex<String> v : graph.vertices()) {
      distance.put(v, MAX_DISTANCE); // every vertex's initial distance: MAX_DISTANCE
    }
    distance.replace(start, 0.0); // start is the source
    vertexPriorityQueue.add(start);
    // search: if end is explored OR PQ is empty, stops loop
    while (!(explored.contains(end) || vertexPriorityQueue.isEmpty())) {
      // remove current vertex from queue front and make it explored
      Vertex<String> vertex = vertexPriorityQueue.remove();
      explored.add(vertex);
      for (Edge<String> edge : graph.outgoing(vertex)) { // for every outgoing edge of this vertex
        Vertex<String> neighbor = graph.to(edge); // the corresponding neighbor is "to" of the edge
        if (!explored.contains(neighbor)) { // if the neighbor is unexplored
          stepForwardUpdate(vertex, edge, neighbor);
        }
      }
    }
  }

  @Override
  public void findShortestPath(String startName, String endName) {
    // exception handle
    try {
      checkValidEndpoint(startName);
      checkValidEndpoint(endName);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return;
    }

    Vertex<String> start = vertices.get(startName);
    Vertex<String> end = vertices.get(endName);

    // TODO - Implement Dijkstra Algorithm!
    search(start, end);
    double totalDist = distance.get(end);  // totalDist must be update below

    // These method calls will create and print the path for you
    List<Edge<String>> path = getPath(end, start);
    if (VERBOSE) {
      printPath(path, totalDist);
    }
  }
}
