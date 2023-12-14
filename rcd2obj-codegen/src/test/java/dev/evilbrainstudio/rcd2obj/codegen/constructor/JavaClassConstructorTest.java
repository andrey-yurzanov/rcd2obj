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

package dev.evilbrainstudio.rcd2obj.codegen.constructor;

import dev.evilbrainstudio.rcd2obj.codegen.modifier.JavaPublicModifier;
import dev.evilbrainstudio.rcd2obj.codegen.parameter.JavaParameter;
import dev.evilbrainstudio.rcd2obj.codegen.parameter.JavaValueParameter;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementWriteRender;
import dev.evilbrainstudio.rcd2obj.codegen.type.JavaExplicitType;
import dev.evilbrainstudio.rcd2obj.codegen.type.JavaNameType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

/**
 * Tests of the constructor's code generator.
 *
 * @author Andrey_Yurzanov
 */
class JavaClassConstructorTest {
  private static final String NAME = "MyClass";
  private static final String PARAM_NAME_1 = "name";
  private static final String PARAM_VALUE_1 = "MyName";
  private static final String PARAM_NAME_2 = "age";
  private static final Integer PARAM_VALUE_2 = 12;
  private static final String EMPTY = "MyClass()";
  private static final String WITH_MODIFIER = "publicMyClass()";
  private static final String WITH_PARAMS = "publicMyClass(Integerage,Stringname)";
  private static final String WITH_PARAMS_VALUE = "publicMyClass(12,\"MyName\")";
  private static final String WITH_IMPL =
    "publicMyClass(Integerage,Stringname){thrownewUnsupportedOperationException();}";

  @Test
  void renderEmptyTest() {
    StringWriter writer = new StringWriter();

    JavaClassConstructor constructor = new JavaClassConstructor();
    constructor.setConstructorType(new JavaNameType(NAME));
    constructor.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EMPTY, writer.toString());
  }

  @Test
  void renderWithModifierTest() {
    StringWriter writer = new StringWriter();

    JavaClassConstructor constructor = new JavaClassConstructor();
    constructor.setConstructorType(new JavaNameType(NAME));
    constructor.setConstructorAccessModifier(new JavaPublicModifier());
    constructor.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(WITH_MODIFIER, writer.toString());
  }

  @Test
  void renderWithParamsTest() {
    StringWriter writer = new StringWriter();

    JavaClassConstructor constructor = new JavaClassConstructor();
    constructor.setConstructorType(new JavaNameType(NAME));
    constructor.setConstructorAccessModifier(new JavaPublicModifier());
    constructor.setConstructorParameters(
      new JavaParameter()
        .setParameterOrder(2)
        .setParameterType(new JavaExplicitType(String.class))
        .setParameterName(PARAM_NAME_1),
      new JavaParameter()
        .setParameterOrder(1)
        .setParameterType(new JavaExplicitType(Integer.class))
        .setParameterName(PARAM_NAME_2)
    );
    constructor.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(WITH_PARAMS, writer.toString());
  }

  @Test
  void renderWithParamsValueTest() {
    StringWriter writer = new StringWriter();

    JavaClassConstructor constructor = new JavaClassConstructor();
    constructor.setConstructorType(new JavaNameType(NAME));
    constructor.setConstructorAccessModifier(new JavaPublicModifier());
    constructor.setConstructorParameters(
      new JavaValueParameter()
        .setParameterOrder(2)
        .setParameterType(new JavaExplicitType(String.class))
        .setParameterName(PARAM_NAME_1)
        .setParameterValue(PARAM_VALUE_1),
      new JavaValueParameter()
        .setParameterOrder(1)
        .setParameterType(new JavaExplicitType(Integer.class))
        .setParameterName(PARAM_NAME_2)
        .setParameterValue(PARAM_VALUE_2)
    );
    constructor.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(WITH_PARAMS_VALUE, writer.toString());
  }

  @Test
  void renderWithImplTest() {
    StringWriter writer = new StringWriter();

    JavaClassConstructor constructor = new JavaClassConstructor();
    constructor.setConstructorType(new JavaNameType(NAME));
    constructor.setConstructorAccessModifier(new JavaPublicModifier());
    constructor.setConstructorParameters(
      new JavaParameter()
        .setParameterOrder(2)
        .setParameterType(new JavaExplicitType(String.class))
        .setParameterName(PARAM_NAME_1),
      new JavaParameter()
        .setParameterOrder(1)
        .setParameterType(new JavaExplicitType(Integer.class))
        .setParameterName(PARAM_NAME_2)
    );
    constructor.setConstructorImpl(new JavaConstructorUnsupportedImpl());
    constructor.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(WITH_IMPL, writer.toString());
  }

  @Test
  void compareToTest() {
    JavaClassConstructor constructor = new JavaClassConstructor();
    constructor.setConstructorType(new JavaNameType(NAME));
    constructor.setConstructorParameters(
      new JavaValueParameter()
        .setParameterOrder(2)
        .setParameterType(new JavaExplicitType(String.class))
        .setParameterName(PARAM_NAME_1)
        .setParameterValue(PARAM_VALUE_1),
      new JavaValueParameter()
        .setParameterOrder(1)
        .setParameterType(new JavaExplicitType(Integer.class))
        .setParameterName(PARAM_NAME_2)
        .setParameterValue(PARAM_VALUE_2)
    );

    Assertions.assertNotEquals(
      0,
      constructor.compareTo(
        new JavaClassConstructor()
          .setConstructorType(new JavaExplicitType(String.class))
      )
    );
    Assertions.assertNotEquals(
      0,
      constructor.compareTo(
        new JavaClassConstructor()
          .setConstructorType(new JavaNameType(NAME))
      )
    );
    Assertions.assertNotEquals(
      0,
      constructor.compareTo(
        new JavaClassConstructor()
          .setConstructorType(new JavaNameType(NAME))
          .setConstructorParameters(
            new JavaValueParameter()
              .setParameterOrder(1)
              .setParameterType(new JavaExplicitType(Integer.class))
              .setParameterName(PARAM_NAME_2)
              .setParameterValue(PARAM_VALUE_2)
          )
      )
    );
    Assertions.assertNotEquals(
      0,
      constructor.compareTo(
        new JavaClassConstructor()
          .setConstructorType(new JavaNameType(NAME))
          .setConstructorParameters(
            new JavaValueParameter()
              .setParameterOrder(1)
              .setParameterType(new JavaExplicitType(String.class))
              .setParameterName(PARAM_NAME_1)
              .setParameterValue(PARAM_VALUE_1),
            new JavaValueParameter()
              .setParameterOrder(2)
              .setParameterType(new JavaExplicitType(Integer.class))
              .setParameterName(PARAM_NAME_2)
              .setParameterValue(PARAM_VALUE_2)
          )
      )
    );

    Assertions.assertEquals(0, constructor.compareTo(constructor));
    Assertions.assertEquals(
      0,
      new JavaClassConstructor()
        .setConstructorType(new JavaNameType(NAME))
        .compareTo(
          new JavaClassConstructor()
            .setConstructorType(new JavaNameType(NAME))
        )
    );
    Assertions.assertEquals(
      0,
      constructor.compareTo(
        new JavaClassConstructor()
          .setConstructorType(new JavaNameType(NAME))
          .setConstructorParameters(
            new JavaValueParameter()
              .setParameterOrder(1)
              .setParameterType(new JavaExplicitType(Integer.class))
              .setParameterName(PARAM_NAME_2)
              .setParameterValue(PARAM_VALUE_2),
            new JavaValueParameter()
              .setParameterOrder(2)
              .setParameterType(new JavaExplicitType(String.class))
              .setParameterName(PARAM_NAME_1)
              .setParameterValue(PARAM_VALUE_1)
          )
      )
    );
  }
}