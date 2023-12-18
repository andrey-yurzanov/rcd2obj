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
import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;
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
  private JavaAssignOperator variableAssign;
  private final JavaType variableType;
  private final String variableName;

  /**
   * Constructs new instance of variable definition renderer.
   *
   * @param variableType type of the variable
   * @param variableName name of the variable
   */
  public JavaVariableDefinition(JavaType variableType, String variableName) {
    this.variableType = variableType;
    this.variableName = variableName;
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
   * Sets assign operator of the variable.
   *
   * @param variableAssign assign operator of the variable.
   * @return current instance of the variable
   */
  public JavaVariableDefinition setVariableAssign(JavaAssignOperator variableAssign) {
    this.variableAssign = variableAssign;
    return this;
  }

  /**
   * Returns operator for assignment new value to current variable.
   *
   * @param variableAssign assignment
   * @return operator for assignment new value to current variable
   */
  public JavaVariableAssignOperator getVariable(JavaAssignOperator variableAssign) {
    return new JavaVariableAssignOperator(this, variableAssign);
  }

  @Override
  public void render(JavaElementRender target) {
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