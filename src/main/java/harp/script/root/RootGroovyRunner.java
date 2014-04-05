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

package harp.script.root;

import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;

/**
 * Logic for evaluating a root.harp script.
 */
// TODO Unit tests a la HarpScriptTest
public final class RootGroovyRunner {

  private RootGroovyRunner() {}  // Do not instantiate.

  /**
   * Evaluates the text of a root.harp script and returns a {@code RootContext} with all the parsed
   * {@link Environment}s in it.
   */
  public static RootContext parseRootHarpScript(String scriptText) {
    CompilerConfiguration config = new CompilerConfiguration();
    config.setScriptBaseClass(RootHarpScript.class.getCanonicalName());

    RootHarpBinding binding = new RootHarpBinding();
    GroovyShell shell = new GroovyShell(binding, config);

    shell.evaluate(scriptText);

    return binding.getRootContextBuilder().build();
  }
}
