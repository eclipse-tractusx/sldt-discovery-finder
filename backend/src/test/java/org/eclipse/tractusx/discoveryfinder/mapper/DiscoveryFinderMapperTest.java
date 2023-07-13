/********************************************************************************
 * Copyright (c) 2023 Robert Bosch Manufacturing Solutions GmbH
 * Copyright (c) 2023 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 ********************************************************************************/
package org.eclipse.tractusx.discoveryfinder.mapper;

import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.eclipse.tractusx.discoveryfinder.dto.EndpointCollectionDto;
import org.eclipse.tractusx.discoveryfinder.model.DiscoveryEndpoint;
import org.eclipse.tractusx.discoveryfinder.model.DiscoveryEndpointCollection;
import org.eclipse.tractusx.discoveryfinder.model.Endpoint;
import org.junit.jupiter.api.Test;

public class DiscoveryFinderMapperTest {

   private final DiscoveryFinderMapper discoveryFinderMapper = new DiscoveryFinderMapperImpl();

   @Test
   public void fromApiDto() {
      // given
      DiscoveryEndpoint givenApiDto = new DiscoveryEndpoint()
            .type( "oen" )
            .endpointAddress( "oen-url-1" )
            .description( "description" )
            .documentation( "swagger-oen-1" );

      // then
      Endpoint actual = discoveryFinderMapper.fromApiDto( givenApiDto );

      // then
      Assertions.assertThat( actual ).isInstanceOf( Endpoint.class );
      Assertions.assertThat( actual.getType() ).isEqualTo( givenApiDto.getType() );
      Assertions.assertThat( actual.getEndpointAddress() ).isEqualTo( givenApiDto.getEndpointAddress() );
      Assertions.assertThat( actual.getDescription() ).isEqualTo( givenApiDto.getDescription() );
      Assertions.assertThat( actual.getDescription() ).isEqualTo( givenApiDto.getDescription() );
      Assertions.assertThat( actual.getResourceId() ).isNotNull();
   }

   @Test
   public void fromApiDto_Null() {
      //given null -> when
      Endpoint actual = discoveryFinderMapper.fromApiDto( (DiscoveryEndpoint) null );

      //then
      Assertions.assertThat( actual ).isNull();
   }

   @Test
   public void toApiDto() {
      //given
      UUID givenUuid = UUID.randomUUID();
      UUID givenResourceID = UUID.randomUUID();

      Endpoint givenDto = new Endpoint(
            givenUuid, "oen", "description", "oen-url-2", "swagger-oen-1", givenResourceID );

      // when
      DiscoveryEndpoint actual = discoveryFinderMapper.toApiDto( givenDto );

      // then
      Assertions.assertThat( actual.getType() ).isEqualTo( givenDto.getType() );
      Assertions.assertThat( actual.getDescription() ).isEqualTo( givenDto.getDescription() );
      Assertions.assertThat( actual.getEndpointAddress() ).isEqualTo( givenDto.getEndpointAddress() );
      Assertions.assertThat( actual.getDescription() ).isEqualTo( givenDto.getDescription() );
      Assertions.assertThat( actual.getResourceId() ).isEqualTo( givenResourceID.toString() );
   }

   @Test
   public void toDiscoveryEndpointCollectionApiDto() {
      // given
      UUID givenUuid = UUID.randomUUID();
      UUID givenResourceID = UUID.randomUUID();

      Endpoint givenDto = new Endpoint(
            givenUuid, "oen", "description", "oen-url-2", "swagger-oen-1", givenResourceID );
      EndpointCollectionDto givenEndpointCollectionDto = EndpointCollectionDto.builder().endpoints( List.of( givenDto ) ).build();

      // when 
      DiscoveryEndpointCollection actual = discoveryFinderMapper.toApiDto( givenEndpointCollectionDto );

      // then
      Assertions.assertThat( actual.getEndpoints() ).hasSize( 1 );
      Assertions.assertThat( actual.getEndpoints().get( 0 ).getType() ).isEqualTo( givenDto.getType() );
      Assertions.assertThat( actual.getEndpoints().get( 0 ).getDescription() ).isEqualTo( givenDto.getDescription() );
      Assertions.assertThat( actual.getEndpoints().get( 0 ).getEndpointAddress() ).isEqualTo( givenDto.getEndpointAddress() );
      Assertions.assertThat( actual.getEndpoints().get( 0 ).getDescription() ).isEqualTo( givenDto.getDescription() );
      Assertions.assertThat( actual.getEndpoints().get( 0 ).getResourceId() ).isEqualTo( givenDto.getResourceId().toString() );
   }
}