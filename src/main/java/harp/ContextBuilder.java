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

package harp;

import com.google.common.base.Preconditions;
import harp.executable.Executable;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 */
public final class ContextBuilder {

  private final List<Executable> executables;

  ContextBuilder() {
    executables = new ArrayList<>();
  }

  public void addExecutable(Executable executable) {
    // TODO this doesn't make any sense yet since we don't know what the executable's equals() does.
    // Figure out whether duplication checks are necessary, and if so, how they should work.
    Preconditions.checkArgument(
        !executables.contains(executable),
        "This executable has already been declared!");
    executables.add(executable);
  }

  public Context build() {
    return new Context(executables);
  }
}
