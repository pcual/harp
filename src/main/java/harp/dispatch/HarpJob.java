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

package harp.dispatch;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * A HarpJob encapsulates an execution task, as specified from a command line.
 *
 * <p>The name(s) of the Executable(s) to run, a set of Harp scripts, and where and how to execute,
 * are all encapsulated in a HarpJob.
 *
 * <p>The purpose of a HarpJob is to bundle up everything a Harp node needs to do its assigned
 * work. It does <em>not</em> parse anything in the scripts, it just holds on to their contents.
 * This is so that a HarpJob can be sent over the wire to another node as unparsed Groovy plus some
 * other simple execution specifiers in String form.
 */
// TODO use something other than a concatenation of all scripts? Like a Map from path/name to
// contents?
public final class HarpJob {

  private final String harpScript;
  private final ImmutableList<String> executablesToRun;

  // TODO take a collection of scripts
  // TODO static factory method instead of constructor?
  public HarpJob(String harpScript, List<String> executablesToRun) {
    this.harpScript = Preconditions.checkNotNull(harpScript);
    Preconditions.checkArgument(executablesToRun.size() > 0);
    this.executablesToRun = ImmutableList.copyOf(executablesToRun);
  }

  public String getScript() {
    return harpScript;
  }

  public List<String> getExecutablesToRun() {
    return executablesToRun;
  }

  // TODO some kind of serialization method that writes everything to a JSON(?) object?
}
