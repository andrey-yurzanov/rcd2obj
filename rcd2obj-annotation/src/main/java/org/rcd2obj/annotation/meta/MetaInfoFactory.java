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

import java.util.Optional;

/**
 * The abstraction for a meta-information creating.
 *
 * @param <S> a source for the meta-information creating.
 * @param <R> the created meta-information
 * @author Andrey_Yurzanov
 * @since 1.0
 */
public interface MetaInfoFactory<S, R> {
  /**
   * The method of the meta-information creating by the source data.
   *
   * @param source the source data
   * @return the created meta-information
   */
  Optional<R> build(S source);
}
