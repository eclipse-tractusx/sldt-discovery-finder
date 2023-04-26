## 1 Introduction and goals

The Digital Twins are no longer administrated in a central registry
anymore. The implementation of two services, "Discovery Finder" and "BPN
Discovery", ensure that EDCs can be found in the decentralized
environment.

### High level requirement 

The Discovery Finder is responsible to help to find an endpoint to a BPD
Discovery for a certain type and at last to find an EDC.

### Quality goals

-   Ensure that EDCs can be found.

-   Data sovereignty is given. The provider takes care / is responsible
    for his data.

-   All users and services are secured and can only access when
    authenticated and authorized.

-   Discovery Finder and BPN Discovery are "central" services
    (horizontal and vertical scalability included) and meant to help to
    connect companies. So, one goal is to be integrated into the
    Catena-X network with all their services.

### Stakeholders

| Role             | Description             | Goal, Intention                                             |
|------------------|-------------------------|-------------------------------------------------------------|
| Consumer         | uses a Discovery Finder | wants to find an Endpoint for a BPN Discovery to find a BPN |
| Data Provider    |                         | wants that his EDC / Digital Twin can be found              |
| Catena-X network |                         | to enable the companies to connect and exchange data        |
