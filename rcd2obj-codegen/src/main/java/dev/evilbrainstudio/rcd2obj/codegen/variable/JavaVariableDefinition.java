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

package dev.evilbrainstudio.rcd2obj.codegen.variable;

import dev.evilbrainstudio.rcd2obj.codegen.JavaElement;
import dev.evilbrainstudio.rcd2obj.codegen.JavaElementRenderingException;
import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;
import dev.evilbrainstudio.rcd2obj.codegen.method.JavaClassMethodInvokeOperator;
import dev.evilbrainstudio.rcd2obj.codegen.operator.JavaAssignOperator;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;
import dev.evilbrainstudio.rcd2obj.codegen.type.JavaType;

/**
 * Java's variable definition.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaVariableDefinition implements JavaElement {
  private final JavaType variableType;
  private final String variableName;
  private final JavaAssignOperator variableAssign;

  /**
   * Constructs new instance of variable definition renderer.
   *
   * @param variableType type of the variable
   * @param variableName name of the variable
   */
  public JavaVariableDefinition(JavaType variableType, String variableName) {
    this(variableType, variableName, null);
  }

  /**
   * Constructs new instance of variable definition renderer.
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
  public JavaVariableInvokeMethodOperator invoke(JavaClassMethodInvokeOperator methodInvoke) {
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
    } else {
      target.append(JavaElementType.END_EXPRESSION_OPERATOR);
    }
    target.append(JavaElementType.VARIABLE_DEFINITION_END);
  }
}
