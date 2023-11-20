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

package dev.evilbrainstudio.rcd2obj.codegen.render.format;

import dev.evilbrainstudio.rcd2obj.codegen.JavaElementType;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementRender;
import dev.evilbrainstudio.rcd2obj.codegen.render.format.rule.JavaElementFormatLineAfterRule;
import dev.evilbrainstudio.rcd2obj.codegen.render.format.rule.JavaElementFormatLineBeforeRule;
import dev.evilbrainstudio.rcd2obj.codegen.render.format.rule.JavaElementFormatRule;
import dev.evilbrainstudio.rcd2obj.codegen.render.format.rule.JavaElementFormatSpaceAfterRule;
import dev.evilbrainstudio.rcd2obj.codegen.render.format.rule.JavaElementFormatSpaceBeforeRule;
import dev.evilbrainstudio.rcd2obj.codegen.render.format.rule.JavaElementIndentBlockFormatRule;
import dev.evilbrainstudio.rcd2obj.codegen.render.format.rule.condition.JavaElementFormatKeywordRuleCondition;
import dev.evilbrainstudio.rcd2obj.codegen.render.format.rule.condition.JavaElementFormatRuleAnyCondition;
import dev.evilbrainstudio.rcd2obj.codegen.render.format.rule.condition.JavaElementFormatRuleIsCondition;
import dev.evilbrainstudio.rcd2obj.codegen.render.format.rule.condition.group.JavaElementFormatRuleConditionAndGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Render for formatting the input elements' stream.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaElementFormatRender implements JavaElementRender {
  private JavaElementType current;
  private JavaElementType next;
  private final JavaElementRender target;
  private final Collection<JavaElementFormatRule> rules;
  private final Collection<JavaElementIndentBlockFormatRule> indentBlocks;
  private final LinkedList<JavaElementIndentBlockFormatRule> currentIndents;

  private static final short LINES_COUNT = 2;
  private static final String NEW_LINE = System.lineSeparator();

  /**
   * Constructs new instance of the renderer, uses the default formatting rules.
   *
   * @param target target for the formatted elements
   */
  public JavaElementFormatRender(JavaElementRender target) {
    this(target, new ArrayList<>(), new ArrayList<>());
    this.rules.addAll(getDefaultRules());
    this.indentBlocks.addAll(getDefaultIndentBlocks());
  }

  /**
   * Constructs new instance of the renderer, uses user's formatting rules.
   *
   * @param target       target for the formatted elements
   * @param rules        user's formatting rules
   * @param indentBlocks user's indenting rules
   */
  public JavaElementFormatRender(
    JavaElementRender target,
    Collection<JavaElementFormatRule> rules,
    Collection<JavaElementIndentBlockFormatRule> indentBlocks
  ) {
    this.target = target;
    this.rules = rules;
    this.indentBlocks = indentBlocks;
    this.currentIndents = new LinkedList<>();
  }

  @Override
  public JavaElementRender append(String... elements) {
    for (String element : elements) {
      target.append(element);

      if (element.contains(NEW_LINE)) {
        target.append(currentIndents);
      }
    }
    return this;
  }

  @Override
  public JavaElementRender append(JavaElementType type) {
    JavaElementType previous = current;
    current = next;
    next = type;

    if (current != null) {
      for (JavaElementIndentBlockFormatRule indent : indentBlocks) {
        JavaElementType begin = indent.getBegin();
        JavaElementType end = indent.getEnd();

        if (current.equals(begin)) {
          currentIndents.push(indent);
        } else if (current.equals(end)) {
          currentIndents.pop();
        }
      }

      for (JavaElementFormatRule rule : rules) {
        rule.before(previous, current, next, this);
      }
      append(current.getValue());

      for (JavaElementFormatRule rule : rules) {
        rule.after(previous, current, next, this);
      }
    }
    return this;
  }

  @Override
  public JavaElementRender append(Class<?> classType) {
    target.append(classType);
    return this;
  }

  @Override
  public JavaElementRender append(Object value, Class<?> valueType) {
    target.append(valueType, valueType);
    return this;
  }

  /**
   * Default formatting rules.
   *
   * @return default formatting rules
   */
  protected Collection<JavaElementFormatRule> getDefaultRules() {
    JavaElementFormatRuleAnyCondition anyRule = new JavaElementFormatRuleAnyCondition();
    ArrayList<JavaElementFormatRule> rules = new ArrayList<>(getDefaultSpaceRules(anyRule));
    rules.addAll(getDefaultLineRules(anyRule));

    return rules;
  }

  /**
   * Default indenting rules.
   *
   * @return default indenting rules
   */
  protected Collection<JavaElementIndentBlockFormatRule> getDefaultIndentBlocks() {
    return Arrays.asList(
      new JavaElementIndentBlockFormatRule(
        JavaElementType.CLASS_METHODS_BLOCK_BEGIN,
        JavaElementType.CLASS_METHODS_BLOCK_END
      ),
      new JavaElementIndentBlockFormatRule(
        JavaElementType.METHOD_IMPL_BLOCK_BEGIN,
        JavaElementType.METHOD_IMPL_BLOCK_END
      )
    );
  }

  private Collection<JavaElementFormatRule> getDefaultSpaceRules(JavaElementFormatRuleAnyCondition anyRule) {
    return Arrays.asList(
      new JavaElementFormatSpaceBeforeRule(
        new JavaElementFormatRuleConditionAndGroup(
          anyRule,
          new JavaElementFormatRuleIsCondition(
            JavaElementType.CLASS_BODY_BEGIN,
            JavaElementType.IMPLEMENTS_KEYWORD,
            JavaElementType.METHOD_IMPL_BLOCK_BEGIN
          ),
          anyRule
        )
      ),
      new JavaElementFormatSpaceAfterRule(
        new JavaElementFormatRuleConditionAndGroup(
          anyRule,
          new JavaElementFormatRuleIsCondition(
            JavaElementType.IMPLEMENTS_SEPARATOR,
            JavaElementType.NEW_TYPE_PARAMS_SEPARATOR,
            JavaElementType.METHOD_PARAMS_SEPARATOR,
            JavaElementType.METHOD_RETURN_TYPE
          ),
          anyRule
        )
      ),
      new JavaElementFormatSpaceAfterRule(
        new JavaElementFormatRuleConditionAndGroup(
          anyRule,
          new JavaElementFormatRuleIsCondition(
            JavaElementType.PARAMETER_TYPE
          ),
          new JavaElementFormatRuleIsCondition(
            JavaElementType.PARAMETER_NAME
          )
        )
      ),
      new JavaElementFormatSpaceAfterRule(
        new JavaElementFormatRuleConditionAndGroup(
          anyRule,
          new JavaElementFormatKeywordRuleCondition(),
          anyRule
        )
      )
    );
  }

  private Collection<JavaElementFormatRule> getDefaultLineRules(JavaElementFormatRuleAnyCondition anyRule) {
    return Arrays.asList(new JavaElementFormatLineBeforeRule(
        new JavaElementFormatRuleConditionAndGroup(
          anyRule,
          new JavaElementFormatRuleIsCondition(
            JavaElementType.METHOD_IMPL_BLOCK_END
          ),
          anyRule
        )
      ),
      new JavaElementFormatLineBeforeRule(
        LINES_COUNT,
        new JavaElementFormatRuleConditionAndGroup(
          anyRule,
          new JavaElementFormatRuleIsCondition(
            JavaElementType.CLASS_METHODS_BLOCK_BEGIN,
            JavaElementType.CLASS_METHODS_SEPARATOR,
            JavaElementType.CLASS_METHODS_BLOCK_END
          ),
          anyRule
        )
      ),
      new JavaElementFormatLineAfterRule(
        LINES_COUNT,
        new JavaElementFormatRuleConditionAndGroup(
          anyRule,
          new JavaElementFormatRuleIsCondition(
            JavaElementType.PACKAGE_END
          ),
          anyRule
        )
      ),
      new JavaElementFormatLineAfterRule(
        new JavaElementFormatRuleConditionAndGroup(
          anyRule,
          new JavaElementFormatRuleIsCondition(
            JavaElementType.IMPORT_END,
            JavaElementType.IMPORT_BLOCK_END,
            JavaElementType.METHOD_IMPL_BLOCK_BEGIN
          ),
          anyRule
        )
      )
    );
  }
}
