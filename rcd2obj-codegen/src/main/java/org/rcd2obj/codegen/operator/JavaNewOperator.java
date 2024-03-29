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
import org.rcd2obj.codegen.constructor.JavaClassConstructorInvokeOperator;
import org.rcd2obj.codegen.render.JavaElementRender;

/**
 * An operator of new keyword.
 * <pre>
 *   Example:
 *   {@code
 *   JavaExplicitType type = new JavaExplicitType(UnsupportedOperationException.class);
 *   JavaConstructorDefinition def = new JavaConstructorDefinition(type);
 *   JavaNewOperator op = new JavaNewOperator(def.invoke());
 *   op.render(...);
 *   }
 *   Result:
 *   {@code
 *   new UnsupportedOperationException()
 *   }
 * </pre>
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaNewOperator implements JavaOperator {
  private final JavaClassConstructorInvokeOperator newConstructor;

  /**
   * Constructs new instance of operator.
   *
   * @param newConstructor constructor for instance creation
   */
  public JavaNewOperator(JavaClassConstructorInvokeOperator newConstructor) {
    this.newConstructor = newConstructor;
  }

  /**
   * Returns constructor for new instance creation.
   *
   * @return constructor for new instance creation
   */
  public JavaClassConstructorInvokeOperator getNewConstructor() {
    return newConstructor;
  }

  @Override
  public void render(JavaElementRender target) throws JavaElementRenderingException {
    if (newConstructor == null) {
      throw new JavaElementRenderingException("Constructor invocation has incorrect value: [$]!", newConstructor);
    }

    target
      .append(JavaElementType.NEW_BEGIN)
      .append(JavaElementType.NEW_KEYWORD)
      .append(newConstructor)
      .append(JavaElementType.NEW_END);
  }
}
