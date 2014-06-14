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

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import harp.dispatch.Dispatcher;
import harp.node.NodeSpec;
import harp.node.basichttp.BasicHttpNodeSpec;
import harp.script.HarpJob;
import harp.script.ScriptGraph;
import harp.script.root.Environment;
import harp.script.root.RootContext;
import harp.script.root.RootGroovyRunner;
import harp.util.FileUtil;
import java.io.FileNotFoundException;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Map.Entry;

/**
 * TODO
 */
public final class HarpMain {

  private static final Map<String, String> OPERATIONS = ImmutableMap.of(
      "run", "Run a Harp executable",
      "node", "Start a Harp node",
      "test", "TODO REMOVE"
  );

  public static void main(String[] args) throws Exception {
    if (args.length == 0 || !OPERATIONS.containsKey(args[0])) {
      System.err.println("Harp usage:");
      System.err.println("java -jar <path to harp jar> OPERATION arg...");
      System.err.println();
      System.err.println("Where operation is one of these commands:");
      for (Entry<String, String> opEntry : OPERATIONS.entrySet()) {
        System.err.println(Strings.padEnd(opEntry.getKey(), 8, ' ') + "  " + opEntry.getValue());
      }
      System.exit(1);
    }

    String op = args[0];
    switch (op) {
      case "run":
        run(args);
        break;
      case "node":
        node(args);
        break;
      case "test":
        test(args);
        break;
    }
  }

  private static void run(String[] args) throws Exception {
    Preconditions.checkArgument(args[0].equals("run"));
    if (args.length != 3) {
      System.err.println("Harp 'run' usage:");
      System.err.println("java -jar <path to harp jar> run ENVIRONMENT EXECUTABLE");
      System.err.println();
      System.err.println("Parameters:");
      System.err.println("ENVIRONMENT  name of the Harp Environment to use for execution");
      System.err.println("EXECUTABLE   Harp-path of the Harp executable to execute");
      System.exit(1);
    }

    Optional<Path> potentialRootHarpFilePath = FileUtil.findUpward(
        "root.harp", Paths.get("").toAbsolutePath());
    if (!potentialRootHarpFilePath.isPresent()) {
      throw new FileNotFoundException(
          "No root.harp file was found in or above the present directory.");
    }
    Path rootHarpFilePath = potentialRootHarpFilePath.get();

    String environmentName = args[1];
    // TODO implement a real naming system for paths. A Harp path like run.harp:executable might be
    // stupid.
    String[] executablePathParts = args[2].split(":");
    String executablePath = executablePathParts[0];
    String executableName = executablePathParts[1];

    String rootHarpContents = new String(Files.readAllBytes(rootHarpFilePath), Charsets.UTF_8);
    RootContext rootContext = RootGroovyRunner.parseRootHarpScript(rootHarpContents);
    Environment environment = rootContext.getEnvironment(environmentName);

    ScriptGraph linkedScripts = environment.getLinker().link(executablePath);

    HarpJob thisJob = new HarpJob(linkedScripts, ImmutableList.of(executableName));

    Dispatcher dispatcher = environment.getDispatcher();
    dispatcher.dispatch(thisJob);

    System.out.println("Done executing " + executableName);
    System.exit(0);
  }

  private static void node(String[] args) throws Exception {
    Preconditions.checkArgument(args[0].equals("node"));
    if (args.length != 3) {
      System.err.println("Harp 'node' usage:");
      System.err.println("java -jar <path to harp jar> node NODESPEC CONFIG_FILE");
      System.err.println();
      System.err.println("Parameters:");
      System.err.println("NODESPEC     name of the Harp NodeSpec to run as");
      System.err.println("CONFIG_FILE  path to a Harp config file to parse");
      System.exit(1);
    }
  }

  private static void test(String[] args) throws Exception {
    NodeSpec basicSpec = BasicHttpNodeSpec.create("testNode");
    basicSpec.getNode().start();
  }
}
