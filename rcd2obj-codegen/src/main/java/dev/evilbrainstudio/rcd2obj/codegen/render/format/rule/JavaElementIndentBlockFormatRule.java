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

package dev.evilbrainstudio.rcd2obj.codegen.render.format.rule;

import dev.evilbrainstudio.rcd2obj.codegen.JavaElement;
import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;

/**
 * The formatting rule for append indents.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaElementIndentBlockFormatRule implements JavaElement {
  private final JavaElementType begin;
  private final JavaElementType end;
  private final String indent;
  private final int indentCount;

  private static final String DEFAULT_INDENT = " ";
  private static final short DEFAULT_INDENT_COUNT = 2;

  /**
   * Constructs new instance of indenting rule.
   *
   * @param begin begin of indenting block
   * @param end   end of indenting block
   */
  public JavaElementIndentBlockFormatRule(JavaElementType begin, JavaElementType end) {
    this(begin, end, DEFAULT_INDENT, DEFAULT_INDENT_COUNT);
  }

  /**
   * Constructs new instance of indenting rule.
   *
   * @param begin       begin of indenting block
   * @param end         end of indenting block
   * @param indent      indenting symbols
   * @param indentCount count of indents
   */
  public JavaElementIndentBlockFormatRule(
    JavaElementType begin,
    JavaElementType end,
    String indent,
    int indentCount
  ) {
    this.begin = begin;
    this.end = end;
    this.indent = indent;
    this.indentCount = indentCount;
  }

  /**
   * Returns begin of indenting block.
   *
   * @return begin of indenting block
   */
  public JavaElementType getBegin() {
    return begin;
  }

  /**
   * Returns end of indenting block.
   *
   * @return end of indenting block
   */
  public JavaElementType getEnd() {
    return end;
  }

  /**
   * Returns indenting symbols.
   *
   * @return indenting symbols
   */
  public String getIndent() {
    return indent;
  }

  @Override
  public void render(JavaElementRender target) {
    for (short i = 0; i < indentCount; i++) {
      target.append(indent);
    }
  }
}
