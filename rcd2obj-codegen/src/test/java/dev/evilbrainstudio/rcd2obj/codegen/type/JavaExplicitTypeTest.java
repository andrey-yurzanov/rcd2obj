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

package dev.evilbrainstudio.rcd2obj.codegen.type;

import dev.evilbrainstudio.rcd2obj.codegen.JavaElementRenderingException;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementWriteRender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.Map;

/**
 * Tests of {@link JavaExplicitType}.
 *
 * @author Andrey_Yurzanov
 */
class JavaExplicitTypeTest {
  @Test
  void renderLangTest() {
    StringWriter writer = new StringWriter();
    JavaExplicitType type = new JavaExplicitType(String.class);
    type.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(String.class.getSimpleName(), writer.toString());
  }

  @Test
  void renderUtilTest() {
    StringWriter writer = new StringWriter();
    JavaExplicitType type = new JavaExplicitType(Map.class);
    type.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(Map.class.getCanonicalName(), writer.toString());
  }

  @Test
  void renderExceptionTest() {
    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> new JavaExplicitType(null).render(new JavaElementWriteRender(new StringWriter()))
    );
  }

  @Test
  void compareToTest() {
    JavaExplicitType type = new JavaExplicitType(String.class);
    Assertions.assertNotEquals(0, type.compareTo(null));
    Assertions.assertNotEquals(0, type.compareTo(new JavaExplicitType(Integer.class)));
    Assertions.assertEquals(0, type.compareTo(type));
    Assertions.assertEquals(0, type.compareTo(new JavaExplicitType(String.class)));
  }
}