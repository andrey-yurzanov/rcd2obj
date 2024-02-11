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

import org.rcd2obj.codegen.JavaElement;
import org.rcd2obj.codegen.JavaElementRenderingException;
import org.rcd2obj.codegen.JavaElementType;
import org.rcd2obj.codegen.render.JavaElementRender;

import java.util.Arrays;
import java.util.Collection;

/**
 * A completion operator.
 * <pre>
 *   Example:
 *   {@code
 *   JavaCompleteOperator op = new JavaCompleteOperator(new JavaNullArgument());
 *   op.render(...);
 *   }
 *
 *   Result:
 *   {@code
 *   null;
 *   }
 * </pre>
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaCompleteOperator implements JavaOperator {
  private final Collection<JavaElement> elements;

  /**
   * It creates new instance of {@link JavaCompleteOperator}.
   *
   * @param elements elements before complete operator
   */
  public JavaCompleteOperator(JavaElement... elements) {
    this.elements = Arrays.asList(elements);
  }

  @Override
  public void render(JavaElementRender target) throws JavaElementRenderingException {
    target
      .append(elements)
      .append(JavaElementType.END_EXPRESSION_OPERATOR);
  }
}
