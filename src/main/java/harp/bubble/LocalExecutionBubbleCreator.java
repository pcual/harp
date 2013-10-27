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

import com.google.common.io.Files;
import java.io.File;

/**
 * A creator of {@link LocalExecutionBubble}s.
 */
public final class LocalExecutionBubbleCreator implements ExecutionBubbleCreator {

  /**
   * Creates a {@link LocalExecutionBubble} backed by a local temporary directory.
   */
  @Override
  public ExecutionBubble create() {
    File tempDir = Files.createTempDir();
    return new LocalExecutionBubble(tempDir);
  }

}
