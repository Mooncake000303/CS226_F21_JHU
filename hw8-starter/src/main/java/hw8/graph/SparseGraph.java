package hw8.graph;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph ADT using incidence lists
 * for sparse graphs where most nodes aren't connected.
 *
 * @param <V> Vertex element type.
 * @param <E> Edge element type.
 */
public class SparseGraph<V, E> implements Graph<V, E> {

  // TODO You may need to add fields/constructor here!
  private Map<V, Vertex<V>> vertices; // hashmap of vertices
  private Set<Edge<E>> edges; // hashset of edges

  /**
   * Default graph constructor.
   */
  public SparseGraph() {
    vertices = new HashMap<>();
    edges = new HashSet<>();
  }

  // Converts the vertex back to a VertexNode to use internally
  private VertexNode<V> convert(Vertex<V> v) throws PositionException {
    try {
      VertexNode<V> gv = (VertexNode<V>) v;
      if (gv.owner != this) {
        throw new PositionException();
      }
      return gv;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  // Converts and edge back to a EdgeNode to use internally
  private EdgeNode<E> convert(Edge<E> e) throws PositionException {
    try {
      EdgeNode<E> ge = (EdgeNode<E>) e;
      if (ge.owner != this) {
        throw new PositionException();
      }
      return ge;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  // Return true if there's already an existed edge between from and to.
  private boolean isDuplicated(Vertex<V> from, Vertex<V> to) {
    Iterable<Edge<E>> outEdges = outgoing(from);
    for (Edge<E> edge : outEdges) {
      if (to(edge).equals(to)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Vertex<V> insert(V v) throws InsertionException {
    // TODO Implement me!
    if (v == null || vertices.containsKey(v)) {
      throw new InsertionException();
    }
    Vertex<V> newVertex = new VertexNode<>(this, v);
    vertices.put(v, newVertex);
    return newVertex;
  }

  @Override
  public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
      throws PositionException, InsertionException {
    // TODO Implement me!
    if (from == null || to == null) {
      throw new PositionException();
    }
    if (!(vertices.containsKey(from.get()) && vertices.containsKey(to.get()))) { // not a key: also invalid
      throw new PositionException();
    }
    if (from.equals(to) || isDuplicated(from, to)) { // self-loop or duplicated edge
      throw new InsertionException();
    }
    Edge<E> newEdge = new EdgeNode<>(this, convert(from), convert(to), e);
    convert(from).outEdges.add(newEdge); // outgoing edge from "from"
    convert(to).inEdges.add(newEdge); // incoming edge to "to"
    edges.add(newEdge); // add to total edgeSet
    return newEdge;
  }

  @Override
  public V remove(Vertex<V> v) throws PositionException, RemovalException {
    // TODO Implement me!
    if (v == null || !vertices.containsKey(v.get())) {
      throw new PositionException();
    }
    if (convert(v).hasEdges()) {
      throw new RemovalException();
    }
    V toRemove = v.get();
    vertices.remove(toRemove);
    return toRemove;
  }

  @Override
  public E remove(Edge<E> e) throws PositionException {
    // TODO Implement me!
    if (e == null || !edges.contains(e)) {
      throw new PositionException();
    }
    convert(from(e)).outEdges.remove(e); // outgoing edge from "from"
    convert(to(e)).inEdges.remove(e); // incoming edge to "to"
    edges.remove(e); // remove from total edgeSet
    return e.get();
  }

  @Override
  public Iterable<Vertex<V>> vertices() {
    // TODO Implement me!
    return Collections.unmodifiableCollection(vertices.values());
  }

  @Override
  public Iterable<Edge<E>> edges() {
    // TODO Implement me!
    return Collections.unmodifiableCollection(edges);
  }

  @Override
  public Iterable<Edge<E>> outgoing(Vertex<V> v) throws PositionException {
    // TODO Implement me!
    if (v == null || !vertices.containsKey(v.get())) {
      throw new PositionException();
    }
    return Collections.unmodifiableCollection(convert(v).outEdges);
  }

  @Override
  public Iterable<Edge<E>> incoming(Vertex<V> v) throws PositionException {
    // TODO Implement me!
    if (v == null || !vertices.containsKey(v.get())) {
      throw new PositionException();
    }
    return Collections.unmodifiableCollection(convert(v).inEdges);
  }

  @Override
  public Vertex<V> from(Edge<E> e) throws PositionException {
    // TODO Implement me!
    if (e == null || !edges.contains(e)) {
      throw new PositionException();
    }
    return convert(e).from;
  }

  @Override
  public Vertex<V> to(Edge<E> e) throws PositionException {
    // TODO Implement me!
    if (e == null || !edges.contains(e)) {
      throw new PositionException();
    }
    return convert(e).to;
  }

  @Override
  public void label(Vertex<V> v, Object l) throws PositionException {
    // TODO Implement me!
    if (v == null || !vertices.containsKey(v.get())) {
      throw new PositionException();
    }
    convert(v).label = l;
  }

  @Override
  public void label(Edge<E> e, Object l) throws PositionException {
    // TODO Implement me!
    if (e == null || !edges.contains(e)) {
      throw new PositionException();
    }
    convert(e).label = l;
  }

  @Override
  public Object label(Vertex<V> v) throws PositionException {
    // TODO Implement me!
    if (v == null || !vertices.containsKey(v.get())) {
      throw new PositionException();
    }
    return convert(v).label;
  }

  @Override
  public Object label(Edge<E> e) throws PositionException {
    // TODO Implement me!
    if (e == null || !edges.contains(e)) {
      throw new PositionException();
    }
    return convert(e).label;
  }

  @Override
  public void clearLabels() {
    // TODO Implement me!
    for (V v : vertices.keySet()) {
      convert(vertices.get(v)).label = null;
    }
    for (Edge edge : edges) {
      convert(edge).label = null;
    }
  }

  @Override
  public String toString() {
    GraphPrinter<V, E> gp = new GraphPrinter<>(this);
    return gp.toString();
  }

  // Class for a vertex of type V
  private final class VertexNode<V> implements Vertex<V> {
    V data;
    Graph<V, E> owner;
    Object label;
    // TODO You may need to add fields/methods here!
    List<Edge<E>> inEdges;
    List<Edge<E>> outEdges;

    // constructor for one single vertex
    VertexNode(V v) {
      this.data = v;
      this.label = null;
    }

    // overloading constructor with owner
    VertexNode(Graph<V, E> graph, V v) {
      this.data = v;
      this.label = null;
      this.owner = graph;
      this.inEdges = new LinkedList<>();
      this.outEdges = new LinkedList<>();
    }

    @Override
    public V get() {
      return this.data;
    }

    // return true if either inEdge and outEdge is not empty
    private boolean hasEdges() {
      return (!this.inEdges.isEmpty() || !this.outEdges.isEmpty());
    }
  }

  //Class for an edge of type E
  private final class EdgeNode<E> implements Edge<E> {
    E data;
    Graph<V, E> owner;
    VertexNode<V> from;
    VertexNode<V> to;
    Object label;
    // TODO You may need to add fields/methods here!

    // Constructor for a new edge
    EdgeNode(VertexNode<V> f, VertexNode<V> t, E e) {
      this.from = f;
      this.to = t;
      this.data = e;
      this.label = null;
    }

    // overloading constructor with owner
    EdgeNode(Graph<V, E> graph, VertexNode<V> f, VertexNode<V> t, E e) {
      this.from = f;
      this.to = t;
      this.data = e;
      this.label = null;
      this.owner = graph;

    }

    @Override
    public E get() {
      return this.data;
    }
  }
}
