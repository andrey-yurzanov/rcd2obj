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

package dev.evilbrainstudio.rcd2obj.codegen.operator;

import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;
import dev.evilbrainstudio.rcd2obj.codegen.operator.JavaArgument;
import dev.evilbrainstudio.rcd2obj.codegen.operator.JavaOperator;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;

import java.util.Objects;

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
    this.assignArgument = Objects.requireNonNull(assignArgument);
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
  public void render(JavaElementRender target) {
    target
      .append(JavaElementType.ASSIGN_BEGIN)
      .append(JavaElementType.ASSIGN_KEYWORD)
      .append(JavaElementType.ASSIGN_VALUE)
      .append(assignArgument)
      .append(JavaElementType.END_EXPRESSION_OPERATOR)
      .append(JavaElementType.ASSIGN_END);
  }
}
