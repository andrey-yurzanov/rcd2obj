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
import org.rcd2obj.codegen.render.format.rule.condition.JavaElementFormatKeywordRuleCondition;
import org.rcd2obj.codegen.render.format.rule.condition.JavaElementFormatRuleAnyCondition;
import org.rcd2obj.codegen.render.format.rule.condition.JavaElementFormatRuleIsCondition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests of JavaElementFormatRuleConditionAndGroup.
 *
 * @author Andrey_Yurzanov
 */
class JavaElementFormatRuleConditionAndGroupTest {
  @Test
  void matchTest() {
    JavaElementFormatRuleConditionAndGroup group = new JavaElementFormatRuleConditionAndGroup(
      new JavaElementFormatRuleAnyCondition(),
      new JavaElementFormatKeywordRuleCondition(),
      new JavaElementFormatRuleIsCondition(JavaElementType.IMPORT_BEGIN)
    );

    Assertions.assertTrue(
      group.match(JavaElementType.PACKAGE_BEGIN, JavaElementType.PACKAGE_KEYWORD, JavaElementType.IMPORT_BEGIN)
    );
    Assertions.assertFalse(
      group.match(JavaElementType.PACKAGE_BEGIN, JavaElementType.IMPORT_BEGIN, JavaElementType.PACKAGE_BEGIN)
    );
  }
}