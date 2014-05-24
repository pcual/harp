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

import com.google.common.base.Joiner;
import harp.node.NodeSpec;
import junit.framework.TestCase;

/**
 * Tests declarations in simple node.harp scripts.
 */
public class NodeHarpScriptTest extends TestCase {

  public void testDeclareNodeSpec() {
    String script = Joiner.on("\n").join(
        "myNode = [",
        "  start: {},",
        "  stop: {}",
        "] as harp.node.Node",
        "",
        "myBridge = [",
        "] as harp.node.NodeBridge",
        "",
        "myNodeSpec = [",
        "  getName: { 'myNodeSpec' },",
        "  getNode: { myNode },",
        "  getBridge: { myBridge }",
        "] as harp.node.NodeSpec",
        "",
        "node myNodeSpec");

    NodeContext result = NodeGroovyRunner.parseHarpScript(script);

    NodeSpec declaredNodeSpec = result.getNodeSpec("myNodeSpec");
    assertEquals("myNodeSpec", declaredNodeSpec.getName());
    assertNotNull(declaredNodeSpec.getNode());
    assertNotNull(declaredNodeSpec.getBridge());
  }
}
