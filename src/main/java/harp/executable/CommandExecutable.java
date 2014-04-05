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

package harp.executable;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@code Executable} that runs a command-line command.
 */
public final class CommandExecutable implements Executable {

  private final String name;
  private final List<String> args;
  private final List<String> resources;

  @SuppressWarnings("unchecked")  // We're casting elements of a dynamic Groovy map.
  public static CommandExecutable of(Map<String, ?> groovyMap) {
    Preconditions.checkArgument(groovyMap.containsKey("name"));
    Preconditions.checkArgument(groovyMap.containsKey("args"));
    Preconditions.checkArgument(groovyMap.containsKey("resources"));
    return new CommandExecutable(
        (String) groovyMap.get("name"),
        (List) groovyMap.get("args"),
        (List) groovyMap.get("resources"));
  }

  private CommandExecutable(String name, List<String> args, List<String> resources) {
    this.name = Preconditions.checkNotNull(name);
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
