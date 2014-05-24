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

package harp.node.basichttp;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import harp.node.Node;
import harp.node.NodeBridge;
import harp.node.NodeSpec;

/**
 * A simple NodeSpec for a node that accepts a job by HTTP request and is managed by HTTP requests.
 */
public class BasicHttpNodeSpec implements NodeSpec {

  private final String name;
  private final BasicHttpNode node;
  private final BasicHttpNodeBridge bridge;

  private BasicHttpNodeSpec(String name, BasicHttpNode node, BasicHttpNodeBridge bridge) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(name));
    this.name = name;
    this.node = Preconditions.checkNotNull(node);
    this.bridge = Preconditions.checkNotNull(bridge);
  }

  @Override
  public Node getNode() {
    return node;
  }

  @Override
  public NodeBridge getBridge() {
    return bridge;
  }

  @Override
  public String getName() {
    return name;
  }
}
