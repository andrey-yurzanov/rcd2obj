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

package dev.evilbrainstudio.rcd2obj.codegen.operator;

import dev.evilbrainstudio.rcd2obj.codegen.operator.JavaNullArgument;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementWriteRender;
import dev.evilbrainstudio.rcd2obj.codegen.operator.JavaAssignOperator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

/**
 * Tests of {@link JavaAssignOperator}.
 *
 * @author Andrey_Yurzanov
 */
class JavaAssignOperatorTest {
  private static final String EXPECTED = "=null;";

  @Test
  void renderTest() {
    Assertions.assertThrows(NullPointerException.class, () -> new JavaAssignOperator(null));

    StringWriter writer = new StringWriter();
    JavaAssignOperator assign = new JavaAssignOperator(
      new JavaNullArgument()
    );
    assign.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EXPECTED, writer.toString());
  }
}