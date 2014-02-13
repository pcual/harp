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

package harp.script;

import com.google.common.collect.ImmutableList;
import harp.dispatch.Dispatcher;
import harp.executable.Executable;
import harp.node.NodeSpec;
import harp.resource.Resource;
import java.util.List;

/**
 * A Context is a container for all the execution elements parsed from a set of Harp scripts.
 *
 * <p>All the execution elements (such as Executables and Resources) declared in your Harp scripts
 * are evaluated to Java objects stored in an immutable Context.
 */
public final class Context {

  private final List<Executable> executables;
  private final List<Resource> resources;
  private final List<NodeSpec> nodeSpecs;
  private final List<Dispatcher> dispatchers;

  Context(
      List<Executable> executables,
      List<Resource> resources,
      List<NodeSpec> nodeSpecs,
      List<Dispatcher> dispatchers) {
    this.executables = ImmutableList.copyOf(executables);
    this.resources = ImmutableList.copyOf(resources);
    this.nodeSpecs = ImmutableList.copyOf(nodeSpecs);
    this.dispatchers = ImmutableList.copyOf(dispatchers);
  }

  /**
   * TODO
   */
  // TODO do we even need an ordered collection? Would Set work?
  public List<Executable> getExecutables() {
    return executables;
  }

  /**
   * TODO
   */
  public List<Resource> getResources() {
    return resources;
  }

  /**
   * TODO
   */
  public List<NodeSpec> getNodeSpecs() {
    return nodeSpecs;
  }

    /**
   * TODO
   */
  public List<Dispatcher> getDispatchers() {
    return dispatchers;
  }

  /**
   * TODO
   */
  public static final ContextBuilder builder() {
    return new ContextBuilder();
  }
}
