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

import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@SpringBootApplication
@EnableJdbcAuditing
@EnableConfigurationProperties( DiscoveryFinderProperties.class )
@ComponentScan( basePackages = { "org.eclipse.tractusx.discoveryfinder", "org.openapitools.configuration" } )
public class DiscoveryFinderApplication {

   @Bean
   public SpringDocConfiguration springDocConfiguration() {
      return new SpringDocConfiguration();
   }

   @Bean
   public SpringDocConfigProperties springDocConfigProperties() {
      return new SpringDocConfigProperties();
   }

   @Bean
   ObjectMapperProvider objectMapperProvider( SpringDocConfigProperties springDocConfigProperties ) {
      return new ObjectMapperProvider( springDocConfigProperties );
   }

   public static void main( String[] args ) {
      SpringApplication.run( DiscoveryFinderApplication.class, args );
   }

}
