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

package org.rcd2obj.codegen.parameter;

import org.rcd2obj.codegen.JavaElement;
import org.rcd2obj.codegen.JavaElementRenderingException;
import org.rcd2obj.codegen.JavaElementType;
import org.rcd2obj.codegen.render.JavaElementRender;
import org.rcd2obj.codegen.type.JavaType;

/**
 * The parameter of constructor or method.
 * <pre>
 *   Example 1:
 *   {@code
 *   JavaNameType type = new JavaNameType("MyClass");
 *   JavaParameter param = new JavaParameter(1, "message", new JavaExplicitType(String.class));
 *   JavaConstructorDefinition def = new JavaConstructorDefinition(type, param);
 *   def.render(...);
 *   }
 *   Result:
 *   {@code
 *   MyClass(String message) {
 *     throw new UnsupportedOperationException();
 *   }
 *   }
 *
 *   Example 2:
 *   {@code
 *   JavaParameter param = new JavaParameter(1, "value", new JavaExplicitType(String.class));
 *   JavaMethodDefinition def = new JavaMethodDefinition("apply", param);
 *   def.render(...);
 *   }
 *   Result:
 *   {@code
 *   void apply(String value) {
 *     throw new UnsupportedOperationException();
 *   }
 *   }
 * </pre>
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaParameter implements JavaElement, Comparable<JavaParameter> {
  private final int parameterOrder;
  private final String parameterName;
  private final JavaType parameterType;

  /**
   * It constructs new instance of the parameter.
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
