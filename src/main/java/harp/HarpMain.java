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

import harp.bubble.ExecutionBubble;
import harp.bubble.LocalExecutionBubbleCreator;
import harp.executable.Executable;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * TODO
 */
public final class HarpMain {

  // TODO Write test(s) for end-to-end execution!
  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Harp usage:");
      System.err.println("java -jar <path to harp jar> EXECUTABLE CONFIG_FILE");
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

    String executableName = args[0];
    String harpScriptPath = args[1];

    String harpScriptText = new Scanner(Paths.get(harpScriptPath)).useDelimiter("\\A").next();

    Context context = GroovyRunner.parseHarpScript(harpScriptText);

    Executable executableToRun = null;
    for (Executable exec : context.getExecutables()) {
      if (exec.getName().equals(executableName)) {
        executableToRun = exec;
        break;
      }
    }

    if (executableToRun == null) {
      System.err.println("Executable '" + executableName + "' not found!");
      System.exit(1);
    }

    // TODO!! Add a Resource to this executable.

    ExecutionBubble bubble = new LocalExecutionBubbleCreator().create();
    bubble.execute(executableToRun);
    bubble.cleanUp();
    System.out.println("Done executing " + executableToRun.getName());
  }

}
