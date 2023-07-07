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
import dev.evilbrainstudio.rcd2obj.codegen.parameter.JavaParameterComparator;
import dev.evilbrainstudio.rcd2obj.codegen.parameter.JavaValueParameter;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;
import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

/**
 * New operator of Java.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaNewOperator implements JavaOperator {
  private Collection<JavaValueParameter> newParameters;
  private final Class<?> newType;

  /**
   * Constructs new instance of operator.
   *
   * @param newType type of instance
   */
  public JavaNewOperator(Class<?> newType) {
    this.newType = newType;
  }

  /**
   * Sets parameters of new operator.
   *
   * @param newParameters parameters of new operator
   * @return current instance
   */
  public JavaNewOperator newParameters(JavaValueParameter... newParameters) {
    if (this.newParameters == null) {
      this.newParameters = new TreeSet<>(new JavaParameterComparator<>());
    }
    this.newParameters.addAll(Arrays.asList(newParameters));

    return this;
  }

  @Override
  public void render(JavaElementRender target) {
    target
        .append(JavaElementType.NEW_BEGIN)
        .append(JavaElementType.NEW_KEYWORD)
        .append(JavaElementType.NEW_TYPE, newType)
        .append(JavaElementType.NEW_TYPE_PARAMS_BLOCK_BEGIN)
        .append(newParameters, JavaElementType.NEW_TYPE_PARAMS_SEPARATOR.toElement())
        .append(JavaElementType.NEW_TYPE_PARAMS_BLOCK_END)
        .append(JavaElementType.NEW_END);
  }
}
