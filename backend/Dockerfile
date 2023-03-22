###############################################################
# Copyright (c) 2023 Robert Bosch Manufacturing Solutions GmbH
# Copyright (c) 2023 Contributors to the Eclipse Foundation
#
# See the NOTICE file(s) distributed with this work for additional
# information regarding copyright ownership.
#
# This program and the accompanying materials are made available under the
# terms of the Apache License, Version 2.0 which is available at
# https://www.apache.org/licenses/LICENSE-2.0.
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
# License for the specific language governing permissions and limitations
# under the License.
#
# SPDX-License-Identifier: Apache-2.0
###############################################################

# Dockerfile to create image for discover-finder application

# step 1: build
FROM maven:3.9.0-eclipse-temurin-17 AS builder
COPY . /build
WORKDIR /build
RUN mvn clean package

# step 2: build image
FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S spring \
    && adduser -S spring -G spring \
    && mkdir -p /service \
    && chown spring:spring /service

USER spring:spring
WORKDIR /service

COPY --from=builder /build/backend/target/discovery-finder-backend*.jar app.jar

ENV JAVA_TOOL_OPTIONS "-Xms512m -Xmx2048m"
EXPOSE 4243

ENTRYPOINT [ "java","-jar","/service/app.jar" ]
