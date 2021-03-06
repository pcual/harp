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

package harp.bubble;

import java.io.IOException;

/**
 * A provider of new {@link ExecutionBubble}s.
 */
// TODO parametrize as ExecutionBubbleCreator<? extends ExecutionBubble>?
public interface ExecutionBubbleCreator {

  /**
   * Creates a new {@link ExecutionBubble}.
   */
  // TODO use something better than IOException
  ExecutionBubble create() throws IOException;
}
