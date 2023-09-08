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
import dev.evilbrainstudio.rcd2obj.codegen.JavaImport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * The renderer of the class accumulates types to construct an import block.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaClassBufferRender extends JavaElementTypeRender {
  private final Map<String, Class<?>> imports;
  private final Collection<JavaElement> buffer;

  /**
   * Constructs new instance of renderer.
   */
  public JavaClassBufferRender() {
    super();
    this.imports = new HashMap<>();
    this.buffer = new ArrayList<>();
  }

  /**
   * Returns the accumulated import.
   *
   * @return accumulated import
   */
  public Collection<JavaImport> getImports() {
    if (!imports.isEmpty()) {
      return imports
        .values()
        .stream()
        .map(JavaImport::new)
        .collect(Collectors.toCollection(TreeSet::new));
    }
    return Collections.emptySet();
  }

  /**
   * Returns a buffer of all appended elements.
   *
   * @return buffer of all appended elements
   */
  public Collection<? extends JavaElement> getBuffer() {
    return buffer;
  }

  @Override
  public JavaElementRender append(String... elements) {
    buffer.add(target -> target.append(elements));
    return this;
  }

  @Override
  public JavaElementRender append(JavaElement element) {
    element.render(this);
    return this;
  }

  @Override
  public JavaElementRender append(JavaElementType type) {
    buffer.add(type.toElement());
    return this;
  }

  @Override
  public JavaElementRender append(Object value, Class<?> valueType) {
    buffer.add(target -> target.append(value, valueType));
    return this;
  }

  @Override
  public JavaElementRender append(Class<?> element) {
    Package elementPackage = element.getPackage();
    if (elementPackage != null && !LANG_PACKAGE_NAME.equals(elementPackage.getName())) {
      String name = element.getSimpleName();
      Class<?> importClass = imports.get(name);
      if (importClass != null) {
        if (importClass.equals(element)) {
          return append(name);
        }
      } else {
        imports.put(name, element);
        return append(name);
      }
    }
    return super.append(element);
  }
}
