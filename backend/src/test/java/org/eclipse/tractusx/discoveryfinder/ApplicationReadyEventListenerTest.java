package org.eclipse.tractusx.discoveryfinder;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.List;

import org.eclipse.tractusx.discoveryfinder.model.Endpoint;
import org.eclipse.tractusx.discoveryfinder.repository.EndpointRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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

@SpringBootTest
@EnableConfigurationProperties( DiscoveryFinderProperties.class )
@ActiveProfiles( "onstartuptest" )
public class ApplicationReadyEventListenerTest {

   @Autowired
   protected EndpointRepository endpointRepository;

   /**
    * This test checks whether the initialEndpoints defined in application-startuptest.yml are saved in the database after the application has been started.
    */
   @Test
   public void givenInitialEndpoints_onApplicationEvent_thenSaveInitialEndpointsSucceeds() {
      // given -> when -> then
      List<Endpoint> expectedDbEndpoints = endpointRepository.findAll();
      assertThat( expectedDbEndpoints ).isNotEmpty();
      assertThat( expectedDbEndpoints.size() ).isEqualTo( 2 );
   }
}
