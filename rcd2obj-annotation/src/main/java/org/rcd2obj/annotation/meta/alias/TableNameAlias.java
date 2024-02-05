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

package org.rcd2obj.annotation.meta.alias;

import org.rcd2obj.annotation.naming.NamingStrategy;
import java.util.Objects;

/**
 * The name of a table's.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class TableNameAlias {
  private final String name;
  private final String originalName;
  private final NamingStrategy naming;
  private final NamingStrategy combining;

  /**
   * Constructs a new instance of {@link TableNameAlias}.
   *
   * @param originalName the original name of the table
   * @param naming       the strategy of table naming
   * @param combining    the strategy of names combining
   */
  public TableNameAlias(String originalName, NamingStrategy naming, NamingStrategy combining) {
    this.originalName = originalName;
    this.naming = naming;
    this.combining = combining;
    this.name = naming.resolve(originalName);
  }

  /**
   * Returns the modified name of the table.
   *
   * @return the modified name of the table
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the original name of the table.
   *
   * @return the original name of the table
   */
  public String getOriginalName() {
    return originalName;
  }

  /**
   * Returns the strategy of table naming.
   *
   * @return the strategy of table naming
   */
  public NamingStrategy getNaming() {
    return naming;
  }

  /**
   * Returns the strategy of names combining.
   *
   * @return the strategy of names combining
   */
  public NamingStrategy getCombining() {
    return combining;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (other == null || getClass() != other.getClass()) {
      return false;
    }

    TableNameAlias alias = (TableNameAlias) other;
    return Objects.equals(name, alias.name) && Objects.equals(originalName, alias.originalName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, originalName);
  }
}
