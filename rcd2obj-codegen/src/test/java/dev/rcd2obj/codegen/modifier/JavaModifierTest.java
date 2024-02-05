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

package dev.rcd2obj.codegen.modifier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of {@link JavaModifier}.
 *
 * @author Andrey_Yurzanov
 */
class JavaModifierTest {
  @Test
  void getModifierTest() {
    Assertions.assertEquals(JavaModifier.getModifier(Modifier.PUBLIC), new JavaPublicModifier());
  }

  @Test
  void getModifierExceptionTest() {
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> JavaModifier.getModifier(Modifier.STRICT)
    );
  }
}