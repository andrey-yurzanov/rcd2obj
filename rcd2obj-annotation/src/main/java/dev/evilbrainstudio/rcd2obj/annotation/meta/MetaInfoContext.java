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

package dev.evilbrainstudio.rcd2obj.annotation.meta;

import java.util.HashMap;
import java.util.Map;

/**
 * The context of meta-information extracting.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class MetaInfoContext {
  private final Map<Object, Object> variables = new HashMap<>();

  /**
   * Returns a variable by type. If the variable does not exist, it will be created using the
   * default constructor.
   *
   * @param <T>  the class of the type
   * @param type the type of variable
   * @return the variable by type
   */
  public <T> T get(Class<T> type) {
    Object value = variables.get(type);
    if (value == null) {
      try {
        value = type.newInstance();
        variables.put(type, value);
      } catch (Exception exception) {
        throw new RuntimeException(exception);
      }
    }
    return type.cast(value);
  }
}
