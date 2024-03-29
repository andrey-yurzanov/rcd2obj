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

package org.rcd2obj.codegen.render.format.rule.condition;

import org.rcd2obj.codegen.JavaElementType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests of JavaElementFormatRuleAnyCondition.
 *
 * @author Andrey_Yurzanov
 */
class JavaElementFormatRuleAnyConditionTest {
  @Test
  void matchTest() {
    JavaElementFormatRuleAnyCondition condition = new JavaElementFormatRuleAnyCondition();
    for (JavaElementType type : JavaElementType.values()) {
      Assertions.assertTrue(condition.match(type));
    }
  }
}