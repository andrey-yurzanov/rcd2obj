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

package org.rcd2obj.codegen.constructor;

import org.rcd2obj.codegen.JavaElementRenderingException;
import org.rcd2obj.codegen.JavaElementType;
import org.rcd2obj.codegen.operator.JavaArgument;
import org.rcd2obj.codegen.render.JavaElementRender;
import org.rcd2obj.codegen.type.JavaType;

import java.util.Collection;

/**
 * Code generator for invocation of a Java's constructor.
 * Generated: JavaType(JavaArgument...N)
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaClassConstructorInvokeOperator implements JavaArgument {
  private final JavaConstructorDefinition constructorDefinition;
  private final Collection<JavaArgument> constructorArguments;

  /**
   * Constructs new instance of constructor invocation.
   *
   * @param constructorDefinition definition of constructor
   */
  protected JavaClassConstructorInvokeOperator(
    JavaConstructorDefinition constructorDefinition,
    Collection<JavaArgument> constructorArguments
  ) {
    this.constructorDefinition = constructorDefinition;
    this.constructorArguments = constructorArguments;
  }

  /**
   * Returns constructor's definition.
   *
   * @return constructor's definition
   */
  public JavaConstructorDefinition getConstructorDefinition() {
    return constructorDefinition;
  }

  /**
   * Returns constructor's invocation arguments
   *
   * @return constructor's invocation arguments
   */
  public Collection<JavaArgument> getConstructorArguments() {
    return constructorArguments;
  }

  @Override
  public void render(JavaElementRender target) throws JavaElementRenderingException {
    if (constructorDefinition == null) {
      throw new JavaElementRenderingException("Constructor has incorrect value: [$]!", constructorDefinition);
    }

    JavaType constructorType = constructorDefinition.getConstructorType();
    if (constructorType == null) {
      throw new JavaElementRenderingException("Constructor type has incorrect value: [$]!", constructorType);
    }

    target
      .append(JavaElementType.CONSTRUCTOR_INVOKE_BEGIN)
      .append(constructorType)
      .append(JavaElementType.CONSTRUCTOR_INVOKE_PARAMS_BLOCK_BEGIN)
      .append(constructorArguments, JavaElementType.CONSTRUCTOR_INVOKE_ARGS_SEPARATOR.toElement())
      .append(JavaElementType.CONSTRUCTOR_INVOKE_PARAMS_BLOCK_END)
      .append(JavaElementType.CONSTRUCTOR_INVOKE_END);
  }
}
