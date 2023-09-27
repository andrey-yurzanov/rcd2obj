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

package dev.evilbrainstudio.rcd2obj.codegen.render.format.rule;

import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;
import dev.evilbrainstudio.rcd2obj.codegen.render.format.rule.condition.group.JavaElementFormatRuleConditionGroup;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The formatting rule for append new line after element.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaElementFormatLineAfterRule implements JavaElementFormatRule {
  private final String separator;
  private final JavaElementFormatRuleConditionGroup condition;

  /**
   * Constructs new instance of rule.
   *
   * @param condition rule's condition
   */
  public JavaElementFormatLineAfterRule(JavaElementFormatRuleConditionGroup condition) {
    this(1, condition);
  }

  /**
   * Constructs new instance of rule.
   *
   * @param count     count of new lines
   * @param condition rule's condition
   */
  public JavaElementFormatLineAfterRule(int count, JavaElementFormatRuleConditionGroup condition) {
    this.condition = condition;
    this.separator = IntStream
      .range(0, count)
      .mapToObj(value -> System.lineSeparator())
      .collect(Collectors.joining());
  }

  @Override
  public void after(
    JavaElementType previous,
    JavaElementType current,
    JavaElementType next,
    JavaElementRender target
  ) {
    if (condition.match(previous, current, next)) {
      target.append(separator);
    }
  }
}
