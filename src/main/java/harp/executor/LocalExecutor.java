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

package harp.executor;

import harp.script.Context;
import harp.bubble.ExecutionBubble;
import harp.bubble.LocalExecutionBubbleCreator;
import harp.executable.Executable;
import harp.resource.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 */
public class LocalExecutor implements Executor {

  @Override
  public void execute(Executable executable, Context context) {
    ExecutionBubble bubble;
    // TODO figure out Exception-passing
    try {
      bubble = new LocalExecutionBubbleCreator().create();
    } catch (IOException ioEx) {
      throw new RuntimeException(ioEx);
    }

    List<Resource> resourcesToAdd = new ArrayList<>();
    for (Resource resource : context.getResources()) {
      // TODO check that the resource doesn't have a null name, since it can be an arbitrary
      // implementation of the Resource interface?
      if (executable.getResources().contains(resource.getName())) {
        resourcesToAdd.add(resource);
      }
    }
    for (Resource resourceToAdd : resourcesToAdd) {
      bubble.addResource(resourceToAdd);
    }

    bubble.execute(executable);

    // TODO figure out Exception-passing
    try {
      bubble.cleanUp();
    } catch (IOException ioEx) {
      throw new RuntimeException(ioEx);
    }
  }

}
