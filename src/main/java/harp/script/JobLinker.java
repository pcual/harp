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

package harp.script;

/**
 * An interface for objects that assemble the Harp scripts in some context (such as the current
 * working directory or repository) into a {@link ScriptGraph}.
 *
 * Conceptually, a {@code JobLinker} concatenates all the needed Harp scripts to perform a
 * particular task, such as running a single Executable.
 */
public interface JobLinker {

  /**
   * Links Harp scripts into a {@code ScriptGraph}.
   *
   * @throws LinkException if there is an error during linking
   */
  ScriptGraph link();

  public static final class LinkException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LinkException() {
      super();
    }

    public LinkException(Throwable cause) {
      super(cause);
    }
  }
}
