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
import dev.rcd2obj.codegen.render.JavaElementWriteRender;
import dev.rcd2obj.codegen.render.format.rule.condition.JavaElementFormatRuleAnyCondition;
import dev.rcd2obj.codegen.render.format.rule.condition.group.JavaElementFormatRuleConditionAndGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

/**
 * Tests of JavaElementFormatLineBeforeRule.
 *
 * @author Andrey_Yurzanov
 */
class JavaElementFormatLineBeforeRuleTest {
  private static final String ONE_LINE_EXPECTED = System.lineSeparator();

  @Test
  void beforeTest() {
    JavaElementFormatLineBeforeRule rule = new JavaElementFormatLineBeforeRule(
      new JavaElementFormatRuleConditionAndGroup(
        new JavaElementFormatRuleAnyCondition(),
        new JavaElementFormatRuleAnyCondition(),
        new JavaElementFormatRuleAnyCondition()
      )
    );

    StringWriter writer = new StringWriter();
    rule.before(
      JavaElementType.IMPORT_BEGIN,
      JavaElementType.IMPORT_KEYWORD,
      JavaElementType.IMPORT_END, new JavaElementWriteRender(writer)
    );
    Assertions.assertEquals(ONE_LINE_EXPECTED, writer.toString());
  }
}