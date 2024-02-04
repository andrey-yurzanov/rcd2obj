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

package dev.rcd2obj.codegen.render.format.rule;

import dev.rcd2obj.codegen.JavaElementType;
import dev.rcd2obj.codegen.render.JavaElementRender;
import dev.rcd2obj.codegen.render.format.rule.condition.group.JavaElementFormatRuleConditionGroup;

/**
 * The formatting rule for append space after element.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaElementFormatSpaceAfterRule implements JavaElementFormatRule {
  private final JavaElementFormatRuleConditionGroup condition;

  private static final String SPACE = " ";

  /**
   * Constructs new instance of rule.
   *
   * @param condition rule's condition
   */
  public JavaElementFormatSpaceAfterRule(JavaElementFormatRuleConditionGroup condition) {
    this.condition = condition;
  }

  @Override
  public void after(
    JavaElementType previous,
    JavaElementType current,
    JavaElementType next,
    JavaElementRender target
  ) {
    if (condition.match(previous, current, next)) {
      target.append(SPACE);
    }
  }
}
