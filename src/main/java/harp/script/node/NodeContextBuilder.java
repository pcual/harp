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

package harp.script.node;

import com.google.common.base.Preconditions;
import harp.node.NodeSpec;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A builder for assembling a NodeContext in the course of evaluating a node.harp script.
 */
public final class NodeContextBuilder {

  private final Set<String> nodeNames;
  private final List<NodeSpec> nodes;

  public NodeContextBuilder() {
    this.nodeNames = new HashSet<>();
    this.nodes = new ArrayList<>();
  }

  void addNode(NodeSpec nodeSpec) {
    Preconditions.checkNotNull(nodeSpec.getName(), "A NodeSpec must have a non-null name.");
    Preconditions.checkState(
        !nodeNames.contains(nodeSpec.getName()),
        "A NodeSpec named %s has already been declared.", nodeSpec.getName());
    nodeNames.add(nodeSpec.getName());
    nodes.add(nodeSpec);
  }

  NodeContext build() {
    return new NodeContext(nodes);
  }
}
