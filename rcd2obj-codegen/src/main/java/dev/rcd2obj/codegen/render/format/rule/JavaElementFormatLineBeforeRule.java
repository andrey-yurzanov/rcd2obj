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

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The formatting rule for append new line before element.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaElementFormatLineBeforeRule implements JavaElementFormatRule {
  private final String separator;
  private final JavaElementFormatRuleConditionGroup condition;

  private static final short DEFAULT_COUNT = 1;

  /**
   * Constructs new instance of rule.
   *
   * @param condition rule's condition
   */
  public JavaElementFormatLineBeforeRule(JavaElementFormatRuleConditionGroup condition) {
    this(DEFAULT_COUNT, condition);
  }

  /**
   * Constructs new instance of rule.
   *
   * @param count     count of new lines
   * @param condition rule's condition
   */
  public JavaElementFormatLineBeforeRule(short count, JavaElementFormatRuleConditionGroup condition) {
    this.condition = condition;
    this.separator = IntStream
      .range(0, count)
      .mapToObj(value -> System.lineSeparator())
      .collect(Collectors.joining());
  }

  @Override
  public void before(
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
