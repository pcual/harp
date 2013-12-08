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
  private List<String> resources = null;

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

  public void resource(String... resources) {
    Preconditions.checkState(this.resources == null);
    Preconditions.checkNotNull(resources);
    Preconditions.checkArgument(resources.length > 0);
    this.resources = ImmutableList.copyOf(resources);
  }

  public Executable build() {
    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(args);
    if (resources == null) {
      resources = ImmutableList.of();
    }
    return new SimpleExecutable(name, args, resources);
  }

  /**
   * TODO
   */
  private static class SimpleExecutable implements Executable {

    private final String name;
    private final List<String> args;
    private final List<String> resources;

    private SimpleExecutable(String name, List<String> args, List<String> resources) {
      Preconditions.checkNotNull(name);
      Preconditions.checkNotNull(args);
      Preconditions.checkNotNull(resources);
      this.name = name;
      this.args = ImmutableList.copyOf(args);
      this.resources = ImmutableList.copyOf(resources);
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public List<String> getArgs() {
      return args;
    }

    @Override
    public List<String> getResources() {
      return resources;
    }
  }
}