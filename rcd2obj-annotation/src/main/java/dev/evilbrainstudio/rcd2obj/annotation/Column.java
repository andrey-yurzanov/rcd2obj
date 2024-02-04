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

package dev.evilbrainstudio.rcd2obj.annotation;

import dev.evilbrainstudio.rcd2obj.annotation.Column.Columns;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation helps to describe the data extraction. It's not required by default, the {@link
 * Table} annotation is enough. When a field is not marked, then uses the name of the field.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
@Documented
@Repeatable(Columns.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
  /**
   * The name of the column that is the data source for mapping.
   */
  String value() default "";

  /**
   * Container of {@link Column} annotations.
   *
   * @author Andrey_Yurzanov
   * @since 1.0
   */
  @Documented
  @Target(ElementType.FIELD)
  @Retention(RetentionPolicy.RUNTIME)
  @interface Columns {
    /**
     * Set of {@link Column} annotations.
     */
    Column[] value();
  }
}
