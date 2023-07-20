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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests of the parameters' comparator.
 *
 * @author Andrey_Yurzanov
 */
class JavaParameterComparatorTest {
  private static final int FIRST = 1;
  private static final int SECOND = 2;

  @Test
  void compareTest() {
    JavaParameterComparator<JavaParameter> comparator = new JavaParameterComparator<>();
    Assertions.assertEquals(
        comparator.compare(new JavaParameter(FIRST, ""), new JavaParameter(FIRST, "")),
        0
    );
    Assertions.assertTrue(
        comparator.compare(new JavaParameter(SECOND, ""), new JavaParameter(FIRST, "")) > 0
    );
    Assertions.assertTrue(
        comparator.compare(new JavaParameter(FIRST, ""), new JavaParameter(SECOND, "")) < 0
    );
  }
}