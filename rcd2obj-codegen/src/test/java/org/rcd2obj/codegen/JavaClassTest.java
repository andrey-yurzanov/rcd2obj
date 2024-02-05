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

package org.rcd2obj.codegen;

import org.rcd2obj.codegen.constructor.JavaClassConstructorDefinition;
import org.rcd2obj.codegen.inherited.JavaInheritableElement;
import org.rcd2obj.codegen.modifier.JavaPublicModifier;
import org.rcd2obj.codegen.parameter.JavaParameter;
import org.rcd2obj.codegen.render.JavaElementWriteRender;
import org.rcd2obj.codegen.type.JavaExplicitType;
import org.rcd2obj.codegen.type.JavaNameType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;

/**
 * Tests of the class's renderer.
 *
 * @author Andrey_Yurzanov
 */
class JavaClassTest {
  private static final String CLASS_NAME = "MyClass";
  private static final String PARAM_NAME_1 = "name";
  private static final String PARAM_NAME_2 = "age";
  private static final String PACKAGE_NAME = "org.rcd2obj.codegen";
  private static final String EMPTY_CLASS_EXPECTED = "publicclassMyClass{}";
  private static final String CLASS_PACKAGE_EXPECTED =
    "packageorg.rcd2obj.codegen;publicclassMyClass{}";
  private static final String CLASS_METHODS_EXPECTED = String.join(
    "",
    "packageorg.rcd2obj.codegen;",
    "importjava.io.Serializable;importjava.util.Iterator;",
    "publicclassMyClassimplementsSerializable,Comparable,Iterable,Runnable{",
    "publicintcompareTo(Objectarg0){thrownewUnsupportedOperationException();}",
    "publicIteratoriterator(){thrownewUnsupportedOperationException();}",
    "publicvoidrun(){thrownewUnsupportedOperationException();}}"
  );
  private static final String CLASS_CONSTRUCTORS_EXPECTED = String.join(
    "",
    "packageorg.rcd2obj.codegen;",
    "importjava.io.Serializable;importjava.util.Iterator;",
    "publicclassMyClassimplementsSerializable,Comparable,Iterable,Runnable{",
    "MyClass(){thrownewUnsupportedOperationException();}",
    "publicMyClass(Stringname){thrownewUnsupportedOperationException();}",
    "publicMyClass(Stringname,Integerage){thrownewUnsupportedOperationException();}",
    "publicintcompareTo(Objectarg0){thrownewUnsupportedOperationException();}",
    "publicIteratoriterator(){thrownewUnsupportedOperationException();}",
    "publicvoidrun(){thrownewUnsupportedOperationException();}}"
  );

  @Test
  void renderEmptyTest() {
    StringWriter writer = new StringWriter();

    JavaClass javaClass = new JavaClass(CLASS_NAME, null, new JavaPublicModifier(), null, null, null);
    javaClass.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EMPTY_CLASS_EXPECTED, writer.toString());
  }

  @Test
  void renderPackageTest() {
    StringWriter writer = new StringWriter();

    JavaClass javaClass = new JavaClass(
      CLASS_NAME,
      new JavaClassPackage(PACKAGE_NAME),
      new JavaPublicModifier(),
      null,
      null,
      null
    );
    javaClass.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(CLASS_PACKAGE_EXPECTED, writer.toString());
  }

  @Test
  void renderImplementsTest() {
    StringWriter writer = new StringWriter();

    JavaClass javaClass = new JavaClass(
      CLASS_NAME,
      new JavaClassPackage(PACKAGE_NAME),
      new JavaPublicModifier(),
      Arrays.asList(
        new JavaInheritableElement(Comparable.class),
        new JavaInheritableElement(Runnable.class),
        new JavaInheritableElement(Iterable.class),
        new JavaInheritableElement(Serializable.class)
      ),
      null,
      null
    );

    javaClass.render(new JavaElementWriteRender(writer));
    Assertions.assertEquals(CLASS_METHODS_EXPECTED, writer.toString());
  }

  @Test
  void renderConstructorsTest() {
    StringWriter writer = new StringWriter();

    JavaClass javaClass = new JavaClass(
      CLASS_NAME,
      new JavaClassPackage(PACKAGE_NAME),
      new JavaPublicModifier(),
      Arrays.asList(
        new JavaInheritableElement(Comparable.class),
        new JavaInheritableElement(Runnable.class),
        new JavaInheritableElement(Iterable.class),
        new JavaInheritableElement(Serializable.class)
      ),
      Arrays.asList(
        new JavaClassConstructorDefinition(new JavaNameType(CLASS_NAME)),
        new JavaClassConstructorDefinition(
          new JavaNameType(CLASS_NAME),
          Collections.singletonList(
            new JavaParameter(1, PARAM_NAME_1, new JavaExplicitType(String.class))
          ),
          new JavaPublicModifier(),
          null
        ),
        new JavaClassConstructorDefinition(
          new JavaNameType(CLASS_NAME),
          Arrays.asList(
            new JavaParameter(1, PARAM_NAME_1, new JavaExplicitType(String.class)),
            new JavaParameter(2, PARAM_NAME_2, new JavaExplicitType(Integer.class))
          ),
          new JavaPublicModifier(),
          null
        )
      ),
      null
    );

    javaClass.render(new JavaElementWriteRender(writer));
    Assertions.assertEquals(CLASS_CONSTRUCTORS_EXPECTED, writer.toString());
  }

  @Test
  void renderExceptionTest() {
    JavaClass javaClass = new JavaClass(null, null, null, null, null, null);
    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> javaClass.render(new JavaElementWriteRender(new StringWriter()))
    );
  }
}