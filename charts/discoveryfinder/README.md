# discoveryfinder

![Version: 0.6.0](https://img.shields.io/badge/Version-0.6.0-informational?style=flat-square) ![Type: application](https://img.shields.io/badge/Type-application-informational?style=flat-square) ![AppVersion: 0.6.1](https://img.shields.io/badge/AppVersion-0.6.1-informational?style=flat-square)

Tractus-X Discovery Finder Helm Chart

**Homepage:** <https://eclipse-tractusx.github.io/>

## Source Code

* <https://github.com/eclipse-tractusx/sldt-discovery-finder>

## Requirements

| Repository | Name | Version |
|------------|------|---------|
| https://charts.bitnami.com/bitnami | postgresql | 12.12.10 |

## Prerequisites

- Kubernetes 1.19+
- Helm 3.2.0+
- PV provisioner support in the underlying infrastructure

## Install

To install the chart with the release name `discoveryfinder`:

```shell
helm repo add tractusx-dev https://eclipse-tractusx.github.io/charts/dev
helm install discoveryfinder tractusx-dev/discoveryfinder
```

To install the helm chart into your cluster with your values:

```shell
helm install -f your-values.yaml discoveryfinder tractusx-dev/discoveryfinder
```

To use the helm chart as a dependency:

```yaml
dependencies:
  - name: discoveryfinder
    repository: https://eclipse-tractusx.github.io/charts/dev
    version: YOUR_VERSION
```

To install the local version in the namespace _semantics_:

```shell

helm dependency update .

helm install discoveryfinder -n semantics . --create-namespace
```

## Values

| Key | Type | Default | Description |
|-----|------|---------|-------------|
| discoveryfinder.authentication | bool | `true` | If 'authentication' is set to false, no OAuth authentication is enforced |
| discoveryfinder.containerPort | int | `4243` |  |
| discoveryfinder.dataSource.driverClassName | string | `"org.postgresql.Driver"` |  |
| discoveryfinder.dataSource.password | string | `nil` |  |
| discoveryfinder.dataSource.sqlInitPlatform | string | `"pg"` |  |
| discoveryfinder.dataSource.url | string | `"jdbc:postgresql://database:5432"` | The url, user, and password parameter will be ignored if 'enablePostgres' is set to true. In that case the postgresql auth parameters are used. |
| discoveryfinder.dataSource.user | string | `nil` |  |
| discoveryfinder.host | string | `"localhost"` |  |
| discoveryfinder.idp.issuerUri | string | `"https://idp-url"` |  |
| discoveryfinder.idp.publicClientId | string | `"idpClientID"` |  |
| discoveryfinder.image.imagePullPolicy | string | `"IfNotPresent"` |  |
| discoveryfinder.image.registry | string | `"docker.io"` |  |
| discoveryfinder.image.repository | string | `"tractusx/sldt-discovery-finder"` |  |
| discoveryfinder.ingress.annotations | object | `{}` |  |
| discoveryfinder.ingress.className | string | `"nginx"` |  |
| discoveryfinder.ingress.enabled | bool | `false` | enable ingress, default false |
| discoveryfinder.ingress.rules | list | `[]` | specify rules on your own, if the pathType Prefix + Regex runs into issues with your ClusterIssuer has a "strict-validate-path-type" check. -host.http.paths[0].path must at least be set. Then pathType "ImplementationSpecific" is used with common service name and port. Multiple paths may be specified |
| discoveryfinder.ingress.tls.enabled | bool | `false` | enable tls, default false |
| discoveryfinder.ingress.tls.secretName | string | `"discoveryfinder-certificate-secret"` | reuse secret in namespace with given name, default "discoveryfinder-certificate-secret" |
| discoveryfinder.ingress.urlPrefix | string | `"/discoveryfinder"` | use a urlPrefix to define a path of pathType "Prefix" with regex. Result is a path "/discoveryfinder(/|$)(.*)" |
| discoveryfinder.livenessProbe.failureThreshold | int | `3` |  |
| discoveryfinder.livenessProbe.initialDelaySeconds | int | `100` |  |
| discoveryfinder.livenessProbe.periodSeconds | int | `3` |  |
| discoveryfinder.properties | object | `{"discoveryfinder":null}` | If initial endpoints need to be created when the application starts, enable the initialEndpoints block and add initialEndpoints. Type and endpointAddress is required is enabled. |
| discoveryfinder.readinessProbe.failureThreshold | int | `3` |  |
| discoveryfinder.readinessProbe.initialDelaySeconds | int | `100` |  |
| discoveryfinder.readinessProbe.periodSeconds | int | `3` |  |
| discoveryfinder.replicaCount | int | `1` |  |
| discoveryfinder.resources.limits.cpu | string | `"750m"` |  |
| discoveryfinder.resources.limits.memory | string | `"1024Mi"` |  |
| discoveryfinder.resources.requests.cpu | string | `"250m"` |  |
| discoveryfinder.resources.requests.memory | string | `"1024Mi"` |  |
| discoveryfinder.service.port | int | `8080` |  |
| discoveryfinder.service.type | string | `"ClusterIP"` |  |
| enablePostgres | bool | `true` |  |
| fullnameOverride | string | `nil` |  |
| nameOverride | string | `nil` |  |
| postgresql.auth.database | string | `"discoveryfinder"` |  |
| postgresql.auth.existingSecret | string | `"secret-discoveryfinder-postgres-init"` | Secret contains passwords for username postgres. |
| postgresql.auth.password | string | `nil` |  |
| postgresql.auth.username | string | `"catenax"` |  |
| postgresql.image.repository | string | `"bitnamilegacy/postgresql"` | workaround to use bitnamilegacy chart for version 12.12.x till committers align on new postgresql charts |
| postgresql.image.tag | string | `"15.4.0-debian-11-r45"` | workaround to use bitnamilegacy chart for version 12.12.x till committers align on new postgresql charts |
| postgresql.primary.persistence.enabled | bool | `true` |  |
| postgresql.primary.persistence.size | string | `"50Gi"` |  |
| postgresql.service.ports.postgresql | int | `5432` |  |

## NOTICE

This work is licensed under the [CC-BY-4.0](https://creativecommons.org/licenses/by/4.0/legalcode).

- SPDX-License-Identifier: CC-BY-4.0
- SPDX-FileCopyrightText: 2023 Contributors to the Eclipse Foundation
- Source URL: https://github.com/eclipse-tractusx/sldt-discovery-finder