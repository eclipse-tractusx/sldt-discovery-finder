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
package org.eclipse.tractusx.discoveryfinder.controller;

import java.util.Optional;

import org.eclipse.tractusx.discoveryfinder.api.FinderApiDelegate;
import org.eclipse.tractusx.discoveryfinder.dto.EndpointCollectionDto;
import org.eclipse.tractusx.discoveryfinder.mapper.DiscoveryFinderMapper;
import org.eclipse.tractusx.discoveryfinder.model.DiscoveryEndpoint;
import org.eclipse.tractusx.discoveryfinder.model.DiscoveryEndpointCollection;
import org.eclipse.tractusx.discoveryfinder.model.Endpoint;
import org.eclipse.tractusx.discoveryfinder.model.SearchRequest;
import org.eclipse.tractusx.discoveryfinder.service.DiscoveryFinderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

@Service
public class DiscoveryEndpointFinderApiDelegate implements FinderApiDelegate {
   private final DiscoveryFinderService discoveryFinderService;
   private final DiscoveryFinderMapper discoveryFinderMapper;

   public DiscoveryEndpointFinderApiDelegate( final DiscoveryFinderService discoveryFinderService, final DiscoveryFinderMapper discoveryFinderMapper ) {
      this.discoveryFinderService = discoveryFinderService;
      this.discoveryFinderMapper = discoveryFinderMapper;
   }

   @Override
   public Optional<NativeWebRequest> getRequest() {
      return FinderApiDelegate.super.getRequest();
   }

   @Override
   public ResponseEntity<Void> deleteDiscoveryEndpointByResourceId( String resourceId ) {
      discoveryFinderService.deleteEndpoint( resourceId );
      return new ResponseEntity<>( HttpStatus.NO_CONTENT );
   }

   @Override
   public ResponseEntity<DiscoveryEndpointCollection> getDiscoveryEndpoints( SearchRequest searchRequest ) {
      EndpointCollectionDto endpoints = discoveryFinderService.findDiscoveryEndpoints( searchRequest.getTypes() );
      return new ResponseEntity<>( discoveryFinderMapper.toApiDto( endpoints ), HttpStatus.OK );
   }

   @Override
   public ResponseEntity<DiscoveryEndpoint> postDiscoveryEndpoint( DiscoveryEndpoint discoveryEndpoint ) {
      Endpoint endpoint = discoveryFinderService.save( discoveryFinderMapper.fromApiDto( discoveryEndpoint ) );
      return new ResponseEntity<>( discoveryFinderMapper.toApiDto( endpoint ), HttpStatus.CREATED );
   }
}