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

import org.rcd2obj.codegen.JavaElement;
import org.rcd2obj.codegen.JavaElementRenderingException;
import org.rcd2obj.codegen.JavaElementType;
import org.rcd2obj.codegen.modifier.JavaModifier;
import org.rcd2obj.codegen.operator.JavaArgument;
import org.rcd2obj.codegen.parameter.JavaParameter;
import org.rcd2obj.codegen.render.JavaElementRender;
import org.rcd2obj.codegen.type.JavaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * The constructor of Java's class.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaConstructorDefinition implements JavaElement, Comparable<JavaConstructorDefinition> {
  private final JavaType constructorType;
  private final Collection<JavaParameter> constructorParameters;
  private final JavaModifier constructorAccessModifier;
  private final JavaConstructorImpl constructorImpl;

  /**
   * It creates new instance of Java constructor. Constructor will be without an access modifier,
   * it will have {@link JavaConstructorUnsupportedImpl} implementation.
   *
   * @param constructorType       type of constructor
   * @param constructorParameters constructor parameters
   */
  public JavaConstructorDefinition(JavaType constructorType, JavaParameter... constructorParameters) {
    this(
      constructorType,
      Arrays.asList(constructorParameters),
      null,
      new JavaConstructorUnsupportedImpl()
    );
  }

  /**
   * It creates new instance of Java constructor.
   *
   * @param constructorType           type of constructor
   * @param constructorParameters     constructor parameters
   * @param constructorAccessModifier access modifier of constructor
   * @param constructorImpl           implementation of the constructor, if it has null-value, then {@link JavaConstructorUnsupportedImpl} will be used
   */
  public JavaConstructorDefinition(
    JavaType constructorType,
    Collection<JavaParameter> constructorParameters,
    JavaModifier constructorAccessModifier,
    JavaConstructorImpl constructorImpl
  ) {
    this.constructorType = constructorType;
    this.constructorAccessModifier = constructorAccessModifier;

    this.constructorParameters = new TreeSet<>();
    if (constructorParameters != null) {
      this.constructorParameters.addAll(constructorParameters);
    }

    if (constructorImpl == null) {
      this.constructorImpl = new JavaConstructorUnsupportedImpl();
    } else {
      this.constructorImpl = constructorImpl;
    }

  }

  /**
   * Returns type of constructor's owner.
   *
   * @return type of constructor's owner
   */
  public JavaType getConstructorType() {
    return constructorType;
  }

  /**
   * Returns constructor's parameters.
   *
   * @return constructor's parameters
   */
  public Collection<JavaParameter> getConstructorParameters() {
    return Collections.unmodifiableCollection(constructorParameters);
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
   * Returns constructor's implementation.
   *
   * @return constructor's implementation
   */
  public JavaConstructorImpl getConstructorImpl() {
    return constructorImpl;
  }

  /**
   * Returns new instance for invocation of the constructor.
   *
   * @param constructorArguments constructor's invocation arguments
   * @return new instance for invocation of the constructor
   */
  public JavaClassConstructorInvokeOperator invoke(JavaArgument... constructorArguments) {
    int size = 0;
    if (constructorParameters != null) {
      size = constructorParameters.size();
    }

    Collection<JavaArgument> arguments = new ArrayList<>();
    if (constructorArguments != null) {
      arguments.addAll(Arrays.asList(constructorArguments));
    }

    if (size == arguments.size()) {
      return new JavaClassConstructorInvokeOperator(this, arguments);
    }
    throw new IllegalArgumentException("The number of arguments does not match the number of parameters!");
  }

  @Override
  public void render(JavaElementRender target) throws JavaElementRenderingException {
    if (constructorType == null) {
      throw new JavaElementRenderingException("Constructor type has incorrect value: [$]!", constructorType);
    }

    target
      .append(JavaElementType.CONSTRUCTOR_DEFINITION_BEGIN)
      .append(constructorAccessModifier)
      .append(constructorType)
      .append(JavaElementType.CONSTRUCTOR_DEFINITION_PARAMS_BLOCK_BEGIN)
      .append(constructorParameters, JavaElementType.CONSTRUCTOR_DEFINITION_PARAMS_SEPARATOR.toElement())
      .append(JavaElementType.CONSTRUCTOR_DEFINITION_PARAMS_BLOCK_END)
      .append(constructorImpl)
      .append(JavaElementType.CONSTRUCTOR_DEFINITION_END);
  }

  @Override
  public int compareTo(JavaConstructorDefinition other) {
    int result = constructorType.compareTo(other.getConstructorType());
    if (result == 0) {
      Collection<JavaParameter> otherParameters = other.getConstructorParameters();
      int otherSize = otherParameters.size();

      result = Integer.compare(constructorParameters.size(), otherSize);
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
