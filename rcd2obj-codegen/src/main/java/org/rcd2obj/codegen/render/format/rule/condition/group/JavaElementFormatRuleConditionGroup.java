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

package org.rcd2obj.codegen.render.format.rule.condition.group;

import org.rcd2obj.codegen.JavaElementType;

/**
 * The base abstraction of the conditions group.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public interface JavaElementFormatRuleConditionGroup {
  /**
   * Checks the types for match with the conditions.
   *
   * @param previous previous type for condition the checking
   * @param current  current type for condition the checking
   * @param next     next type for condition the checking
   * @return true when types matches, otherwise false
   */
  boolean match(JavaElementType previous, JavaElementType current, JavaElementType next);
}
