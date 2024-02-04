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

package dev.evilbrainstudio.rcd2obj.codegen.render;

import dev.evilbrainstudio.rcd2obj.codegen.JavaElement;
import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;

import java.util.Collection;
import java.util.Iterator;

/**
 * Base abstraction for the Java elements rendering.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public interface JavaElementRender {
  /**
   * Appends the Java elements to the render.
   *
   * @param elements some literals for the element rendering
   * @return current instance
   */
  JavaElementRender append(String... elements);

  /**
   * Appends the Java class to the render.
   *
   * @param classType the Java class for the element rendering
   * @return current instance
   */
  JavaElementRender append(Class<?> classType);

  /**
   * Appends the typed element to the render.
   *
   * @param value     the value for rendering
   * @param valueType the type of the value
   * @return current instance
   */
  JavaElementRender append(Object value, Class<?> valueType);

  /**
   * Appends the Java element type to the render.
   *
   * @param type element's type {@link JavaElementType}
   * @return current instance
   */
  default JavaElementRender append(JavaElementType type) {
    return append(type.getValue());
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
   * Appends the elements collection to the render.
   *
   * @param elements  the collection of some elements for rendering
   * @param separator the separator for rendering, appends after each element, except of the last element
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
   * Appends the elements collection to the render.
   *
   * @param elements the collection of some elements for rendering
   * @return current instance
   */
  default JavaElementRender append(Collection<? extends JavaElement> elements) {
    return append(elements, (JavaElement) null);
  }
}
