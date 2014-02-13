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

package harp.node;

/**
 * A container for the various classes involved in running and interacting with a {@link Node}.
 */
public interface NodeSpec {

  /**
   * Returns the {@code Node} instance encapsulated by this spec.
   */
  Node getNode();

  /**
   * Returns the {@code NodeBridge} instance for communicating with the {@code Node} encapsulated
   * by this spec.
   */
  NodeBridge getBridge();

  // TODO: extend some common Named interface, or something for all parsed Harp objects?
  String getName();
}
