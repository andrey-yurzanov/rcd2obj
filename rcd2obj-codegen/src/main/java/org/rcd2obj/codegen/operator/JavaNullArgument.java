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

import org.rcd2obj.codegen.render.JavaElementRender;

/**
 * A null value argument.
 * <pre>
 *   Example:
 *   {@code
 *   JavaNullArgument nullArg = new JavaNullArgument();
 *   nullArg.render(...);
 *   }
 *   Result:
 *   {@code
 *   null
 *   }
 * </pre>
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaNullArgument implements JavaArgument {
  private static final String VALUE = "null";

  @Override
  public void render(JavaElementRender target) {
    target.append(VALUE);
  }
}
