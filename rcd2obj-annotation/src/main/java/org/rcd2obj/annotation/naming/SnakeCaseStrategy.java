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

package org.rcd2obj.annotation.naming;

/**
 * Implementation of {@link NamingStrategy} for a data extraction. The name represents in the form
 * of snake_case.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class SnakeCaseStrategy implements NamingStrategy {
  private static final char SEPARATOR = '_';

  @Override
  public String resolve(String... original) {
    boolean match = true;

    String name = String.join(Character.toString(SEPARATOR), original);
    StringBuilder buffer = new StringBuilder();
    char[] symbols = name.toCharArray();
    for (int i = 0; i < symbols.length; i++) {
      boolean isUpper = Character.isUpperCase(symbols[i]);
      if (match && SEPARATOR != symbols[i]) {
        match = isUpper;
      }

      if (isUpper && i > 0) {
        buffer.append(SEPARATOR);
      }
      buffer.append(Character.toLowerCase(symbols[i]));
    }

    if (match) {
      return name;
    }
    return buffer.toString();
  }
}
