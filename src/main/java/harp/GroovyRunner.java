/*
 * Copyright 2013 Yash Parghi
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

package harp;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;

/**
 * TODO
 */
public class GroovyRunner {

  /**
   * TODO
   */
  // TODO maybe this needs to return the builder instead of a finalized context? OR we need a single
  // method that parses all the scripts needed in one pass, then returns a finalized context.
  public static Context parseHarpScript(String scriptText) {
    ContextBuilder builder = Context.builder();

    Binding binding = new Binding();
    binding.setVariable("context", builder);

    CompilerConfiguration config = new CompilerConfiguration();
    config.setScriptBaseClass(HarpScript.class.getCanonicalName());

    GroovyShell shell = new GroovyShell(binding, config);
    shell.setProperty("contextToBuild", builder);

    shell.evaluate(scriptText);

    return builder.build();
  }
}
