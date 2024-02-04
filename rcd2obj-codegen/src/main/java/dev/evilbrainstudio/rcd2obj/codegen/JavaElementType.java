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
  PACKAGE_KEYWORD("package", true),
  /**
   * The name of the package.
   */
  PACKAGE_NAME,
  /**
   * The end of the package definition.
   */
  PACKAGE_END,
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
  MODIFIER_PUBLIC_KEYWORD("public", true),
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
  THROW_KEYWORD("throw", true),
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
  NEW_KEYWORD("new", true),
  /**
   * The end of the new operator.
   */
  NEW_END,
  /**
   * The start of the method definition.
   */
  METHOD_DEFINITION_BEGIN,
  /**
   * The start of the method's access modifier definition.
   */
  METHOD_DEFINITION_ACCESS_MODIFIER_BEGIN,
  /**
   * The end of the method's access modifier definition.
   */
  METHOD_DEFINITION_ACCESS_MODIFIER_END,
  /**
   * Return type of the method.
   */
  METHOD_DEFINITION_RETURN_TYPE,
  /**
   * Name of the method.
   */
  METHOD_DEFINITION_NAME,
  /**
   * The start of the method parameters.
   */
  METHOD_DEFINITION_PARAMS_BLOCK_BEGIN("("),
  /**
   * Separator of the method parameters.
   */
  METHOD_DEFINITION_PARAMS_SEPARATOR(","),
  /**
   * The end of the method parameters.
   */
  METHOD_DEFINITION_PARAMS_BLOCK_END(")"),
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
  METHOD_DEFINITION_END,
  /**
   * The start of the method invocation.
   */
  METHOD_INVOKE_BEGIN,
  /**
   * Name of the method.
   */
  METHOD_INVOKE_NAME,
  /**
   * The start of the method's arguments.
   */
  METHOD_INVOKE_ARGS_BLOCK_BEGIN("("),
  /**
   * Separator of the method's arguments.
   */
  METHOD_INVOKE_ARGS_SEPARATOR(","),
  /**
   * The end of the method's arguments.
   */
  METHOD_INVOKE_ARGS_BLOCK_END(")"),
  /**
   * The end of the method invocation.
   */
  METHOD_INVOKE_END,
  /**
   * The start of the constructor's definition.
   */
  CONSTRUCTOR_DEFINITION_BEGIN,
  /**
   * The start of the constructor's access modifier definition.
   */
  CONSTRUCTOR_DEFINITION_ACCESS_MODIFIER_BEGIN,
  /**
   * The end of the constructor's access modifier definition.
   */
  CONSTRUCTOR_DEFINITION_ACCESS_MODIFIER_END,
  /**
   * The start of the constructor's parameters.
   */
  CONSTRUCTOR_DEFINITION_PARAMS_BLOCK_BEGIN("("),
  /**
   * Separator of the constructor's parameters.
   */
  CONSTRUCTOR_DEFINITION_PARAMS_SEPARATOR(","),
  /**
   * The end of the constructor's parameters.
   */
  CONSTRUCTOR_DEFINITION_PARAMS_BLOCK_END(")"),
  /**
   * The start of the constructor's implementation.
   */
  CONSTRUCTOR_DEFINITION_IMPL_BLOCK_BEGIN("{"),
  /**
   * The end of the constructor's implementation.
   */
  CONSTRUCTOR_DEFINITION_IMPL_BLOCK_END("}"),
  /**
   * The end of the constructor's definition.
   */
  CONSTRUCTOR_DEFINITION_END,
  /**
   * The start of a constructor invoking.
   */
  CONSTRUCTOR_INVOKE_BEGIN,
  /**
   * The start of the constructor's parameters.
   */
  CONSTRUCTOR_INVOKE_PARAMS_BLOCK_BEGIN("("),
  /**
   * Separator of the constructor's arguments.
   */
  CONSTRUCTOR_INVOKE_ARGS_SEPARATOR(","),
  /**
   * The end of the constructor's parameters.
   */
  CONSTRUCTOR_INVOKE_PARAMS_BLOCK_END(")"),
  /**
   * The end of a constructor invoking.
   */
  CONSTRUCTOR_INVOKE_END,
  /**
   * The start of the implements block.
   */
  IMPLEMENTS_BLOCK_BEGIN,
  /**
   * The implements keyword.
   */
  IMPLEMENTS_KEYWORD("implements", true),
  /**
   * The separator of interfaces.
   */
  IMPLEMENTS_SEPARATOR(","),
  /**
   * The end of the implements block.
   */
  IMPLEMENTS_BLOCK_END,
  /**
   * The start of type's name in the extends or implements statements.
   */
  INHERITED_ELEMENT_BEGIN,
  /**
   * The type's name in the extends or implements statements.
   */
  INHERITED_ELEMENT_TYPE,
  /**
   * The end of type's name in the extends or implements statements.
   */
  INHERITED_ELEMENT_END,
  /**
   * The start of a class definition.
   */
  CLASS_DEFINITION_BLOCK_BEGIN,
  /**
   * The keyword of the class.
   */
  CLASS_KEYWORD("class", true),
  /**
   * Name of the class.
   */
  CLASS_NAME,
  /**
   * The start of a class body.
   */
  CLASS_BODY_BEGIN("{"),
  /**
   * The end of a class body.
   */
  CLASS_BODY_END("}"),
  /**
   * The end of a class definition.
   */
  CLASS_DEFINITION_BLOCK_END,
  /**
   * The start of methods' logical block.
   */
  CLASS_METHODS_BLOCK_BEGIN,
  /**
   * The logical separator of the methods.
   */
  CLASS_METHODS_SEPARATOR,
  /**
   * The end of methods' logical block.
   */
  CLASS_METHODS_BLOCK_END,
  /**
   * The start of constructors' logical block.
   */
  CLASS_CONSTRUCTORS_BLOCK_BEGIN,
  /**
   * The logical separator of the constructors.
   */
  CLASS_CONSTRUCTORS_SEPARATOR,
  /**
   * The end of constructors' logical block.
   */
  CLASS_CONSTRUCTORS_BLOCK_END,
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
  IMPORT_KEYWORD("import", true),
  /**
   * The type of the import's operator.
   */
  IMPORT_TYPE,
  /**
   * The end of import's operator.
   */
  IMPORT_END,
  /**
   * The end of imports.
   */
  IMPORT_BLOCK_END,
  /**
   * The start of variable definition.
   */
  VARIABLE_DEFINITION_BEGIN,
  /**
   * Type of the variable.
   */
  VARIABLE_DEFINITION_TYPE,
  /**
   * Name of the variable.
   */
  VARIABLE_DEFINITION_NAME,
  /**
   * The end of variable definition.
   */
  VARIABLE_DEFINITION_END,
  /**
   * The start of variable assignment.
   */
  VARIABLE_ASSIGN_BEGIN,
  /**
   * Name of the variable.
   */
  VARIABLE_ASSIGN_NAME,
  /**
   * The end of variable assignment.
   */
  VARIABLE_ASSIGN_END,
  /**
   * The start of variable's method invocation.
   */
  VARIABLE_METHOD_INVOKE_BEGIN,
  /**
   * A name of the variable.
   */
  VARIABLE_METHOD_INVOKE_NAME,
  /**
   * A separator between variable's name and invokable method.
   */
  VARIABLE_METHOD_INVOKE_SEPARATOR("."),
  /**
   * The end of variable's method invocation.
   */
  VARIABLE_METHOD_INVOKE_END,
  /**
   * The start of assign operator.
   */
  ASSIGN_BEGIN,
  /**
   * The keyword of assign operator.
   */
  ASSIGN_OPERATOR("="),
  /**
   * A value of assign operator.
   */
  ASSIGN_VALUE,
  /**
   * The end of assign operator.
   */
  ASSIGN_END,
  /**
   * Java's end of expression.
   */
  END_EXPRESSION_OPERATOR(";");

  private final String value;
  private final boolean keyword;

  JavaElementType() {
    this("", false);
  }

  JavaElementType(String value) {
    this(value, false);
  }

  JavaElementType(String value, boolean keyword) {
    this.value = value;
    this.keyword = keyword;
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
   * Returns true when type is a keyword, otherwise false.
   *
   * @return keyword's flag
   */
  public boolean isKeyword() {
    return keyword;
  }

  /**
   * Converts type to the element for rendering.
   *
   * @return the element for rendering
   */
  public JavaElement toElement() {
    return (target) -> target.append(this);
  }
}
