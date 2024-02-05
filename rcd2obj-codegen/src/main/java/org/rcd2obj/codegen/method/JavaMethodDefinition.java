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

package org.rcd2obj.codegen.method;

import org.rcd2obj.codegen.JavaElement;
import org.rcd2obj.codegen.JavaElementRenderingException;
import org.rcd2obj.codegen.JavaElementType;
import org.rcd2obj.codegen.modifier.JavaModifier;
import org.rcd2obj.codegen.operator.JavaArgument;
import org.rcd2obj.codegen.parameter.JavaParameter;
import org.rcd2obj.codegen.render.JavaElementRender;
import org.rcd2obj.codegen.type.JavaExplicitType;
import org.rcd2obj.codegen.type.JavaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * A code generator for method definition generating.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaMethodDefinition implements JavaElement, Comparable<JavaMethodDefinition> {
  private final String methodName;
  private final JavaModifier methodAccessModifier;
  private final JavaType methodReturnType;
  private final Collection<JavaParameter> methodParameters;
  private final JavaMethodImpl methodImpl;

  /**
   * It creates new instance of method's generator. It generates method with void return type and without an access
   * modifier. The generated method will have {@link JavaMethodUnsupportedImpl} implementation.
   *
   * @param methodName       name of the method
   * @param methodParameters parameters of the method
   */
  public JavaMethodDefinition(String methodName, JavaParameter... methodParameters) {
    this(methodName, null, null, Arrays.asList(methodParameters), null);
  }

  /**
   * It creates new instance of method's generator.
   *
   * @param methodName           name of the method, it is required parameter
   * @param methodAccessModifier access modifier of the method
   * @param methodReturnType     return type of the method, if it has null-value, then void will be used
   * @param methodParameters     parameters of the method
   * @param methodImpl           implementation of the method, if it has null-value, then
   *                             {@link JavaMethodUnsupportedImpl} will be used
   */
  public JavaMethodDefinition(
    String methodName,
    JavaModifier methodAccessModifier,
    JavaType methodReturnType,
    Collection<JavaParameter> methodParameters,
    JavaMethodImpl methodImpl
  ) {
    this.methodName = methodName;
    this.methodAccessModifier = methodAccessModifier;

    if (methodReturnType == null) {
      this.methodReturnType = new JavaExplicitType(void.class);
    } else {
      this.methodReturnType = methodReturnType;
    }

    this.methodParameters = new TreeSet<>();
    if (methodParameters != null) {
      this.methodParameters.addAll(methodParameters);
    }

    if (methodImpl == null) {
      this.methodImpl = new JavaMethodUnsupportedImpl();
    } else {
      this.methodImpl = methodImpl;
    }
  }

  /**
   * Returns name of the method.
   *
   * @return method's name
   */
  public String getMethodName() {
    return methodName;
  }

  /**
   * Returns method's access modifier.
   *
   * @return method's access modifier
   */
  public JavaModifier getMethodAccessModifier() {
    return methodAccessModifier;
  }

  /**
   * Returns method's return type.
   *
   * @return method's return type
   */
  public JavaType getMethodReturnType() {
    return methodReturnType;
  }

  /**
   * Returns parameters of the method.
   *
   * @return method's parameters
   */
  public Collection<JavaParameter> getMethodParameters() {
    return Collections.unmodifiableCollection(methodParameters);
  }

  /**
   * Returns an implementation of the method.
   *
   * @return method's implementation
   */
  public JavaMethodImpl getMethodImpl() {
    return methodImpl;
  }

  /**
   * Returns method for invocation.
   *
   * @return method for invocation
   */
  public JavaMethodInvokeOperator invoke(JavaArgument... methodArguments) {
    List<JavaArgument> arguments = new ArrayList<>();
    if (methodArguments != null && methodArguments.length > 0) {
      arguments.addAll(Arrays.asList(methodArguments));
    }

    if (methodParameters.size() == arguments.size()) {
      return new JavaMethodInvokeOperator(this, arguments);
    }
    throw new IllegalArgumentException("The number of arguments does not match the number of parameters!");
  }

  @Override
  public int compareTo(JavaMethodDefinition other) {
    int result = methodName.compareTo(other.getMethodName());
    if (result == 0) {
      int otherSize = 0;
      Collection<JavaParameter> otherParameters = other.getMethodParameters();
      if (otherParameters != null) {
        otherSize = otherParameters.size();
      }

      result = Integer.compare(methodParameters.size(), otherSize);
      if (result == 0 && otherSize > 0) {
        Iterator<JavaParameter> iterator = otherParameters.iterator();

        for (JavaParameter parameter : methodParameters) {
          JavaParameter otherParameter = iterator.next();

          result = parameter.compareTo(otherParameter);
          if (result != 0) {
            break;
          }
        }
      }
    }
    return result;
  }

  @Override
  public void render(JavaElementRender target) throws JavaElementRenderingException {
    if (methodName == null || methodName.trim().isEmpty()) {
      throw new JavaElementRenderingException("Method name has incorrect value: [$]", methodName);
    }

    target
      .append(JavaElementType.METHOD_DEFINITION_BEGIN)
      .append(JavaElementType.METHOD_DEFINITION_ACCESS_MODIFIER_BEGIN)
      .append(methodAccessModifier)
      .append(JavaElementType.METHOD_DEFINITION_ACCESS_MODIFIER_END)
      .append(JavaElementType.METHOD_DEFINITION_RETURN_TYPE)
      .append(methodReturnType)
      .append(JavaElementType.METHOD_DEFINITION_NAME)
      .append(methodName)
      .append(JavaElementType.METHOD_DEFINITION_PARAMS_BLOCK_BEGIN)
      .append(methodParameters, JavaElementType.METHOD_DEFINITION_PARAMS_SEPARATOR.toElement())
      .append(JavaElementType.METHOD_DEFINITION_PARAMS_BLOCK_END)
      .append(methodImpl)
      .append(JavaElementType.METHOD_DEFINITION_END);
  }
}
