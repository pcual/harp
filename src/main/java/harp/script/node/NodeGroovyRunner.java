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

import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;

/**
 * Logic for evaluating a node.harp script.
 */
public final class NodeGroovyRunner {

  private NodeGroovyRunner() {}  // Do not instantiate.

  /**
   * Evaluates the text of a Harp script and returns a {@code Context} with all the parsed
   * elements in it.
   */
  public static NodeContext parseHarpScript(String scriptText) {
    CompilerConfiguration config = new CompilerConfiguration();
    config.setScriptBaseClass(NodeHarpScript.class.getCanonicalName());

    NodeHarpBinding binding = new NodeHarpBinding();
    GroovyShell shell = new GroovyShell(binding, config);

    shell.evaluate(scriptText);

    return binding.getNodeContextBuilder().build();
  }
}
