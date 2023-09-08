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

package dev.evilbrainstudio.rcd2obj.codegen.render;

import java.io.IOException;
import java.io.Writer;

/**
 * The renderer for appends data to writer.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaElementWriteRender extends JavaElementTypeRender {
  private final Writer writer;

  /**
   * Constructs new instance of the render.
   *
   * @param writer target for data writing
   */
  public JavaElementWriteRender(Writer writer) {
    super();
    this.writer = writer;
  }

  @Override
  public JavaElementRender append(String... elements) {
    try {
      for (String element : elements) {
        writer.write(element);
      }
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
    return this;
  }
}
