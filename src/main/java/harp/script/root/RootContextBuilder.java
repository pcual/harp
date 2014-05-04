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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A builder for assembling a RootContext in the course of evaluating a root.harp script.
 */
public final class RootContextBuilder {

  private final Set<String> environmentNames;
  private final List<Environment> environments;

  public RootContextBuilder() {
    this.environments = new ArrayList<>();
    this.environmentNames = new HashSet<>();
  }

  void addEnvironment(Environment environment) {
    Preconditions.checkNotNull(environment.getName(), "An Environment must have a non-null name.");
    Preconditions.checkState(
        !environmentNames.contains(environment.getName()),
        "An Environment named %s has already been declared.", environment.getName());
    environmentNames.add(environment.getName());
    environments.add(environment);
  }

  RootContext build() {
    return new RootContext(environments);
  }
}
