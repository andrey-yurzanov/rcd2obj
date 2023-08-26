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

package dev.evilbrainstudio.rcd2obj.codegen.method;

import dev.evilbrainstudio.rcd2obj.codegen.type.JavaExplicitType;
import dev.evilbrainstudio.rcd2obj.codegen.type.JavaType;
import dev.evilbrainstudio.rcd2obj.codegen.modifier.JavaPublicModifier;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementWriteRender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.function.Function;

/**
 * Tests of the method code generator.
 *
 * @author Andrey_Yurzanov
 */
class JavaMethodTest {
  private static final String METHOD_NAME = "renderTest";
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

    new JavaMethod()
      .setMethodName(METHOD_NAME)
      .setMethodReturnType(new JavaExplicitType(void.class))
      .setMethodImpl(new JavaMethodUnsupportedImpl())
      .render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EXPECTED_VALUE, writer.toString());
  }

  @Test
  void renderWithReturnTest() {
    StringWriter writer = new StringWriter();

    new JavaMethod()
      .setMethodName(METHOD_NAME)
      .setMethodReturnType(new JavaExplicitType(String.class))
      .setMethodImpl(new JavaMethodUnsupportedImpl())
      .render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EXPECTED_VALUE_WITH_RETURN, writer.toString());
  }

  @Test
  void renderWithAccessModifierTest() {
    StringWriter writer = new StringWriter();

    new JavaMethod()
      .setMethodName(METHOD_NAME)
      .setMethodReturnType(new JavaExplicitType(String.class))
      .setMethodAccessModifier(new JavaPublicModifier())
      .setMethodImpl(new JavaMethodUnsupportedImpl())
      .render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EXPECTED_VALUE_WITH_ACCESS_MODIFIER, writer.toString());
  }

  @Test
  void renderByMethodTest() {
    StringWriter writer = new StringWriter();

    new JavaMethod(Comparable.class.getDeclaredMethods()[0])
      .render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EXPECTED_VALUE_BY_METHOD, writer.toString());
  }

  @Test
  void renderGenericMethod() { // TODO. Issue: #10
    // 1. нужно иметь список типов для резолвинга: Map<String, Class<?>> = {"T", String.class, "R", Integer.class};
    // 2. нужно иметь список стратегий обработки различных типов дженериков. ParamGenericType, ArrayGenericType и т.д.
    // 3. нужно иметь фабрику для создания стратегий
    // 4. нужены ли пункты 2 и 3?
    // 5. пример создания типа: factory.create(Type) -> type.get(Type.getClass()).create(Type, this);

    StringWriter writer = new StringWriter();

    new JavaMethod(Function.class.getDeclaredMethods()[0])
      .render(new JavaElementWriteRender(writer));

//    Assertions.assertEquals(EXPECTED_GENERIC, writer.toString());
  }
}