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
import dev.evilbrainstudio.rcd2obj.codegen.parameter.JavaParameter;
import dev.evilbrainstudio.rcd2obj.codegen.parameter.JavaParameterComparator;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * The method of Java class.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaMethod implements JavaElement {
  private String methodName;
  private JavaModifier methodAccessModifier;
  private Class<?> methodReturnType = void.class;
  private Set<JavaParameter> methodParameters;
  private JavaMethodImpl methodImpl = new JavaMethodUnsupportedImpl();

  /**
   * Constructs new instance of a method's generator.
   *
   * @param methodName name of the method
   */
  public JavaMethod(String methodName) {
    this.methodName = methodName;
  }

  /**
   * Sets name of the method.
   *
   * @param methodName name of the method
   * @return current method's instance
   */
  public JavaMethod methodName(String methodName) {
    this.methodName = methodName;
    return this;
  }

  /**
   * Returns name of the method.
   *
   * @return method's name
   */
  public String methodName() {
    return methodName;
  }

  /**
   * Sets access modifier of the method.
   *
   * @param methodAccessModifier method's access modfier
   * @return current method's instance
   */
  public JavaMethod methodAccessModifier(JavaModifier methodAccessModifier) {
    this.methodAccessModifier = methodAccessModifier;
    return this;
  }

  /**
   * Returns method's access modifier.
   *
   * @return method's access modifier
   */
  public JavaModifier methodAccessModifier() {
    return methodAccessModifier;
  }

  /**
   * Sets return type of the method.
   *
   * @param methodReturnType method's return type
   * @return current method's instance
   */
  public JavaMethod methodReturnType(Class<?> methodReturnType) {
    this.methodReturnType = methodReturnType;
    return this;
  }

  /**
   * Returns method's return type.
   *
   * @return method's return type
   */
  public Class<?> methodReturnType() {
    return methodReturnType;
  }

  /**
   * Sets parameters of the method.
   *
   * @param methodParameters method's parameters
   * @return current method's instance
   */
  public JavaMethod methodParameters(JavaParameter... methodParameters) {
    if (this.methodParameters == null) {
      this.methodParameters = new TreeSet<>(new JavaParameterComparator<>());
    }
    this.methodParameters.addAll(Arrays.asList(methodParameters));

    return this;
  }

  /**
   * Returns parameters of the method.
   *
   * @return method's parameters
   */
  public Set<JavaParameter> methodParameters() {
    return methodParameters;
  }

  /**
   * Sets an implementation of the method.
   *
   * @param methodImpl method's implementation
   * @return current method's instance
   */
  public JavaMethod methodImpl(JavaMethodImpl methodImpl) {
    this.methodImpl = methodImpl;
    return this;
  }

  /**
   * Returns an implementation of the method.
   *
   * @return method's implementation
   */
  public JavaMethodImpl methodImpl() {
    return methodImpl;
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
