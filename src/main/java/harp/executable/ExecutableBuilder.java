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

package harp.executable;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * TODO
 */
public class ExecutableBuilder {
  private String name = null;
  private List<String> args = null;

  public void name(String name) {
    Preconditions.checkState(this.name == null);
    Preconditions.checkNotNull(name);
    this.name = name;
  }

  public void args(String... args) {
    Preconditions.checkState(this.args == null);
    Preconditions.checkNotNull(args);
    Preconditions.checkArgument(args.length > 0);
    this.args = ImmutableList.copyOf(args);
  }

  public Executable build() {
    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(args);
    return new Executable() {
      @Override
      public String getName() {
        return name;
      }

      @Override
      public List<String> getArgs() {
        return args;
      }
    };
  }

}
