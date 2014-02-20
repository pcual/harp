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

import com.google.common.collect.ImmutableList;
import java.util.List;
import junit.framework.TestCase;

/**
 * Tests for {@link harp.util.Graph}.
 */
public class GraphTest extends TestCase {

  public void testSimpleGraph() throws Exception {
    Graph<String> graph = new Graph();
    graph.addNode("a");
    graph.addNode("b");
    graph.addNode("c");
    graph.addEdge("c", "b");
    graph.addEdge("b", "a");

    assertEquals(ImmutableList.of("c", "b", "a"), graph.topologicallySorted());
  }

  /**
   *     b - d
   * a -       - f
   *     c - e
   *
   */
  public void testDiamondGraph() throws Exception {
    Graph<String> graph = new Graph();
    graph.addNode("a");
    graph.addNode("b");
    graph.addNode("c");
    graph.addNode("d");
    graph.addNode("e");
    graph.addNode("f");
    graph.addEdge("a", "b");
    graph.addEdge("a", "c");
    graph.addEdge("b", "d");
    graph.addEdge("c", "e");
    graph.addEdge("d", "f");
    graph.addEdge("e", "f");

    List<String> sorted = graph.topologicallySorted();
    assertTrue(sorted.indexOf("a") < sorted.indexOf("b"));
    assertTrue(sorted.indexOf("b") < sorted.indexOf("d"));
    assertTrue(sorted.indexOf("d") < sorted.indexOf("f"));
    assertTrue(sorted.indexOf("a") < sorted.indexOf("c"));
    assertTrue(sorted.indexOf("c") < sorted.indexOf("e"));
    assertTrue(sorted.indexOf("e") < sorted.indexOf("f"));
  }

  public void testCycle() throws Exception {
    Graph<String> graph = new Graph();
    graph.addNode("a");
    graph.addNode("b");
    graph.addNode("c");
    graph.addEdge("a", "b");
    graph.addEdge("b", "c");
    graph.addEdge("c", "a");

    try {
      graph.topologicallySorted();
      fail("Cycle error expected, but none thrown.");
    } catch (IllegalStateException expected) {}
  }
}
