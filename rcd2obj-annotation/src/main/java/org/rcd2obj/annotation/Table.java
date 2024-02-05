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

package org.rcd2obj.annotation;

import org.rcd2obj.annotation.Table.Tables;
import org.rcd2obj.annotation.naming.NamingStrategy;
import org.rcd2obj.annotation.naming.PointCombineStrategy;
import org.rcd2obj.annotation.naming.SnakeCaseStrategy;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation to indicate that the object marked is a target for data mapping.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
@Documented
@Repeatable(Tables.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
  /**
   * The name of the table that is the data source for mapping.
   */
  String value() default "";

  /**
   * Naming strategy for data extraction. By default {@link SnakeCaseStrategy}.
   */
  Class<? extends NamingStrategy> naming() default SnakeCaseStrategy.class;

  /**
   * Naming strategy for names combination. By default {@link PointCombineStrategy}.
   */
  Class<? extends NamingStrategy> combining() default PointCombineStrategy.class;

  /**
   * Container of {@link Table} annotations.
   *
   * @author Andrey_Yurzanov
   * @since 1.0
   */
  @Documented
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @interface Tables {
    /**
     * Set of {@link Table} annotations.
     */
    Table[] value();
  }
}
