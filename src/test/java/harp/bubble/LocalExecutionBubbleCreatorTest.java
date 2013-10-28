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

package harp.bubble;

import java.nio.file.Files;
import java.nio.file.Path;
import junit.framework.TestCase;

/**
 * Tests for {@link LocalExecutionBubbleCreator}.
 */
public class LocalExecutionBubbleCreatorTest extends TestCase {

  public void testBubbleCreation() throws Exception {
    LocalExecutionBubbleCreator creator = new LocalExecutionBubbleCreator();
    ExecutionBubble bubble = creator.create();
    assertTrue(Files.exists(bubble.getLocation()));
    assertEquals(System.getProperty("java.io.tmpdir"), bubble.getLocation().getParent().toString());
    bubble.cleanUp();
  }

  public void testBubbleCleanup_empty() throws Exception {
    LocalExecutionBubbleCreator creator = new LocalExecutionBubbleCreator();
    ExecutionBubble bubble = creator.create();
    bubble.cleanUp();
    assertFalse(Files.exists(bubble.getLocation()));
  }

  public void testBubbleCleanup_nonEmpty() throws Exception {
    LocalExecutionBubbleCreator creator = new LocalExecutionBubbleCreator();
    ExecutionBubble bubble = creator.create();
    Path newFilePath = bubble.getLocation().resolve("some_file.txt");
    Files.createFile(newFilePath);
    assertTrue(Files.exists(newFilePath));
    bubble.cleanUp();
    assertFalse(Files.exists(bubble.getLocation()));
  }
}
