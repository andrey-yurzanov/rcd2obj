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

package dev.rcd2obj.codegen.render.format.rule.condition;

import dev.rcd2obj.codegen.JavaElementType;

import java.util.Arrays;
import java.util.Collection;

/**
 * The condition returns true when type matches with specified types, otherwise false.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaElementFormatRuleIsCondition implements JavaElementFormatRuleCondition {
  private final Collection<JavaElementType> types;

  /**
   * Constructs new instance of condition.
   *
   * @param types types for matching
   */
  public JavaElementFormatRuleIsCondition(JavaElementType... types) {
    this.types = Arrays.asList(types);
  }

  @Override
  public boolean match(JavaElementType type) {
    return types.contains(type);
  }
}
