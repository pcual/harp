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

import com.google.common.collect.ImmutableSet;
import groovy.lang.Binding;
import java.util.Set;

/**
 * TODO -- Document that this class isn't just for holding variables. It also stores the base
 * context for a harp script: the context builder, the resource manager, etc.
 */
final class HarpBinding extends Binding {

  /**
   * Variables or properties you can't change in a Harp script.
   */
  private static final Set<String> FORBIDDEN_VARIABLES = ImmutableSet.of(
      "resource"
  );

  private final ContextBuilder contextToBuild;
  private final ResourceRegistrar resourceRegistrar;

  HarpBinding() {
    contextToBuild = Context.builder();
    resourceRegistrar = new ResourceRegistrar(contextToBuild);
    super.setVariable("resource", resourceRegistrar);
  }

  ContextBuilder getContextBuilder() {
    return contextToBuild;
  }

  private void checkUnreserved(String variableName) {
    if (FORBIDDEN_VARIABLES.contains(variableName)) {
      throw new UnsupportedOperationException(
          "The property '" + variableName + "' is reserved in a Harp script.");
    }
  }

  @Override
  public void setProperty(String property, Object newValue) {
    checkUnreserved(property);
    super.setProperty(property, newValue);
  }

  @Override
  public void setVariable(String name, Object newValue) {
    checkUnreserved(name);
    super.setVariable(name, newValue);
  }
}
