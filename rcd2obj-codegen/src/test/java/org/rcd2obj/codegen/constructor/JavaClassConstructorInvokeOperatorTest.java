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

package org.rcd2obj.codegen.constructor;

import org.rcd2obj.codegen.JavaElementRenderingException;
import org.rcd2obj.codegen.operator.JavaNullArgument;
import org.rcd2obj.codegen.parameter.JavaParameter;
import org.rcd2obj.codegen.render.JavaElementWriteRender;
import org.rcd2obj.codegen.type.JavaExplicitType;
import org.rcd2obj.codegen.type.JavaNameType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;

/**
 * Tests of {@link JavaClassConstructorInvokeOperator}.
 *
 * @author Andrey_Yurzanov
 */
class JavaClassConstructorInvokeOperatorTest {
  private static final String NAME = "MyClass";
  private static final String PARAM_NAME_1 = "name";
  private static final String PARAM_NAME_2 = "age";
  private static final String EMPTY_EXPECTED = "MyClass()";
  private static final String ARGS_EXPECTED = "MyClass(null,null)";

  @Test
  void renderTest() {
    StringWriter writer = new StringWriter();
    JavaClassConstructorInvokeOperator operator = new JavaClassConstructorInvokeOperator(
      new JavaConstructorDefinition(new JavaNameType(NAME)),
      Collections.emptyList()
    );
    operator.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EMPTY_EXPECTED, writer.toString());
  }

  @Test
  void renderArgsTest() {
    StringWriter writer = new StringWriter();
    JavaClassConstructorInvokeOperator operator = new JavaClassConstructorInvokeOperator(
      new JavaConstructorDefinition(
        new JavaNameType(NAME),
        new JavaParameter(2, PARAM_NAME_1, new JavaExplicitType(String.class)),
        new JavaParameter(1, PARAM_NAME_2, new JavaExplicitType(Integer.class))
      ),
      Arrays.asList(
        new JavaNullArgument(),
        new JavaNullArgument()
      )
    );
    operator.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(ARGS_EXPECTED, writer.toString());
  }

  @Test
  void renderExceptionTest() {
    JavaElementWriteRender render = new JavaElementWriteRender(new StringWriter());
    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> new JavaClassConstructorInvokeOperator(null, null).render(render)
    );
    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> new JavaClassConstructorInvokeOperator(new JavaConstructorDefinition(null), null).render(render)
    );
  }
}