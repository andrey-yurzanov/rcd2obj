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

package org.rcd2obj.codegen.modifier;

import org.rcd2obj.codegen.JavaElement;

import java.lang.reflect.Modifier;

/**
 * Basic abstraction for Java modifiers, such as public, private, static, final etc.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public interface JavaModifier extends JavaElement {
  /**
   * Returns modifier by modifier's code: {@link Modifier}.
   *
   * @param modifier modifier's code
   * @return found modifier
   * @throws IllegalArgumentException throws if modifier is not found by code
   */
  static JavaModifier getModifier(int modifier) throws IllegalArgumentException {
    if (Modifier.isPublic(modifier)) {
      return new JavaPublicModifier();
    }
    throw new IllegalArgumentException(
      String.join(
        "",
        "Unsupported modifier: [", String.valueOf(modifier), "]"
      )
    );
  }
}
