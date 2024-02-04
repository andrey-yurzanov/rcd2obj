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

package dev.rcd2obj.annotation.meta.alias;

import dev.rcd2obj.annotation.naming.NamingStrategy;
import java.util.Objects;

/**
 * The name of a table's column.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class ColumnNameAlias {
  private final String name;
  private final String originalName;
  private final TableNameAlias tableAlias;

  /**
   * Constructs a new instance of {@link ColumnNameAlias}.
   *
   * @param originalName the original column name
   * @param tableAlias   the name of a table
   */
  public ColumnNameAlias(String originalName, TableNameAlias tableAlias) {
    NamingStrategy combining = tableAlias.getCombining();
    NamingStrategy naming = tableAlias.getNaming();

    this.originalName = originalName;
    this.tableAlias = tableAlias;
    this.name = combining.resolve(
        tableAlias.getName(),
        naming.resolve(originalName)
    );
  }

  /**
   * Returns the modified name of this column.
   *
   * @return the modified name of this column
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the original name of this column.
   *
   * @return the original name of this column
   */
  public String getOriginalName() {
    return originalName;
  }

  /**
   * Returns the table's name of this column.
   *
   * @return the table's name
   */
  public TableNameAlias getTableAlias() {
    return tableAlias;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (other == null || getClass() != other.getClass()) {
      return false;
    }

    ColumnNameAlias alias = (ColumnNameAlias) other;
    return Objects.equals(name, alias.name) && Objects.equals(originalName, alias.originalName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, originalName);
  }
}
