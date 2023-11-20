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
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementWriteRender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

/**
 * Tests of JavaElementIndentBlockFormatRule.
 *
 * @author Andrey_Yurzanov
 */
class JavaElementIndentBlockFormatRuleTest {
  private static final String INDENT = " ";
  private static final String TWO_INDENT_EXPECTED = "  ";
  private static final String FOUR_INDENT_EXPECTED = "    ";
  private static final int INDENTS_COUNT = 4;

  @Test
  void renderTest() {
    JavaElementIndentBlockFormatRule rule = new JavaElementIndentBlockFormatRule(
      JavaElementType.PACKAGE_BEGIN,
      JavaElementType.PACKAGE_END
    );

    StringWriter writer = new StringWriter();
    rule.render(new JavaElementWriteRender(writer));
    Assertions.assertEquals(TWO_INDENT_EXPECTED, writer.toString());

    rule = new JavaElementIndentBlockFormatRule(
      JavaElementType.PACKAGE_BEGIN,
      JavaElementType.PACKAGE_END,
      INDENT,
      INDENTS_COUNT
    );

    writer = new StringWriter();
    rule.render(new JavaElementWriteRender(writer));
    Assertions.assertEquals(FOUR_INDENT_EXPECTED, writer.toString());
  }
}