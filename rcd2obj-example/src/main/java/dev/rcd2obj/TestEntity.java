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

package dev.rcd2obj;

import dev.rcd2obj.annotation.Column;
import dev.rcd2obj.annotation.Table;
import lombok.Data;

/**
 * An entity for testing the code generator.
 *
 * @author Andrey_Yurzanov
 */
@Data
@Table
public class TestEntity {
  @Column
  private String name;
  private short age;
}
