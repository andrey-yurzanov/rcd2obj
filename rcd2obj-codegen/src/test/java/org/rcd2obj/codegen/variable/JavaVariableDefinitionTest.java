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
import org.rcd2obj.codegen.method.JavaClassMethodDefinition;
import org.rcd2obj.codegen.method.JavaClassMethodInvokeOperator;
import org.rcd2obj.codegen.operator.JavaAssignOperator;
import org.rcd2obj.codegen.operator.JavaNullArgument;
import org.rcd2obj.codegen.parameter.JavaParameter;
import org.rcd2obj.codegen.render.JavaElementWriteRender;
import org.rcd2obj.codegen.type.JavaExplicitType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.Map;

/**
 * Tests of {@link JavaVariableDefinition}.
 *
 * @author Andrey_Yurzanov
 */
class JavaVariableDefinitionTest {
  private static final String NAME = "myVariable";
  private static final String METHOD_NAME = "setMyVariable";
  private static final String RENDER_LANG_EXPECTED = "StringmyVariable;";
  private static final String RENDER_UTIL_EXPECTED = "java.util.MapmyVariable;";

  private static final String RENDER_ASSIGN_EXPECTED = "StringmyVariable=null;";

  @Test
  void renderLangTest() {
    StringWriter writer = new StringWriter();

    JavaVariableDefinition definition = new JavaVariableDefinition(
      new JavaExplicitType(String.class),
      NAME
    );
    definition.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(RENDER_LANG_EXPECTED, writer.toString());
  }

  @Test
  void renderUtilTest() {
    StringWriter writer = new StringWriter();

    JavaVariableDefinition definition = new JavaVariableDefinition(
      new JavaExplicitType(Map.class),
      NAME
    );
    definition.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(RENDER_UTIL_EXPECTED, writer.toString());
  }

  @Test
  void renderAssignTest() {
    StringWriter writer = new StringWriter();

    JavaVariableDefinition definition = new JavaVariableDefinition(
      new JavaExplicitType(String.class),
      NAME,
      new JavaAssignOperator(new JavaNullArgument())
    );;
    definition.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(RENDER_ASSIGN_EXPECTED, writer.toString());
  }

  @Test
  void renderExceptionTest() {
    JavaElementWriteRender render = new JavaElementWriteRender(new StringWriter());

    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> new JavaVariableDefinition(null, null, null).render(render)
    );

    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> new JavaVariableDefinition(new JavaExplicitType(String.class), null, null).render(render)
    );

    Assertions.assertDoesNotThrow(
      () -> new JavaVariableDefinition(
        new JavaExplicitType(String.class), NAME, null
      ).render(render)
    );
  }

  @Test
  void assignTest() {
    JavaVariableDefinition definition = new JavaVariableDefinition(
      new JavaExplicitType(String.class),
      NAME
    );

    JavaAssignOperator assignOperator = new JavaAssignOperator(new JavaNullArgument());
    JavaVariableAssignOperator variable = definition.assign(assignOperator);
    Assertions.assertEquals(definition, variable.getVariableDefinition());
    Assertions.assertEquals(assignOperator, variable.getVariableAssign());
  }

  @Test
  void invokeTest() {
    JavaClassMethodInvokeOperator method = new JavaClassMethodDefinition(
      METHOD_NAME,
      new JavaParameter(1, NAME, new JavaExplicitType(String.class))
    ).invoke(new JavaNullArgument());

    JavaVariableDefinition definition = new JavaVariableDefinition(
      new JavaExplicitType(String.class),
      NAME
    );
    JavaVariableInvokeMethodOperator invoke = definition.invoke(method);

    Assertions.assertEquals(method, invoke.getMethodInvoke());
    Assertions.assertEquals(definition, invoke.getVariableDefinition());
  }
}