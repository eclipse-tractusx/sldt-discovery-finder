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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.node.ObjectNode;

@DirtiesContext( classMode = DirtiesContext.ClassMode.BEFORE_CLASS )
public class DiscoveryFinderApiSecurityTest extends AbstractDiscoveryFinderApi {
   @Test
   public void givenNoAuthenticationToken_whenGetDiscoveryEndpoints_thenReturnUnauthorized() throws Exception {
      // given
      String givenTypes = "{\n\"types\": [\n   \"oen\",\n    \"bpId\",\n    \"bpId\"\n ]\n}";

      // when -> then
      mockMvc.perform(
                  MockMvcRequestBuilders
                        .post( DISCOVERY_FINDER_SEARCH_API )
                        .accept( MediaType.APPLICATION_JSON )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( givenTypes ) )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isUnauthorized() );
   }

   @Test
   public void givenAuthenticationTokenWithoutResourceAccess_whenGetDiscoveryEndpoints_thenReturnForbidden() throws Exception {
      String givenTypes = "{\n\"types\": [\n   \"oen\",\n    \"bpId\",\n    \"bpId\"\n ]\n}";

      // when -> then
      mockMvc.perform(
                  MockMvcRequestBuilders
                        .post( DISCOVERY_FINDER_SEARCH_API )
                        .accept( MediaType.APPLICATION_JSON )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( givenTypes )
                        .with( jwtTokenFactory.withoutResourceAccess() ) )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isForbidden() );

      mockMvc.perform(
                  MockMvcRequestBuilders
                        .post( DISCOVERY_FINDER_SEARCH_API )
                        .accept( MediaType.APPLICATION_JSON )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( givenTypes )
                        .with( jwtTokenFactory.withoutRoles() )
            )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isForbidden() );
   }

   @Test
   public void givenAuthenticationTokenWithoutViewRole_whenGetDiscoveryEndpoints_thenReturnForbidden() throws Exception {
      String givenTypes = "{\n\"types\": [\n   \"oen\",\n    \"bpId\",\n    \"bpId\"\n ]\n}";

      // when -> then
      mockMvc.perform(
                  MockMvcRequestBuilders
                        .post( DISCOVERY_FINDER_SEARCH_API )
                        .accept( MediaType.APPLICATION_JSON )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( givenTypes )
                        .with( jwtTokenFactory.addDiscoveryEndpoints() ) )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isForbidden() );

      mockMvc.perform(
                  MockMvcRequestBuilders
                        .post( DISCOVERY_FINDER_SEARCH_API )
                        .accept( MediaType.APPLICATION_JSON )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( givenTypes )
                        .with( jwtTokenFactory.deleteDiscoveryEndpoints() ) )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isForbidden() );
   }

   @Test
   public void givenAuthenticationTokenWithoutAddRole_whenSave_thenReturnForbidden() throws Exception {
      // given
      ObjectNode givenValidPayload = createDiscoveryEndpoint( "oen", "oen-url-sec-0", "description", "http://oen-swagger", 31536000 );

      //when -> then
      mockMvc.perform(
                  MockMvcRequestBuilders
                        .post( DISCOVERY_FINDER_BASE_PATH )
                        .accept( MediaType.APPLICATION_JSON )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( toJson( givenValidPayload ) )
                        .with( jwtTokenFactory.deleteDiscoveryEndpoints() )
            )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isForbidden() );

      mockMvc.perform(
                  MockMvcRequestBuilders
                        .post( DISCOVERY_FINDER_BASE_PATH )
                        .accept( MediaType.APPLICATION_JSON )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( toJson( givenValidPayload ) )
                        .with( jwtTokenFactory.readDiscoveryEndpoints() )
            )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isForbidden() );
   }

   @Test
   public void givenAuthenticationTokenWithoutDeleteRole_whenDelete_thenReturnForbidden() throws Exception {
      // given
      String givenResourceId = "338f797a-bbfd-11ed-afa1-0242ac120002";

      // when -> then
      mockMvc.perform(
                  MockMvcRequestBuilders
                        .delete( DISCOVERY_FINDER_BASE_PATH + "/{givenResourceId}", givenResourceId )
                        .accept( MediaType.APPLICATION_JSON )
                        .contentType( MediaType.APPLICATION_JSON )
                        .with( jwtTokenFactory.addDiscoveryEndpoints() )
            )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isForbidden() );

      mockMvc.perform(
                  MockMvcRequestBuilders
                        .delete( DISCOVERY_FINDER_BASE_PATH + "/{givenResourceId}", givenResourceId )
                        .accept( MediaType.APPLICATION_JSON )
                        .contentType( MediaType.APPLICATION_JSON )
                        .with( jwtTokenFactory.readDiscoveryEndpoints() )
            )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isForbidden() );
   }

   @Test
   public void givenAuthenticationTokenWithAddRole_whenSave_thenSaveSucceeds() throws Exception {
      // given
      ObjectNode givenValidPayload = createDiscoveryEndpoint( "oen", "oen-url-sec-1", "description", "http://oen-swagger", 31536000 );

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
            .andExpect( status().isCreated() );
   }

   @Test
   public void givenAuthenticationTokenWithDeleteRole_whenDelete_thenDeleteSucceeds() throws Exception {
      // given
      ObjectNode givenPayload = createDiscoveryEndpoint( "oen", "oen-url-sec-2", "description", "http://oen-swagger", 31536000 );
      String givenResourceId = performDiscoveryEndpointCreateRequest( toJson( givenPayload ) );

      // when -> then
      mockMvc.perform(
                  MockMvcRequestBuilders
                        .delete( DISCOVERY_FINDER_BASE_PATH + "/{givenResourceId}", givenResourceId )
                        .accept( MediaType.APPLICATION_JSON )
                        .contentType( MediaType.APPLICATION_JSON )
                        .with( jwtTokenFactory.deleteDiscoveryEndpoints() )
            )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isNoContent() );
   }

   @Test
   public void givenAuthenticationTokenWithViewRole_whenGetDiscoveryEndpoints_thenReturnResult() throws Exception {
      // given
      ObjectNode givenOenNode1 = createDiscoveryEndpoint( "oen", "oen-url-sec-3", "description", "http://oen-swagger",31536000 );
      performDiscoveryEndpointCreateRequest( toJson( givenOenNode1 ) );

      String givenTypes = "{\n\"types\": [\n   \"oen\",\n    \"bpId\",\n    \"bpId\"\n ]\n}";

      // when -> then
      mockMvc.perform(
                  MockMvcRequestBuilders
                        .post( DISCOVERY_FINDER_SEARCH_API )
                        .accept( MediaType.APPLICATION_JSON )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( givenTypes )
                        .with( jwtTokenFactory.readDiscoveryEndpoints() )
            )
            .andDo( MockMvcResultHandlers.print() )
            .andExpect( status().isOk() );
   }
}
