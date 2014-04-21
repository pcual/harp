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

package harp.script.root;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import harp.dispatch.Dispatcher;
import java.util.List;
import java.util.Map;

/**
 * A RootContext is a container for all the {@link Environment}s parsed from a root.harp script.
 */
public final class RootContext {

  private final Map<String, Environment> nameToEnvironment;

  RootContext(List<Environment> environments) {
    ImmutableMap.Builder<String, Environment> builder = ImmutableMap.builder();
    // TODO: Consider throwing a Harp-specific Exception for duplicate names. For now, guard against
    // this via ImmutableMap.Builder's checks for duplicate keys.
    for (Environment env : environments) {
      builder.put(env.getName(), env);
    }
    this.nameToEnvironment = builder.build();
  }

  public Environment getEnvironment(String name) {
    Preconditions.checkArgument(nameToEnvironment.containsKey(name), "No such environment " + name);
    return nameToEnvironment.get(name);
  }
}
