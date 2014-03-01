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

package harp.script;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import harp.util.Graph;
import java.util.List;
import java.util.Map;

/**
 * Holds the result of linking Harp scripts: script contents, and their dependency graph.
 */
public final class ScriptGraph {

  @VisibleForTesting final static String LINKED_FILE_HEADER = "/* Linked script: %s */\n";

  private final Map<String, String> scriptContents;
  private final Graph<String> scriptDependencies;

  // TODO static factory method?
  public ScriptGraph(Map<String, String> scriptContents, Graph<String> scriptDependencies) {
    this.scriptContents = ImmutableMap.copyOf(Preconditions.checkNotNull(scriptContents));
    this.scriptDependencies = Preconditions.checkNotNull(scriptDependencies);
  }

  public String getConcatenatedScript() {
    List<String> scriptNamesInOrder = scriptDependencies.topologicallySorted();
    StringBuilder out = new StringBuilder();
    for (String scriptName : scriptNamesInOrder) {
      if (!scriptContents.containsKey(scriptName)) {
        throw new IllegalStateException("Unknown script name: " + scriptName);
      }
      out.append(String.format(LINKED_FILE_HEADER, scriptName));
      out.append(scriptContents.get(scriptName));
      out.append("\n");
    }
    return out.toString();
  }
}
