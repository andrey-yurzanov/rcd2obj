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

package org.rcd2obj.codegen.type;

import org.rcd2obj.codegen.JavaElementRenderingException;
import org.rcd2obj.codegen.render.JavaElementRender;

/**
 * Simple type of the Java, renders any specified name.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaNameType implements JavaType {
  private final String name;

  /**
   * Constructs new instance of type.
   *
   * @param name type's name
   */
  public JavaNameType(String name) {
    this.name = name;
  }

  /**
   * Returns type's name
   *
   * @return type's name
   */
  public String getName() {
    return name;
  }

  @Override
  public void render(JavaElementRender target) throws JavaElementRenderingException {
    if (name == null || name.trim().isEmpty()) {
      throw new JavaElementRenderingException("Type name has incorrect value: [$]!", name);
    }

    target.append(name);
  }

  @Override
  public int compareTo(JavaType type) {
    int result = -1;
    if (type instanceof JavaNameType) {
      result = name.compareTo(((JavaNameType) type).getName());
    }
    return result;
  }
}
