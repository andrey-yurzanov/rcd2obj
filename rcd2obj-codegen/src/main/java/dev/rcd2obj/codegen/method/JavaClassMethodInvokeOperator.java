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

package dev.rcd2obj.codegen.method;

import dev.rcd2obj.codegen.JavaElementType;
import dev.rcd2obj.codegen.operator.JavaArgument;
import dev.rcd2obj.codegen.render.JavaElementRender;

import java.util.Collection;
import java.util.Objects;

/**
 * A code generator for generating of class' method invocation.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaClassMethodInvokeOperator implements JavaArgument {
  private final JavaClassMethodDefinition methodDefinition;
  private final Collection<JavaArgument> methodArguments;

  /**
   * Constructs new code generator for generating of class' method invocation.
   *
   * @param methodDefinition declared method
   * @param methodArguments  method's arguments
   */
  protected JavaClassMethodInvokeOperator(
    JavaClassMethodDefinition methodDefinition,
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
  public JavaClassMethodDefinition getMethodDefinition() {
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
  public void render(JavaElementRender target) {
    String methodName = methodDefinition.getMethodName();
    target
      .append(JavaElementType.METHOD_INVOKE_BEGIN)
      .append(JavaElementType.METHOD_INVOKE_NAME)
      .append(Objects.requireNonNull(methodName, "[methodName] is required for invocation!"))
      .append(JavaElementType.METHOD_INVOKE_ARGS_BLOCK_BEGIN)
      .append(methodArguments, JavaElementType.METHOD_INVOKE_ARGS_SEPARATOR.toElement())
      .append(JavaElementType.METHOD_INVOKE_ARGS_BLOCK_END)
      .append(JavaElementType.METHOD_INVOKE_END);
  }
}
