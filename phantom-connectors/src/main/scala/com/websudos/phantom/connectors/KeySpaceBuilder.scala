/*
 * Copyright 2014-2015 Sphonic Ltd. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.websudos.phantom.connectors

import com.datastax.driver.core.PoolingOptions

/**
 * A builder for KeySpace instances.
 *
 * When using multiple keySpaces in the same Cassandra cluster,
 * it is recommended to create all `KeySpace` instances from the
 * same builder instance.
 */
class KeySpaceBuilder(clusterBuilder: ClusterBuilder) {

  /**
   * Specify an additional builder to be applied when creating the Cluster instance.
   * This hook exposes the underlying Java API of the builder API of the Cassandra
   * driver.
   */
  def withClusterBuilder(builder: ClusterBuilder): KeySpaceBuilder =
    new KeySpaceBuilder(clusterBuilder andThen builder)

  /**
    * Disables the heartbeat for the current builder.
    * This is designed for local instantiations of connectors or test environments.
    * @return A new cluster builder, with the heartbeat interval set to 0(disabled).
    */
  def noHeartbeat(): KeySpaceBuilder = {
    new KeySpaceBuilder(clusterBuilder andThen(_.withPoolingOptions(
      new PoolingOptions().setHeartbeatIntervalSeconds(0))
      )
    )
  }

  /**
   * Create a new keySpace with the specified name.
   */
  def keySpace(name: String): KeySpaceDef =
    new KeySpaceDef(name, clusterBuilder)

}
