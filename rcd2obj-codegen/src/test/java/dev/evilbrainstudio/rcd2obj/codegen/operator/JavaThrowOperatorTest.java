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

package dev.evilbrainstudio.rcd2obj.codegen.operator;

import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementWriteRender;
import java.io.StringWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JavaThrowOperator}.
 *
 * @author Andrey_Yurzanov
 */
class JavaThrowOperatorTest {
  private static final String RESULT = "throwX";
  private static final String TYPE = "X";

  @Test
  void renderTestTest() {
    JavaThrowOperator operator = new JavaThrowOperator(
        (target) -> target.append(JavaElementType.EMPTY_LITERAL, TYPE)
    );

    StringWriter writer = new StringWriter();
    operator.render(new JavaElementWriteRender(writer));
    Assertions.assertEquals(writer.toString(), RESULT);
  }
}