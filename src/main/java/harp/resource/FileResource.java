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

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A {@link Resource} for a local file. If you simply want to copy a file or directory into an
 * {@link harp.bubble.ExecutionBubble}, use a {@code FileResource}.
 */
// TODO figure out how this will work vis a vis a file in this repo/computer that should be
// shipped off to a bubble on a remote machine.
public final class FileResource implements Resource {

  private final String name;
  private final Path src;
  private final Path dest;

  private boolean initialized = false;
  private Path bubbleLocation;

  private FileResource(Path src, Path dest, String name) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(name));
    Preconditions.checkArgument(
        !dest.isAbsolute(),
        "You can't add a FileResource at an absolute path. The path you give must be relative "
        + "so that the file can be included under the root directory of an execution bubble.");
    this.name = name;
    this.src = src;
    this.dest = dest;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void initialize(Path bubbleLocation) throws IOException {
    Preconditions.checkState(!initialized);
    Files.copy(src, bubbleLocation.resolve(dest));
    initialized = true;
    this.bubbleLocation = bubbleLocation;
  }

  @Override
  public void cleanUp() throws IOException {
    Preconditions.checkState(initialized);
    Files.delete(bubbleLocation.resolve(dest));
  }

  /**
   * TODO
   */
  public static final FileResource of(Path src, String name) {
    return new FileResource(src, src, name);
  }

  /**
   * TODO
   */
  public static final FileResource atPath(Path src, Path dest, String name) {
    return new FileResource(src, dest, name);
  }
}
