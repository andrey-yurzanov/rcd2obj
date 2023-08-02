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

package dev.evilbrainstudio.rcd2obj.codegen;

import dev.evilbrainstudio.rcd2obj.codegen.method.JavaMethod;
import dev.evilbrainstudio.rcd2obj.codegen.parameter.JavaGenericParameter;
import dev.evilbrainstudio.rcd2obj.codegen.parameter.JavaParameter;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;

/**
 * Base inheritable element of Java language. Can be the class or the interface.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaInheritableElement implements JavaElement {
  private final Class<?> type;
  private final Map<String, JavaGenericParameter> genericTypes;

  /**
   * Constructs new instance.
   *
   * @param type type of inheritable element
   */
  public JavaInheritableElement(Class<?> type) {
    this.type = type;
    this.genericTypes = new LinkedHashMap<>();

    TypeVariable<? extends Class<?>>[] types = type.getTypeParameters();
    for (int i = 0; i < types.length; i++) {
      String typeName = types[i].getTypeName();
      genericTypes.put(typeName, new JavaGenericParameter(i, typeName));
    }
  }

  /**
   * Returns generic type by name of the type.
   *
   * @param typeName name of the type
   * @return generic type
   */
  public JavaGenericParameter getGenericType(String typeName) {
    return genericTypes.get(typeName);
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
  public Collection<JavaMethod> getInheritedMethods() {
    Method[] typeMethods = type.getMethods();
    if (typeMethods.length > 0) {
      List<JavaMethod> methods = new ArrayList<>();
      for (Method method : type.getMethods()) {
        if (!isDefinedInObject(method) && canOverride(method)) {
          JavaMethod javaMethod = new JavaMethod(method);
          for (JavaParameter parameter : javaMethod.getMethodParameters()) {
            AnnotatedType annotatedType = parameter.getAnnotatedType();
            if (annotatedType != null) {
              Type typeValue = annotatedType.getType();
              JavaGenericParameter genericParameter = genericTypes.get(typeValue.getTypeName());
              if (genericParameter != null) {
                parameter.setParameterType(genericParameter.getParameterType());
              }
            }
          }
          methods.add(javaMethod);
        }
      }
      return methods;
    }
    return Collections.emptyList();
  }

  @Override
  public void render(JavaElementRender target) {
    target
      .append(JavaElementType.INHERITED_ELEMENT_BEGIN)
      .append(JavaElementType.INHERITED_ELEMENT_TYPE, type);

    if (!genericTypes.isEmpty()) {
      target.append(JavaElementType.GENERIC_PARAMS_BLOCK_BEGIN);

      Iterator<JavaGenericParameter> iterator = genericTypes.values().iterator();
      while (iterator.hasNext()) {
        iterator
          .next()
          .render(target);

        if (iterator.hasNext()) {
          target.append(JavaElementType.GENERIC_PARAMS_SEPARATOR);
        }
      }
      target.append(JavaElementType.GENERIC_PARAMS_BLOCK_END);
    }
    target.append(JavaElementType.INHERITED_ELEMENT_END);
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
