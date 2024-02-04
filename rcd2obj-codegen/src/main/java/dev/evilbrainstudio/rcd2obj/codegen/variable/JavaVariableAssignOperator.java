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

package dev.evilbrainstudio.rcd2obj.codegen.variable;

import dev.evilbrainstudio.rcd2obj.codegen.JavaElementRenderingException;
import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;
import dev.evilbrainstudio.rcd2obj.codegen.operator.JavaArgument;
import dev.evilbrainstudio.rcd2obj.codegen.operator.JavaAssignOperator;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;

/**
 * An operator for assignment new value to defined variable.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaVariableAssignOperator implements JavaArgument {
  private final JavaVariableDefinition variableDefinition;
  private final JavaAssignOperator variableAssign;

  /**
   * Constructs new instance of operator.
   *
   * @param variableDefinition defined variable
   * @param variableAssign     assignment
   */
  protected JavaVariableAssignOperator(
    JavaVariableDefinition variableDefinition,
    JavaAssignOperator variableAssign
  ) {
    this.variableDefinition = variableDefinition;
    this.variableAssign = variableAssign;
  }

  /**
   * Returns defined variable.
   *
   * @return defined variable
   */
  public JavaVariableDefinition getVariableDefinition() {
    return variableDefinition;
  }

  /**
   * Returns assignment.
   *
   * @return assignment
   */
  public JavaAssignOperator getVariableAssign() {
    return variableAssign;
  }

  @Override
  public void render(JavaElementRender target) throws JavaElementRenderingException {
    if (variableDefinition == null) {
      throw new JavaElementRenderingException("Variable definition has incorrect value: [$]!", variableDefinition);
    }

    if (variableAssign == null) {
      throw new JavaElementRenderingException("Variable assignment has incorrect value: [$]!", variableAssign);
    }

    target
      .append(JavaElementType.VARIABLE_ASSIGN_BEGIN)
      .append(JavaElementType.VARIABLE_ASSIGN_NAME)
      .append(variableDefinition.getVariableName())
      .append(variableAssign)
      .append(JavaElementType.VARIABLE_ASSIGN_END);
  }
}
