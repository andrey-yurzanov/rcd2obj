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

import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;

/**
 * The parameter with a value. Used with constructors and methods.
 *
 * @author Andrey_Yurzanov
 */
public class JavaValueParameter extends JavaParameter {
  protected Object parameterValue;

  /**
   * Constructs new instance of the parameter.
   */
  public JavaValueParameter() {
    super();
  }

  /**
   * Constructs new instance of the parameter.
   *
   * @param parameterOrder order of the parameter
   * @param parameterName  name of the parameter
   */
  public JavaValueParameter(Integer parameterOrder, String parameterName) {
    super(parameterOrder, parameterName);
  }

  /**
   * Sets value of the parameter.
   *
   * @param parameterValue value of the parameter
   * @return current instance
   */
  public JavaValueParameter setParameterValue(Object parameterValue) {
    this.parameterValue = parameterValue;
    return this;
  }

  /**
   * Returns value of the parameter.
   *
   * @return value of the parameter
   */
  public Object getParameterValue() {
    return parameterValue;
  }

  @Override
  public JavaValueParameter setParameterName(String parameterName) {
    super.setParameterName(parameterName);
    return this;
  }

  @Override
  public JavaValueParameter setParameterOrder(Integer parameterOrder) {
    super.setParameterOrder(parameterOrder);
    return this;
  }

  @Override
  public JavaValueParameter setParameterType(Class<?> parameterType) {
    super.setParameterType(parameterType);
    return this;
  }

  @Override
  public void render(JavaElementRender target) {
    target
      .append(JavaElementType.PARAMETER_BEGIN)
      .append(JavaElementType.PARAMETER_VALUE, parameterValue, parameterType)
      .append(JavaElementType.PARAMETER_END);
  }
}
