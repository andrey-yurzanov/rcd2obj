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
   * Value of the parameter.
   */
  PARAMETER_VALUE,
  /**
   * The end of the parameter definition.
   */
  PARAMETER_END,
  /**
   * The start of the modifier definition.
   */
  MODIFIER_BEGIN,
  /**
   * The keyword of the public modifier.
   */
  MODIFIER_PUBLIC_KEYWORD("public"),
  /**
   * The end of the modifier definition.
   */
  MODIFIER_END,
  /**
   * The start of the throw operator.
   */
  THROW_BEGIN,
  /**
   * The keyword of the throw operator.
   */
  THROW_KEYWORD("throw"),
  /**
   * The end of the throw operator.
   */
  THROW_END,
  /**
   * The start of the new operator.
   */
  NEW_BEGIN,
  /**
   * The keyword of the new operator.
   */
  NEW_KEYWORD("new"),
  /**
   * The type of new instance.
   */
  NEW_TYPE,
  /**
   * The start of type's constructor.
   */
  NEW_TYPE_PARAMS_BLOCK_BEGIN("("),
  /**
   * The separator of type's constructor parameters.
   */
  NEW_TYPE_PARAMS_SEPARATOR(","),
  /**
   * The end of type's constructor.
   */
  NEW_TYPE_PARAMS_BLOCK_END(")"),
  /**
   * The end of the new operator.
   */
  NEW_END(";"),

  /**
   * The start of the method definition.
   */
  METHOD_BEGIN,
  /**
   * The start of the method's access modifier definition.
   */
  METHOD_ACCESS_MODIFIER_BEGIN,
  /**
   * The end of the method's access modifier definition.
   */
  METHOD_ACCESS_MODIFIER_END,
  /**
   * Return type of the method.
   */
  METHOD_RETURN_TYPE,
  /**
   * Name of the method.
   */
  METHOD_NAME,
  /**
   * The start of the method parameters.
   */
  METHOD_PARAMS_BLOCK_BEGIN("("),
  /**
   * Separator of the method parameters.
   */
  METHOD_PARAMS_SEPARATOR(","),
  /**
   * The end of the method parameters.
   */
  METHOD_PARAMS_BLOCK_END(")"),
  /**
   * The start of the method implementation.
   */
  METHOD_IMPL_BLOCK_BEGIN("{"),
  /**
   * The end of the method implementation.
   */
  METHOD_IMPL_BLOCK_END("}"),
  /**
   * The end of the method definition.
   */
  METHOD_END,

  IMPLEMENTS_BLOCK_BEGIN,

  IMPLEMENTS_KEYWORD("implements"),

  IMPLEMENTS_SEPARATOR(","),

  IMPLEMENTS_BLOCK_END,

  INHERITED_ELEMENT_BEGIN,

  INHERITED_ELEMENT_TYPE,

  INHERITED_ELEMENT_END,

  CLASS_DEFINITION_BLOCK_BEGIN,
  CLASS_KEYWORD("class"),
  CLASS_NAME,
  CLASS_BODY_BEGIN("{"),
  CLASS_BODY_END("}"),
  CLASS_DEFINITION_BLOCK_END,

  /**
   * The start of imports.
   */
  IMPORT_BLOCK_BEGIN,
  /**
   * The start of import's operator.
   */
  IMPORT_BEGIN,
  /**
   * The import's keyword.
   */
  IMPORT_KEYWORD("import"),
  /**
   * The type of the import's operator.
   */
  IMPORT_TYPE,
  /**
   * The end of import's operator.
   */
  IMPORT_END(";"),
  /**
   * The end of imports.
   */
  IMPORT_BLOCK_END,

  /**
   * Empty literal.
   */
  EMPTY_LITERAL;

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

  /**
   *
   */
  public JavaElement toElement() {
    return (target) -> target.append(this);
  }
}
