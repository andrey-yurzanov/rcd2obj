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

package dev.evilbrainstudio.rcd2obj.codegen.constructor;

import dev.evilbrainstudio.rcd2obj.codegen.JavaElement;
import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;
import dev.evilbrainstudio.rcd2obj.codegen.modifier.JavaModifier;
import dev.evilbrainstudio.rcd2obj.codegen.parameter.JavaParameter;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;
import dev.evilbrainstudio.rcd2obj.codegen.type.JavaType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * The constructor of Java's class.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaClassConstructor implements JavaElement, Comparable<JavaClassConstructor> {
  private JavaType constructorType;
  private Collection<JavaParameter> constructorParameters;
  private JavaModifier constructorAccessModifier;
  private JavaConstructorImpl constructorImpl;

  /**
   * Returns type of constructor's owner.
   *
   * @return type of constructor's owner
   */
  public JavaType getConstructorType() {
    return constructorType;
  }

  /**
   * Sets type of constructor's owner.
   *
   * @param constructorType type of constructor's owner
   */
  public JavaClassConstructor setConstructorType(JavaType constructorType) {
    this.constructorType = constructorType;
    return this;
  }

  /**
   * Returns constructor's parameters.
   *
   * @return constructor's parameters
   */
  public Collection<JavaParameter> getConstructorParameters() {
    return constructorParameters;
  }

  /**
   * Sets constructor's parameters.
   *
   * @param constructorParameters constructor's parameters
   */
  public JavaClassConstructor setConstructorParameters(JavaParameter... constructorParameters) {
    if (this.constructorParameters == null) {
      this.constructorParameters = new TreeSet<>();
    }
    this.constructorParameters.addAll(Arrays.asList(constructorParameters));

    return this;
  }

  /**
   * Returns constructor's access modifier.
   *
   * @return constructor's access modifier
   */
  public JavaModifier getConstructorAccessModifier() {
    return constructorAccessModifier;
  }

  /**
   * Sets constructor's access modifier.
   *
   * @param constructorAccessModifier constructor's access modifier.
   */
  public JavaClassConstructor setConstructorAccessModifier(JavaModifier constructorAccessModifier) {
    this.constructorAccessModifier = constructorAccessModifier;
    return this;
  }

  /**
   * Returns constructor's implementation.
   *
   * @return constructor's implementation
   */
  public JavaConstructorImpl getConstructorImpl() {
    return constructorImpl;
  }

  /**
   * Sets constructor's implementation.
   *
   * @param constructorImpl constructor's implementation.
   */
  public JavaClassConstructor setConstructorImpl(JavaConstructorImpl constructorImpl) {
    this.constructorImpl = constructorImpl;
    return this;
  }

  @Override
  public void render(JavaElementRender target) {
    target
      .append(JavaElementType.CONSTRUCTOR_BEGIN)
      .append(constructorAccessModifier)
      .append(constructorType)
      .append(JavaElementType.CONSTRUCTOR_PARAMS_BLOCK_BEGIN)
      .append(constructorParameters, JavaElementType.CONSTRUCTOR_PARAMS_SEPARATOR.toElement())
      .append(JavaElementType.CONSTRUCTOR_PARAMS_BLOCK_END)
      .append(constructorImpl)
      .append(JavaElementType.CONSTRUCTOR_END);
  }

  @Override
  public int compareTo(JavaClassConstructor other) {
    int result = constructorType.compareTo(other.getConstructorType());
    if (result == 0) {
      int size = 0;
      if (constructorParameters != null) {
        size = constructorParameters.size();
      }

      int otherSize = 0;
      Collection<JavaParameter> otherParameters = other.getConstructorParameters();
      if (otherParameters != null) {
        otherSize = otherParameters.size();
      }

      result = Integer.compare(size, otherSize);
      if (result == 0 && otherSize > 0) {
        Iterator<JavaParameter> iterator = otherParameters.iterator();
        for (JavaParameter parameter : constructorParameters) {
          JavaParameter otherParameter = iterator.next();

          result = parameter.compareTo(otherParameter);
          if (result != 0) {
            break;
          }
        }
      }
    }
    return result;
  }
}
