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
package org.eclipse.tractusx.discoveryfinder.service.utils;

import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.tractusx.discoveryfinder.service.ValidationException;

public class UuidUtils {
   private static final Pattern PATTERN =
         Pattern.compile( "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$" );

   public static void validateUUID( String uuidAsString ) {
      if ( !PATTERN.matcher( uuidAsString ).matches() ) {
         throw new ValidationException( "Validation failed.",
               Map.of( "value format", String.format( "Format for the value '%s' is not correct.", uuidAsString ) ) );
      }
   }
}
