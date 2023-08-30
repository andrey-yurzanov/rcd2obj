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

package dev.evilbrainstudio.rcd2obj.codegen.method;

import dev.evilbrainstudio.rcd2obj.codegen.JavaElement;
import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;
import dev.evilbrainstudio.rcd2obj.codegen.modifier.JavaModifier;
import dev.evilbrainstudio.rcd2obj.codegen.modifier.JavaPublicModifier;
import dev.evilbrainstudio.rcd2obj.codegen.parameter.JavaParameter;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;
import dev.evilbrainstudio.rcd2obj.codegen.type.JavaExplicitType;
import dev.evilbrainstudio.rcd2obj.codegen.type.JavaType;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * The method of Java class.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaMethod implements JavaElement, Comparable<JavaMethod> {
  private String methodName;
  private JavaModifier methodAccessModifier;
  private JavaType methodReturnType;
  private Collection<JavaParameter> methodParameters;
  private JavaMethodImpl methodImpl;

  /**
   * Constructs new instance of a method's generator.
   */
  public JavaMethod() {
  }

  /**
   * Constructs new instance of a method's generator.
   *
   * @param method method's meta information
   */
  public JavaMethod(Method method) {
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
        this.methodParameters.add(new JavaParameter(i + 1, parameters[i]));
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
  public JavaMethod(
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
  public JavaMethod setMethodName(String methodName) {
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
  public JavaMethod setMethodAccessModifier(JavaModifier methodAccessModifier) {
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
  public JavaMethod setMethodReturnType(JavaType methodReturnType) {
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
  public JavaMethod setMethodParameters(JavaParameter... methodParameters) {
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
  public JavaMethod setMethodImpl(JavaMethodImpl methodImpl) {
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

  @Override
  public int compareTo(JavaMethod other) {
    int result = methodName.compareTo(other.getMethodName());
    if (result == 0) {
      Collection<JavaParameter> otherMethodParams = other.getMethodParameters();

      result = methodParameters.size() - otherMethodParams.size();
      if (result == 0) {
        Iterator<JavaParameter> paramsIterator = methodParameters.iterator();
        Iterator<JavaParameter> otherParamsIterator = otherMethodParams.iterator();

        while (paramsIterator.hasNext() && result == 0) {
          JavaParameter param = paramsIterator.next();
          JavaParameter otherParam = otherParamsIterator.next();

          result = param.compareTo(otherParam);
        }
      }
    }
    return result;
  }

  @Override
  public void render(JavaElementRender target) {
    target
      .append(JavaElementType.METHOD_BEGIN)
      .append(JavaElementType.METHOD_ACCESS_MODIFIER_BEGIN)
      .append(methodAccessModifier)
      .append(JavaElementType.METHOD_ACCESS_MODIFIER_END)
      .append(JavaElementType.METHOD_RETURN_TYPE, methodReturnType)
      .append(JavaElementType.METHOD_NAME, methodName)
      .append(JavaElementType.METHOD_PARAMS_BLOCK_BEGIN)
      .append(methodParameters, JavaElementType.METHOD_PARAMS_SEPARATOR.toElement())
      .append(JavaElementType.METHOD_PARAMS_BLOCK_END)
      .append(methodImpl)
      .append(JavaElementType.METHOD_END);
  }
}
