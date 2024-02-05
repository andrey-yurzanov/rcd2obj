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
 * Tests of JavaElementFormatRuleIsCondition.
 *
 * @author Andrey_Yurzanov
 */
class JavaElementFormatRuleIsConditionTest {
  @Test
  void matchTest() {
    JavaElementFormatRuleIsCondition condition = new JavaElementFormatRuleIsCondition(
      JavaElementType.METHOD_DEFINITION_RETURN_TYPE,
      JavaElementType.METHOD_DEFINITION_BEGIN
    );

    Assertions.assertTrue(condition.match(JavaElementType.METHOD_DEFINITION_RETURN_TYPE));
    Assertions.assertTrue(condition.match(JavaElementType.METHOD_DEFINITION_BEGIN));

    Assertions.assertFalse(condition.match(JavaElementType.IMPORT_BEGIN));
    Assertions.assertFalse(condition.match(JavaElementType.CLASS_BODY_BEGIN));
  }
}