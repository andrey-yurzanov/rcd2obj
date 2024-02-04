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

package dev.evilbrainstudio.rcd2obj.codegen.render.format.rule;

import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementWriteRender;
import dev.evilbrainstudio.rcd2obj.codegen.render.format.rule.condition.JavaElementFormatRuleAnyCondition;
import dev.evilbrainstudio.rcd2obj.codegen.render.format.rule.condition.group.JavaElementFormatRuleConditionAndGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

/**
 * Tests of JavaElementFormatLineAfterRule.
 *
 * @author Andrey_Yurzanov
 */
class JavaElementFormatLineAfterRuleTest {
  private static final int COUNT = 3;
  private static final String ONE_LINE_EXPECTED = System.lineSeparator();
  private static final String MULTI_LINE_EXPECTED = String.join(
    "",
    System.lineSeparator(),
    System.lineSeparator(),
    System.lineSeparator()
  );

  @Test
  void afterTest() {
    JavaElementFormatLineAfterRule rule = new JavaElementFormatLineAfterRule(
      new JavaElementFormatRuleConditionAndGroup(
        new JavaElementFormatRuleAnyCondition(),
        new JavaElementFormatRuleAnyCondition(),
        new JavaElementFormatRuleAnyCondition()
      )
    );

    StringWriter writer = new StringWriter();
    rule.after(
      JavaElementType.IMPORT_BEGIN,
      JavaElementType.IMPORT_KEYWORD,
      JavaElementType.IMPORT_END, new JavaElementWriteRender(writer)
    );
    Assertions.assertEquals(ONE_LINE_EXPECTED, writer.toString());

    rule = new JavaElementFormatLineAfterRule(
      COUNT,
      new JavaElementFormatRuleConditionAndGroup(
        new JavaElementFormatRuleAnyCondition(),
        new JavaElementFormatRuleAnyCondition(),
        new JavaElementFormatRuleAnyCondition()
      )
    );

    writer = new StringWriter();
    rule.after(
      JavaElementType.IMPORT_BEGIN,
      JavaElementType.IMPORT_KEYWORD,
      JavaElementType.IMPORT_END, new JavaElementWriteRender(writer)
    );

    Assertions.assertEquals(MULTI_LINE_EXPECTED, writer.toString());
  }
}