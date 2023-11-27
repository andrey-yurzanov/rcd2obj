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

package dev.evilbrainstudio.rcd2obj.codegen.plugin;

import dev.evilbrainstudio.rcd2obj.annotation.mapping.Mapper;
import dev.evilbrainstudio.rcd2obj.annotation.meta.TableMetaInfo;
import dev.evilbrainstudio.rcd2obj.codegen.JavaClass;
import dev.evilbrainstudio.rcd2obj.codegen.JavaClassPackage;
import dev.evilbrainstudio.rcd2obj.codegen.inherited.JavaInheritableElement;
import dev.evilbrainstudio.rcd2obj.codegen.render.JavaElementWriteRender;
import dev.evilbrainstudio.rcd2obj.codegen.render.format.JavaElementFormatRender;
import org.apache.maven.plugin.logging.Log;

import java.io.StringWriter;

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
  public JavaSourceCode(Log log, TableMetaInfo info) {
    Class<?> type = info.getType();
    this.name = type.getSimpleName().concat("StubMapper");
    this.writer = new StringWriter();

    log.info(type.getName());
    log.info(type.getSimpleName());
    log.info(type.getName().replace("." + type.getSimpleName(), ""));

    this.javaClass = new JavaClass(name)
      .setClassImplements(new JavaInheritableElement(Mapper.class));
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
