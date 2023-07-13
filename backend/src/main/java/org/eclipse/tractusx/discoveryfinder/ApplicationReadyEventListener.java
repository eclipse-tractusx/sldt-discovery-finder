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
package org.eclipse.tractusx.discoveryfinder;

import java.util.List;

import org.eclipse.tractusx.discoveryfinder.mapper.DiscoveryFinderMapper;
import org.eclipse.tractusx.discoveryfinder.model.Endpoint;
import org.eclipse.tractusx.discoveryfinder.service.DiscoveryFinderService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Profile( "!test" )
@Component
@Slf4j
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

   private final DiscoveryFinderService discoveryFinderService;
   private final DiscoveryFinderProperties discoveryFinderProperties;
   private final DiscoveryFinderMapper discoveryFinderMapper;

   public ApplicationReadyEventListener( DiscoveryFinderService discoveryFinderService, DiscoveryFinderProperties discoveryFinderProperties,
         DiscoveryFinderMapper discoveryFinderMapper ) {
      this.discoveryFinderService = discoveryFinderService;
      this.discoveryFinderProperties = discoveryFinderProperties;
      this.discoveryFinderMapper = discoveryFinderMapper;
   }

   @Override
   public void onApplicationEvent( ApplicationReadyEvent event ) {
      List<DiscoveryFinderProperties.InitialEndpoint> initialEndpoints = discoveryFinderProperties.getInitialEndpoints();
      if ( initialEndpoints != null && !initialEndpoints.isEmpty() ) {
         initialEndpoints.forEach( initialEndpoint -> {
            try {
               Endpoint exists = discoveryFinderService.findDiscoveryEndpointByTypeAndAddress(
                     initialEndpoint.getType(),
                     initialEndpoint.getEndpointAddress() );
               if ( exists != null ) {
                  discoveryFinderService.deleteEndpoint( exists.getResourceId().toString() );
               }
               discoveryFinderService.save( discoveryFinderMapper.fromApiDto( initialEndpoint ) );
               log.info( "Initial endpoint created for " + initialEndpoint.getType() );
            } catch ( Exception e ) {
               log.error( "Something went wrong on save initial Endpoint!" + e.getMessage(), e.getCause() );
            }
         } );
      }
   }
}
