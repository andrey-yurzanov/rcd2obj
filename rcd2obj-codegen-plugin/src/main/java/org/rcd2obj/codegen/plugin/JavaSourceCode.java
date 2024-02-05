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

import org.rcd2obj.annotation.mapping.Mapper;
import org.rcd2obj.annotation.meta.TableMetaInfo;
import org.rcd2obj.codegen.JavaClass;
import org.rcd2obj.codegen.JavaClassPackage;
import org.rcd2obj.codegen.inherited.JavaInheritableElement;
import org.rcd2obj.codegen.modifier.JavaPublicModifier;
import org.rcd2obj.codegen.render.JavaElementWriteRender;
import org.rcd2obj.codegen.render.format.JavaElementFormatRender;

import java.io.StringWriter;
import java.util.Collections;

/**
 * The generated Java's source code.
 *
 * @author Andrey_Yurzanov
 */
public class JavaSourceCode {
  private final StringWriter writer;
  private final String name;
  private final JavaClass javaClass;

  /**
   * Constructs new instance of Java's source code.
   *
   * @param info information about annotated entity
   */
  public JavaSourceCode(TableMetaInfo info) {
    Class<?> type = info.getType();
    this.name = type.getSimpleName().concat("StubMapper");
    this.writer = new StringWriter();
    this.javaClass = new JavaClass(
      name,
      new JavaClassPackage(type.getName().replace("." + type.getSimpleName(), "")),
      new JavaPublicModifier(),
      Collections.singletonList(new JavaInheritableElement(Mapper.class)),
      null,
      null
    );
  }

  /**
   * Returns generated source code.
   *
   * @return generated source code
   */
  public String getCode() {
    String code = writer.toString();
    if (code.isEmpty()) {
      javaClass.render(new JavaElementFormatRender(new JavaElementWriteRender(writer)));
    }
    return writer.toString();
  }

  /**
   * Returns name of the generated class.
   *
   * @return name of the generated class
   */
  public String getName() {
    return name;
  }
}
