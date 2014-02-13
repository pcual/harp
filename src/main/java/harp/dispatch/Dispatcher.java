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

/**
 * A Dispatcher sends a {@link HarpJob} somewhere for execution.
 *
 * <p>A Dispatcher may simply be a wrapper for another Dispatcher, so that, for example, you could
 * have a Dispatcher that sends a HarpJob to a master node, which then dispatches that job to a
 * slave node.
 */
public interface Dispatcher {

  // TODO(yparghi): Return something?
  void dispatch(HarpJob job);

  // TODO: extend some common Named interface, or something for all parsed Harp objects?
  String getName();
}
