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

import com.google.common.base.Joiner;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

/**
 * TODO
 */
public final class HarpMain {

  public static void main(String[] args) {
    System.out.println("Hello Harp!");

    ContextBuilder builder = Context.builder();

    Binding binding = new Binding();
    binding.setVariable("context", builder);
    GroovyShell shell = new GroovyShell(binding);

    Object scriptResult = shell.evaluate(Joiner.on("\n").join(
        "import harp.definitions.Executable;",
        "println \"Context: \" + context",
        "context.addExecutable(new Executable() {",
        "  public String getCommand() { return \"some_command\"; }",
        "  public List<String> getArgs() { return null; }",
        "})"
        ));

    Context context = builder.build();
    System.out.println("scriptResult: " + context.getExecutables().get(0).getCommand());
  }

}
