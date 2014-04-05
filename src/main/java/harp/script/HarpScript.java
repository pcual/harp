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

import groovy.lang.Script;
import harp.dispatch.Dispatcher;
import harp.executable.Executable;
import harp.node.NodeSpec;
import harp.resource.Resource;

/**
 * TODO
 */
public abstract class HarpScript extends Script {

  /**
   * TODO
   */
  public final void executable(Executable executable) {
    getMyContextBuilder().addExecutable(executable);
  }

  /**
   * TODO
   */
  public final void resource(Resource resource) {
    getMyContextBuilder().addResource(resource);
  }

  /**
   * TODO
   */
  public final void node(NodeSpec nodeSpec) {
    getMyContextBuilder().addNodeSpec(nodeSpec);
  }

  /**
   * TODO
   */
  public final void dispatcher(Dispatcher dispatcher) {
    getMyContextBuilder().addDispatcher(dispatcher);
  }

  /**
   * TODO
   */
  // TODO use this for something? Or bind it in HarpBinding instead of here, so that it's not
  // a part of the public HarpScript API?
  public final void harpInclude(String includePath) {
    // No op.
  }

  private ContextBuilder getMyContextBuilder() {
    return ((HarpBinding) getBinding()).getContextBuilder();
  }
}
