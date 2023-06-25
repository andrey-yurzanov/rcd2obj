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

/**
 * Type of the Java element. Contains definition of keywords and some special symbols.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public enum JavaElementType {
  /**
   * The start of the package definition.
   */
  PACKAGE_BEGIN,
  /**
   * The keyword of the Java package.
   */
  PACKAGE_KEYWORD("package"),
  /**
   * The name of the package.
   */
  PACKAGE_NAME,
  /**
   * The end of the package definition.
   */
  PACKAGE_END(";"),
  /**
   * The start of the parameter definition.
   */
  PARAMETER_BEGIN,
  /**
   * Type of the parameter.
   */
  PARAMETER_TYPE,
  /**
   * Name of the parameter.
   */
  PARAMETER_NAME,
  /**
   * The end of the parameter definition.
   */
  PARAMETER_END,

  IMPLEMENTS_BLOCK_BEGIN,

  IMPLEMENTS_KEYWORD("implements"),

  IMPLEMENTS_SEPARATOR(","),

  IMPLEMENTS_BLOCK_END,

  INHERITED_ELEMENT_BEGIN,

  INHERITED_ELEMENT_TYPE,

  INHERITED_ELEMENT_END,

  GENERIC_PARAMS_BLOCK_BEGIN("<"),
  GENERIC_PARAMS_SEPARATOR(","),
  GENERIC_PARAMS_BLOCK_END(">"),

  CLASS_DEFINITION_BLOCK_BEGIN,
  CLASS_KEYWORD("class"),
  CLASS_NAME,
  CLASS_DEFINITION_BLOCK_END,

  IMPORT_BLOCK_BEGIN,
  IMPORT_BEGIN,
  IMPORT_KEYWORD("import"),
  IMPORT_TYPE,
  IMPORT_END,
  IMPORT_BLOCK_END;

  private final String value;

  JavaElementType() {
    this("");
  }

  JavaElementType(String value) {
    this.value = value;
  }

  /**
   * Returns value of the type.
   *
   * @return value of the type
   */
  public String getValue() {
    return value;
  }
}
