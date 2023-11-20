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

package dev.evilbrainstudio.rcd2obj.codegen.render.format.rule.condition;

import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests of JavaElementFormatKeywordRuleCondition.
 *
 * @author Andrey_Yurzanov
 */
class JavaElementFormatKeywordRuleConditionTest {
  @Test
  void matchTest() {
    JavaElementFormatKeywordRuleCondition condition = new JavaElementFormatKeywordRuleCondition();

    Assertions.assertTrue(condition.match(JavaElementType.IMPORT_KEYWORD));
    Assertions.assertFalse(condition.match(JavaElementType.IMPORT_BEGIN));
  }
}