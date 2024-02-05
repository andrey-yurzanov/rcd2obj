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

package org.rcd2obj;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Tests of TestEntity class.
 *
 * @author Andrey_Yurzanov
 */
class TestEntityTest {
  private static final String ROOT_DIRECTORY = FileSystems
    .getDefault()
    .getPath("")
    .toAbsolutePath()
    .toString();

  /**
   * Checks generated class.
   */
  @Test
  void checkGeneratedClassTest() {
    Assertions.assertTrue(
      Files.exists(
        Paths.get(
          ROOT_DIRECTORY,
          "target/classes/org/rcd2obj/TestEntityStubMapper.class"
        )
      )
    );
  }

  /**
   * Checks generated source code.
   */
  @Test
  void checkGeneratedCodeTest() {
    Assertions.assertTrue(
      Files.exists(
        Paths.get(
          ROOT_DIRECTORY,
          "target/generated-sources/annotations/org/rcd2obj/TestEntityStubMapper.java"
        )
      )
    );
  }
}