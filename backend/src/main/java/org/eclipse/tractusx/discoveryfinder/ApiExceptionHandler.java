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

import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.tractusx.discoveryfinder.model.Error;
import org.eclipse.tractusx.discoveryfinder.model.ErrorResponse;
import org.eclipse.tractusx.discoveryfinder.service.EntityNotFoundException;
import org.eclipse.tractusx.discoveryfinder.service.ValidationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

   @Override
   protected ResponseEntity<Object> handleMethodArgumentNotValid( final MethodArgumentNotValidException ex,
         final HttpHeaders headers,
         final HttpStatusCode status, final WebRequest request ) {

      List<Error> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map( fieldError -> new Error()
                  .putDetailsItem( "parameter", fieldError.getField() )
                  .message( Optional.ofNullable( fieldError.getDefaultMessage() ).orElseGet( () -> "null" ) )
            ).collect( Collectors.toList());
      return new ResponseEntity<>(
            errors, HttpStatus.BAD_REQUEST );
   }


   @ExceptionHandler( { ValidationException.class } )
   public ResponseEntity<ErrorResponse> handleValidationException( final HttpServletRequest request,
         final ValidationException exception ) {
      return new ResponseEntity<>( new ErrorResponse()
            .error( new Error()
                  .message( exception.getMessage() )
                  .details( exception.getDetails() )
                  .path( request.getRequestURI() ) ), HttpStatus.BAD_REQUEST );
   }

   @ExceptionHandler( { EntityNotFoundException.class } )
   public ResponseEntity<ErrorResponse> handleEntityNotFoundException( final HttpServletRequest request,
         final RuntimeException exception ) {
      return new ResponseEntity<>( new ErrorResponse()
            .error( new Error()
                  .message( exception.getMessage() )
                  .path( request.getRequestURI() ) ), HttpStatus.NOT_FOUND );
   }

   @ExceptionHandler( { DuplicateKeyException.class } )
   public ResponseEntity<ErrorResponse> handleDuplicateKeyException( final HttpServletRequest request, DuplicateKeyException exception ) {
      return new ResponseEntity<>( new ErrorResponse()
            .error( new Error()
                  .message( "Entity for the given resourceId already exists." )
                  .path( request.getRequestURI() ) ), HttpStatus.BAD_REQUEST );
   }

}
