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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A creator of {@link LocalExecutionBubble}s.
 */
public final class LocalExecutionBubbleCreator implements ExecutionBubbleCreator {

  // TODO use a static factory method and hide the default constructor?

  /**
   * Creates a {@link LocalExecutionBubble} backed by a local temporary directory.
   */
  @Override
  public ExecutionBubble create() throws IOException {
    Path tempDir = Files.createTempDirectory("harp_local_");
    return new LocalExecutionBubble(tempDir);
  }

}
