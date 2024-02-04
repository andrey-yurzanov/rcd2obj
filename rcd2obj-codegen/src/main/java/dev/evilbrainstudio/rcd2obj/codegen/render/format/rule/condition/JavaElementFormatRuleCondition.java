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

package dev.evilbrainstudio.rcd2obj.codegen.render.format.rule.condition;

import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;

/**
 * The base abstraction of the formatting rule condition.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public interface JavaElementFormatRuleCondition {
  /**
   * Checks the type for match with the condition.
   *
   * @param type type for condition the checking
   * @return true when type matches, otherwise false
   */
  boolean match(JavaElementType type);
}
