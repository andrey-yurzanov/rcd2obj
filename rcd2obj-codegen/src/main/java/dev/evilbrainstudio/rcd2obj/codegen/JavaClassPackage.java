/*
 * Copyright 2023 Andrey Yurzanov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.evilbrainstudio.rcd2obj.codegen;

import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;

/**
 * Java package. Renders by template 'package[name];'.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaClassPackage implements JavaElement {
  private final String name;

  /**
   * Constructs of new instance Java package.
   *
   * @param name package name
   */
  public JavaClassPackage(String name) {
    this.name = name;
  }

  @Override
  public void render(JavaElementRender target) {
    target
      .append(JavaElementType.PACKAGE_BEGIN)
      .append(JavaElementType.PACKAGE_KEYWORD)
      .append(JavaElementType.PACKAGE_NAME)
      .append(name)
      .append(JavaElementType.END_EXPRESSION_OPERATOR)
      .append(JavaElementType.PACKAGE_END);
  }
}
