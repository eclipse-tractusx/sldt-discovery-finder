## 1 Introduction and goals

The Digital Twins are no longer administrated in a central registry. The implementation of two services, "Discovery Finder" and "BPN
Discovery", ensure that Eclipse Data Space Connectors (EDC) can be found in the decentralized
environment.

The Discovery Finder is responsible to find a matching BPN Discovery for a specific type.

### High level requirement 

The Discovery Finder is used to find an endpoint to a BPD Discovery for a certain type and at last to find an EDC.

### Quality goals

-   Ensure that EDCs can be found.

-   Data sovereignty is given. The provider is responsible for his data.

-   All users and services are secured and can only access when
    authenticated and authorized.

-   Discovery Finder and BPN Discovery are "central" services
    (horizontal and vertical scalability included) and meant to help to
    connect companies. So it is integrated into the
    Catena-X network with all their services.

### Stakeholders

| Role             | Description                                        | Goal, Intention                                             |
|------------------|----------------------------------------------------|-------------------------------------------------------------|
| Consumer         | uses a Discovery Finder                            | wants to find an Endpoint for a BPN Discovery to find a BPN |
| Data Provider    |                                                    | wants that his EDC / Digital Twin can be found              |
| Catena-X network | provides the environment and operates the services | to enable the companies to connect and exchange data        |


### NOTICE

This work is licensed under the [Apache-2.0](https://www.apache.org/licenses/LICENSE-2.0).

- SPDX-License-Identifier: Apache-2.0
- SPDX-FileCopyrightText: 2023 Robert Bosch Manufacturing Solutions GmbH
- SPDX-FileCopyrightText: 2023 Contributors to the Eclipse Foundation
- Source URL: https://github.com/eclipse-tractusx/sldt-discovery-finder.git