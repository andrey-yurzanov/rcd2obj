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

package dev.evilbrainstudio.rcd2obj.codegen;

import dev.evilbrainstudio.rcd2obj.codegen.inherited.JavaInheritableElement;
import dev.evilbrainstudio.rcd2obj.codegen.method.JavaClassMethodDefinition;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementWriteRender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Comparator;

/**
 * Tests of the inherited element.
 *
 * @author Andrey_Yurzanov
 */
class JavaInheritableElementTest {
  private static final String INHERITABLE_EXPECTED = "java.util.Comparator";
  private static final String INHERITABLE_EMPTY_EXPECTED = "java.io.Serializable";
  private static final String INHERITED_METHOD_EXPECTED =
    "publicintcompare(Objectarg0,Objectarg1){thrownewUnsupportedOperationException();}";
  private static final int INHERITED_METHODS_SIZE_EXPECTED = 1;

  @Test
  void renderTest() {
    JavaInheritableElement element = new JavaInheritableElement(Comparator.class);

    StringWriter writer = new StringWriter();
    element.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(INHERITABLE_EXPECTED, writer.toString());
  }

  @Test
  void renderEmptyTest() {
    JavaInheritableElement element = new JavaInheritableElement(Serializable.class);

    StringWriter writer = new StringWriter();
    element.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(INHERITABLE_EMPTY_EXPECTED, writer.toString());
  }

  @Test
  void renderMethodsTest() {
    JavaInheritableElement element = new JavaInheritableElement(Comparator.class);

    Collection<JavaClassMethodDefinition> methods = element.getInheritedMethods();
    Assertions.assertEquals(INHERITED_METHODS_SIZE_EXPECTED, methods.size());

    StringWriter writer = new StringWriter();
    methods
      .stream()
      .findFirst()
      .get()
      .render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(INHERITED_METHOD_EXPECTED, writer.toString());
  }

  @Test
  void renderEmptyMethodsTest() {
    JavaInheritableElement element = new JavaInheritableElement(Serializable.class);

    Collection<JavaClassMethodDefinition> methods = element.getInheritedMethods();
    Assertions.assertTrue(methods.isEmpty());
  }
}