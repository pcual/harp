/*
 * Copyright 2014 Yash Parghi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package harp.util;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * TODO
 */
// TODO: immutability; use a builder?
// TODO: sub-graph of a node and its dependencies (transitive closure)
public final class Graph<T> {

  private final Map<T, Node<T>> keyToNode;
  private final Set<Node<T>> nodes;

  public Graph() {
    this.keyToNode = new HashMap<>();
    this.nodes = new HashSet<>();
  }

  public void addNode(T data) {
    Node<T> newNode = new Node<>(data);
    nodes.add(newNode);
    keyToNode.put(data, newNode);
  }

  public void addEdge(T from, T to) {
    if (!keyToNode.containsKey(from)) {
      addNode(from);
    }
    if (!keyToNode.containsKey(to)) {
      addNode(to);
    }
    keyToNode.get(from).addEdge(keyToNode.get(to));
  }

  /**
   * Returns a list of Node <em>keys</em> in topological order of nodes.
   *
   * <p>The topological sort algorithm used is the standard one based on depth-first search (marked,
   * unmarked, temporarily marked).
   */
  public List<T> topologicallySorted() {
    List<T> outList = new ArrayList<>();
    Set<Node<T>> unmarked = new HashSet<>(nodes);
    Set<Node<T>> temporarilyMarked = new HashSet<>();
    while (!unmarked.isEmpty()) {
      Node<T> next = unmarked.iterator().next();
      visit(next, unmarked, temporarilyMarked, outList);
    }
    return outList;
  }

  private void visit(
      Node<T> node, Set<Node<T>> unmarked, Set<Node<T>> temporarilyMarked, List<T> outList) {
    if (temporarilyMarked.contains(node)) {
      // TODO Use some GraphCycleException instead?
      throw new IllegalStateException("Graph has a cycle!");
    }
    if (!unmarked.contains(node)) {
      return;
    }
    temporarilyMarked.add(node);
    unmarked.remove(node);
    for (Node<T> toNode : node.outEdges) {
      visit(toNode, unmarked, temporarilyMarked, outList);
    }
    temporarilyMarked.remove(node);
    outList.add(0, node.data);
  }

  private static final class Node<T> {

    private final T data;

    /**
     * Nodes for which there is an edge from this Node to that one.
     */
    private final Set<Node<T>> outEdges;

    private Node(T data) {
      this.data = data;
      this.outEdges = new HashSet<>();
    }

    private void addEdge(Node<T> to) {
      Preconditions.checkArgument(!outEdges.contains(to));
      outEdges.add(to);
    }
  }
}
