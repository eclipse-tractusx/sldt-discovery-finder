# Discovery Finder
The Discovery Finder is a logical and architectural component of Tractus-X. The source code under this folder contains reference implementations of the SLDT Discovery Finder.
The Discovery Finder is used to find endpoints of BPN Discoveries for a specific type, e.g. "oen". For further
information have a look to the [documentation](docs/1-introduction-and-goals.md).

## Build Packages
Run `mvn clean install` to run unit tests, build and install the package.

## Run Package Locally
To check whether the build was successful, you can start the resulting JAR file from the build process by running `java -jar backend/target/discovery-finder-backend-{current-version}.jar --spring.profiles.active=local`.

## Build Docker
Run `docker build -f backend/Dockerfile -t sldt-discovery-finder .`

In case you want to publish your image into a remote container registry, apply the tag accordingly and `docker push` the image.

## Install Instructions
For detailed install instructions please refer to our [INSTALL.md](INSTALL.md).

## Notice for Docker image

DockerHub: [https://hub.docker.com/r/tractusx/sldt-discovery-finder] (https://hub.docker.com/r/tractusx/sldt-discovery-finder)
This application provides container images for demonstrations purposes.
Eclipse Tractus-X product(s) installed within the image:

- GitHub: https://github.com/eclipse-tractusx/sldt-discovery-finder
- Project home: https://projects.eclipse.org/projects/automotive.tractusx
- Dockerfile: https://github.com/eclipse-tractusx/sldt-discovery-finder/blob/main/backend/Dockerfile
- Project license: Apache License, Version 2.0

**Used base image**

- eclipse-temurin:17-jre-alpine
- Official Eclipse Temurin DockerHub page: https://hub.docker.com/_/eclipse-temurin
- Eclipse Temurin Project: https://projects.eclipse.org/projects/adoptium.temurin
- Additional information about the Eclipse Temurin images: https://github.com/docker-library/repo-info/tree/master/repos/eclipse-temurin

As with all Docker images, these likely also contain other software which may be under other licenses (such as Bash, etc from the base distribution, along with any direct or indirect dependencies of the primary software being contained).

As for any pre-built image usage, it is the image user's responsibility to ensure that any use of this image complies with any relevant licenses for all software contained within.
