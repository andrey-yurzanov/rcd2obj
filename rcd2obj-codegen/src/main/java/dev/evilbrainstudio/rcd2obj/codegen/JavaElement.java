/*
 *    Copyright 2023 Evil Brain Studio
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package dev.evilbrainstudio.rcd2obj.codegen;

import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;

/**
 * Basic abstraction for elements of the Java language.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public interface JavaElement {
  /**
   * Renders element of the Java language.
   *
   * @param target render of current element
   */
  void render(JavaElementRender target);
}
