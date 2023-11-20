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

import dev.evilbrainstudio.rcd2obj.codegen.inherited.JavaInheritableElement;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementWriteRender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.io.StringWriter;

/**
 * Tests of the class's renderer.
 *
 * @author Andrey_Yurzanov
 */
class JavaClassTest {
  private static final String CLASS_NAME = "MyClass";
  private static final String PACKAGE_NAME = "dev.evilbrainstudio.rcd2obj.codegen";
  private static final String EMPTY_CLASS_EXPECTED = "publicclassMyClass{}";
  private static final String CLASS_PACKAGE_EXPECTED =
    "packagedev.evilbrainstudio.rcd2obj.codegen;publicclassMyClass{}";
  private static final String CLASS_METHODS_EXPECTED = String.join(
    "",
    "packagedev.evilbrainstudio.rcd2obj.codegen;",
    "importjava.io.Serializable;importjava.util.Iterator;",
    "publicclassMyClassimplementsSerializable,Comparable,Iterable,Runnable{",
    "publicintcompareTo(Objectarg0){thrownewUnsupportedOperationException();}",
    "publicIteratoriterator(){thrownewUnsupportedOperationException();}",
    "publicvoidrun(){thrownewUnsupportedOperationException();}}"
  );

  @Test
  void renderEmptyTest() {
    StringWriter writer = new StringWriter();

    JavaClass javaClass = new JavaClass(CLASS_NAME);
    javaClass.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EMPTY_CLASS_EXPECTED, writer.toString());
  }

  @Test
  void renderPackageTest() {
    StringWriter writer = new StringWriter();

    JavaClass javaClass = new JavaClass(CLASS_NAME)
      .setClassPackage(new JavaClassPackage(PACKAGE_NAME));

    javaClass.render(new JavaElementWriteRender(writer));
    Assertions.assertEquals(CLASS_PACKAGE_EXPECTED, writer.toString());
  }

  @Test
  void renderImplementsTest() {
    StringWriter writer = new StringWriter();

    JavaClass javaClass = new JavaClass(CLASS_NAME)
      .setClassPackage(new JavaClassPackage(PACKAGE_NAME))
      .setClassImplements(
        new JavaInheritableElement(Comparable.class),
        new JavaInheritableElement(Runnable.class),
        new JavaInheritableElement(Iterable.class),
        new JavaInheritableElement(Serializable.class)
      );

    javaClass.render(new JavaElementWriteRender(writer));
    Assertions.assertEquals(CLASS_METHODS_EXPECTED, writer.toString());
  }
}