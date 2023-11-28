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

package dev.evilbrainstudio.rcd2obj;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Logger;

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
    Logger logger = Logger
      .getLogger("checkGeneratedClassTest");

    logger.info(ROOT_DIRECTORY);
    logger.info(String.valueOf(Arrays.asList(new File(ROOT_DIRECTORY).listFiles())));
    logger.info(
      String.valueOf(
        Paths.get(
          ROOT_DIRECTORY,
          "target/classes/dev/evilbrainstudio/rcd2obj"
        )
      )
    );
    logger.info(
      String.valueOf(
        Files.exists(
          Paths.get(
            ROOT_DIRECTORY,
            "target/classes/dev/evilbrainstudio/rcd2obj"
          ))
      )
    );


    Assertions.assertTrue(
      Files.exists(
        Paths.get(
          ROOT_DIRECTORY,
          "target/classes/dev/evilbrainstudio/rcd2obj/TestEntityStubMapper.class"
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
          "target/generated-sources/annotations/dev/evilbrainstudio/rcd2obj/TestEntityStubMapper.java"
        )
      )
    );
  }
}