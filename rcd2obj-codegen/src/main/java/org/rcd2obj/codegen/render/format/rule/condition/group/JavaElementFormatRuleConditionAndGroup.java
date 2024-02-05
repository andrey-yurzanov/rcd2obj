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
import org.rcd2obj.codegen.render.format.rule.condition.JavaElementFormatRuleCondition;

/**
 * The group of conditions, implements the logical operator 'and'. Returns true when all conditions return true,
 * otherwise returns false.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaElementFormatRuleConditionAndGroup implements JavaElementFormatRuleConditionGroup {
  private final JavaElementFormatRuleCondition previousCondition;
  private final JavaElementFormatRuleCondition currentCondition;
  private final JavaElementFormatRuleCondition nextCondition;

  /**
   * Constructs new instance of the conditions group.
   *
   * @param previousCondition condition for the previous type
   * @param currentCondition  condition for the current type
   * @param nextCondition     condition for the next type
   */
  public JavaElementFormatRuleConditionAndGroup(
    JavaElementFormatRuleCondition previousCondition,
    JavaElementFormatRuleCondition currentCondition,
    JavaElementFormatRuleCondition nextCondition
  ) {
    this.previousCondition = previousCondition;
    this.currentCondition = currentCondition;
    this.nextCondition = nextCondition;
  }

  @Override
  public boolean match(JavaElementType previous, JavaElementType current, JavaElementType next) {
    return previousCondition.match(previous)
      && currentCondition.match(current)
      && nextCondition.match(next);
  }
}
