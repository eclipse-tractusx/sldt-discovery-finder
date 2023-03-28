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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class DiscoveryFinderApiDelegateTest extends AbstractDiscoveryFinderApi {

   @BeforeEach
   public void cleanUp() {
      endpointRepository.deleteAll();
   }

   @Test
   public void givenValidDiscoveryEndpoint_whenSave_thenSaveSucceeds() throws Exception {
      // given
      ObjectNode givenValidPayload = createDiscoveryEndpoint( "oen", "oen-url-1", "description", "http://oen-swagger" );

      //when -> then
      mockMvc.perform(
                  MockMvcRequestBuilders
                        .post( DISCOVERY_FINDER_BASE_PATH )
                        .accept( MediaType.APPLICATION_JSON )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( toJson( givenValidPayload ) )
                        .with( jwtTokenFactory.allRoles() )
            )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isCreated() )
            .andExpect( content().json( toJson( givenValidPayload ) ) );
   }

   @Test
   public void givenDuplicateDiscoveryEndpoint_whenSave_thenThrowDuplicateKeyException() throws Exception {
      // given
      ObjectNode givenPayload = createDiscoveryEndpoint( "oen", "oen-url-2", "description", "http://oen-swagger" );
      performDiscoveryEndpointCreateRequest( toJson( givenPayload ) );
      ObjectNode duplicatedPayload = givenPayload;

      // when -> then
      mockMvc.perform(
                  MockMvcRequestBuilders
                        .post( DISCOVERY_FINDER_BASE_PATH )
                        .accept( MediaType.APPLICATION_JSON )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( toJson( duplicatedPayload ) )
                        .with( jwtTokenFactory.allRoles() )
            )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isBadRequest() )
            .andExpect( jsonPath( "$.error.message", is( "Entity for the given resourceId already exists." ) ) );
   }

   @Test
   public void givenValidResourceId_whenDelete_thenDeleteSucceeds() throws Exception {
      // given
      ObjectNode givenPayload = createDiscoveryEndpoint( "oen", "oen-url-3", "description", "http://oen-swagger" );
      String givenResourceId = performDiscoveryEndpointCreateRequest( toJson( givenPayload ) );

      // when -> then
      mockMvc.perform(
                  MockMvcRequestBuilders
                        .delete( DISCOVERY_FINDER_BASE_PATH + "/{givenResourceId}", givenResourceId )
                        .accept( MediaType.APPLICATION_JSON )
                        .contentType( MediaType.APPLICATION_JSON )
                        .with( jwtTokenFactory.allRoles() )
            )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isNoContent() );
   }

   @Test
   public void givenInvalidResourceId_whenDelete_thenThrowEntityNotFoundException() throws Exception {
      // given
      String givenInvalidResourceId = "338f797a-bbfd-11ed-afa1-0242ac120002";

      // when -> then

      mockMvc.perform(
                  MockMvcRequestBuilders
                        .delete( DISCOVERY_FINDER_BASE_PATH + "/{givenResourceId}", givenInvalidResourceId )
                        .accept( MediaType.APPLICATION_JSON )
                        .contentType( MediaType.APPLICATION_JSON )
                        .with( jwtTokenFactory.allRoles() )
            )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isNotFound() )
            .andExpect( jsonPath( "$.error.message", is( String.format( "Discovery Endpoint for the resourceId %s not found.", givenInvalidResourceId ) ) ) );
   }

   @Test
   public void givenInvalidResourceIdFormat_whenDelete_thenThrowValidationException() throws Exception {
      // given
      String givenInvalidResourceId = "invalid-uuid-bbfd-11ed-afa1-0242ac120002";

      // when -> then

      mockMvc.perform(
                  MockMvcRequestBuilders
                        .delete( DISCOVERY_FINDER_BASE_PATH + "/{givenResourceId}", givenInvalidResourceId )
                        .accept( MediaType.APPLICATION_JSON )
                        .contentType( MediaType.APPLICATION_JSON )
                        .with( jwtTokenFactory.allRoles() )
            )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isBadRequest() )
            .andExpect( jsonPath( "$.error.message", is( "Validation failed." ) ) );
   }

   @Test
   public void givenSearchRequest_whenGetDiscoveryEndpoints_thenReturnResult() throws Exception {
      // given
      ObjectNode givenOenNode1 = createDiscoveryEndpoint( "oen", "oen-url-4", "description", "http://oen-swagger" );
      ObjectNode givenOenNode2 = createDiscoveryEndpoint( "bpId", "bpId-url-1", "description", "http://oen-swagger" );
      performDiscoveryEndpointCreateRequest( toJson( givenOenNode1 ) );
      performDiscoveryEndpointCreateRequest( toJson( givenOenNode2 ) );

      String givenTypes = "{\n\"types\": [\n   \"oen\",\n    \"bpId\",\n    \"bpId\"\n ]\n}";

      // when -> then
      mockMvc.perform(
                  MockMvcRequestBuilders
                        .post( DISCOVERY_FINDER_SEARCH_API )
                        .accept( MediaType.APPLICATION_JSON )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( givenTypes )
                        .with( jwtTokenFactory.allRoles() )
            )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isOk() )
            .andExpect( jsonPath( "$.endpoints", hasSize( 2 ) ) )
            .andExpect( jsonPath( "$.endpoints[0].type", equalTo( "bpId" ) ) )
            .andExpect( jsonPath( "$.endpoints[0].endpointAddress", equalTo( "bpId-url-1" ) ) )
            .andExpect( jsonPath( "$.endpoints[1].type", equalTo( "oen" ) ) )
            .andExpect( jsonPath( "$.endpoints[1].endpointAddress", equalTo( "oen-url-4" ) ) );
   }

   @Test
   public void givenEmptySearchRequest_whenGetDiscoveryEndpoints_thenReturnAllDiscoveryEndpoints() throws Exception {
      // given
      ObjectNode givenOenNode1 = createDiscoveryEndpoint( "bpId", "bpId-url-2", "description", "http://oen-swagger" );
      ObjectNode givenOenNode2 = createDiscoveryEndpoint( "oen", "oen-url-5", "description", "http://oen-swagger" );
      ObjectNode givenOenNode3 = createDiscoveryEndpoint( "serialId", "serialId-url-1", "description", "http://oen-swagger" );
      performDiscoveryEndpointCreateRequest( toJson( givenOenNode1 ) );
      performDiscoveryEndpointCreateRequest( toJson( givenOenNode2 ) );
      performDiscoveryEndpointCreateRequest( toJson( givenOenNode3 ) );

      String givenEmptyTypes = "{\n\"types\": []\n}";

      // when -> then
      mockMvc.perform(
                  MockMvcRequestBuilders
                        .post( DISCOVERY_FINDER_SEARCH_API )
                        .accept( MediaType.APPLICATION_JSON )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( givenEmptyTypes )
                        .with( jwtTokenFactory.allRoles() )
            )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isOk() )
            .andExpect( jsonPath( "$.endpoints", hasSize( 3 ) ) )
            .andExpect( jsonPath( "$.endpoints[0].type", equalTo( "bpId" ) ) )
            .andExpect( jsonPath( "$.endpoints[1].type", equalTo( "oen" ) ) )
            .andExpect( jsonPath( "$.endpoints[2].type", equalTo( "serialId" ) ) );
   }
}