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

package dev.evilbrainstudio.rcd2obj.codegen.parameter;

import dev.evilbrainstudio.rcd2obj.codegen.JavaElement;
import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;
import dev.evilbrainstudio.rcd2obj.codegen.type.JavaExplicitType;
import dev.evilbrainstudio.rcd2obj.codegen.type.JavaType;

import java.lang.reflect.Parameter;

/**
 * Parameter of constructor, method and something else.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaParameter implements JavaElement, Comparable<JavaParameter> {
  protected Integer parameterOrder;
  protected String parameterName;
  protected JavaType parameterType;

  /**
   * Constructs new instance of the parameter.
   */
  public JavaParameter() {
    this(null, null, new JavaExplicitType(Object.class));
  }

  /**
   * Constructs new instance of the parameter.
   *
   * @param parameterOrder order of the parameter
   * @param parameter      parameter's meta information
   */
  public JavaParameter(Integer parameterOrder, Parameter parameter) {
    this(
      parameterOrder,
      parameter.getName(),
      new JavaExplicitType(parameter.getType())
    );
  }

  /**
   * Constructs new instance of the parameter.
   *
   * @param parameterOrder order of the parameter
   * @param parameterName  name of the parameter
   */
  public JavaParameter(Integer parameterOrder, String parameterName) {
    this(
      parameterOrder,
      parameterName,
      new JavaExplicitType(Object.class)
    );
  }

  /**
   * Constructs new instance of the parameter.
   *
   * @param parameterOrder order of the parameter
   * @param parameterName  name of the parameter
   * @param parameterType  type of the parameter
   */
  public JavaParameter(
    Integer parameterOrder,
    String parameterName,
    JavaType parameterType
  ) {
    this.parameterOrder = parameterOrder;
    this.parameterName = parameterName;
    this.parameterType = parameterType;
  }

  /**
   * Sets order of the parameter.
   *
   * @param parameterOrder new order of the parameter
   * @return current instance
   */
  public JavaParameter setParameterOrder(Integer parameterOrder) {
    this.parameterOrder = parameterOrder;
    return this;
  }

  /**
   * Returns order of the parameter.
   *
   * @return order of the parameter
   */
  public Integer getParameterOrder() {
    return parameterOrder;
  }

  /**
   * Sets name of the parameter.
   *
   * @param parameterName new name of the parameter
   * @return current instance
   */
  public JavaParameter setParameterName(String parameterName) {
    this.parameterName = parameterName;
    return this;
  }

  /**
   * Returns name of the parameter.
   *
   * @return name of the parameter
   */
  public String getParameterName() {
    return parameterName;
  }

  /**
   * Sets type of the parameter.
   *
   * @param parameterType new type of the parameter
   * @return current instance
   */
  public JavaParameter setParameterType(JavaType parameterType) {
    this.parameterType = parameterType;
    return this;
  }

  /**
   * Returns type of the parameter.
   *
   * @return type of the parameter
   */
  public JavaType getParameterType() {
    return parameterType;
  }

  @Override
  public int compareTo(JavaParameter other) {
    int result = Integer.compare(parameterOrder, other.getParameterOrder());
    if (result == 0) {
      result = parameterType.compareTo(other.getParameterType());
    }
    return result;
  }

  @Override
  public void render(JavaElementRender target) {
    target.append(JavaElementType.PARAMETER_BEGIN);
    if (parameterType != null) {
      target.append(JavaElementType.PARAMETER_TYPE, parameterType);
    }

    if (parameterName != null) {
      target.append(JavaElementType.PARAMETER_NAME, parameterName);
    }
    target.append(JavaElementType.PARAMETER_END);
  }
}
