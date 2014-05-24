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
import com.google.common.collect.ImmutableMap;
import harp.node.NodeSpec;
import java.util.List;
import java.util.Map;

/**
 * A container for all the {@link NodeSpec}s parsed from a node.harp script.
 */
public final class NodeContext {

  private final Map<String, NodeSpec> namesToNodes;

  NodeContext(List<NodeSpec> nodes) {
    ImmutableMap.Builder<String, NodeSpec> builder = ImmutableMap.builder();
    for (NodeSpec node : nodes) {
      builder.put(node.getName(), node);
    }
    this.namesToNodes = builder.build();
  }

  public NodeSpec getNodeSpec(String name) {
    Preconditions.checkArgument(namesToNodes.containsKey(name), "No such NodeSpec " + name);
    return namesToNodes.get(name);
  }
}
