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

package dev.rcd2obj.codegen.method;

import dev.rcd2obj.codegen.JavaElement;
import dev.rcd2obj.codegen.JavaElementType;
import dev.rcd2obj.codegen.modifier.JavaModifier;
import dev.rcd2obj.codegen.modifier.JavaPublicModifier;
import dev.rcd2obj.codegen.operator.JavaArgument;
import dev.rcd2obj.codegen.parameter.JavaParameter;
import dev.rcd2obj.codegen.render.JavaElementRender;
import dev.rcd2obj.codegen.type.JavaExplicitType;
import dev.rcd2obj.codegen.type.JavaType;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * A code generator for method definition generating.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaClassMethodDefinition implements JavaElement, Comparable<JavaClassMethodDefinition> {
  private String methodName;
  private JavaModifier methodAccessModifier;
  private JavaType methodReturnType;
  private Collection<JavaParameter> methodParameters;
  private JavaMethodImpl methodImpl;

  /**
   * Constructs new instance of a method's code generator.
   */
  public JavaClassMethodDefinition() {
  }

  /**
   * Constructs new instance of a method's generator.
   *
   * @param method method's meta information
   */
  public JavaClassMethodDefinition(Method method) {
    this(
      method.getName(),
      new JavaPublicModifier(),
      new JavaExplicitType(method.getReturnType()),
      null,
      new JavaMethodUnsupportedImpl()
    );

    Parameter[] parameters = method.getParameters();
    if (parameters.length > 0) {
      this.methodParameters = new TreeSet<>();
      for (int i = 0; i < parameters.length; i++) {
        this.methodParameters.add(
          new JavaParameter(
            i + 1,
            parameters[i].getName(),
            new JavaExplicitType(parameters[i].getType())
          )
        );
      }
    }
  }

  /**
   * Constructs new instance of a method's generator.
   *
   * @param methodName           method's name
   * @param methodAccessModifier method's access modifier
   * @param methodReturnType     method's return type
   * @param methodParameters     method's parameters
   * @param methodImpl           method's implementation
   */
  public JavaClassMethodDefinition(
    String methodName,
    JavaModifier methodAccessModifier,
    JavaType methodReturnType,
    Collection<JavaParameter> methodParameters,
    JavaMethodImpl methodImpl
  ) {
    this.methodName = methodName;
    this.methodAccessModifier = methodAccessModifier;
    this.methodReturnType = methodReturnType;
    this.methodParameters = methodParameters;
    this.methodImpl = methodImpl;
  }

  /**
   * Sets name of the method.
   *
   * @param methodName name of the method
   * @return current method's instance
   */
  public JavaClassMethodDefinition setMethodName(String methodName) {
    this.methodName = methodName;
    return this;
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
   * Sets access modifier of the method.
   *
   * @param methodAccessModifier method's access modfier
   * @return current method's instance
   */
  public JavaClassMethodDefinition setMethodAccessModifier(JavaModifier methodAccessModifier) {
    this.methodAccessModifier = methodAccessModifier;
    return this;
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
   * Sets return type of the method.
   *
   * @param methodReturnType method's return type
   * @return current method's instance
   */
  public JavaClassMethodDefinition setMethodReturnType(JavaType methodReturnType) {
    this.methodReturnType = methodReturnType;
    return this;
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
   * Sets parameters of the method.
   *
   * @param methodParameters method's parameters
   * @return current method's instance
   */
  public JavaClassMethodDefinition setMethodParameters(JavaParameter... methodParameters) {
    if (this.methodParameters == null) {
      this.methodParameters = new TreeSet<>();
    }
    this.methodParameters.addAll(Arrays.asList(methodParameters));

    return this;
  }

  /**
   * Returns parameters of the method.
   *
   * @return method's parameters
   */
  public Collection<JavaParameter> getMethodParameters() {
    return methodParameters;
  }

  /**
   * Sets an implementation of the method.
   *
   * @param methodImpl method's implementation
   * @return current method's instance
   */
  public JavaClassMethodDefinition setMethodImpl(JavaMethodImpl methodImpl) {
    this.methodImpl = methodImpl;
    return this;
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
  public JavaClassMethodInvokeOperator getMethod(JavaArgument... methodArguments) {
    int size = 0;
    if (methodParameters != null) {
      size = methodParameters.size();
    }

    List<JavaArgument> arguments = new ArrayList<>();
    if (methodArguments != null) {
      arguments.addAll(Arrays.asList(methodArguments));
    }

    if (size == arguments.size()) {
      return new JavaClassMethodInvokeOperator(this, arguments);
    }
    throw new IllegalArgumentException("The number of arguments does not match the number of parameters!");
  }

  @Override
  public int compareTo(JavaClassMethodDefinition other) {
    int result = methodName.compareTo(other.getMethodName());
    if (result == 0) {
      int size = 0;
      if (methodParameters != null) {
        size = methodParameters.size();
      }

      int otherSize = 0;
      Collection<JavaParameter> otherParameters = other.getMethodParameters();
      if (otherParameters != null) {
        otherSize = otherParameters.size();
      }

      result = Integer.compare(size, otherSize);
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
  public void render(JavaElementRender target) {
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
