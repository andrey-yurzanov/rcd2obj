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

package org.rcd2obj.codegen.method;

import org.rcd2obj.codegen.JavaElementRenderingException;
import org.rcd2obj.codegen.operator.JavaNullArgument;
import org.rcd2obj.codegen.parameter.JavaParameter;
import org.rcd2obj.codegen.render.JavaElementWriteRender;
import org.rcd2obj.codegen.type.JavaExplicitType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;

/**
 * Tests of {@link JavaMethodInvokeOperator}.
 *
 * @author Andrey_Yurzanov
 */
class JavaMethodInvokeOperatorTest {
  private static final String METHOD_NAME = "renderTest";
  private static final String PARAM_NAME_1 = "name";
  private static final String PARAM_NAME_2 = "age";
  private static final String WITHOUT_PARAMS_EXPECTED = "renderTest()";
  private static final String SINGLE_PARAM_EXPECTED = "renderTest(null)";
  private static final String MULTI_PARAMS_EXPECTED = "renderTest(null,null)";

  @Test
  void renderExceptionTest() {
    JavaElementWriteRender render = new JavaElementWriteRender(new StringWriter());

    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> new JavaMethodInvokeOperator(new JavaMethodDefinition(null), Collections.emptyList())
        .render(render)
    );

    Assertions.assertThrows(
      JavaElementRenderingException.class,
      () -> new JavaMethodInvokeOperator(null, Collections.emptyList()).render(render)
    );
  }

  @Test
  void renderWithoutParamsTest() {
    JavaMethodInvokeOperator operator = new JavaMethodInvokeOperator(
      new JavaMethodDefinition(METHOD_NAME),
      Collections.emptyList()
    );

    StringWriter writer = new StringWriter();
    operator.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(WITHOUT_PARAMS_EXPECTED, writer.toString());
  }

  @Test
  void renderSingleParamTest() {
    JavaMethodInvokeOperator operator = new JavaMethodInvokeOperator(
      new JavaMethodDefinition(
        METHOD_NAME,
        new JavaParameter(1, PARAM_NAME_1, new JavaExplicitType(String.class))
      ),
      Collections.singletonList(new JavaNullArgument())
    );

    StringWriter writer = new StringWriter();
    operator.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(SINGLE_PARAM_EXPECTED, writer.toString());
  }

  @Test
  void renderMultiParamTest() {
    JavaMethodInvokeOperator operator = new JavaMethodInvokeOperator(
      new JavaMethodDefinition(
        METHOD_NAME,
        new JavaParameter(1, PARAM_NAME_1, new JavaExplicitType(String.class)),
        new JavaParameter(2, PARAM_NAME_2, new JavaExplicitType(Integer.class))
      ),
      Arrays.asList(new JavaNullArgument(), new JavaNullArgument())
    );

    StringWriter writer = new StringWriter();
    operator.render(new JavaElementWriteRender(writer));

    Assertions.assertEquals(MULTI_PARAMS_EXPECTED, writer.toString());
  }
}