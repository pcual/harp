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

import java.io.File;

/**
 * An {@link ExecutionBubble} in a local temporary directory.
 */
final class LocalExecutionBubble implements ExecutionBubble {

  private final File tempDir;

  public LocalExecutionBubble(File tempDir) {
    this.tempDir = tempDir;
  }

  @Override
  public File getLocation() {
    return tempDir;
  }

  // TODO: Do this correctly the JDK7 way. See:
  // http://stackoverflow.com/questions/779519/delete-files-recursively-in-java/8685959#8685959
  @Override
  public void cleanUp() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
