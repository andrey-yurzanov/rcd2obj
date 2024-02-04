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

package dev.evilbrainstudio.rcd2obj.annotation.naming;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests of {@link PointCombineStrategy}.
 *
 * @author Andrey_Yurzanov
 */
class PointCombineStrategyTest {
  private final PointCombineStrategy strategy;
  private final Map<String[], String> names;

  public PointCombineStrategyTest() {
    strategy = new PointCombineStrategy();
    names = new HashMap<>();
    names.put(new String[]{}, "");
    names.put(new String[]{"test_name"}, "test_name");
    names.put(new String[]{"TestName", "test"}, "TestName.test");
  }

  @Test
  void resolveTest() {
    Assertions.assertThrows(
        NullPointerException.class,
        () -> strategy.resolve(null)
    );

    for (Entry<String[], String> entry : names.entrySet()) {
      Assertions.assertEquals(
          strategy.resolve(entry.getKey()),
          entry.getValue()
      );
    }
  }
}