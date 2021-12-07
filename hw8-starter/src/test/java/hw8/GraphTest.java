package hw8;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;
import hw8.graph.Edge;
import hw8.graph.Graph;
import hw8.graph.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public abstract class GraphTest {

  protected Graph<String, String> graph;

  @BeforeEach
  public void setupGraph() {
    this.graph = createGraph();
  }

  protected abstract Graph<String, String> createGraph();

  @Test
  @DisplayName("insert(v) returns a vertex with given data")
  public void canGetVertexAfterInsert() {
    Vertex<String> v1 = graph.insert("v1");
    assertEquals(v1.get(), "v1");
  }

  @Test
  @DisplayName("insert(U, V, e) returns an edge with given data")
  public void canGetEdgeAfterInsert() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    assertEquals(e1.get(), "v1-v2");
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void insertEdgeThrowsPositionExceptionWhenfirstVertexIsNull() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.insert(null, v, "e");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  // TODO add more tests here.
  // insert a vertex
  @Test
  public void insertVertexNullThrowsInsertionException() {
    try {
      graph.insert(null);
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  public void insertVertexDuplicateThrowsInsertionException() {
    graph.insert("v");
    try {
      graph.insert("v");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  void insertVertices() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    assertEquals(v1.get(), "v1");
    assertEquals(v2.get(), "v2");
    assertEquals(v3.get(), "v3");
  }

  // insert an edge
  @Test
  public void insertEdgeNullVertexThrowsPositionException() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.insert(v, null, "e");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  public void insertEdgeSelfLoopThrowsInsertionException() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.insert(v, v, "e");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  public void insertEdgeDuplicateThrowsInsertionException() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    graph.insert(v1, v2, "e");
    try {
      graph.insert(v1, v2, "e");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  public void insertEdgeValueNull() {
    Vertex<String> v1 = graph.insert("from");
    Vertex<String> v2 = graph.insert("to");
    Edge<String> e1 = graph.insert(v1, v2, null);
    assertNull(e1.get());
  }

  @Test
  public void insertEdgesValueTwoDirections() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1v2");
    Edge<String> v2v1 = graph.insert(v2, v1, "v2v1");
    assertEquals(v1v2.get(), "v1v2");
    assertEquals(v2v1.get(), "v2v1");
  }

  @Test
  public void insertEdgesValueDuplicate() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> v1v2 = graph.insert(v1, v2, "e");
    Edge<String> v1v3 = graph.insert(v1, v3, "e");
    Edge<String> v2v3 = graph.insert(v2, v3, "e");
    assertEquals(v1v2.get(), "e");
    assertEquals(v1v3.get(), "e");
    assertEquals(v2v3.get(), "e");
  }

  @Test
  public void insertMultipleEdgesWorks() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Vertex<String> v4 = graph.insert("v4");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1v2");
    Edge<String> v1v3 = graph.insert(v1, v3, "v1v3");
    Edge<String> v2v4 = graph.insert(v2, v4, "v2v4");
    assertEquals(v1v2.get(), "v1v2");
    assertEquals(v1v3.get(), "v1v3");
    assertEquals(v2v4.get(), "v2v4");
  }

  // outgoing
  @Test
  public void outgoingNullVertexThrowsPositionException() {
    try {
      Vertex<String> v = null;
      graph.outgoing(v);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  public void outgoingRemovedVertexThrowsPositionException() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.remove(v);
      graph.outgoing(v);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  public void outgoingNoEdgeVertex() {
    Vertex<String> v = graph.insert("v");
    Iterable<Edge<String>> outgoingEdges = graph.outgoing(v);
    for (Edge<String> edge : outgoingEdges) {
      fail("No outgoing edges");
    }
  }

  @Test
  public void outgoingIncomingOnlyVertex() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> v2v1 = graph.insert(v2, v1, "v2v1");
    Iterable<Edge<String>> outgoingEdges = graph.outgoing(v1);
    for (Edge<String> edge : outgoingEdges) {
      fail("No outgoing edges");
    }
  }

  @Test
  public void outgoing() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1v2");
    Edge<String> v1v3 = graph.insert(v1, v3, "v1v3");
    Edge<String> v2v1 = graph.insert(v2, v1, "v2v1");
    Edge<String> v3v1 = graph.insert(v3, v1, "v3v1");
    String[] edgeValues = {"v1v2", "v1v3"};
    Iterable<Edge<String>> outgoingEdges = graph.outgoing(v1);
    int ctr = 0;
    for (Edge<String> edge : outgoingEdges) {
      ctr++;
      assertTrue(Arrays.asList(edgeValues).contains(edge.get()));
    }
    assertEquals(2, ctr);
  }

  // incoming
  @Test
  void incomingNullVertexThrowsPositionException() {
    try {
      Vertex<String> v = null;
      graph.incoming(v);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  void incomingWithRemovedVertexThrowsPositionException() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.remove(v);
      graph.incoming(v);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  void incomingOutgoingOnlyVertex() {
    Vertex<String> v = graph.insert("v");
    Iterable<Edge<String>> incomingEdges = graph.incoming(v);

    for (Edge<String> edge : incomingEdges) {
      fail("No incoming edges");
    }
  }

  @Test
  void incoming() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1v2");
    Edge<String> v1v3 = graph.insert(v1, v3, "v1v3");
    Edge<String> v2v1 = graph.insert(v2, v1, "v2v1");
    Edge<String> v3v1 = graph.insert(v3, v1, "v3v1");
    String[] edgeValues = {"v2v1", "v3v1"};
    Iterable<Edge<String>> incomingEdges = graph.incoming(v1);
    int ctr = 0;
    for (Edge<String> edge : incomingEdges) {
      ctr++;
      assertTrue(Arrays.asList(edgeValues).contains(edge.get()));
    }
    assertEquals(2, ctr);
  }

  // get endpoints
  // from
  @Test
  public void fromNullEdgeThrowsPositionException() {
    try {
      Edge<String> e = null;
      graph.from(e);
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  public void fromRemovedEdgeThrowsPositionException() {
    try {
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      Edge<String> v1v2 = graph.insert(v1, v2, "e");
      graph.remove(v1v2);
      graph.from(v1v2);
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  public void fromStartVertex() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> v1v2 = graph.insert(v1, v2, "e");
    assertEquals(v1, graph.from(v1v2));
  }

  // to
  @Test
  public void toNullEdgeThrowsPositionException() {
    try {
      Edge<String> e = null;
      graph.to(e);
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  public void toRemovedEdgeThrowsPositionException() {
    try {
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      Edge<String> v1v2 = graph.insert(v1, v2, "v1v2");
      graph.remove(v1v2);
      graph.to(v1v2);
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  public void toEndVertex() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> v1v2 = graph.insert(v1, v2, "e");
    assertEquals(v2, graph.to(v1v2));
  }

  // remove a vertex
  @Test
  public void removeVertexEmptyGraphThrowsPositionException() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.remove(v);
      graph.remove(v);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  public void removeVertexNullThrowsPositionException() {
    try {
      Vertex<String> v = null;
      graph.remove(v);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  public void removeVertexNonExistThrowsPositionException() {
    Vertex<String> v = graph.insert("v");
    graph.remove(v);
    try {
      graph.remove(v);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  public void removeVertexEdgePertainsThrowsPositionException() {
    Vertex<String> v1 = graph.insert("V1");
    Vertex<String> v2 = graph.insert("V2");
    graph.insert(v1, v2, "e");
    try {
      graph.remove(v1);
      fail("The expected exception was not thrown");
    } catch (RemovalException ex) {
      return;
    }
  }

  @Test
  public void removeVertexNoEdgePertains() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    String v1Return = graph.remove(v1);
    String v2Return = graph.remove(v2);
    String v3Return = graph.remove(v3);
    assertEquals(v1Return, "v1");
    assertEquals(v2Return, "v2");
    assertEquals(v3Return, "v3");
  }

  // remove a edge
  @Test
  public void removeEdgeNullThrowsPositionException() {
    try {
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      Edge<String> e = null;
      graph.remove(e);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  public void removeEdgeNonExistThrowsPositionException() {
    try {
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      Edge<String> e = graph.insert(v1, v2, "e");
      graph.remove(e);
      graph.remove(e);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  public void removeEdgeSimple() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1v2");
    Edge<String> v1v3 = graph.insert(v1, v3, "v1v3");
    assertEquals("v1v2", graph.remove(v1v2));
    assertEquals("v1v3", graph.remove(v1v3));
  }

  @Test
  public void removeEdgeComplex() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1v2");
    Edge<String> v2v1 = graph.insert(v2, v1, "v2v1");
    graph.remove(v1v2);
    graph.remove(v2v1);
    String v1Return = graph.remove(v1);
    String v2Return = graph.remove(v2);
    assertEquals(v1Return, "v1");
    assertEquals(v2Return, "v2");
  }

  // iterate over vertices
  @Test
  public void iterateVerticesEmptyGraph() {
    Iterable<Vertex<String>> vertices = graph.vertices();
    for (Vertex<String> vertex : vertices) {
      fail("Empty graph");
    }
  }

  @Test
  public void iterateVertices() {
    graph.insert("v1");
    graph.insert("v2");
    graph.insert("v3");
    graph.insert("v4");
    graph.insert("v5");
    graph.insert("v6");
    int count = 0;
    for (Vertex<String> v: graph.vertices()) {
      count++;
    }
    assertEquals(6, count);
  }

  // iterate over edges
  @Test
  public void iterateEdgesEmptyGraph() {
    Iterable<Edge<String>> edges = graph.edges();
    for (Edge<String> edge : edges) {
      fail("Empty graph");
    }
  }

  @Test
  public void iterateEdges() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    graph.insert(v1, v2, "v1-v2");

    Vertex<String> v3 = graph.insert("v3");
    Vertex<String> v4 = graph.insert("v4");
    graph.insert(v3, v4, "v3-v4");

    Vertex<String> v5 = graph.insert("v5");
    Vertex<String> v6 =graph.insert("v6");
    graph.insert(v5, v6, "v5-v6");

    int count = 0;
    for (Edge<String> e: graph.edges()) {
      count++;
    }
    assertEquals(3, count);
  }

  // label vertices
  @Test
  public void labelNullVertexThrowsPositionException() {
    try {
      Vertex<String> v = null;
      graph.label(v, "null");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  public void labelForNullVertexThrowsPositionException() {
    try {
      Vertex<String> v = null;
      graph.label(v);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  public void labelRemovedVertexThrowsPositionException() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.remove(v);
      graph.label(v, "l");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  // label edges
  @Test
  public void labelNullEdgeThrowsPositionException() {
    try {
      Edge<String> e = null;
      graph.label(e, "null");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  public void labelForNullEdgeThrowsPositionException() {
    try {
      Edge<String> e = null;
      graph.label(e);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  public void labelRemovedEdgeThrowsPositionException() {
    try {
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      Edge<String> v1v2 = graph.insert(v1, v2, "e");
      graph.remove(v1v2);
      graph.label(v1v2, "l");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  public void labelVertex() {
    Vertex<String> v1 = graph.insert("v1");
    graph.label(v1, "LABEL");
    assertEquals("LABEL", graph.label(v1));
    Vertex<String> v2 = graph.insert("v2");
    graph.label(v2, null);
    assertNull(graph.label(v2));
  }

  @Test
  public void labelEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1v2");
    graph.label(v1v2, "LABEL");
    assertEquals("LABEL", graph.label(v1v2));

    Vertex<String> v3 = graph.insert("v3");
    Vertex<String> v4 = graph.insert("v4");
    Edge<String> v3v4 = graph.insert(v3, v4, "v3v4");
    graph.label(v3v4, null);
    assertNull(graph.label(v3v4));
  }

  // clear labels
  @Test
  public void clearLabels() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");

    graph.label(v1, "a");
    graph.label(v2, "b");
    graph.label(v3, "c");

    Edge<String> v1v2 = graph.insert(v1, v2, "e1");
    Edge<String> v1v3 = graph.insert(v1, v3, "e2");

    graph.label(v1v2, "a b");
    graph.label(v1v3, "b c");

    graph.clearLabels();

    Iterable<Vertex<String>> vertices = graph.vertices();
    Iterable<Edge<String >> edges = graph.edges();

    // all labels should be null now!
    for (Vertex<String> vertex : vertices) {
      assertNull(graph.label(vertex));
      for (Edge<String> edge : graph.outgoing(vertex)) {
        assertNull(graph.label(edge));
      }
      for (Edge<String> edge : graph.incoming(vertex)) {
        assertNull(graph.label(edge));
      }
    }
    for(Edge<String> edge : edges) {
      assertNull(graph.label(edge));
    }
  }
}
