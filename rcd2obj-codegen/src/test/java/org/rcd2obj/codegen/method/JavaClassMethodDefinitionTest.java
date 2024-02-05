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

package org.rcd2obj.codegen.method;

import org.rcd2obj.codegen.JavaElementRenderingException;
import org.rcd2obj.codegen.modifier.JavaPublicModifier;
import org.rcd2obj.codegen.operator.JavaNullArgument;
import org.rcd2obj.codegen.parameter.JavaParameter;
import org.rcd2obj.codegen.render.JavaElementWriteRender;
import org.rcd2obj.codegen.type.JavaExplicitType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

/**
 * Tests of the method code generator.
 *
 * @author Andrey_Yurzanov
 */
class JavaClassMethodDefinitionTest {
  private static final String METHOD_NAME = "renderTest";
  private static final String WRONG_METHOD_NAME = "renderTest1";
  private static final String PARAM_NAME_1 = "name";
  private static final String PARAM_NAME_2 = "age";
  private static final String EXPECTED_VALUE =
    "voidrenderTest(){thrownewUnsupportedOperationException();}";
  private static final String EXPECTED_VALUE_WITH_RETURN =
    "StringrenderTest(){thrownewUnsupportedOperationException();}";
  private static final String EXPECTED_VALUE_WITH_ACCESS_MODIFIER =
    "publicStringrenderTest(){thrownewUnsupportedOperationException();}";
  private static final String EXPECTED_VALUE_BY_METHOD =
    "publicintcompareTo(Objectarg0){thrownewUnsupportedOperationException();}";
  private static final String EXPECTED_GENERIC =
    "Map<Integer,List<Double>>apply(Collection<String> arg0){thrownewUnsupportedOperationException();}";

  @Test
  void renderTest() {
    StringWriter writer = new StringWriter();

    JavaClassMethodDefinition definition = new JavaClassMethodDefinition(METHOD_NAME);
    definition.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EXPECTED_VALUE, writer.toString());
  }

  @Test
  void renderWithReturnTest() {
    StringWriter writer = new StringWriter();

    JavaClassMethodDefinition definition = new JavaClassMethodDefinition(
      METHOD_NAME,
      null,
      new JavaExplicitType(String.class),
      null,
      null
    );
    definition.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EXPECTED_VALUE_WITH_RETURN, writer.toString());
  }

  @Test
  void renderWithAccessModifierTest() {
    StringWriter writer = new StringWriter();

    JavaClassMethodDefinition definition = new JavaClassMethodDefinition(
      METHOD_NAME,
      new JavaPublicModifier(),
      new JavaExplicitType(String.class),
      null,
      null
    );
    definition.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EXPECTED_VALUE_WITH_ACCESS_MODIFIER, writer.toString());
  }

  @Test
  void renderExceptionTest() {
    JavaClassMethodDefinition definition = new JavaClassMethodDefinition(
      null,
      null,
      null,
      null,
      null
    );
    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> definition.render(new JavaElementWriteRender(new StringWriter()))
    );
  }

  @Test
  void invokeTest() {
    JavaClassMethodDefinition definition = new JavaClassMethodDefinition(METHOD_NAME);
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> definition.invoke(new JavaNullArgument())
    );
  }

  @Test
  void invokeWithParamsTest() {
    JavaClassMethodDefinition definition = new JavaClassMethodDefinition(
      METHOD_NAME,
      new JavaParameter(2, PARAM_NAME_1, new JavaExplicitType(String.class)),
      new JavaParameter(1, PARAM_NAME_2, new JavaExplicitType(Integer.class))
    );
    Assertions.assertThrows(IllegalArgumentException.class, definition::invoke);
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> definition.invoke(new JavaNullArgument())
    );

    JavaClassMethodInvokeOperator method = definition.invoke(new JavaNullArgument(), new JavaNullArgument());
    Assertions.assertEquals(
      definition.getMethodParameters().size(),
      method.getMethodArguments().size()
    );
    Assertions.assertEquals(definition, method.getMethodDefinition());
  }

  @Test
  void compareToTest() {
    Assertions.assertNotEquals(
      0,
      new JavaClassMethodDefinition(METHOD_NAME)
        .compareTo(new JavaClassMethodDefinition(WRONG_METHOD_NAME))
    );
    Assertions.assertNotEquals(
      0,
      new JavaClassMethodDefinition(
        METHOD_NAME,
        new JavaParameter(1, PARAM_NAME_1, new JavaExplicitType(String.class))
      ).compareTo(new JavaClassMethodDefinition(METHOD_NAME))
    );
    Assertions.assertNotEquals(
      0,
      new JavaClassMethodDefinition(
        METHOD_NAME,
        new JavaParameter(1, PARAM_NAME_1, new JavaExplicitType(String.class)),
        new JavaParameter(2, PARAM_NAME_2, new JavaExplicitType(Integer.class))
      ).compareTo(
        new JavaClassMethodDefinition(
          METHOD_NAME,
          new JavaParameter(2, PARAM_NAME_1, new JavaExplicitType(String.class)),
          new JavaParameter(1, PARAM_NAME_2, new JavaExplicitType(Integer.class))
        )
      )
    );

    JavaClassMethodDefinition methodDefinition = new JavaClassMethodDefinition(METHOD_NAME);

    Assertions.assertEquals(0, methodDefinition.compareTo(methodDefinition));
    Assertions.assertEquals(0, methodDefinition.compareTo(new JavaClassMethodDefinition(METHOD_NAME)));

    methodDefinition = new JavaClassMethodDefinition(
      METHOD_NAME,
      new JavaParameter(1, PARAM_NAME_1, new JavaExplicitType(String.class))
    );
    Assertions.assertEquals(0, methodDefinition.compareTo(
      new JavaClassMethodDefinition(
        METHOD_NAME,
        new JavaParameter(1, PARAM_NAME_1, new JavaExplicitType(String.class))
      )));
  }
}