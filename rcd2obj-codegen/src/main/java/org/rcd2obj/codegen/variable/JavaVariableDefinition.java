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

package org.rcd2obj.codegen.variable;

import org.rcd2obj.codegen.JavaElement;
import org.rcd2obj.codegen.JavaElementRenderingException;
import org.rcd2obj.codegen.JavaElementType;
import org.rcd2obj.codegen.method.JavaMethodInvokeOperator;
import org.rcd2obj.codegen.operator.JavaAssignOperator;
import org.rcd2obj.codegen.render.JavaElementRender;
import org.rcd2obj.codegen.type.JavaType;

/**
 * An operation of variable definition.
 * <pre>
 *   Example 1:
 *   {@code
 *   JavaVariableDefinition def = new JavaVariableDefinition(new JavaExplicitType(String.class), "myVar");
 *   def.render(...);
 *   }
 *   Result:
 *   {@code
 *   String myVar
 *   }
 *
 *   Example 2:
 *   {@code
 *   JavaExplicitType type = new JavaExplicitType(String.class);
 *   JavaAssignOperator assign = new JavaAssignOperator(new JavaNullArgument());
 *   JavaVariableDefinition def = new JavaVariableDefinition(type, "myVar", assign);
 *   def.render(...);
 *   }
 *   Result:
 *   {@code
 *   String myVar = null
 *   }
 * </pre>
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaVariableDefinition implements JavaElement {
  private final JavaType variableType;
  private final String variableName;
  private final JavaAssignOperator variableAssign;

  /**
   * It constructs new instance of variable definition renderer.
   *
   * @param variableType type of the variable
   * @param variableName name of the variable
   */
  public JavaVariableDefinition(JavaType variableType, String variableName) {
    this(variableType, variableName, null);
  }

  /**
   * It constructs new instance of variable definition renderer.
   *
   * @param variableType type of the variable
   * @param variableName name of the variable
   */
  public JavaVariableDefinition(JavaType variableType, String variableName, JavaAssignOperator variableAssign) {
    this.variableType = variableType;
    this.variableName = variableName;
    this.variableAssign = variableAssign;
  }

  /**
   * Returns type of the variable.
   *
   * @return type of the variable
   */
  public JavaType getVariableType() {
    return variableType;
  }

  /**
   * Returns name of the variable.
   *
   * @return name of the variable
   */
  public String getVariableName() {
    return variableName;
  }

  /**
   * Returns assign operator of the variable.
   *
   * @return assign operator of the variable
   */
  public JavaAssignOperator getVariableAssign() {
    return variableAssign;
  }

  /**
   * Returns operator for code generating assignment new value to a variable.
   *
   * @param variableAssign assignment
   * @return operator for code generating assignment new value to a variable
   */
  public JavaVariableAssignOperator assign(JavaAssignOperator variableAssign) {
    return new JavaVariableAssignOperator(this, variableAssign);
  }

  /**
   * Returns operator for code generating variable's method invocation.
   *
   * @param methodInvoke variable's method
   * @return operator for generating variable's method invocation
   */
  public JavaVariableInvokeMethodOperator invoke(JavaMethodInvokeOperator methodInvoke) {
    return new JavaVariableInvokeMethodOperator(this, methodInvoke);
  }

  @Override
  public void render(JavaElementRender target) throws JavaElementRenderingException {
    if (variableType == null) {
      throw new JavaElementRenderingException("Variable type has incorrect value: [$]!", variableType);
    }

    if (variableName == null || variableName.trim().isEmpty()) {
      throw new JavaElementRenderingException("Variable name has incorrect value: [$]!", variableName);
    }

    target
      .append(JavaElementType.VARIABLE_DEFINITION_BEGIN)
      .append(JavaElementType.VARIABLE_DEFINITION_TYPE)
      .append(variableType)
      .append(JavaElementType.VARIABLE_DEFINITION_NAME)
      .append(variableName);

    // assign value
    if (variableAssign != null) {
      target.append(variableAssign);
    }
    target.append(JavaElementType.VARIABLE_DEFINITION_END);
  }
}
