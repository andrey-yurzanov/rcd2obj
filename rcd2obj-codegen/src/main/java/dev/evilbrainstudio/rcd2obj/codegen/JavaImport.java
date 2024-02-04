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

package dev.evilbrainstudio.rcd2obj.codegen;

import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;

/**
 * Import's renderer of the Java language.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaImport implements JavaElement, Comparable<JavaImport> {
  private final Class<?> type;

  /**
   * Constructs new instance of import's renderer.
   *
   * @param type type for import operator
   */
  public JavaImport(Class<?> type) {
    this.type = type;
  }

  /**
   * Returns type for import operator.
   *
   * @return type for import operator
   */
  public Class<?> getType() {
    return type;
  }

  @Override
  public int compareTo(JavaImport other) {
    String name = type.getCanonicalName();

    Class<?> otherType = other.getType();
    return name.compareTo(otherType.getCanonicalName());
  }

  @Override
  public void render(JavaElementRender target) throws JavaElementRenderingException {
    if (type == null) {
      throw new JavaElementRenderingException("Import type has incorrect value: [$]!", type);
    }

    target
      .append(JavaElementType.IMPORT_BEGIN)
      .append(JavaElementType.IMPORT_KEYWORD)
      .append(JavaElementType.IMPORT_TYPE)
      .append(type)
      .append(JavaElementType.END_EXPRESSION_OPERATOR)
      .append(JavaElementType.IMPORT_END);
  }
}
