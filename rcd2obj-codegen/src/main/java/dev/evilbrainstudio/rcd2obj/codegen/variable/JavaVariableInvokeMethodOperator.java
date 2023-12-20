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

import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;
import dev.evilbrainstudio.rcd2obj.codegen.method.JavaClassMethodInvokeOperator;
import dev.evilbrainstudio.rcd2obj.codegen.operator.JavaArgument;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;

import java.util.Objects;

/**
 * An operator for code generating of variable's method invocation.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaVariableInvokeMethodOperator implements JavaArgument {
  private final JavaVariableDefinition variableDefinition;
  private final JavaClassMethodInvokeOperator methodInvoke;

  /**
   * It constructs new instance of the operator to code generating of variable's method invocation.
   *
   * @param variableDefinition defined variable
   * @param methodInvoke       variable's method
   */
  protected JavaVariableInvokeMethodOperator(
    JavaVariableDefinition variableDefinition,
    JavaClassMethodInvokeOperator methodInvoke
  ) {
    this.variableDefinition = variableDefinition;
    this.methodInvoke = methodInvoke;
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
   * Returns variable's method.
   *
   * @return variable's method
   */
  public JavaClassMethodInvokeOperator getMethodInvoke() {
    return methodInvoke;
  }

  @Override
  public void render(JavaElementRender target) {
    String variableName = variableDefinition.getVariableName();
    target
      .append(JavaElementType.VARIABLE_METHOD_INVOKE_BEGIN)
      .append(JavaElementType.VARIABLE_METHOD_INVOKE_NAME)
      .append(Objects.requireNonNull(variableName, "[variableName] is required for invocation!"))
      .append(JavaElementType.VARIABLE_METHOD_INVOKE_SEPARATOR)
      .append(methodInvoke)
      .append(JavaElementType.VARIABLE_METHOD_INVOKE_END);
  }
}
