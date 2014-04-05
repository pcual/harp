/*
 * Copyright 2014 Yash Parghi
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

package harp.util;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Static utility methods for file operations.
 */
public final class FileUtil {

  /**
   * Find a file with the given base name, searching upward from the given starting directory.
   *
   * @param filename the base filename to look for
   * @param startingPath the starting directory to search upward from
   *
   * @return an {@code Optional} containing the Path of the file if found, or an empty Optional
   *     otherwise
   */
  public static Optional<Path> findUpward(String filename, Path startingPath) {
    Preconditions.checkNotNull(filename);
    Preconditions.checkArgument(!filename.contains(File.separator));
    Preconditions.checkArgument(Files.isDirectory(startingPath));
    Path possibleRootDir = startingPath;
    while (possibleRootDir != null) {
      Path possiblePath = possibleRootDir.resolve(filename);
      if (Files.isRegularFile(possiblePath)) {
        return Optional.of(possiblePath);
      } else {
        possibleRootDir = possibleRootDir.getParent();
      }
    }
    return Optional.absent();
  }
}
