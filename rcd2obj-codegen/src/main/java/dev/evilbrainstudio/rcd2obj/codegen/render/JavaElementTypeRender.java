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

package dev.evilbrainstudio.rcd2obj.codegen.render;

import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;
import java.util.HashMap;
import java.util.Map;

/**
 * The abstract renderer realizes specific types rendering.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public abstract class JavaElementTypeRender implements JavaElementRender {
  protected final Map<Class<?>, JavaElementRender> typeRenders;

  /**
   * Double quote for strings building.
   */
  protected static final String DOUBLE_QUOTE = "\"";

  /**
   * Constructs of new instance of the type renderer.
   */
  protected JavaElementTypeRender() {
    this.typeRenders = new HashMap<>();
    this.typeRenders.put(CharSequence.class, this::appendString);
  }

  @Override
  public Map<Class<?>, JavaElementRender> getTypeRenders() {
    return typeRenders;
  }

  // Appends strings
  private JavaElementRender appendString(JavaElementType type, String... elements) {
    StringBuilder buffer = new StringBuilder(DOUBLE_QUOTE);
    for (String element : elements) {
      buffer.append(element);
    }
    buffer.append(DOUBLE_QUOTE);

    return append(type, buffer.toString());
  }
}