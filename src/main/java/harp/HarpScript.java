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

import harp.executable.ExecutableBuilder;
import groovy.lang.Closure;
import groovy.lang.Script;
import harp.executable.Executable;
import harp.resource.Resource;

/**
 * TODO
 */
public abstract class HarpScript extends Script {

  /**
   * TODO
   */
  public void executable(Closure closure) {
    closure.setResolveStrategy(Closure.DELEGATE_ONLY);
    ExecutableBuilder builder = new ExecutableBuilder();
    closure.setDelegate(builder);
    closure.call();
    Executable executable = builder.build();
    ((ContextBuilder) getProperty("contextToBuild")).addExecutable(executable);
  }

  /**
   * TODO
   */
  public void executable(Executable executable) {
    ((ContextBuilder) getProperty("contextToBuild")).addExecutable(executable);
  }

  /**
   * TODO
   */
  // TODO Settle on a flexible, simple syntax for adding resources, in the spirit of
  // executable { ... }. Something like 'resource file { path "my/file" }' would be nice.
  public void resource(Resource resource) {
    ((ContextBuilder) getProperty("contextToBuild")).addResource(resource);
  }
}
