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

package dev.evilbrainstudio.rcd2obj.codegen.operator;

import dev.evilbrainstudio.rcd2obj.codegen.constructor.JavaClassConstructor;
import dev.evilbrainstudio.rcd2obj.codegen.parameter.JavaValueParameter;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementWriteRender;

import java.io.StringWriter;

import dev.evilbrainstudio.rcd2obj.codegen.type.JavaExplicitType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests of new operator.
 *
 * @author Andrey_Yurzanov
 */
class JavaNewOperatorTest {
  private static final String RESULT = "newUnsupportedOperationException();";
  private static final String RESULT_WITH_PARAMS =
    "newUnsupportedOperationException(\"message\");";

  @Test
  void renderTest() {
    JavaNewOperator operator = new JavaNewOperator(
      new JavaClassConstructor()
        .setConstructorType(new JavaExplicitType(UnsupportedOperationException.class))
    );

    StringWriter writer = new StringWriter();
    operator.render(new JavaElementWriteRender(writer));
    Assertions.assertEquals(writer.toString(), RESULT);
  }

  @Test
  void renderTestWithParams() {
    JavaNewOperator operator = new JavaNewOperator(
      new JavaClassConstructor()
        .setConstructorType(new JavaExplicitType(UnsupportedOperationException.class))
        .setConstructorParameters(
          new JavaValueParameter()
            .setParameterOrder(1)
            .setParameterType(new JavaExplicitType(String.class))
            .setParameterValue("message")
        )
    );

    StringWriter writer = new StringWriter();
    operator.render(new JavaElementWriteRender(writer));
    Assertions.assertEquals(writer.toString(), RESULT_WITH_PARAMS);
  }
}