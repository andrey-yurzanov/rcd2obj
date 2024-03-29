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

package org.rcd2obj.codegen.plugin;

import org.rcd2obj.annotation.meta.TableMetaInfo;

import javax.inject.Named;

/**
 * Generator of Java's source code.
 *
 * @author Andrey_Yurzanov
 */
@Named
public class JavaSourceCodeGenerator {
  /**
   * Generates source code by information about annotated entity.
   *
   * @param info information about annotated entity
   */
  public JavaSourceCode generate(TableMetaInfo info) {
    return new JavaSourceCode(info);
  }
}
