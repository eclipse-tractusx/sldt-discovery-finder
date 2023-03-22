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
package org.eclipse.tractusx.discoveryfinder.configuration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.tractusx.discoveryfinder.security.JwtConverter;
import org.eclipse.tractusx.discoveryfinder.security.JwtRoles;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import lombok.Value;
import net.minidev.json.JSONArray;

public class TestJwtTokenFactory {

   private final Client client;

   public TestJwtTokenFactory( String publicClientId ) {
      this.client = new Client( publicClientId );
   }

   public RequestPostProcessor allRoles() {
      return client.allRoles();
   }

   public RequestPostProcessor readDiscoveryEndpoints() {
      return client.readDiscoveryEndpoints();
   }

   public RequestPostProcessor addDiscoveryEndpoints() {
      return client.addDiscoveryEndpoints();
   }

   public RequestPostProcessor deleteDiscoveryEndpoints() {
      return client.deleteDiscoveryEndpoints();
   }

   public RequestPostProcessor withoutResourceAccess() {
      return client.withoutResourceAccess();
   }

   public RequestPostProcessor withoutRoles() {
      return client.withoutRoles();
   }

   @Value
   public class Client {
      String publicClientId;
      JwtConverter jwtConverter;

      Client( String publicClientId ) {
         this.publicClientId = publicClientId;
         this.jwtConverter = new JwtConverter( publicClientId );
      }

      public RequestPostProcessor allRoles() {
         return authenticationWithRoles(
               List.of( JwtRoles.DELETE.getRole(), JwtRoles.ADD.getRole(), JwtRoles.VIEW.getRole() )
         );
      }

      public RequestPostProcessor readDiscoveryEndpoints() {
         return authenticationWithRoles( List.of( JwtRoles.VIEW.getRole() ) );
      }

      public RequestPostProcessor addDiscoveryEndpoints() {
         return authenticationWithRoles( List.of( JwtRoles.ADD.getRole() ) );
      }

      public RequestPostProcessor deleteDiscoveryEndpoints() {
         return authenticationWithRoles( List.of( JwtRoles.DELETE.getRole() ) );
      }

      public JwtRequestPostProcessor withoutResourceAccess() {
         Jwt jwtToken = Jwt.withTokenValue( "token" )
               .header( "alg", "none" )
               .claim( "sub", "user" )
               .build();
         return jwt().jwt( jwtToken ).authorities( jwtConverter );
      }

      public JwtRequestPostProcessor withoutRoles() {
         Jwt jwtToken = Jwt.withTokenValue( "token" )
               .header( "alg", "none" )
               .claim( "sub", "user" )
               .claim( "resource_access", Map.of( publicClientId, new HashMap<String, String>() ) )
               .build();
         return jwt().jwt( jwtToken ).authorities( jwtConverter );
      }

      private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor authenticationWithRoles( List<String> roles ) {
         Jwt jwtToken = Jwt.withTokenValue( "token" )
               .header( "alg", "none" )
               .claim( "sub", "user" )
               .claim( "resource_access", Map.of( publicClientId, Map.of( "roles", toJsonArray( roles ) ) ) )
               .build();
         return jwt().jwt( jwtToken ).authorities( jwtConverter );
      }

      private static JSONArray toJsonArray( List<String> elements ) {
         JSONArray jsonArray = new JSONArray();
         for ( String element : elements ) {
            jsonArray.appendElement( element );
         }
         return jsonArray;
      }
   }
}
