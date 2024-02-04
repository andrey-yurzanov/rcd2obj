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

package dev.rcd2obj.codegen.render;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * The abstract renderer realizes specific types rendering.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public abstract class JavaElementTypeRender implements JavaElementRender {
  protected final Map<Class<?>, BiConsumer<Object, JavaElementRender>> typeRenders;

  /**
   * Double quote for strings building.
   */
  protected static final String DOUBLE_QUOTE = "\"";

  /**
   * Java core package.
   */
  protected static final String LANG_PACKAGE_NAME = "java.lang";

  /**
   * Constructs of new instance of the type renderer.
   */
  protected JavaElementTypeRender() {
    this.typeRenders = new HashMap<>();
    this.typeRenders.put(CharSequence.class, this::appendString);
  }

  @Override
  public JavaElementRender append(Class<?> classType) {
    Package elementPackage = classType.getPackage();
    if (elementPackage == null || LANG_PACKAGE_NAME.equals(elementPackage.getName())) {
      return append(classType.getSimpleName());
    }
    return append(classType.getCanonicalName());
  }

  @Override
  public JavaElementRender append(Object value, Class<?> valueType) {
    if (value != null) {
      Map<Class<?>, BiConsumer<Object, JavaElementRender>> renders = getTypeRenders();
      for (Map.Entry<Class<?>, BiConsumer<Object, JavaElementRender>> entry : renders.entrySet()) {
        Class<?> renderType = entry.getKey();
        if (renderType.isAssignableFrom(valueType)) {
          BiConsumer<Object, JavaElementRender> render = entry.getValue();
          render.accept(value, this);

          return this;
        }
      }
    }

    return append(String.valueOf(value));
  }

  /**
   * Returns registered types renderers.
   *
   * @return registered types renderers
   */
  public Map<Class<?>, BiConsumer<Object, JavaElementRender>> getTypeRenders() {
    return typeRenders;
  }

  // Appends string
  private void appendString(Object value, JavaElementRender target) {
    if (value != null) {
      target
        .append(DOUBLE_QUOTE)
        .append(String.valueOf(value))
        .append(DOUBLE_QUOTE);
    } else {
      target.append(String.valueOf(value));
    }
  }
}