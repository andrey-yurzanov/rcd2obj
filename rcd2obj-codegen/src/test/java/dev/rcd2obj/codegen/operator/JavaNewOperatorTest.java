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

package dev.rcd2obj.codegen.operator;

import dev.rcd2obj.codegen.JavaElementRenderingException;
import dev.rcd2obj.codegen.constructor.JavaClassConstructorDefinition;
import dev.rcd2obj.codegen.parameter.JavaParameter;
import dev.rcd2obj.codegen.render.JavaElementWriteRender;

import java.io.StringWriter;

import dev.rcd2obj.codegen.type.JavaExplicitType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests of new operator.
 *
 * @author Andrey_Yurzanov
 */
class JavaNewOperatorTest {
  private static final String NAME = "testParameter";
  private static final String NEW_EXPECTED = "newUnsupportedOperationException()";
  private static final String NEW_WITH_PARAMS_EXPECTED =
    "newUnsupportedOperationException(null)";

  @Test
  void renderTest() {
    JavaNewOperator operator = new JavaNewOperator(
      new JavaClassConstructorDefinition()
        .setConstructorType(new JavaExplicitType(UnsupportedOperationException.class))
        .getConstructor()
    );

    StringWriter writer = new StringWriter();
    operator.render(new JavaElementWriteRender(writer));
    Assertions.assertEquals(writer.toString(), NEW_EXPECTED);
  }

  @Test
  void renderTestWithParams() {
    JavaNewOperator operator = new JavaNewOperator(
      new JavaClassConstructorDefinition()
        .setConstructorType(new JavaExplicitType(UnsupportedOperationException.class))
        .setConstructorParameters(
          new JavaParameter(1, NAME, new JavaExplicitType(String.class))
        )
        .getConstructor(new JavaNullArgument())
    );

    StringWriter writer = new StringWriter();
    operator.render(new JavaElementWriteRender(writer));
    Assertions.assertEquals(writer.toString(), NEW_WITH_PARAMS_EXPECTED);
  }

  @Test
  void renderExceptionTest() {
    JavaElementWriteRender render = new JavaElementWriteRender(new StringWriter());
    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> new JavaNewOperator(null).render(render)
    );
  }
}