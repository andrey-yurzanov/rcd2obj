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

package dev.evilbrainstudio.rcd2obj.codegen.variable;

import dev.evilbrainstudio.rcd2obj.codegen.operator.JavaAssignOperator;
import dev.evilbrainstudio.rcd2obj.codegen.operator.JavaNullArgument;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementWriteRender;
import dev.evilbrainstudio.rcd2obj.codegen.type.JavaExplicitType;
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
      NAME
    );
    definition.setVariableAssign(new JavaAssignOperator(new JavaNullArgument()));
    definition.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(RENDER_ASSIGN_EXPECTED, writer.toString());
  }

  @Test
  void getVariableTest() {
    JavaVariableDefinition definition = new JavaVariableDefinition(
      new JavaExplicitType(String.class),
      NAME
    );

    JavaAssignOperator assignOperator = new JavaAssignOperator(new JavaNullArgument());
    JavaVariableAssignOperator variable = definition.getVariable(assignOperator);
    Assertions.assertEquals(definition, variable.getVariableDefinition());
    Assertions.assertEquals(assignOperator, variable.getVariableAssign());
  }
}