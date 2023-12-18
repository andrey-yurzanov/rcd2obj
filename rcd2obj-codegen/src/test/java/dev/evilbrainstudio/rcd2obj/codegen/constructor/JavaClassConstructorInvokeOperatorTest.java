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

package dev.evilbrainstudio.rcd2obj.codegen.constructor;

import dev.evilbrainstudio.rcd2obj.codegen.operator.JavaNullArgument;
import dev.evilbrainstudio.rcd2obj.codegen.parameter.JavaParameter;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementWriteRender;
import dev.evilbrainstudio.rcd2obj.codegen.type.JavaExplicitType;
import dev.evilbrainstudio.rcd2obj.codegen.type.JavaNameType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

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
      new JavaClassConstructorDefinition()
        .setConstructorType(new JavaNameType(NAME))
    );
    operator.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(EMPTY_EXPECTED, writer.toString());
  }

  @Test
  void renderArgsTest() {
    StringWriter writer = new StringWriter();
    JavaClassConstructorInvokeOperator operator = new JavaClassConstructorInvokeOperator(
      new JavaClassConstructorDefinition()
        .setConstructorType(new JavaNameType(NAME))
        .setConstructorParameters(
          new JavaParameter()
            .setParameterOrder(2)
            .setParameterType(new JavaExplicitType(String.class))
            .setParameterName(PARAM_NAME_1),
          new JavaParameter()
            .setParameterOrder(1)
            .setParameterType(new JavaExplicitType(Integer.class))
            .setParameterName(PARAM_NAME_2)
        ),
      new JavaNullArgument(),
      new JavaNullArgument()
    );
    operator.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(ARGS_EXPECTED, writer.toString());
  }

  @Test
  void renderThrowsTest() {
    StringWriter writer = new StringWriter();
    JavaClassConstructorInvokeOperator operator = new JavaClassConstructorInvokeOperator(
      new JavaClassConstructorDefinition()
    );
    Assertions.assertThrows(NullPointerException.class, () -> operator.render(new JavaElementWriteRender(writer)));
  }
}