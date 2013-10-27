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

import harp.executable.Executable;
import java.io.File;

/**
 * An ExecutionBubble is an isolated environment in which a single {@link Executable} is run.
 *
 * <p>Roughly speaking, an {@code ExecutionBubble} corresponds to a temporary directory.
 */
public interface ExecutionBubble {

  /**
   * Returns the filesystem location of this bubble, i.e. the temporary directory to which it
   * corresponds.
   */
  File getLocation();

  /**
   * Clean up all resources used by this bubble.
   */
  void cleanUp();
}
