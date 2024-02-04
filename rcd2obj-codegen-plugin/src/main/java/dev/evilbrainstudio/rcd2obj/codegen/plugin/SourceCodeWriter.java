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

package dev.evilbrainstudio.rcd2obj.codegen.plugin;

import dev.evilbrainstudio.rcd2obj.annotation.meta.TableMetaInfo;

import javax.inject.Named;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Writer of generated source code.
 *
 * @author Andrey_Yurzanov
 */
@Named
public class SourceCodeWriter {
  private static final String PACKAGE_SEPARATOR = ".";
  private static final String FILE_EXTENSION = ".java";
  private static final String PATH_SEPARATOR = getPathSeparator();

  /**
   * Writes generated source code.
   *
   * @param root       path without name of the file and package
   * @param info       annotated entity's meta information
   * @param sourceCode generated source code
   * @throws IOException when can't write to file
   */
  public void write(Path root, TableMetaInfo info, JavaSourceCode sourceCode) throws IOException {
    Class<?> type = info.getType();
    String packageName = type.getName().replace("." + type.getSimpleName(), "");

    Path directory = Paths.get(root.toString(), packageName.replace(PACKAGE_SEPARATOR, PATH_SEPARATOR));
    if (!Files.exists(directory)) {
      Files.createDirectories(directory);
    }

    Path filePath = Paths.get(directory.toString(), sourceCode.getName() + FILE_EXTENSION);
    try (Writer out = new FileWriter(filePath.toFile())) {
      out.write(sourceCode.getCode());
      out.flush();
    }
  }

  // Returns path's separator of current os
  private static String getPathSeparator() {
    FileSystem fileSystem = FileSystems.getDefault();
    return fileSystem.getSeparator();
  }
}
