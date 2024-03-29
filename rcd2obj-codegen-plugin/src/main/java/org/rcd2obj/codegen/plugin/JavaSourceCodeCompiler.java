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

import javax.inject.Named;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Java code compiler, uses standard Java compiler API.
 *
 * @author Andrey_Yurzanov
 */
@Named
public class JavaSourceCodeCompiler {
  /**
   * Compiles the Java code.
   *
   * @param root              directory for compiled class
   * @param classpathElements Java's classpath
   * @param sourceCode        code for compiling
   */
  public void compile(File root, Collection<String> classpathElements, JavaSourceCode sourceCode) throws IOException {
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
    manager.setLocation(
      StandardLocation.CLASS_OUTPUT,
      Collections.singletonList(root)
    );
    manager.setLocation(
      StandardLocation.CLASS_PATH,
      classpathElements.stream().map(File::new).collect(Collectors.toList())
    );

    JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, Collections.singletonList(
      new SimpleJavaFileObject(
        URI.create(String.join("", "string:///", sourceCode.getName(), ".java")),
        JavaFileObject.Kind.SOURCE) {
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors)
          throws IOException {
          return sourceCode.getCode();
        }
      }
    ));
    task.call();
  }
}
