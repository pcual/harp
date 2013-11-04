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

package harp.resource;

import harp.bubble.ExecutionBubble;
import harp.bubble.LocalExecutionBubbleCreator;
import java.nio.file.Files;
import java.nio.file.Path;
import junit.framework.TestCase;

/**
 * Tests for adding a {@link FileResource}.
 */
public class FileResourceTest extends TestCase {

  /**
   * Create a local bubble with a {@link harp.bubble.LocalExecutionBubbleCreator} and add a
   * {@code FileResource} for some temporary file.
   */
  public void testAddFileResourceToLocalBubble() throws Exception {
    Path src = Files.createTempFile("FileResourceTest", null);
    Path tempDirPath = src.getParent();
    // The relative (to tempDirPath) path of the temp file we just created
    Path relativeDest = tempDirPath.relativize(src);
    FileResource fileResource = FileResource.atPath(src, relativeDest);
    LocalExecutionBubbleCreator creator = new LocalExecutionBubbleCreator();
    ExecutionBubble bubble = creator.create();
    bubble.addResource(fileResource);
    // TODO Get the bubble to execute some simple (no-op?) executable instead of relying on its
    // current initialization hack. THEN check that initialization happened correctly.
    Path finalDest = bubble.getLocation().resolve(relativeDest);
    assertTrue(Files.exists(finalDest));
    bubble.cleanUp();
    assertFalse(Files.exists(finalDest));
    Files.delete(src);
  }

  // TODO Find some way to test adding a file under the current directory, using FileResource.of().
}
