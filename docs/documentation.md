# Developer Documentation Tractus-X Discovery Finder

This page provides an overview of the Discovery Finder.

## Architectural Overview
The Digital Twins are no longer administrated in a central registry. The implementation of two services, "Discovery Finder" and "BPN
Discovery", ensures that EDCs still can be found in the decentralized environment.
The Discovery Finder hereby is responsible to send endpoints from BPN Discoveries for a specific type.

![Overall view](media/OverallConcept.png)

## Actors
There are two actors who interact with the Discovery Finder

| Actor         | Description                                                                                                  | Example                                               |
|---------------|--------------------------------------------------------------------------------------------------------------|-------------------------------------------------------|
| Data Consumer | The data consumer uses the Discovery Finder to search for endpoints for BPN Discoveries for a specific type. |                                                       |
| Data Provider | The data provider adds or deletes his endpoint at the Discovery Finder                                       | A BPN Discovery registers its endpoint after startup. |
| Keycloak      | Keycloak is used for token validation                                                                        |                                                       |


## Disovery Finder Api

The Discovery Finder provides a Swagger-Interface for all its endpoints:
https://semantics.int.demo.catena-x.net/discoveryfinder/swagger-ui/index.html

#### Search request
![](media/search_DF.PNG)

**Reqiest body**

![](media/Request_Search_DF.PNG)

**Response**

![](media/Response_Search_DF.PNG)

#### Add request
![add endpoint](media/setDF_Endpoint.PNG)

**Request body**

![request body](media/Request_Set_DF_Endpoint.PNG)

**Response**

![response](media/Response_Set_DF_Endpoint.PNG)

#### Delete request
![delete endpoint](media/Delete_DF.PNG)

ResourceID in path

![ResourceID ](media/ResourceID_DF.PNG)

**Response**

![response](media/Response_Delete_DF.PNG)

## Authentication & Authorization
The service is secured by a OAuth2 compliant authorization. Every API call has to provide a
valid Bearer Token. Authorization is provided by a role based access. These roles are possible:

| Role                      | Description                            |
|---------------------------|----------------------------------------|
| view_discovery_endpoint   | can search for BPN Discovery endpoints |
| add_discovery_endpoint    | can add BPN Discovery endpoints        |
| delete_discovery_endpoint | can delete BPN Discovery endpoints     |

## Deployment

To deploy this system, you need to use the Helm Chart in a running
Kubernetes cluster. The Helm Chart is located under
"charts/discoveryfinder". For further information
checkout the [readme.md](https://github.com/eclipse-tractusx/sldt-discovery-finder/blob/main/README.md). 
