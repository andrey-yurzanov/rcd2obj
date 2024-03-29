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

package org.rcd2obj.codegen.inherited;

import org.rcd2obj.codegen.JavaElement;
import org.rcd2obj.codegen.JavaElementRenderingException;
import org.rcd2obj.codegen.JavaElementType;
import org.rcd2obj.codegen.method.JavaMethodDefinition;
import org.rcd2obj.codegen.method.JavaMethodUnsupportedImpl;
import org.rcd2obj.codegen.modifier.JavaModifier;
import org.rcd2obj.codegen.parameter.JavaParameter;
import org.rcd2obj.codegen.render.JavaElementRender;
import org.rcd2obj.codegen.type.JavaExplicitType;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * The base inheritable element of Java language. Can be the class or the interface.
 * <pre>
 *   Example:
 *   {@code
 *   JavaInheritableElement inh = new JavaInheritableElement(Comparable.class);
 *   JavaClass cls = new JavaClass("MyClass", null, null, Arrays.asList(inh), null, null); // implements
 *   cls.render(...)
 *   }
 *   Result:
 *   {@code
 *   class MyClass implements Comparable {}
 *   }
 * </pre>
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaInheritableElement implements JavaElement, Comparable<JavaInheritableElement> {
  private final Class<?> type;

  /**
   * Constructs new instance.
   *
   * @param type type of inheritable element
   */
  public JavaInheritableElement(Class<?> type) {
    this.type = type;
  }

  /**
   * Checks the inherited type, interface or class.
   *
   * @return true, if type is the interface, false is the class
   */
  public boolean isInterface() {
    return type.isInterface();
  }

  /**
   * Returns type of the inherited type.
   *
   * @return type of the inherited type
   */
  public Class<?> getType() {
    return type;
  }

  /**
   * Returns inherited methods.
   *
   * @return inherited methods
   */
  public Collection<JavaMethodDefinition> getInheritedMethods() {
    Method[] typeMethods = type.getMethods();
    if (typeMethods.length > 0) {
      List<JavaMethodDefinition> methods = new ArrayList<>();
      for (Method method : type.getMethods()) {
        if (!isDefinedInObject(method) && canOverride(method)) {
          Set<JavaParameter> methodParameters = new TreeSet<>();
          Parameter[] parameters = method.getParameters();
          if (parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
              methodParameters.add(
                new JavaParameter(
                  i + 1,
                  parameters[i].getName(),
                  new JavaExplicitType(parameters[i].getType())
                )
              );
            }
          }

          methods.add(
            new JavaMethodDefinition(
              method.getName(),
              JavaModifier.getModifier(method.getModifiers()),
              new JavaExplicitType(method.getReturnType()),
              methodParameters,
              new JavaMethodUnsupportedImpl()
            )
          );
        }
      }
      return methods;
    }
    return Collections.emptyList();
  }

  @Override
  public int compareTo(JavaInheritableElement other) {
    String name = type.getCanonicalName();
    return name.compareTo(other.getType().getCanonicalName());
  }

  @Override
  public void render(JavaElementRender target) throws JavaElementRenderingException {
    if (type == null) {
      throw new JavaElementRenderingException("Inherited type has incorrect value: [$]!", type);
    }

    target
      .append(JavaElementType.INHERITED_ELEMENT_BEGIN)
      .append(JavaElementType.INHERITED_ELEMENT_TYPE)
      .append(type)
      .append(JavaElementType.INHERITED_ELEMENT_END);
  }

  private boolean canOverride(Method method) {
    boolean isAbstract = Modifier.isAbstract(method.getModifiers());
    return (type.isInterface() && !method.isDefault()) && isAbstract;
  }

  private boolean isDefinedInObject(Method method) {
    String name = method.getName();
    Class<?>[] parameters = method.getParameterTypes();

    boolean result = false;
    for (Method declaredMethod : Object.class.getDeclaredMethods()) {
      String declaredName = declaredMethod.getName();
      Class<?>[] declaredParameters = declaredMethod.getParameterTypes();

      if (declaredName.equals(name) && Arrays.equals(declaredParameters, parameters)) {
        result = true;
        break;
      }
    }

    return result;
  }
}
