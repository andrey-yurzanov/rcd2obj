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

package org.rcd2obj.codegen;

import org.rcd2obj.codegen.render.JavaElementRender;

/**
 * A renderer of Java package. It renders code 'package ...', where '...' is name of the package.
 * <pre>
 *   Example:
 *   {@code
 *   JavaPackage pkg = new JavaPackage("org.rcd2obj.codegen");
 *   pkg.render(...);
 *   }
 *
 *   Result:
 *   {@code
 *   package org.rcd2obj.codegen
 *   }
 * </pre>
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaPackage implements JavaElement {
  private final String name;

  /**
   * Constructs of new instance Java package.
   *
   * @param name package name
   */
  public JavaPackage(String name) {
    this.name = name;
  }

  @Override
  public void render(JavaElementRender target) {
    if (name == null || name.trim().isEmpty()) {
      throw new JavaElementRenderingException("The package name has incorrect value: [$]!", name);
    }

    target
      .append(JavaElementType.PACKAGE_BEGIN)
      .append(JavaElementType.PACKAGE_KEYWORD)
      .append(JavaElementType.PACKAGE_NAME)
      .append(name)
      .append(JavaElementType.PACKAGE_END);
  }
}
