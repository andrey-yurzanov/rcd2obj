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

package org.rcd2obj.codegen.operator;

import org.rcd2obj.codegen.JavaElementRenderingException;
import org.rcd2obj.codegen.JavaElementType;
import org.rcd2obj.codegen.render.JavaElementRender;

/**
 * Java's assign operator.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaAssignOperator implements JavaOperator {
  private final JavaArgument assignArgument;

  /**
   * Constructs new instance of assign operator.
   *
   * @param assignArgument argument of operator
   */
  public JavaAssignOperator(JavaArgument assignArgument) {
    this.assignArgument = assignArgument;
  }

  /**
   * Returns argument of operator.
   *
   * @return argument of operator
   */
  public JavaArgument getAssignArgument() {
    return assignArgument;
  }

  @Override
  public void render(JavaElementRender target) throws JavaElementRenderingException {
    if (assignArgument == null) {
      throw new JavaElementRenderingException("Assignment has incorrect value: [$]!", assignArgument);
    }

    target
      .append(JavaElementType.ASSIGN_BEGIN)
      .append(JavaElementType.ASSIGN_OPERATOR)
      .append(JavaElementType.ASSIGN_VALUE)
      .append(assignArgument)
      .append(JavaElementType.END_EXPRESSION_OPERATOR)
      .append(JavaElementType.ASSIGN_END);
  }
}
