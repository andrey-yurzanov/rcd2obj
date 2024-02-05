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

package org.rcd2obj.annotation.meta;

import org.rcd2obj.annotation.Table;
import org.rcd2obj.annotation.meta.alias.TableNameAlias;
import org.rcd2obj.annotation.naming.NamingStrategy;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The meta-information of the table. Contains information from {@link Table} and marked type.
 *
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public class TableMetaInfo {
  private final Class<?> type;
  private final Collection<TableNameAlias> aliases;
  private final Collection<ColumnMetaInfo> columns;

  /**
   * Constructs of new instance {@link TableMetaInfo}.
   *
   * @param type    the marked type
   * @param aliases the list of a table's names
   * @param columns the list of a table's columns
   */
  public TableMetaInfo(
      Class<?> type,
      Collection<TableNameAlias> aliases,
      Collection<ColumnMetaInfo> columns
  ) {
    this.type = type;
    this.aliases = aliases;
    this.columns = columns;
  }

  /**
   * Returns type of the annotated entity.
   *
   * @return type of the annotated entity
   */
  public Class<?> getType() {
    return type;
  }

  /**
   * Returns the names list of the table.
   *
   * @return the names list of the table
   */
  public Collection<TableNameAlias> getAliases() {
    return aliases;
  }

  /**
   * Returns the columns list of the table.
   *
   * @return the columns list of the table
   */
  public Collection<ColumnMetaInfo> getColumns() {
    return columns;
  }

  /**
   * Implementation of {@link MetaInfoFactory} for {@link TableMetaInfo} creating.
   *
   * @author Andrey_Yurzanov
   * @since 1.0
   */
  public static class Factory implements MetaInfoFactory<Class<?>, TableMetaInfo> {
    private final MetaInfoContext context;

    /**
     * Construct new instance of {@link Factory}.
     *
     * @param context the context of meta-information extracting
     */
    public Factory(MetaInfoContext context) {
      this.context = context;
    }

    @Override
    public Optional<TableMetaInfo> build(Class<?> source) {
      Table[] annotations = source.getAnnotationsByType(Table.class);
      if (annotations.length > 0) {
        String typeName = source.getSimpleName();

        Set<TableNameAlias> aliases = new HashSet<>();
        for (Table annotation : annotations) {
          NamingStrategy naming = context.get(annotation.naming());
          NamingStrategy combining = context.get(annotation.combining());

          String name = annotation.value();
          if (name.isEmpty()) {
            name = typeName;
          }
          aliases.add(new TableNameAlias(name, naming, combining));
        }

        Set<ColumnMetaInfo> columns = new HashSet<>();
        TableMetaInfo table = new TableMetaInfo(source, aliases, columns);

        ColumnMetaInfo.Factory factory = new ColumnMetaInfo.Factory(table);
        Field[] fields = source.getDeclaredFields();
        for (Field field : fields) {
          Optional<ColumnMetaInfo> built = factory.build(field);
          if (built.isPresent()) {
            columns.add(built.get());
          }
        }

        return Optional.of(table);
      }
      return Optional.empty();
    }
  }
}
