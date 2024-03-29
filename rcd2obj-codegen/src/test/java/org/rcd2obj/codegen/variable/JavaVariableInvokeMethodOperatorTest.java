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

package org.rcd2obj.codegen.variable;

import org.rcd2obj.codegen.JavaElementRenderingException;
import org.rcd2obj.codegen.method.JavaMethodDefinition;
import org.rcd2obj.codegen.method.JavaMethodInvokeOperator;
import org.rcd2obj.codegen.operator.JavaNullArgument;
import org.rcd2obj.codegen.parameter.JavaParameter;
import org.rcd2obj.codegen.render.JavaElementWriteRender;
import org.rcd2obj.codegen.type.JavaExplicitType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

/**
 * Tests of {@link JavaVariableInvokeMethodOperator}.
 *
 * @author Andrey_Yurzanov
 */
class JavaVariableInvokeMethodOperatorTest {
  private static final String VARIABLE_NAME = "myVariable";
  private static final String METHOD_NAME = "setMyVariable";
  private static final String EXPECTED = "myVariable.setMyVariable(null)";

  @Test
  void renderTest() {
    JavaMethodInvokeOperator method = new JavaMethodDefinition(
      METHOD_NAME,
      new JavaParameter(1, VARIABLE_NAME, new JavaExplicitType(String.class))
    ).invoke(new JavaNullArgument());

    StringWriter writer = new StringWriter();
    JavaVariableInvokeMethodOperator operator = new JavaVariableInvokeMethodOperator(
      new JavaVariableDefinition(new JavaExplicitType(String.class), VARIABLE_NAME),
      method
    );
    operator.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EXPECTED, writer.toString());
  }

  @Test
  void renderExceptionTest() {
    JavaElementWriteRender render = new JavaElementWriteRender(new StringWriter());

    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> new JavaVariableInvokeMethodOperator(null, null).render(render)
    );

    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> new JavaVariableInvokeMethodOperator(
        new JavaVariableDefinition(new JavaExplicitType(String.class), VARIABLE_NAME),
        null
      ).render(render)
    );

    Assertions.assertDoesNotThrow(
      () -> new JavaVariableInvokeMethodOperator(
        new JavaVariableDefinition(new JavaExplicitType(String.class), VARIABLE_NAME),
        new JavaMethodDefinition(
          METHOD_NAME,
          new JavaParameter(1, VARIABLE_NAME, new JavaExplicitType(String.class))
        ).invoke(new JavaNullArgument())
      ).render(render)
    );
  }
}