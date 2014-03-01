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
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import harp.util.DeletingFileVisitor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Map.Entry;
import junit.framework.TestCase;

/**
 * Tests for {@link LocalTreeJobLinker}.
 */
public class LocalTreeJobLinkerTest extends TestCase {

  private static final Splitter NEWLINE_SPLITTER = Splitter.on("\n");

  private Path tempDir;

  @Override public void setUp() throws Exception {
    tempDir = Files.createTempDirectory("harp_LocalTreeJobLinkerTest_");
  }

  @Override public void tearDown() throws Exception {
    Files.walkFileTree(tempDir, new DeletingFileVisitor());
    tempDir = null;
  }

  private Path setTempDirContents(Map<String, String> files) throws IOException {
    for (Entry<String, String> fileEntry : files.entrySet()) {
      Path outFile = tempDir.resolve(fileEntry.getKey());
      Files.write(outFile, NEWLINE_SPLITTER.split(fileEntry.getValue()), Charsets.UTF_8);
    }
    return tempDir;
  }

  private void assertScriptConcatenation(
      String concatenated, Map<String, String> contents, String... files) {
    StringBuilder expected = new StringBuilder();
    for (String file : files) {
      expected.append(String.format(ScriptGraph.LINKED_FILE_HEADER, file));
      expected.append(contents.get(file));
      expected.append("\n");
    }
    assertEquals(expected.toString(), concatenated);
  }

  public void testSingleFile() throws Exception {
    Map<String, String> contents = ImmutableMap.of(
        "root.harp", "",
        "run.harp", "blah"
    );
    setTempDirContents(contents);
    ScriptGraph linked = new LocalTreeJobLinker(tempDir.resolve("run.harp").toString()).link();
    assertScriptConcatenation(linked.getConcatenatedScript(), contents, "run.harp");
  }

  public void testParentAndChild() throws Exception {
    Map<String, String> contents = ImmutableMap.of(
        "root.harp", "",
        "parent.harp", "parent",
        "child.harp", "harpInclude 'parent.harp'\nchild"
    );
    setTempDirContents(contents);
    ScriptGraph linked = new LocalTreeJobLinker(tempDir.resolve("child.harp").toString()).link();
    assertScriptConcatenation(
        linked.getConcatenatedScript(), contents, "parent.harp", "child.harp");
  }

  public void testThreeLevels() throws Exception {
    Map<String, String> contents = ImmutableMap.of(
        "root.harp", "",
        "grandparent.harp", "grandparent",
        "parent.harp", "harpInclude 'grandparent.harp'\nparent",
        "child.harp", "harpInclude 'grandparent.harp'\nharpInclude 'parent.harp'\nchild"
    );
    setTempDirContents(contents);
    ScriptGraph linked = new LocalTreeJobLinker(tempDir.resolve("child.harp").toString()).link();
    assertScriptConcatenation(
        linked.getConcatenatedScript(), contents, "grandparent.harp", "parent.harp", "child.harp");
  }

  public void testNonexistentInclude() throws Exception {
    Map<String, String> contents = ImmutableMap.of(
        "root.harp", "",
        "run.harp", "harpInclude 'nonexistent.harp'\nblah"
    );
    setTempDirContents(contents);
    try {
      ScriptGraph linked = new LocalTreeJobLinker(tempDir.resolve("run.harp").toString()).link();
      fail("Expected linking to fail for a nonexistent include, but it succeeded.");
    } catch (JobLinker.LinkException expected) {}
  }
}
