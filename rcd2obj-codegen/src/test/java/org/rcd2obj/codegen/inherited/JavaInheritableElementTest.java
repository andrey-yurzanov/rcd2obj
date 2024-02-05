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

package org.rcd2obj.codegen.inherited;

import org.rcd2obj.codegen.JavaElementRenderingException;
import org.rcd2obj.codegen.method.JavaClassMethodDefinition;
import org.rcd2obj.codegen.render.JavaElementWriteRender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests of {@link JavaInheritableElement}.
 *
 * @author Andrey_Yurzanov
 */
class JavaInheritableElementTest {
  private static final String EXPECTED = "Comparable";

  @Test
  void isInterfaceTest() {
    Assertions.assertTrue(new JavaInheritableElement(Map.class).isInterface());
    Assertions.assertFalse(new JavaInheritableElement(HashMap.class).isInterface());
  }

  @Test
  void getInheritedMethodsTest() {
    JavaInheritableElement element = new JavaInheritableElement(Comparable.class);
    Collection<JavaClassMethodDefinition> methods = element.getInheritedMethods();

    Assertions.assertEquals(1, methods.size());
    Assertions.assertEquals("compareTo", methods.stream().findFirst().get().getMethodName());
  }

  @Test
  void compareToTest() {
    Assertions.assertNotEquals(
      0,
      new JavaInheritableElement(Comparable.class).compareTo(new JavaInheritableElement(Map.class))
    );
    Assertions.assertEquals(
      0,
      new JavaInheritableElement(Comparable.class).compareTo(new JavaInheritableElement(Comparable.class))
    );
  }

  @Test
  void renderTest() {
    StringWriter writer = new StringWriter();

    JavaInheritableElement element = new JavaInheritableElement(Comparable.class);
    element.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EXPECTED, writer.toString());
  }

  @Test
  void renderExceptionTest() {
    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> new JavaInheritableElement(null).render(new JavaElementWriteRender(new StringWriter()))
    );
  }
}