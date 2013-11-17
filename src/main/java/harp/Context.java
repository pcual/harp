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

import com.google.common.collect.ImmutableList;
import harp.executable.Executable;
import harp.resource.Resource;
import java.util.List;

/**
 * TODO
 */
public final class Context {

  private final List<Executable> executables;
  private final List<Resource> resources;

  Context(List<Executable> executables, List<Resource> resources) {
    this.executables = ImmutableList.copyOf(executables);
    this.resources = ImmutableList.copyOf(resources);
  }

  /**
   * TODO
   */
  // TODO do we even need an ordered collection? Would Set work?
  List<Executable> getExecutables() {
    return executables;
  }

  /**
   * TODO
   */
  List<Resource> getResources() {
    return resources;
  }

  /**
   * TODO
   */
  public static final ContextBuilder builder() {
    return new ContextBuilder();
  }
}
