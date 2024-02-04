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
import dev.rcd2obj.codegen.constructor.JavaClassConstructorDefinition;
import dev.rcd2obj.codegen.operator.JavaNewOperator;
import dev.rcd2obj.codegen.operator.JavaOperator;
import dev.rcd2obj.codegen.operator.JavaThrowOperator;
import dev.rcd2obj.codegen.render.JavaElementRender;
import dev.rcd2obj.codegen.type.JavaExplicitType;

/**
 * Implementation of the method, it throws {@link UnsupportedOperationException}.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaMethodUnsupportedImpl implements JavaMethodImpl {
  private static final JavaOperator UNSUPPORTED = new JavaThrowOperator(
    new JavaNewOperator(
      new JavaClassConstructorDefinition(
        new JavaExplicitType(UnsupportedOperationException.class)
      ).getConstructor()
    )
  );

  @Override
  public void render(JavaElementRender target) {
    target
      .append(JavaElementType.METHOD_IMPL_BLOCK_BEGIN)
      .append(UNSUPPORTED)
      .append(JavaElementType.METHOD_IMPL_BLOCK_END);
  }
}
