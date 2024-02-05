/*
 *    Copyright 2024 Andrey Yurzanov
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

package org.rcd2obj.codegen.render.format.rule;

import org.rcd2obj.codegen.JavaElementType;
import org.rcd2obj.codegen.render.JavaElementRender;

/**
 * Base abstraction of formatting rule.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public interface JavaElementFormatRule {
  /**
   * Calls before appending element to target renderer.
   *
   * @param previous previous element, appended to target already
   * @param current  current element
   * @param next     next element, will be current one on the next call
   * @param target   target for the formatted elements
   */
  default void before(
    JavaElementType previous,
    JavaElementType current,
    JavaElementType next,
    JavaElementRender target
  ) {
  }

  /**
   * Calls after appending element to target renderer.
   *
   * @param previous previous element, appended to target already
   * @param current  current element, appended to target already
   * @param next     next element, will be current one on the next call
   * @param target   target for the formatted elements
   */
  default void after(
    JavaElementType previous,
    JavaElementType current,
    JavaElementType next,
    JavaElementRender target
  ) {
  }
}
