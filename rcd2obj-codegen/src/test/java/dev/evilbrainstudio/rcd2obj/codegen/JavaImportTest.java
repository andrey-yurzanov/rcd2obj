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

package dev.evilbrainstudio.rcd2obj.codegen;

import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementWriteRender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of the import's operator.
 *
 * @author Andrey_Yurzanov
 */
class JavaImportTest {
  private static final String IMPORT_EXPECTED = "importjava.util.ArrayList;";

  @Test
  void compareToTest() {
    Assertions.assertEquals(0, new JavaImport(ArrayList.class).compareTo(new JavaImport(ArrayList.class)));
    Assertions.assertNotEquals(0, new JavaImport(Integer.class).compareTo(new JavaImport(ArrayList.class)));
  }

  @Test
  void renderTest() {
    StringWriter writer = new StringWriter();

    JavaImport javaImport = new JavaImport(ArrayList.class);
    javaImport.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(IMPORT_EXPECTED, writer.toString());
  }
}