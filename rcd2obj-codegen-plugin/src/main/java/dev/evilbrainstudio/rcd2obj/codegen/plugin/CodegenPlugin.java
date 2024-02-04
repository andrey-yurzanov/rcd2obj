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

import dev.evilbrainstudio.rcd2obj.annotation.meta.MetaInfoContext;
import dev.evilbrainstudio.rcd2obj.annotation.meta.TableMetaInfo;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * This mojo generates code by annotations. It has steps:
 * <br/>
 * Step 1. Extracting of meta information.
 * <br/>
 * Step 2. Generating source code.
 * <br/>
 * Step 3. Writing source code to the file.
 * <br/>
 * Step 4. Compiling source code.
 * <br/>
 * It's first simplify realization, will be change in next iterations.
 *
 * @author Andrey_Yurzanov
 */
@Mojo(
  name = "rcd2obj-codegen",
  defaultPhase = LifecyclePhase.PROCESS_CLASSES,
  requiresDependencyResolution = ResolutionScope.COMPILE
)
public class CodegenPlugin extends AbstractMojo {
  @Parameter(defaultValue = "${project.build.directory}/classes", readonly = true)
  private File classesDirectory;
  @Parameter(defaultValue = "${project.build.directory}/test-classes", readonly = true)
  private File testClassesDirectory;
  @Parameter(defaultValue = "${project.build.directory}/generated-sources/annotations", readonly = true)
  private File generatedSources;
  @Parameter(property = "directories")
  private List<File> directories;
  @Parameter(
    defaultValue = "${project.compileClasspathElements}"
  )
  private List<String> classpathElements;

  @Component
  private PathClassLoader loader;
  @Component
  private JavaSourceCodeCompiler compiler;
  @Component
  private SourceCodeWriter sourceCodeWriter;
  @Component
  private JavaSourceCodeGenerator generator;
  private final TableMetaInfo.Factory metaFactory = new TableMetaInfo.Factory(new MetaInfoContext());

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (directories == null || directories.isEmpty()) {
      directories = Arrays.asList(
        classesDirectory,
        testClassesDirectory
      );
    }

    Log log = getLog();
    try {
      for (File directory : directories) {
        Path root = directory.toPath();
        log.info(String.join("", "Start scanning [", root.toString(), "]"));

        JavaClassVisitor visitor = new JavaClassVisitor();
        Files.walkFileTree(root, visitor);

        for (Path path : visitor.getJavaClasses()) {
          Class<?> loaded = loader.loadClass(root, path);

          // Step 1. Extracting of meta information
          Optional<TableMetaInfo> value = metaFactory.build(loaded);
          if (value.isPresent()) {
            log.info(String.join("", "Start processing [", path.toString(), "]"));

            // Step 2. Generating source code
            TableMetaInfo info = value.get();
            JavaSourceCode sourceCode = generator.generate(value.get());

            // Step 3. Writing source code
            sourceCodeWriter.write(generatedSources.toPath(), info, sourceCode);

            // Step 4. Compiling source code
            compiler.compile(directory, classpathElements, sourceCode);
          } else {
            log.info(String.join("", "Skipped [", path.toString(), "]"));
          }
        }

        log.info(String.join("", "Finish scanning [", root.toString(), "]"));
      }
    } catch (Exception exception) {
      log.error("Class files processing error", exception);
      throw new RuntimeException(exception);
    }
  }
}
