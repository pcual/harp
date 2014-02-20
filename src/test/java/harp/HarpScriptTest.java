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

import harp.script.Context;
import harp.script.GroovyRunner;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import harp.dispatch.Dispatcher;
import harp.executable.Executable;
import harp.node.NodeSpec;
import harp.resource.Resource;
import java.util.List;
import junit.framework.TestCase;

/**
 * TODO
 */
public class HarpScriptTest extends TestCase {

  public void testDeclareExecutableWithClosure() {
    String script = Joiner.on("\n").join(
        "executable {",
        "  name 'myExec'",
        "  args 'arg1', 'arg2', 'arg3'",
        "  resources 'res1', 'res2', 'res3'",
        "}"
        );
    Context result = GroovyRunner.parseHarpScript(script);

    List<Executable> executables = result.getExecutables();
    assertEquals(1, executables.size());
    assertEquals("myExec", executables.get(0).getName());
    assertEquals(ImmutableList.of("arg1", "arg2", "arg3"), executables.get(0).getArgs());
    assertEquals(ImmutableList.of("res1", "res2", "res3"), executables.get(0).getResources());
  }

  public void testDeclareExecutableWithMap() {
    String script = Joiner.on("\n").join(
        "myExec = [",
        "  getName: { 'myExec' },",
        "  getArgs: { [ 'arg1', 'arg2', 'arg3' ] },",
        "  getResources: { [ 'res1', 'res2', 'res3' ] }",
        "] as harp.executable.Executable",
        "executable myExec"
        );

    Context result = GroovyRunner.parseHarpScript(script);

    List<Executable> executables = result.getExecutables();
    assertEquals(1, executables.size());
    assertEquals("myExec", executables.get(0).getName());
    assertEquals(ImmutableList.of("arg1", "arg2", "arg3"), executables.get(0).getArgs());
    assertEquals(ImmutableList.of("res1", "res2", "res3"), executables.get(0).getResources());
  }

  public void testDeclareExecutableWithClass() {
    String script = Joiner.on("\n").join(
        "public class MyExec implements harp.executable.Executable {",
        "  String name = 'myExec'",
        "  List<String> args = [ 'arg1', 'arg2', 'arg3' ]",
        "  List<String> resources = [ 'res1', 'res2', 'res3' ]",
        "}",
        "executable new MyExec()"
        );

    Context result = GroovyRunner.parseHarpScript(script);

    List<Executable> executables = result.getExecutables();
    assertEquals(1, executables.size());
    assertEquals("myExec", executables.get(0).getName());
    assertEquals(ImmutableList.of("arg1", "arg2", "arg3"), executables.get(0).getArgs());
    assertEquals(ImmutableList.of("res1", "res2", "res3"), executables.get(0).getResources());
  }

  // TODO Find a sleeker resource declaration syntax and test it.
  public void testDeclareResourceWithClass() {
    String script = Joiner.on("\n").join(
        "public class MyResource implements harp.resource.Resource {",
        "  String name = 'myResource'",
        "  void initialize(java.nio.file.Path bubbleLocation) {}",
        "  void cleanUp() {}",
        "}",
        "resource new MyResource()"
        );

    Context result = GroovyRunner.parseHarpScript(script);

    List<Resource> resources = result.getResources();
    assertEquals(1, resources.size());
    assertEquals("myResource", resources.get(0).getName());
  }

  public void testDeclareDispatcher() {
    String script = Joiner.on("\n").join(
        "public class MyDispatcher implements harp.dispatch.Dispatcher {",
        "  String name = 'myDispatcher'",
        "  void dispatch(harp.script.HarpJob job) {}",
        "}",
        "dispatcher new MyDispatcher()"
        );

    Context result = GroovyRunner.parseHarpScript(script);

    List<Dispatcher> dispatchers = result.getDispatchers();
    assertEquals(1, dispatchers.size());
    assertEquals("myDispatcher", dispatchers.get(0).getName());
  }

  public void testDeclareNodeSpec() {
    String script = Joiner.on("\n").join(
        "public class MyNodeSpec implements harp.node.NodeSpec {",
        "  String name = 'myNodeSpec'",
        "  harp.node.Node getNode() { return null }",
        "  harp.node.NodeBridge getBridge() { return null }",
        "}",
        "node new MyNodeSpec()"
        );

    Context result = GroovyRunner.parseHarpScript(script);

    List<NodeSpec> nodeSpecs = result.getNodeSpecs();
    assertEquals(1, nodeSpecs.size());
    assertEquals("myNodeSpec", nodeSpecs.get(0).getName());
  }
}
