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

package org.rcd2obj.codegen;

import org.rcd2obj.codegen.constructor.JavaConstructorDefinition;
import org.rcd2obj.codegen.inherited.JavaInheritableElement;
import org.rcd2obj.codegen.method.JavaMethodDefinition;
import org.rcd2obj.codegen.method.JavaMethodUnsupportedImpl;
import org.rcd2obj.codegen.modifier.JavaModifier;
import org.rcd2obj.codegen.render.JavaClassBufferRender;
import org.rcd2obj.codegen.render.JavaElementRender;

import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

/**
 * The renderer of the Java class.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaClass implements JavaElement {
  private final String className;
  private final JavaClassPackage classPackage;
  private final JavaModifier classAccessModifier;
  private final Collection<JavaInheritableElement> classImplements;
  private final Collection<JavaConstructorDefinition> classConstructors;
  private final Collection<JavaMethodDefinition> classMethods;

  /**
   * It creates new instance of the class renderer. The generated class won't have package, constructor and methods.
   *
   * @param className name of the class, it is required
   */
  public JavaClass(String className) {
    this(className, null, null, null, null, null);
  }

  /**
   * It creates new instance of the class renderer. Any types will add to the import block.
   *
   * @param className           name of the class, it is required
   * @param classPackage        package of the class
   * @param classAccessModifier access modifier of the class
   * @param classImplements     interfaces for implements, methods of interfaces will be implements with
   *                            {@link JavaMethodUnsupportedImpl} body
   * @param classConstructors   constructor of the class
   * @param classMethods        methods of the class
   * @throws IllegalArgumentException throws if classImplements contains something other than an interface
   */
  public JavaClass(
    String className,
    JavaClassPackage classPackage,
    JavaModifier classAccessModifier,
    Collection<JavaInheritableElement> classImplements,
    Collection<JavaConstructorDefinition> classConstructors,
    Collection<JavaMethodDefinition> classMethods
  ) {
    this.className = className;
    this.classPackage = classPackage;
    this.classAccessModifier = classAccessModifier;

    if (classConstructors != null) {
      this.classConstructors = new TreeSet<>(classConstructors);
    } else {
      this.classConstructors = null;
    }

    this.classMethods = new TreeSet<>();
    if (classMethods != null) {
      this.classMethods.addAll(classMethods);
    }

    if (classImplements != null) {
      this.classImplements = new TreeSet<>();

      for (JavaInheritableElement classImplement : classImplements) {
        if (classImplement.isInterface()) {
          this.classImplements.add(classImplement);
          this.classMethods.addAll(classImplement.getInheritedMethods());
        } else {
          throw new IllegalArgumentException(
            String.format("[%s] is not interface!", classImplement.getType())
          );
        }
      }
    } else {
      this.classImplements = null;
    }
  }

  /**
   * Returns name of the class.
   *
   * @return name of the class
   */
  public String getClassName() {
    return className;
  }

  /**
   * Returns package of the class.
   *
   * @return package of the class.
   */
  public JavaClassPackage getClassPackage() {
    return classPackage;
  }

  /**
   * Returns class's access modifier.
   *
   * @return class's access modifier
   */
  public JavaModifier getClassAccessModifier() {
    return classAccessModifier;
  }

  /**
   * Returns implemented interfaces.
   *
   * @return implemented interfaces
   */
  public Collection<JavaInheritableElement> getClassImplements() {
    return Collections.unmodifiableCollection(classImplements);
  }

  /**
   * Returns constructors of the class.
   *
   * @return constructors of the class
   */
  public Collection<JavaConstructorDefinition> getClassConstructors() {
    return Collections.unmodifiableCollection(classConstructors);
  }

  /**
   * Returns methods of the class.
   *
   * @return methods of the class
   */
  public Collection<JavaMethodDefinition> getClassMethods() {
    return Collections.unmodifiableCollection(classMethods);
  }

  @Override
  public void render(JavaElementRender target) throws JavaElementRenderingException {
    if (className == null || className.trim().isEmpty()) {
      throw new JavaElementRenderingException("Class name has incorrect value: [$]", className);
    }

    JavaClassBufferRender classRender = new JavaClassBufferRender();
    classRender
      // class signature
      .append(JavaElementType.CLASS_DEFINITION_BLOCK_BEGIN)
      .append(classAccessModifier)
      .append(JavaElementType.CLASS_KEYWORD)
      .append(JavaElementType.CLASS_NAME)
      .append(className);

    // implements
    if (classImplements != null && !classImplements.isEmpty()) {
      classRender
        .append(JavaElementType.IMPLEMENTS_BLOCK_BEGIN)
        .append(JavaElementType.IMPLEMENTS_KEYWORD)
        .append(classImplements, JavaElementType.IMPLEMENTS_SEPARATOR.toElement())
        .append(JavaElementType.IMPLEMENTS_BLOCK_END);
    }

    // constructors
    classRender
      .append(JavaElementType.CLASS_BODY_BEGIN)
      .append(JavaElementType.CLASS_CONSTRUCTORS_BLOCK_BEGIN)
      .append(classConstructors, JavaElementType.CLASS_CONSTRUCTORS_SEPARATOR.toElement())
      .append(JavaElementType.CLASS_CONSTRUCTORS_BLOCK_END);

    // methods
    classRender
      .append(JavaElementType.CLASS_METHODS_BLOCK_BEGIN)
      .append(classMethods, JavaElementType.CLASS_METHODS_SEPARATOR.toElement())
      .append(JavaElementType.CLASS_METHODS_BLOCK_END)
      .append(JavaElementType.CLASS_BODY_END)
      .append(JavaElementType.CLASS_DEFINITION_BLOCK_END);

    target
      // package
      .append(classPackage)
      // imports
      .append(JavaElementType.IMPORT_BLOCK_BEGIN)
      .append(classRender.getImports())
      .append(JavaElementType.IMPORT_BLOCK_END)
      // class
      .append(classRender.getBuffer());
  }
}
