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

import harp.dispatch.Dispatcher;
import harp.script.JobLinker;

/**
 * An Environment specifies an execution context for Harp, that is, a way to run Harp jobs.
 *
 * <p>For example, in your {@code root.harp} file you could define a "local" Environment that
 * specifies the use of a local Dispatcher and a local JobLinker. Or you could define a "remote"
 * Environment that also uses a local JobLinker, but a dispatcher that sends a HarpJob off by
 * HTTP to some central execution server.
 */
public interface Environment {

  /**
   * Returns the name of this Environment.
   */
  String getName();

  /**
   * Returns the {@link JobLinker} specified by this Environment.
   */
  JobLinker getLinker();

  /**
   * Returns the {@link Dispatcher} specified by this Environment.
   */
  Dispatcher getDispatcher();
}
