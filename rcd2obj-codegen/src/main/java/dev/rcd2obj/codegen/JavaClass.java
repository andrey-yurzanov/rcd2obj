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

package dev.rcd2obj.codegen;

import dev.rcd2obj.codegen.constructor.JavaClassConstructorDefinition;
import dev.rcd2obj.codegen.inherited.JavaInheritableElement;
import dev.rcd2obj.codegen.method.JavaClassMethodDefinition;
import dev.rcd2obj.codegen.modifier.JavaModifier;
import dev.rcd2obj.codegen.modifier.JavaPublicModifier;
import dev.rcd2obj.codegen.render.JavaClassBufferRender;
import dev.rcd2obj.codegen.render.JavaElementRender;
import dev.rcd2obj.codegen.type.JavaNameType;

import java.util.Collection;
import java.util.TreeSet;

/**
 * The renderer of the Java class.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class JavaClass implements JavaElement {
  private String className;
  private JavaClassPackage classPackage;
  private JavaModifier classAccessModifier = new JavaPublicModifier();
  private Collection<JavaInheritableElement> classImplements;
  private Collection<JavaClassConstructorDefinition> classConstructors;
  private Collection<JavaClassMethodDefinition> classMethods;

  /**
   * Constructs new instance of the renderer by class's name.
   *
   * @param className name of the class
   */
  public JavaClass(String className) {
    this.className = className;
  }

  /**
   * Sets name of the class.
   *
   * @param className name of the class
   * @return current instance of renderer
   */
  public JavaClass setClassName(String className) {
    this.className = className;

    if (classConstructors != null && !classConstructors.isEmpty()) {
      JavaNameType type = new JavaNameType(className);
      for (JavaClassConstructorDefinition classConstructor : classConstructors) {
        // TODO. Need fix
//        classConstructor.setConstructorType(type);
      }
    }
    return this;
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
   * Sets package of the class.
   *
   * @param classPackage package of the class
   * @return current instance of renderer
   */
  public JavaClass setClassPackage(JavaClassPackage classPackage) {
    this.classPackage = classPackage;
    return this;
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
   * Sets class's access modifier.
   *
   * @param accessModifier class's access modifier
   * @return current instance of renderer
   */
  public JavaClass setClassAccessModifier(JavaModifier accessModifier) {
    this.classAccessModifier = accessModifier;
    return this;
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
   * Sets interfaces to be implemented by the class.
   *
   * @param classImplements implemented interfaces
   * @return current instance of renderer
   * @throws IllegalArgumentException when params aren't interfaces
   */
  public JavaClass setClassImplements(JavaInheritableElement... classImplements) {
    if (this.classImplements == null) {
      this.classImplements = new TreeSet<>();
    }

    for (JavaInheritableElement classImplement : classImplements) {
      if (classImplement.isInterface()) {
        this.classImplements.add(classImplement);

        setClassMethods(classImplement.getInheritedMethods());
      } else {
        throw new IllegalArgumentException(
          String.format("[%s] is not interface", classImplement.getType())
        );
      }
    }
    return this;
  }

  /**
   * Returns implemented interfaces.
   *
   * @return implemented interfaces
   */
  public Collection<JavaInheritableElement> getClassImplements() {
    return classImplements;
  }

  /**
   * Sets constructors of the class.
   *
   * @param classConstructors constructors of the class
   * @return current instance of renderer
   */
  public JavaClass setClassConstructors(JavaClassConstructorDefinition... classConstructors) {
    if (this.classConstructors == null) {
      this.classConstructors = new TreeSet<>();
    }

    JavaNameType type = new JavaNameType(className);
    for (JavaClassConstructorDefinition classConstructor : classConstructors) {
//      classConstructor.setConstructorType(type);
      // TODO. Need fix
      this.classConstructors.add(classConstructor);
    }
    return this;
  }

  /**
   * Returns constructors of the class.
   *
   * @return constructors of the class
   */
  public Collection<JavaClassConstructorDefinition> getClassConstructors() {
    return classConstructors;
  }

  /**
   * Sets methods of the class.
   *
   * @param classMethods class's methods
   * @return current instance of renderer
   */
  public JavaClass setClassMethods(Collection<JavaClassMethodDefinition> classMethods) {
    if (this.classMethods == null) {
      this.classMethods = new TreeSet<>();
    }
    this.classMethods.addAll(classMethods);

    return this;
  }

  /**
   * Returns methods of the class.
   *
   * @return methods of the class
   */
  public Collection<JavaClassMethodDefinition> getClassMethods() {
    return classMethods;
  }

  @Override
  public void render(JavaElementRender target) {
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
