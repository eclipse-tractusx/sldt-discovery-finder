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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Validated
@ConfigurationProperties( prefix = "discoveryfinder" )
public class DiscoveryFinderProperties {
   @NotEmpty( message = "public client id must not be empty" )
   private String publicClientId;

   private List<InitialEndpoint> initialEndpoints;

   /**
    * Properties for Initial Endpoints, which created on application start.
    */
   @Data
   public static class InitialEndpoint {

      @NotEmpty( message = "type must not be empty" )
      private String type;

      @NotEmpty( message = "endpointAddress must not be empty" )
      private String endpointAddress;

      private String description;
      private String documentation;

      @Min( value = 1, message = "value must be greater or equal to 1" )
      @Max( value = 31536000, message = "value must be lesser or equal to 31536000")
      private Integer timeToLive;
   }
}