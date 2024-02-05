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

package dev.rcd2obj.codegen.render.format;

import dev.rcd2obj.codegen.JavaClass;
import dev.rcd2obj.codegen.JavaClassPackage;
import dev.rcd2obj.codegen.inherited.JavaInheritableElement;
import dev.rcd2obj.codegen.modifier.JavaPublicModifier;
import dev.rcd2obj.codegen.render.JavaElementWriteRender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.function.BiConsumer;

/**
 * Tests of formatting renderer.
 *
 * @author Andrey_Yurzanov
 */
class JavaElementFormatRenderTest {
  private static final String CLASS_NAME = "MyClass";
  private static final String PACKAGE_NAME = "dev.rcd2obj.codegen";
  private static final String NEW_LINE = System.lineSeparator();
  private static final String EXPECTED = String.join(
    "",
    "package dev.rcd2obj.codegen;",
    NEW_LINE,
    NEW_LINE,
    "import java.io.Serializable;",
    NEW_LINE,
    "import java.util.Iterator;",
    NEW_LINE,
    "import java.util.function.BiConsumer;",
    NEW_LINE,
    NEW_LINE,
    "public class MyClass implements Serializable, Comparable, Iterable, Runnable, BiConsumer {",
    NEW_LINE,
    NEW_LINE,
    "  public void accept(Object arg0, Object arg1) {",
    NEW_LINE,
    "    throw new UnsupportedOperationException();",
    NEW_LINE,
    "  }",
    NEW_LINE,
    NEW_LINE,
    "  public int compareTo(Object arg0) {",
    NEW_LINE,
    "    throw new UnsupportedOperationException();",
    NEW_LINE,
    "  }",
    NEW_LINE,
    NEW_LINE,
    "  public Iterator iterator() {",
    NEW_LINE,
    "    throw new UnsupportedOperationException();",
    NEW_LINE,
    "  }",
    NEW_LINE,
    NEW_LINE,
    "  public void run() {",
    NEW_LINE,
    "    throw new UnsupportedOperationException();",
    NEW_LINE,
    "  }",
    NEW_LINE,
    NEW_LINE,
    "}"
  );

  @Test
  void renderTest() {
    StringWriter writer = new StringWriter();

    JavaClass javaClass = new JavaClass(
      CLASS_NAME,
      new JavaClassPackage(PACKAGE_NAME),
      new JavaPublicModifier(),
      Arrays.asList(
        new JavaInheritableElement(Comparable.class),
        new JavaInheritableElement(Runnable.class),
        new JavaInheritableElement(Iterable.class),
        new JavaInheritableElement(Serializable.class),
        new JavaInheritableElement(BiConsumer.class)
      ),
      null,
      null
    );

    JavaElementFormatRender renderer = new JavaElementFormatRender(new JavaElementWriteRender(writer));

    javaClass.render(renderer);
    Assertions.assertEquals(EXPECTED, writer.toString());
  }
}