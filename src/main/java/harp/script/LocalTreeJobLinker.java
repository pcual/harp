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

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import harp.util.Graph;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A {@code JobLinker} that concatenates Harp scripts in a local tree.
 *
 * A {@code LocalTreeJobLinker} looks for Harp scripts under some root directory indicated by
 * the presence of a file {@code root.harp}, and concatenates the transitive closure of included
 * Harp scripts starting at a given path.
 */
// TODO integration test parsing multiple .harp files in multiple dir levels
// TODO figure out how root.harp fits into overall evaluation!
// TODO what if we want to link for a set of scripts/executables?
public final class LocalTreeJobLinker implements JobLinker {

  private static final String ROOT_FILE_NAME = "root.harp";
  /**
   * A pattern for lines that match:
   *   harpInclude "path/to/some/file.harp"
   */
  private static final Pattern INCLUDE_PATTERN = Pattern.compile(
      "^\\s*harpInclude\\s*\"([\\w\\.-" + Pattern.quote(File.separator) + "]*)\"\\s*$");

  private static final Joiner NEWLINE_JOINER = Joiner.on("\n");

  private final Path startingPath;
  private Path rootDir;

  // TODO static factory method?
  public LocalTreeJobLinker(String startingPath) {
    this.startingPath = Paths.get(Preconditions.checkNotNull(startingPath)).toAbsolutePath();
    Preconditions.checkArgument(
        Files.isRegularFile(this.startingPath),
        "Expected a harp script at this path but didn't fine one: " + this.startingPath);
  }

  // TODO tests!!!! then an integration test for correctly executing the concatenated script
  @Override
  public HarpJob link() {
    this.rootDir = findRootDir();

    Set<Path> scriptsToRead = new HashSet<>();
    scriptsToRead.add(startingPath);
    Graph<String> scriptDependencies = new Graph<>();
    Map<String, String> scriptContents = new HashMap<>();

    while (!scriptsToRead.isEmpty()) {
      Path nextScript = scriptsToRead.iterator().next();
      processFile(nextScript, scriptDependencies, scriptsToRead, scriptContents);
      scriptsToRead.remove(nextScript);
    }

    System.err.println(scriptContents);
    System.err.println(scriptDependencies.topologicallySorted());
    throw new RuntimeException("stopping for testing");
  }

  private void processFile(
      Path scriptPath,
      Graph<String> scriptDependencies,
      Set<Path> scriptsToRead,
      Map<String, String> scriptContents) {
    List<String> lines;
    try {
      lines = Files.readAllLines(scriptPath, Charsets.UTF_8);
    } catch (IOException ioEx) {
      throw new LinkException(ioEx);
    }

    String scriptRelativePath = this.rootDir.relativize(scriptPath).toString();
    scriptContents.put(scriptRelativePath, NEWLINE_JOINER.join(lines));

    for (String line : lines) {
      Matcher lineMatcher = INCLUDE_PATTERN.matcher(line);
      if (lineMatcher.find()) {
        String includedPath = lineMatcher.group(1);
        scriptDependencies.addEdge(includedPath, scriptRelativePath);
        if (!scriptContents.containsKey(includedPath)) {
          scriptsToRead.add(this.rootDir.resolve(includedPath));
        }
      }
    }
  }

  private Path findRootDir() {
    Path possibleRootDir = this.startingPath.getParent();
    while (possibleRootDir != null) {
      if (Files.isRegularFile(possibleRootDir.resolve(ROOT_FILE_NAME))) {
        return possibleRootDir.toAbsolutePath();
      } else {
        possibleRootDir = possibleRootDir.getParent();
      }
    }
    throw new IllegalStateException(
        "No root.harp file was found in or above the starting directory: "
        + this.startingPath.getParent());
  }
}
