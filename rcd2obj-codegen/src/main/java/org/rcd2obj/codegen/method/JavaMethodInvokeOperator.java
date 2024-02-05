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

package org.rcd2obj.codegen.method;

import org.rcd2obj.codegen.JavaElementRenderingException;
import org.rcd2obj.codegen.JavaElementType;
import org.rcd2obj.codegen.operator.JavaArgument;
import org.rcd2obj.codegen.render.JavaElementRender;

import java.util.Collection;

/**
 * A code generator for generating of class' method invocation.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaMethodInvokeOperator implements JavaArgument {
  private final JavaMethodDefinition methodDefinition;
  private final Collection<JavaArgument> methodArguments;

  /**
   * Constructs new code generator for generating of class' method invocation.
   *
   * @param methodDefinition declared method
   * @param methodArguments  method's arguments
   */
  protected JavaMethodInvokeOperator(
    JavaMethodDefinition methodDefinition,
    Collection<JavaArgument> methodArguments
  ) {
    this.methodDefinition = methodDefinition;
    this.methodArguments = methodArguments;
  }

  /**
   * Returns declared method.
   *
   * @return declared method
   */
  public JavaMethodDefinition getMethodDefinition() {
    return methodDefinition;
  }

  /**
   * Returns method's arguments.
   *
   * @return method's arguments
   */
  public Collection<JavaArgument> getMethodArguments() {
    return methodArguments;
  }

  @Override
  public void render(JavaElementRender target) throws JavaElementRenderingException {
    if (methodDefinition == null) {
      throw new JavaElementRenderingException("Method definition has incorrect value: [$]", methodDefinition);
    }

    String methodName = methodDefinition.getMethodName();
    if (methodName == null || methodName.trim().isEmpty()) {
      throw new JavaElementRenderingException("Method name has incorrect value: [$]", methodName);
    }

    target
      .append(JavaElementType.METHOD_INVOKE_BEGIN)
      .append(JavaElementType.METHOD_INVOKE_NAME)
      .append(methodName)
      .append(JavaElementType.METHOD_INVOKE_ARGS_BLOCK_BEGIN)
      .append(methodArguments, JavaElementType.METHOD_INVOKE_ARGS_SEPARATOR.toElement())
      .append(JavaElementType.METHOD_INVOKE_ARGS_BLOCK_END)
      .append(JavaElementType.METHOD_INVOKE_END);
  }
}
