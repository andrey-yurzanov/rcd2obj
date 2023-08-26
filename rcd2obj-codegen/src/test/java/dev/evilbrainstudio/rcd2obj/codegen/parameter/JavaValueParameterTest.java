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

package dev.evilbrainstudio.rcd2obj.codegen.parameter;

import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementWriteRender;
import java.io.StringWriter;

import dev.evilbrainstudio.rcd2obj.codegen.type.JavaExplicitType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests of the parameter with a value.
 *
 * @author Andrey_Yurzanov
 */
class JavaValueParameterTest {
  private static final int ORDER = 1;
  private static final String NAME = "testParameter";
  private static final String VALUE = "parameterValue";
  private static final String EXPECTED_WITHOUT_VALUE = "null";
  private static final String EXPECTED_WITH_VALUE = "\"parameterValue\"";

  @Test
  void renderTest() {
    JavaValueParameter parameter = new JavaValueParameter()
        .setParameterName(NAME)
        .setParameterOrder(ORDER)
        .setParameterType(new JavaExplicitType(String.class));

    StringWriter writer = new StringWriter();
    parameter.render(new JavaElementWriteRender(writer));
    Assertions.assertEquals(EXPECTED_WITHOUT_VALUE, writer.toString());

    writer = new StringWriter();
    parameter.setParameterValue(VALUE).render(new JavaElementWriteRender(writer));
    Assertions.assertEquals(EXPECTED_WITH_VALUE, writer.toString());
  }
}