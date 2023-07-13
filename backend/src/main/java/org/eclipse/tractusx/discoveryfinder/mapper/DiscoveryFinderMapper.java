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

import org.eclipse.tractusx.discoveryfinder.DiscoveryFinderProperties;
import org.eclipse.tractusx.discoveryfinder.dto.EndpointCollectionDto;
import org.eclipse.tractusx.discoveryfinder.model.DiscoveryEndpoint;
import org.eclipse.tractusx.discoveryfinder.model.DiscoveryEndpointCollection;
import org.eclipse.tractusx.discoveryfinder.model.Endpoint;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper( uses = { Endpoint.class }, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR )
public interface DiscoveryFinderMapper {

   @Mapping( target = "resourceId", expression = "java(UUID.randomUUID())" )
   Endpoint fromApiDto( DiscoveryEndpoint apiDto );

   @Mapping( target = "resourceId", expression = "java(UUID.randomUUID())" )
   Endpoint fromApiDto( DiscoveryFinderProperties.InitialEndpoint apiDto );

   DiscoveryEndpoint toApiDto( Endpoint endpoint );

   DiscoveryEndpointCollection toApiDto( EndpointCollectionDto endpoints );

}
