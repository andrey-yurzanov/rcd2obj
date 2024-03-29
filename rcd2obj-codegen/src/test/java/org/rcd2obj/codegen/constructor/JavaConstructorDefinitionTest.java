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

package org.rcd2obj.codegen.constructor;

import org.rcd2obj.codegen.JavaElementRenderingException;
import org.rcd2obj.codegen.modifier.JavaPublicModifier;
import org.rcd2obj.codegen.operator.JavaNullArgument;
import org.rcd2obj.codegen.parameter.JavaParameter;
import org.rcd2obj.codegen.render.JavaElementWriteRender;
import org.rcd2obj.codegen.type.JavaExplicitType;
import org.rcd2obj.codegen.type.JavaNameType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.Arrays;

/**
 * Tests of the constructor's code generator.
 *
 * @author Andrey_Yurzanov
 */
class JavaConstructorDefinitionTest {
  private static final String NAME = "MyClass";
  private static final String PARAM_NAME_1 = "name";
  private static final String PARAM_NAME_2 = "age";
  private static final String EMPTY_EXPECTED = "MyClass(){thrownewUnsupportedOperationException();}";
  private static final String WITH_MODIFIER_EXPECTED = "publicMyClass(){thrownewUnsupportedOperationException();}";
  private static final String WITH_PARAMS_EXPECTED =
    "publicMyClass(Integerage,Stringname){thrownewUnsupportedOperationException();}";

  @Test
  void renderEmptyTest() {
    StringWriter writer = new StringWriter();

    JavaConstructorDefinition constructor = new JavaConstructorDefinition(new JavaNameType(NAME));
    constructor.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EMPTY_EXPECTED, writer.toString());
  }

  @Test
  void renderWithModifierTest() {
    StringWriter writer = new StringWriter();

    JavaConstructorDefinition constructor = new JavaConstructorDefinition(
      new JavaNameType(NAME),
      null,
      new JavaPublicModifier(),
      null
    );
    constructor.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(WITH_MODIFIER_EXPECTED, writer.toString());
  }

  @Test
  void renderWithParamsTest() {
    StringWriter writer = new StringWriter();

    JavaConstructorDefinition constructor = new JavaConstructorDefinition(
      new JavaNameType(NAME),
      Arrays.asList(
        new JavaParameter(2, PARAM_NAME_1, new JavaExplicitType(String.class)),
        new JavaParameter(1, PARAM_NAME_2, new JavaExplicitType(Integer.class))
      ),
      new JavaPublicModifier(),
      null
    );
    constructor.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(WITH_PARAMS_EXPECTED, writer.toString());
  }

  @Test
  void renderExceptionTest() {
    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> new JavaConstructorDefinition(null).render(new JavaElementWriteRender(new StringWriter()))
    );
  }

  @Test
  void invokeTest() {
    JavaConstructorDefinition constructor = new JavaConstructorDefinition(new JavaNameType(NAME));
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> constructor.invoke(new JavaNullArgument())
    );

    JavaConstructorDefinition newConstructor = new JavaConstructorDefinition(
      new JavaNameType(NAME),
      new JavaParameter(2, PARAM_NAME_1, new JavaExplicitType(String.class)),
      new JavaParameter(1, PARAM_NAME_2, new JavaExplicitType(Integer.class))
    );

    Assertions.assertThrows(IllegalArgumentException.class, newConstructor::invoke);
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> newConstructor.invoke(new JavaNullArgument())
    );

    JavaClassConstructorInvokeOperator invokable = newConstructor.invoke(
      new JavaNullArgument(),
      new JavaNullArgument()
    );
    Assertions.assertEquals(newConstructor, invokable.getConstructorDefinition());
    Assertions.assertEquals(
      newConstructor.getConstructorParameters().size(),
      invokable.getConstructorArguments().size()
    );
  }

  @Test
  void compareToTest() {
    JavaConstructorDefinition constructor = new JavaConstructorDefinition(
      new JavaNameType(NAME),
      new JavaParameter(2, PARAM_NAME_1, new JavaExplicitType(String.class)),
      new JavaParameter(1, PARAM_NAME_2, new JavaExplicitType(Integer.class))
    );

    Assertions.assertNotEquals(
      0,
      constructor.compareTo(new JavaConstructorDefinition(new JavaExplicitType(String.class)))
    );
    Assertions.assertNotEquals(
      0,
      constructor.compareTo(new JavaConstructorDefinition(new JavaNameType(NAME)))
    );
    Assertions.assertNotEquals(
      0,
      constructor.compareTo(
        new JavaConstructorDefinition(
          new JavaNameType(NAME),
          new JavaParameter(1, PARAM_NAME_2, new JavaExplicitType(Integer.class))
        )
      )
    );
    Assertions.assertNotEquals(
      0,
      constructor.compareTo(
        new JavaConstructorDefinition(
          new JavaNameType(NAME),
          new JavaParameter(1, PARAM_NAME_1, new JavaExplicitType(String.class)),
          new JavaParameter(2, PARAM_NAME_2, new JavaExplicitType(Integer.class))
        )
      )
    );

    Assertions.assertEquals(0, constructor.compareTo(constructor));
    Assertions.assertEquals(
      0,
      new JavaConstructorDefinition(new JavaNameType(NAME))
        .compareTo(new JavaConstructorDefinition(new JavaNameType(NAME)))
    );
    Assertions.assertEquals(
      0,
      constructor.compareTo(
        new JavaConstructorDefinition(
          new JavaNameType(NAME),
          new JavaParameter(1, PARAM_NAME_2, new JavaExplicitType(Integer.class)),
          new JavaParameter(2, PARAM_NAME_1, new JavaExplicitType(String.class))
        )
      )
    );
  }
}