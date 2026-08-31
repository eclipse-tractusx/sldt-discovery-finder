# Contributing to Eclipse Tractus-X

Thanks for your interest in this project.

## Project description

The companies involved want to increase the automotive industry's competitiveness, improve efficiency through industry-specific cooperation and accelerate
company processes through standardization and access to information and data. A special focus is also on SMEs, whose active participation is of central
importance for the network's success. That is why Catena-X has been conceived from the outset as an open network with solutions ready for SMEs, where these
companies will be able to participate quickly and with little IT infrastructure investment. Tractus-X is meant to be the PoC project of the Catena-X alliance
focusing on parts traceability.

* https://projects.eclipse.org/projects/automotive.tractusx

## Project licenses

The Tractus-X project uses the following licenses:

* Apache-2.0 for code
* CC-BY-4.0 for non-code

## Terms of Use

This repository is subject to the Terms of Use of the Eclipse Foundation

* https://www.eclipse.org/legal/termsofuse.php

## Developer resources

Information regarding source code management, builds, coding standards, and more.

* https://projects.eclipse.org/projects/automotive.tractusx/developer

Getting started:

* https://eclipse-tractusx.github.io/docs/developer

The project maintains the following source code repositories

* https://github.com/eclipse-tractusx/sldt-semantic-hub
* https://github.com/eclipse-tractusx/sldt-digital-twin-registry
* https://github.com/eclipse-tractusx/sldt-bpn-discovery
* https://github.com/eclipse-tractusx/sldt-discovery-finder

## Eclipse Development Process

This Eclipse Foundation open project is governed by the Eclipse Foundation Development Process and operates under the terms of the Eclipse IP Policy.

* https://eclipse.org/projects/dev_process
* https://www.eclipse.org/org/documents/Eclipse_IP_Policy.pdf

## Eclipse Contributor Agreement

In order to be able to contribute to Eclipse Foundation projects you must electronically sign the Eclipse Contributor Agreement (ECA).

* http://www.eclipse.org/legal/ECA.php

The ECA provides the Eclipse Foundation with a permanent record that you agree that each of your contributions will comply with the commitments documented in
the Developer Certificate of Origin (DCO). Having an ECA on file associated with the email address matching the "Author" field of your contribution's Git
commits fulfills the DCO's requirement that you sign-off on your contributions.

For more information, please see the Eclipse Committer Handbook:
https://www.eclipse.org/projects/handbook/#resources-commit

## Keeping dependencies-files up to date

Navigate to the `./backend` folder and run:

```shell
mvn org.eclipse.dash:license-tool-plugin:license-check
```

This line runs the maven license tool with the parameters specified in the `./backend/pom.xml` and produces a `DEPENDENCIES` file in the root folder of this project.

Further a [workflow](./.github/workflows/dash-dependency-check.yml) is run when merging main to check if everything is up to date.

As a committer, to request IP checks, use this command

```shell
mvn org.eclipse.dash:license-tool-plugin:license-check -Ddash.iplab.token=<token>
```

## Contact

Contact the project developers via the project's "dev" list.

* https://accounts.eclipse.org/mailing-list/tractusx-dev

## NOTICE

This work is licensed under the [CC-BY-4.0](https://creativecommons.org/licenses/by/4.0/legalcode).

- SPDX-License-Identifier: CC-BY-4.0
- SPDX-FileCopyrightText: 2023 Contributors to the Eclipse Foundation
- Source URL: https://github.com/eclipse-tractusx/sldt-discovery-finder
