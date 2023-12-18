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
import dev.evilbrainstudio.rcd2obj.codegen.operator.JavaArgument;
import dev.evilbrainstudio.rcd2obj.codegen.parameter.JavaParameter;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;
import dev.evilbrainstudio.rcd2obj.codegen.type.JavaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.TreeSet;

/**
 * The constructor of Java's class.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaClassConstructorDefinition implements JavaElement, Comparable<JavaClassConstructorDefinition> {
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
  public JavaClassConstructorDefinition setConstructorType(JavaType constructorType) {
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
  public JavaClassConstructorDefinition setConstructorParameters(JavaParameter... constructorParameters) {
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
  public JavaClassConstructorDefinition setConstructorAccessModifier(JavaModifier constructorAccessModifier) {
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
  public JavaClassConstructorDefinition setConstructorImpl(JavaConstructorImpl constructorImpl) {
    this.constructorImpl = constructorImpl;
    return this;
  }

  /**
   * Returns new instance for invocation of the constructor.
   *
   * @param constructorArguments constructor's invocation arguments
   * @return new instance for invocation of the constructor
   */
  public JavaClassConstructorInvokeOperator getConstructor(JavaArgument... constructorArguments) {
    int size = 0;
    if (constructorParameters != null) {
      size = constructorParameters.size();
    }

    Collection<JavaArgument> arguments = new ArrayList<>();
    if (constructorArguments != null) {
      arguments.addAll(Arrays.asList(constructorArguments));
    }

    if (size == arguments.size()) {
      return new JavaClassConstructorInvokeOperator(this, constructorArguments);
    }
    throw new IllegalArgumentException("The number of arguments does not match the number of parameters!");
  }

  @Override
  public void render(JavaElementRender target) {
    if (constructorImpl == null) {
      constructorImpl = new JavaConstructorUnsupportedImpl();
    }

    target
      .append(JavaElementType.CONSTRUCTOR_DEFINITION_BEGIN)
      .append(constructorAccessModifier)
      .append(Objects.requireNonNull(constructorType, "[constructorType] is required for definition!"))
      .append(JavaElementType.CONSTRUCTOR_DEFINITION_PARAMS_BLOCK_BEGIN)
      .append(constructorParameters, JavaElementType.CONSTRUCTOR_DEFINITION_PARAMS_SEPARATOR.toElement())
      .append(JavaElementType.CONSTRUCTOR_DEFINITION_PARAMS_BLOCK_END)
      .append(constructorImpl)
      .append(JavaElementType.CONSTRUCTOR_DEFINITION_END);
  }

  @Override
  public int compareTo(JavaClassConstructorDefinition other) {
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
