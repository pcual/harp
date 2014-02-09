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

import java.io.IOException;
import java.nio.file.Path;

/**
 * A {@code Resource}, be it a file, service, or some kind of dependency, is anything made
 * available inside an {@link harp.bubble.ExecutionBubble} for use by the
 * {@link harp.executable.Executable} run there.
 *
 * <p>Examples of common {@code Resource}s:
 * <ul>
 * <li>A code repository, containing the code for the {@code Executable} or for other
 * {@code Resource}s
 * <li>A software library on which the {@code Executable} depends
 * <li>A database server
 * <li>A background service such as a message queue
 * </ul>
 */
public interface Resource {

  /**
   * TODO
   */
  String getName();

  /**
   * Initialize this {@code Resource} at the given location. Subsequent calls to this method,
   * regardless of the location given, must throw an {@link IllegalStateException}.
   *
   * @throws IllegalStateException if this {@code Resource} has already been initialized
   * @throws IOException thrown by the initialization code
   */
  void initialize(Path bubbleLocation) throws IOException;

  /**
   * Clean up this {@code Resource}, called when execution is complete and the
   * {@link harp.bubble.ExecutionBubble} containing this resource is being torn down.
   */
  void cleanUp() throws IOException;
}
