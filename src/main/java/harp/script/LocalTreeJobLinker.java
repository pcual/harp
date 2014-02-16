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

package harp.script;

import com.google.common.base.Preconditions;
import harp.dispatch.HarpJob;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A {@code JobLinker} that gathers the transitive closure of included Harp scripts starting at
 * a given path and searching from that path's directory.
 *
 * A {@code LocalTreeJobLinker} looks for Harp scripts under some root directory indicated by
 * the presence of a file {@code root.harp}.
 */
// TODO integration test parsing multiple .harp files in multiple dir levels
// TODO figure out how root.harp fits into overall evaluation!
//
// TODO is there a better way to find "include ..." lines in a Groovy script besides regex search?
//      Can we do it without evaluating the whole script as Groovy, since evaluation may have side
//      effects (even though it shouldn't), and so that we don't need to evaluate a script twice?
public final class LocalTreeJobLinker implements JobLinker {

  private static final String ROOT_FILE_NAME = "root.harp";

  private final Path startingPath;
  private Path rootDir;
  private final StringBuilder scriptConcatenator;

  // TODO static factory method?
  public LocalTreeJobLinker(String startingPath) {
    this.startingPath = Paths.get(Preconditions.checkNotNull(startingPath));
    Preconditions.checkArgument(Files.isRegularFile(this.startingPath));
    this.scriptConcatenator = new StringBuilder();
  }

  @Override
  public HarpJob link() {
    this.rootDir = findRootDir();

    // TODO build an include dependency graph and concatenate them in topological order using a
    // topological sort. See:
    // http://stackoverflow.com/questions/2739392/sample-directed-graph-and-topological-sort-code
    // http://en.wikipedia.org/wiki/Topological_sort
    //
    // TODO Then write tests!!!

    System.err.println("Result script:");
    System.err.println(this.scriptConcatenator.toString());
    throw new RuntimeException("stopping for testing");
  }

  private Path findRootDir() {
    Path possibleRootDir = this.startingPath.getParent();
    while (possibleRootDir != null) {
      if (Files.isRegularFile(this.startingPath.resolve(ROOT_FILE_NAME))) {
        return possibleRootDir;
      }
      possibleRootDir = possibleRootDir.getParent();
    }
    throw new IllegalStateException(
        "No root.harp file was found in or above the starting directory: "
        + this.startingPath.getParent());
  }
}
