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

package dev.evilbrainstudio.rcd2obj.codegen.parameter;

import dev.evilbrainstudio.rcd2obj.codegen.JavaElementRenderingException;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementWriteRender;
import java.io.StringWriter;

import dev.evilbrainstudio.rcd2obj.codegen.type.JavaExplicitType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests of the parameter.
 *
 * @author Andrey_Yurzanov
 */
class JavaParameterTest {
  private static final int ORDER = 1;
  private static final String NAME = "testParameter";
  private static final String PARAMETER_EXPECTED = "StringtestParameter";

  @Test
  void renderTest() {
    JavaParameter parameter = new JavaParameter(ORDER, NAME, new JavaExplicitType(String.class));

    StringWriter writer = new StringWriter();
    parameter.render(new JavaElementWriteRender(writer));
    Assertions.assertEquals(PARAMETER_EXPECTED, writer.toString());
  }

  @Test
  void renderExceptionTest() {
    JavaElementWriteRender render = new JavaElementWriteRender(new StringWriter());

    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> new JavaParameter(ORDER, null, null).render(render)
    );
    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> new JavaParameter(ORDER, "", null).render(render)
    );
    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> new JavaParameter(ORDER, NAME, null).render(render)
    );
    Assertions.assertDoesNotThrow(
      () -> new JavaParameter(ORDER, NAME, new JavaExplicitType(String.class)).render(render)
    );
  }
}