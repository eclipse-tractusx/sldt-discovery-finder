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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

   private final String clientId;

   public JwtConverter( String clientId ) {
      this.clientId = clientId;
   }

   @Override
   public Collection<GrantedAuthority> convert( Jwt jwt ) {
      Map<String, Object> claims = jwt.getClaims();
      Object resourceAccess = claims.get( "resource_access" );
      if ( !(resourceAccess instanceof Map) ) {
         return Collections.emptyList();
      }
      Object resource = ((Map<String, Object>) resourceAccess).get( clientId );
      if ( !(resource instanceof Map) ) {
         return Collections.emptyList();
      }
      Object roles = ((Map<String, Object>) resource).get( "roles" );
      if ( !(roles instanceof Collection) ) {
         return Collections.emptyList();
      }
      Collection<String> rolesList = (Collection<String>) roles;

      return rolesList.stream()
            // Default Role prefix is ROLE_. This is needed to check roles over hasRole()
            .map( roleName -> "ROLE_" + roleName )
            .map( SimpleGrantedAuthority::new )
            .collect( Collectors.toCollection( HashSet::new ) );
   }
}
