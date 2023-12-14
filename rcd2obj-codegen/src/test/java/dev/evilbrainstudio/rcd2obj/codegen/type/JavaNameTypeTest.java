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

import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementWriteRender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

/**
 * Tests of {@link JavaNameType}.
 *
 * @author Andrey_Yurzanov
 */
class JavaNameTypeTest {
  private static final String NAME = "MyClass";
  private static final String WRONG_NAME = "MyClassWrong";

  @Test
  void renderTest() {
    Assertions.assertThrows(NullPointerException.class, () -> new JavaNameType(null));

    StringWriter writer = new StringWriter();
    JavaNameType type = new JavaNameType(NAME);
    type.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(NAME, writer.toString());
    Assertions.assertEquals(NAME, type.getName());
  }

  @Test
  void compareToTest() {
    JavaNameType type = new JavaNameType(NAME);
    Assertions.assertNotEquals(0, type.compareTo(null));
    Assertions.assertNotEquals(0, type.compareTo(new JavaExplicitType(String.class)));
    Assertions.assertNotEquals(0, type.compareTo(new JavaNameType(WRONG_NAME)));
    Assertions.assertEquals(0, type.compareTo(type));
    Assertions.assertEquals(0, type.compareTo(new JavaNameType(NAME)));
  }
}