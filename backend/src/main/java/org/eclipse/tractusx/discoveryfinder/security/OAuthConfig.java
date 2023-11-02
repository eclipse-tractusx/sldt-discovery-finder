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
package org.eclipse.tractusx.discoveryfinder.security;

import org.eclipse.tractusx.discoveryfinder.DiscoveryFinderProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Profile( "!local" )
@Configuration
public class OAuthConfig {

   @Bean
   protected SecurityFilterChain configure( HttpSecurity http, JwtAuthenticationConverter jwtAuthenticationConverter ) throws Exception {
      http
            .authorizeHttpRequests( auth -> auth
                  .requestMatchers( HttpMethod.OPTIONS ).permitAll()
                  .requestMatchers( HttpMethod.GET, "/**" ).permitAll()
                  .requestMatchers( HttpMethod.DELETE, "/api/v1.0/administration/connectors/discovery/**" ).hasRole( JwtRoles.DELETE.getRole() )
                  .requestMatchers( HttpMethod.POST, "/api/v1.0/administration/connectors/discovery" ).hasRole( JwtRoles.ADD.getRole() )
                  .requestMatchers( HttpMethod.POST, "/api/v1.0/administration/connectors/discovery/search" ).hasRole( JwtRoles.VIEW.getRole() )
            )
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS )
            .and()
            .oauth2ResourceServer( oauth2 -> oauth2
                  .jwt( jwt -> jwt
                        .jwtAuthenticationConverter( jwtAuthenticationConverter ) ) );
      return http.build();
   }

   @Bean
   public JwtAuthenticationConverter jwtAuthenticationConverter( DiscoveryFinderProperties discoveryFinderProperties ) {
      final JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
      converter.setJwtGrantedAuthoritiesConverter( new JwtConverter( discoveryFinderProperties.getPublicClientId() ) );
      return converter;
   }
}
