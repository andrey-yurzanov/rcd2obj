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

package dev.rcd2obj.annotation.meta;

import dev.rcd2obj.annotation.Column;
import dev.rcd2obj.annotation.meta.alias.ColumnNameAlias;
import dev.rcd2obj.annotation.meta.alias.TableNameAlias;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The meta-information of the column. Contains information from {@link Column} and marked
 * attribute.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class ColumnMetaInfo {
  private final Field field;
  private final Collection<ColumnNameAlias> aliases;

  /**
   * Constructs of new instance column's meta-information.
   *
   * @param field   a marked attribute
   * @param aliases a list of the column names
   */
  public ColumnMetaInfo(Field field, Collection<ColumnNameAlias> aliases) {
    this.field = field;
    this.aliases = aliases;
  }

  /**
   * Returns the marked attribute.
   *
   * @return the marked attribute
   */
  public Field getField() {
    return field;
  }

  /**
   * The list of the column names.
   *
   * @return the list of the column names
   */
  public Collection<ColumnNameAlias> getAliases() {
    return aliases;
  }

  /**
   * Implementation of {@link MetaInfoFactory} for {@link ColumnMetaInfo} creating.
   *
   * @author Andrey_Yurzanov
   * @since 1.0
   */
  public static class Factory implements MetaInfoFactory<Field, ColumnMetaInfo> {
    private final TableMetaInfo table;

    /**
     * Construct new instance of {@link Factory}.
     *
     * @param table the meta-information of a table
     */
    public Factory(TableMetaInfo table) {
      this.table = table;
    }

    @Override
    public Optional<ColumnMetaInfo> build(Field source) {
      int modifiers = source.getModifiers();
      if (!Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers)) {
        Column[] annotations = source.getAnnotationsByType(Column.class);
        Collection<TableNameAlias> tableAliases = table.getAliases();

        String fieldName = source.getName();
        Set<ColumnNameAlias> aliases = new HashSet<>();
        for (TableNameAlias tableAlias : tableAliases) {
          if (annotations.length == 0) {
            aliases.add(new ColumnNameAlias(fieldName, tableAlias));
          } else {
            for (Column annotation : annotations) {
              String name = annotation.value();
              if (name.isEmpty()) {
                name = fieldName;
              }

              aliases.add(new ColumnNameAlias(name, tableAlias));
            }
          }
        }
        return Optional.of(new ColumnMetaInfo(source, aliases));
      }
      return Optional.empty();
    }
  }
}
