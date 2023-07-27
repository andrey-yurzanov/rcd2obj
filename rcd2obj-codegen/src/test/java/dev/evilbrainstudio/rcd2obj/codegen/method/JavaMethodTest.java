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

import dev.evilbrainstudio.rcd2obj.codegen.modifier.JavaPublicModifier;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementWriteRender;
import java.io.StringWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests of the method code generator.
 *
 * @author Andrey_Yurzanov
 */
class JavaMethodTest {
  private static final String METHOD_NAME = "renderTest";
  private static final String EXPECTED_VALUE =
      "voidrenderTest(){thrownewjava.lang.UnsupportedOperationException();}";
  private static final String EXPECTED_VALUE_WITH_RETURN =
      "java.lang.StringrenderTest(){thrownewjava.lang.UnsupportedOperationException();}";
  private static final String EXPECTED_VALUE_WITH_ACCESS_MODIFIER =
      "publicjava.lang.StringrenderTest(){thrownewjava.lang.UnsupportedOperationException();}";

  @Test
  void renderTest() {
    StringWriter writer = new StringWriter();

    JavaMethod method = new JavaMethod(METHOD_NAME);
    method.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EXPECTED_VALUE, writer.toString());
  }

  @Test
  void renderWithReturnTest() {
    StringWriter writer = new StringWriter();

    JavaMethod method = new JavaMethod(METHOD_NAME);
    method.methodReturnType(String.class);
    method.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EXPECTED_VALUE_WITH_RETURN, writer.toString());
  }

  @Test
  void renderWithAccessModifierTest() {
    StringWriter writer = new StringWriter();

    JavaMethod method = new JavaMethod(METHOD_NAME);
    method.methodReturnType(String.class);
    method.methodAccessModifier(new JavaPublicModifier());
    method.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EXPECTED_VALUE_WITH_ACCESS_MODIFIER, writer.toString());
  }
}