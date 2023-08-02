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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests of the parameter.
 *
 * @author Andrey_Yurzanov
 */
class JavaParameterTest {
  private static final int ORDER = 1;
  private static final String NAME = "testParameter";
  private static final String EXPECTED_WITHOUT_TYPE = "java.lang.ObjecttestParameter";
  private static final String EXPECTED_WITH_TYPE = "java.lang.StringtestParameter";

  @Test
  void render() {
    JavaParameter parameter = new JavaParameter()
        .setParameterName(NAME)
        .setParameterOrder(ORDER);

    StringWriter writer = new StringWriter();
    parameter.render(new JavaElementWriteRender(writer));
    Assertions.assertEquals(EXPECTED_WITHOUT_TYPE, writer.toString());

    writer = new StringWriter();
    parameter.setParameterType(String.class).render(new JavaElementWriteRender(writer));
    Assertions.assertEquals(EXPECTED_WITH_TYPE, writer.toString());
  }
}