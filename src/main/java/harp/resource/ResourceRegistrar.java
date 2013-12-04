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

package harp.resource;

import com.google.common.base.Preconditions;
import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 */
// TODO Test resource declaration with the registrar in HarpScriptTest.
public final class ResourceRegistrar extends GroovyObjectSupport {

  private final Map<String, ResourceFactory> resourceFactories = new HashMap<>();

  @Override
  public Object invokeMethod(String name, Object args) {
    if (!(args instanceof Object[])) {
      throw new IllegalArgumentException(
          "The ResourceRegistrar expected an array of args but didn't get one.");
    }
    Object[] arrayArgs = (Object[]) args;
    if (arrayArgs.length != 1 || !(arrayArgs[0] instanceof Closure)) {
      throw new IllegalArgumentException(
          "Resources must be declared with one argument: a closure.");
    }

    // TODO delegate to the right ResourceFactory and add it to this script's ContextBuilder.
    return null;
  }

  /**
   * TODO
   */
  public final void registerResourceFactory(String name, ResourceFactory factory) {
    Preconditions.checkArgument(
        !resourceFactories.containsKey(name),
        "You've already registered a resource factory named '" + name + "'!");
    resourceFactories.put(name, factory);
  }
}
