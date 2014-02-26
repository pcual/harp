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

import harp.script.HarpJob;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import harp.dispatch.Dispatcher;
import harp.dispatch.LocalDispatcher;
import harp.script.LocalTreeJobLinker;
import java.nio.file.Files;
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
      System.err.println("java -jar <path to harp jar> run EXECUTABLE CONFIG_FILE");
      System.err.println();
      System.err.println("Parameters:");
      System.err.println("EXECUTABLE   name of the Harp executable to execute");
      System.err.println("CONFIG_FILE  path to a Harp config file to parse");
      System.exit(1);
    }

    // For now, look for the given executable and all its resources within a single file.
    //
    // TODO Figure out how to search for resources (at least) across directories, possibly
    // bounded by the presence of some root harp config file.
    //
    // TODO Don't take the harp config file as a parameter! Enforce some convention like looking
    // for files named 'HARP' or 'harp.conf' or 'run.harp'.

    String executableName = args[1];
    String harpScriptPath = args[2];

    // TODO Choose a linker from the command line or some configuration means, possibly root.harp
    // TODO uncomment this when it's ready!
    //HarpJob thisJob = new LocalTreeJobLinker(harpScriptPath).link();

    String harpScriptText = Joiner.on("\n").join(
        Files.readAllLines(Paths.get(harpScriptPath), Charsets.UTF_8));

    HarpJob thisJob = new HarpJob(harpScriptText, ImmutableList.of(executableName));

    // TODO Choose a dispatcher from the command line or some configuration means, possibly
    // root.harp
    Dispatcher dispatcher = new LocalDispatcher("simpleCommandLineDispatcher");
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
    Preconditions.checkArgument(args[0].equals("test"));
    new LocalTreeJobLinker(args[1]).link();
  }
}
