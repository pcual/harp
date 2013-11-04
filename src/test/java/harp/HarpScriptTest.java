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
import com.google.common.collect.ImmutableList;
import harp.executable.Executable;
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
        "}"
        );
    Context result = GroovyRunner.parseHarpScript(script);

    List<Executable> executables = result.getExecutables();
    assertEquals(1, executables.size());
    assertEquals("myExec", executables.get(0).getName());
    assertEquals(ImmutableList.of("arg1", "arg2", "arg3"), executables.get(0).getArgs());
  }

  public void testDeclareExecutableWithMap() {
    String script = Joiner.on("\n").join(
        "myExec = [",
        "  getName: { 'myExec' },",
        "  getArgs: { [ 'arg1', 'arg2', 'arg3' ] }",
        "] as harp.executable.Executable",
        "executable myExec"
        );

    Context result = GroovyRunner.parseHarpScript(script);

    List<Executable> executables = result.getExecutables();
    assertEquals(1, executables.size());
    assertEquals("myExec", executables.get(0).getName());
    assertEquals(ImmutableList.of("arg1", "arg2", "arg3"), executables.get(0).getArgs());
  }

  public void testDeclareExecutableWithClass() {
    String script = Joiner.on("\n").join(
        "public class MyExec implements harp.executable.Executable {",
        "  String name = 'myExec'",
        "  List<String> args = [ 'arg1', 'arg2', 'arg3' ]",
        "}",
        "executable new MyExec()"
        );

    Context result = GroovyRunner.parseHarpScript(script);

    List<Executable> executables = result.getExecutables();
    assertEquals(1, executables.size());
    assertEquals("myExec", executables.get(0).getName());
    assertEquals(ImmutableList.of("arg1", "arg2", "arg3"), executables.get(0).getArgs());
  }

  public void testDeclareResource() {
    // TODO
  }
}