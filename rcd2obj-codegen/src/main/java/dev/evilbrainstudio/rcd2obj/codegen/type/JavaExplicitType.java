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

package dev.evilbrainstudio.rcd2obj.codegen.type;

import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;

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

  @Override
  public void render(JavaElementRender target) {
    target.append(JavaElementType.EMPTY_LITERAL, type);
  }

  /**
   * Returns the class for rendering.
   *
   * @return the class for rendering
   */
  public Class<?> getType() {
    return type;
  }
}
