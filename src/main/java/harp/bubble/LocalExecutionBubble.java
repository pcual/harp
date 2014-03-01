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

import com.google.common.base.Preconditions;
import harp.executable.Executable;
import harp.resource.Resource;
import harp.util.DeletingFileVisitor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * An {@link ExecutionBubble} in a local temporary directory.
 */
// TODO One day, use something like chroot to isolate the bubble in the filesystem. Maybe I'll need
// to break off a UnixLocalExecutionBubble? Note this may require spawning a separate Harp process
// to create a bubble, load resources in it, and execute an executable!
// NOTE: Consider using mount --bind to include outside resources, as described in:
// http://unix.stackexchange.com/questions/55710/how-can-i-access-a-directory-outside-a-chroot-from-within-it
final class LocalExecutionBubble implements ExecutionBubble {

  private final Path tempDir;

  private final List<Resource> resources = new ArrayList<>();

  public LocalExecutionBubble(Path tempDir) {
    Preconditions.checkArgument(Files.isDirectory(tempDir));
    this.tempDir = tempDir;
  }

  @Override
  public Path getLocation() {
    return tempDir;
  }

  @Override
  public void cleanUp() throws IOException {
    Files.walkFileTree(tempDir, new DeletingFileVisitor());
  }

  @Override
  public void addResource(Resource resource) {
    resources.add(resource);
  }

  @Override
  public void execute(Executable executable) {
    try {
      for (Resource resource : resources) {
        resource.initialize(tempDir);
      }
    } catch (IOException ioEx) {
      throw new RuntimeException(ioEx);
    }

    ProcessBuilder processBuilder = new ProcessBuilder(executable.getArgs());
    // For now, for testing, pipe the process's stdout to Harp's stdout.
    processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
    processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
    processBuilder.directory(tempDir.toFile());
    // TODO figure out exception-handling
    try {
      processBuilder.start();
    } catch (IOException ioEx) {
      throw new RuntimeException(ioEx);
    }
  }
}
