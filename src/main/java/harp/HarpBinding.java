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

import groovy.lang.Binding;

/**
 * TODO -- Document that this class isn't just for holding variables. It also stores the base
 * context for a harp script: the context builder, the resource manager, etc.
 */
final class HarpBinding extends Binding {

  private final ContextBuilder contextToBuild;

  HarpBinding() {
    contextToBuild = Context.builder();
  }

  ContextBuilder getContextBuilder() {
    return contextToBuild;
  }
}
