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

package dev.rcd2obj.codegen.constructor;

import dev.rcd2obj.codegen.modifier.JavaPublicModifier;
import dev.rcd2obj.codegen.operator.JavaNullArgument;
import dev.rcd2obj.codegen.parameter.JavaParameter;
import dev.rcd2obj.codegen.render.JavaElementWriteRender;
import dev.rcd2obj.codegen.type.JavaExplicitType;
import dev.rcd2obj.codegen.type.JavaNameType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

/**
 * Tests of the constructor's code generator.
 *
 * @author Andrey_Yurzanov
 */
class JavaClassConstructorDefinitionTest {
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

    JavaClassConstructorDefinition constructor = new JavaClassConstructorDefinition();
    constructor.setConstructorType(new JavaNameType(NAME));
    constructor.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EMPTY_EXPECTED, writer.toString());
  }

  @Test
  void renderWithModifierTest() {
    StringWriter writer = new StringWriter();

    JavaClassConstructorDefinition constructor = new JavaClassConstructorDefinition();
    constructor.setConstructorType(new JavaNameType(NAME));
    constructor.setConstructorAccessModifier(new JavaPublicModifier());
    constructor.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(WITH_MODIFIER_EXPECTED, writer.toString());
  }

  @Test
  void renderWithParamsTest() {
    StringWriter writer = new StringWriter();

    JavaClassConstructorDefinition constructor = new JavaClassConstructorDefinition();
    constructor.setConstructorType(new JavaNameType(NAME));
    constructor.setConstructorAccessModifier(new JavaPublicModifier());
    constructor.setConstructorParameters(
      new JavaParameter(2, PARAM_NAME_1, new JavaExplicitType(String.class)),
      new JavaParameter(1, PARAM_NAME_2, new JavaExplicitType(Integer.class))
    );
    constructor.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(WITH_PARAMS_EXPECTED, writer.toString());
  }

  @Test
  void getConstructorTest() {
    JavaClassConstructorDefinition constructor = new JavaClassConstructorDefinition();
    constructor.setConstructorType(new JavaNameType(NAME));
    constructor.setConstructorAccessModifier(new JavaPublicModifier());

    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> constructor.getConstructor(new JavaNullArgument())
    );

    constructor.setConstructorParameters(
      new JavaParameter(2, PARAM_NAME_1, new JavaExplicitType(String.class)),
      new JavaParameter(1, PARAM_NAME_2, new JavaExplicitType(Integer.class))
    );

    Assertions.assertThrows(IllegalArgumentException.class, constructor::getConstructor);
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> constructor.getConstructor(new JavaNullArgument())
    );

    JavaClassConstructorInvokeOperator invokable = constructor.getConstructor(
      new JavaNullArgument(),
      new JavaNullArgument()
    );
    Assertions.assertEquals(constructor, invokable.getConstructorDefinition());
    Assertions.assertEquals(
      constructor.getConstructorParameters().size(),
      invokable.getConstructorArguments().size()
    );
  }

  @Test
  void compareToTest() {
    JavaClassConstructorDefinition constructor = new JavaClassConstructorDefinition();
    constructor.setConstructorType(new JavaNameType(NAME));
    constructor.setConstructorParameters(
      new JavaParameter(2, PARAM_NAME_1, new JavaExplicitType(String.class)),
      new JavaParameter(1, PARAM_NAME_2, new JavaExplicitType(Integer.class))
    );

    Assertions.assertNotEquals(
      0,
      constructor.compareTo(
        new JavaClassConstructorDefinition()
          .setConstructorType(new JavaExplicitType(String.class))
      )
    );
    Assertions.assertNotEquals(
      0,
      constructor.compareTo(
        new JavaClassConstructorDefinition()
          .setConstructorType(new JavaNameType(NAME))
      )
    );
    Assertions.assertNotEquals(
      0,
      constructor.compareTo(
        new JavaClassConstructorDefinition()
          .setConstructorType(new JavaNameType(NAME))
          .setConstructorParameters(
            new JavaParameter(1, PARAM_NAME_2, new JavaExplicitType(Integer.class))
          )
      )
    );
    Assertions.assertNotEquals(
      0,
      constructor.compareTo(
        new JavaClassConstructorDefinition()
          .setConstructorType(new JavaNameType(NAME))
          .setConstructorParameters(
            new JavaParameter(1, PARAM_NAME_1, new JavaExplicitType(String.class)),
            new JavaParameter(2, PARAM_NAME_2, new JavaExplicitType(Integer.class))
          )
      )
    );

    Assertions.assertEquals(0, constructor.compareTo(constructor));
    Assertions.assertEquals(
      0,
      new JavaClassConstructorDefinition()
        .setConstructorType(new JavaNameType(NAME))
        .compareTo(
          new JavaClassConstructorDefinition()
            .setConstructorType(new JavaNameType(NAME))
        )
    );
    Assertions.assertEquals(
      0,
      constructor.compareTo(
        new JavaClassConstructorDefinition()
          .setConstructorType(new JavaNameType(NAME))
          .setConstructorParameters(
            new JavaParameter(1, PARAM_NAME_2, new JavaExplicitType(Integer.class)),
            new JavaParameter(2, PARAM_NAME_1, new JavaExplicitType(String.class))
          )
      )
    );
  }
}