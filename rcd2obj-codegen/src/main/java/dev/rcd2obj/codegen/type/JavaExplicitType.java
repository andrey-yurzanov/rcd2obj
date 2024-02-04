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

package dev.rcd2obj.codegen.type;

import dev.rcd2obj.codegen.JavaElementRenderingException;
import dev.rcd2obj.codegen.render.JavaElementRender;

/**
 * Explicit type of Java. Renders name of the class.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaExplicitType implements JavaType {
  private final Class<?> type;

  /**
   * Constructs new instance of Java type.
   *
   * @param type a class for rendering
   */
  public JavaExplicitType(Class<?> type) {
    this.type = type;
  }

  /**
   * Returns the class for rendering.
   *
   * @return the class for rendering
   */
  public Class<?> getType() {
    return type;
  }

  @Override
  public int compareTo(JavaType other) {
    int result = -1;
    if (other instanceof JavaExplicitType) {
      String name = type.getCanonicalName();
      Class<?> otherType = ((JavaExplicitType) other).getType();
      result = name.compareTo(otherType.getCanonicalName());
    }
    return result;
  }

  @Override
  public void render(JavaElementRender target) throws JavaElementRenderingException {
    if (type == null) {
      throw new JavaElementRenderingException("Type has incorrect value: [$]!", type);
    }

    target.append(type);
  }
}
