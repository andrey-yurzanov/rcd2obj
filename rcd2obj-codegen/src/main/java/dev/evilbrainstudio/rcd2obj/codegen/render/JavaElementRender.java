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

import dev.evilbrainstudio.rcd2obj.codegen.JavaElement;
import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Base abstraction for the Java elements rendering.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public interface JavaElementRender {
  /**
   * Java core package.
   */
  String LANG_PACKAGE_NAME = "java.lang";

  /**
   * Appends the Java element to the render.
   *
   * @param type     element's type {@link JavaElementType}
   * @param elements some literals for the element rendering
   * @return current instance
   */
  JavaElementRender append(JavaElementType type, String... elements);

  /**
   * Appends the Java element to the render.
   *
   * @param type    element's type {@link JavaElementType}
   * @param element the Java class for the element rendering
   * @return current instance
   */
  default JavaElementRender append(JavaElementType type, Class<?> element) {
    Package elementPackage = element.getPackage();
    if (elementPackage == null || LANG_PACKAGE_NAME.equals(elementPackage.getName())) {
      return append(type, element.getSimpleName());
    }
    return append(type, element.getCanonicalName());
  }

  /**
   * Appends the Java element to the render.
   *
   * @param element some element for rendering
   * @return current instance
   */
  default JavaElementRender append(JavaElement element) {
    if (element != null) {
      element.render(this);
    }
    return this;
  }

  /**
   * Appends the Java element to the render.
   *
   * @param elements  the collection of some elements for rendering
   * @param separator the separator for rendering, appends after each element, except of the last
   *                  element
   * @return current instance
   */
  default JavaElementRender append(Collection<? extends JavaElement> elements, JavaElement separator) {
    if (elements != null && !elements.isEmpty()) {
      Iterator<? extends JavaElement> iterator = elements.iterator();
      while (iterator.hasNext()) {
        iterator
          .next()
          .render(this);

        if (iterator.hasNext() && separator != null) {
          separator.render(this);
        }
      }
    }
    return this;
  }

  /**
   * Appends the typed element to the render.
   *
   * @param type      element's type {@link JavaElementType}
   * @param value     the value for rendering
   * @param valueType the type of the value
   * @return current instance
   */
  default JavaElementRender append(JavaElementType type, Object value, Class<?> valueType) {
    if (value != null) {
      Map<Class<?>, JavaElementRender> renders = getTypeRenders();
      for (Entry<Class<?>, JavaElementRender> entry : renders.entrySet()) {
        Class<?> renderType = entry.getKey();
        if (renderType.isAssignableFrom(valueType)) {
          JavaElementRender render = entry.getValue();
          return render.append(type, String.valueOf(value));
        }
      }
    }

    return append(type, String.valueOf(value));
  }

  /**
   * The method returns renders for specific types rendering.
   *
   * @return renders for specific types
   */
  default Map<Class<?>, JavaElementRender> getTypeRenders() {
    return Collections.emptyMap();
  }
}
