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
import org.rcd2obj.codegen.operator.JavaAssignOperator;
import org.rcd2obj.codegen.operator.JavaNullArgument;
import org.rcd2obj.codegen.render.JavaElementWriteRender;
import org.rcd2obj.codegen.type.JavaExplicitType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

/**
 * Tests of {@link JavaVariableAssignOperator}.
 *
 * @author Andrey_Yurzanov
 */
class JavaVariableAssignOperatorTest {
  private static final String NAME = "myVariable";
  private static final String RENDER_ASSIGN_EXPECTED = "myVariable=null;";

  @Test
  void renderTest() {
    StringWriter writer = new StringWriter();

    JavaVariableDefinition definition = new JavaVariableDefinition(
      new JavaExplicitType(String.class),
      NAME
    );

    JavaVariableAssignOperator variable = definition.assign(new JavaAssignOperator(new JavaNullArgument()));
    variable.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(RENDER_ASSIGN_EXPECTED, writer.toString());
  }

  @Test
  void renderExceptionTest() {
    JavaElementWriteRender render = new JavaElementWriteRender(new StringWriter());

    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> new JavaVariableAssignOperator(null, null).render(render)
    );

    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> new JavaVariableAssignOperator(
        new JavaVariableDefinition(
          new JavaExplicitType(String.class),
          null
        ),
        null
      ).render(render)
    );

    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> new JavaVariableAssignOperator(
        new JavaVariableDefinition(
          new JavaExplicitType(String.class),
          NAME
        ),
        null
      ).render(render)
    );

    Assertions.assertDoesNotThrow(
      () -> new JavaVariableAssignOperator(
        new JavaVariableDefinition(
          new JavaExplicitType(String.class),
          NAME
        ),
        new JavaAssignOperator(new JavaNullArgument())
      ).render(render)
    );
  }
}