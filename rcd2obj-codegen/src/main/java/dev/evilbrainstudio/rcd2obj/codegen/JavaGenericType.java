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

package dev.evilbrainstudio.rcd2obj.codegen;

import java.lang.reflect.AnnotatedType;

/**
 * Generic type, a pair of real java's type and generic type's name.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaGenericType {
  protected Class<?> type;
  protected String genericTypeName;

  /**
   * Constructs new instance of generic type.
   */
  public JavaGenericType() {
    this(Object.class);
  }

  /**
   * Constructs new instance of generic type.
   *
   * @param type            java's type
   * @param genericTypeName name of the generic type
   */
  public JavaGenericType(Class<?> type, String genericTypeName) {
    this.type = type;
    this.genericTypeName = genericTypeName;
  }

  /**
   * Constructs new instance of generic type.
   *
   * @param type          java's type
   * @param annotatedType annotated java's type
   */
  public JavaGenericType(Class<?> type, AnnotatedType annotatedType) {
    this.type = type;
    if (annotatedType != null) {
      this.genericTypeName = annotatedType.getType().getTypeName();
    }
  }

  /**
   * Constructs new instance of generic type.
   *
   * @param type java's type
   */
  public JavaGenericType(Class<?> type) {
    this.type = type;
  }

  /**
   * Sets java's type.
   *
   * @param type java's type
   * @return current instance
   */
  public JavaGenericType setType(Class<?> type) {
    this.type = type;
    return this;
  }

  /**
   * Returns java's type.
   *
   * @return java's type
   */
  public Class<?> getType() {
    return type;
  }

  /**
   * Sets name of the generic type.
   *
   * @param genericTypeName name of the generic type
   * @return current instance
   */
  public JavaGenericType setGenericTypeName(String genericTypeName) {
    this.genericTypeName = genericTypeName;
    return this;
  }

  /**
   * Returns name of the generic type.
   *
   * @return name of the generic type.
   */
  public String getGenericTypeName() {
    return genericTypeName;
  }
}
