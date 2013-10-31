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
import harp.resource.Resource;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * An {@link ExecutionBubble} in a local temporary directory.
 */
// TODO One day, use something like chroot to isolate the bubble in the filesystem. Maybe I'll need
// to break off a UnixLocalExecutionBubble? Note this may require spawning a separate Harp process
// to create a bubble, load resources in it, and execute an executable!
final class LocalExecutionBubble implements ExecutionBubble {

  private final Path tempDir;

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
    // TODO add resources to some kind of manifest -- a list?
    throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * A {@link FileVisitor} for walking a directory and deleting files. This is used to recursively
   * delete the entire contents of a directory.
   */
  private static class DeletingFileVisitor extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
      Files.delete(file);
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path file, IOException exc) throws IOException {
      if (exc != null) {
        throw exc;
      }
      Files.delete(file);
      return FileVisitResult.CONTINUE;
    }
  }
}
