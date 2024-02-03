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
import dev.evilbrainstudio.rcd2obj.codegen.JavaElementRenderingException;
import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;
import dev.evilbrainstudio.rcd2obj.codegen.type.JavaType;

/**
 * Parameter of constructor, method and something else.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaParameter implements JavaElement, Comparable<JavaParameter> {
  private final int parameterOrder;
  private final String parameterName;
  private final JavaType parameterType;

  /**
   * Constructs new instance of the parameter.
   *
   * @param parameterOrder order of the parameter
   * @param parameterName  name of the parameter
   * @param parameterType  type of the parameter
   */
  public JavaParameter(
    int parameterOrder,
    String parameterName,
    JavaType parameterType
  ) {
    this.parameterOrder = parameterOrder;
    this.parameterName = parameterName;
    this.parameterType = parameterType;
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
   * Returns name of the parameter.
   *
   * @return name of the parameter
   */
  public String getParameterName() {
    return parameterName;
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
  public void render(JavaElementRender target) throws JavaElementRenderingException {
    if (parameterType == null) {
      throw new JavaElementRenderingException("Parameter type has incorrect value: [$]!", parameterType);
    }

    if (parameterName == null || parameterName.trim().isEmpty()) {
      throw new JavaElementRenderingException("Parameter name has incorrect value: [$]!", parameterName);
    }

    target
      .append(JavaElementType.PARAMETER_BEGIN)
      .append(JavaElementType.PARAMETER_TYPE)
      .append(parameterType)
      .append(JavaElementType.PARAMETER_NAME)
      .append(parameterName)
      .append(JavaElementType.PARAMETER_END);
  }
}
