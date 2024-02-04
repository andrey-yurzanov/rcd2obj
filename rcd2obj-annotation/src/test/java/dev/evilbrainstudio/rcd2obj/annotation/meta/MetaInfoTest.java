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

package dev.evilbrainstudio.rcd2obj.annotation.meta;

import dev.evilbrainstudio.rcd2obj.annotation.Table;
import dev.evilbrainstudio.rcd2obj.annotation.meta.TableMetaInfo.Factory;
import dev.evilbrainstudio.rcd2obj.annotation.meta.alias.ColumnNameAlias;
import dev.evilbrainstudio.rcd2obj.annotation.meta.alias.TableNameAlias;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests of {@link TableMetaInfo} and {@link ColumnMetaInfo}.
 *
 * @author Andrey_Yurzanov
 */
class MetaInfoTest {
  private static final String ENTITY_WITH_FIELDS = "entity_with_fields";
  private static final String ENTITY_WITHOUT_FIELDS = "entity_without_fields";
  private static final String TEST_TABLE = "test_table";
  private static final String FLAG = "flag";
  private static final String NUM = "num";

  @Test
  void buildEntityWithoutAnnotationTest() {
    Factory factory = new Factory(new MetaInfoContext());
    Assertions.assertFalse(factory.build(Entity.class).isPresent());
  }

  @Test
  void buildEntityWithFieldsTest() {
    Factory factory = new Factory(new MetaInfoContext());
    Optional<TableMetaInfo> built = factory.build(EntityWithFields.class);
    Assertions.assertTrue(built.isPresent());

    TableMetaInfo metaInfo = built.get();
    Assertions.assertEquals(EntityWithFields.class, metaInfo.getType());

    TableNameAlias[] aliases = metaInfo.getAliases().toArray(new TableNameAlias[]{});
    Assertions.assertEquals(2, aliases.length);
    Assertions.assertTrue(
        Arrays.asList(aliases[0].getName(), aliases[1].getName()).contains(ENTITY_WITH_FIELDS)
    );
    Assertions.assertTrue(
        Arrays.asList(aliases[0].getName(), aliases[1].getName()).contains(TEST_TABLE)
    );

    ColumnMetaInfo[] columns = metaInfo.getColumns().toArray(new ColumnMetaInfo[]{});
    Assertions.assertEquals(2, columns.length);

    ColumnNameAlias[] flagAliases = columns[0].getAliases().toArray(new ColumnNameAlias[]{});
    Assertions.assertEquals(2, flagAliases.length);
    Assertions.assertTrue(
        Arrays
            .asList(flagAliases[0].getName(), flagAliases[1].getName())
            .contains(ENTITY_WITH_FIELDS + "." + FLAG)
    );
    Assertions.assertTrue(
        Arrays
            .asList(flagAliases[0].getName(), flagAliases[1].getName())
            .contains(TEST_TABLE + "." + FLAG)
    );

    ColumnNameAlias[] numAliases = columns[1].getAliases().toArray(new ColumnNameAlias[]{});
    Assertions.assertEquals(2, numAliases.length);
    Assertions.assertTrue(
        Arrays
            .asList(numAliases[0].getName(), numAliases[1].getName())
            .contains(ENTITY_WITH_FIELDS + "." + NUM)
    );
    Assertions.assertTrue(
        Arrays
            .asList(numAliases[0].getName(), numAliases[1].getName())
            .contains(TEST_TABLE + "." + NUM)
    );
  }

  @Test
  void buildEntityWithoutFieldsTest() {
    Factory factory = new Factory(new MetaInfoContext());
    Optional<TableMetaInfo> built = factory.build(EntityWithoutFields.class);
    Assertions.assertTrue(built.isPresent());

    TableMetaInfo metaInfo = built.get();
    Assertions.assertEquals(EntityWithoutFields.class, metaInfo.getType());

    TableNameAlias[] aliases = metaInfo.getAliases().toArray(new TableNameAlias[]{});
    Assertions.assertEquals(2, aliases.length);
    Assertions.assertTrue(
        Arrays.asList(aliases[0].getName(), aliases[1].getName()).contains(ENTITY_WITHOUT_FIELDS)
    );
    Assertions.assertTrue(
        Arrays.asList(aliases[0].getName(), aliases[1].getName()).contains(TEST_TABLE)
    );

    Collection<ColumnMetaInfo> columns = metaInfo.getColumns();
    Assertions.assertTrue(columns.isEmpty());
  }

  static class Entity {
  }

  @Table
  @Table("test_table")
  static class EntityWithFields {
    private final String text = "";
    private boolean flag;
    private double num;

    private static final int STATIC_FIELD = 0;
  }

  @Table
  @Table
  @Table("test_table")
  static class EntityWithoutFields {
  }
}
