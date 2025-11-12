# discoveryfinder

![Version: 0.5.2](https://img.shields.io/badge/Version-0.5.2-informational?style=flat-square) ![Type: application](https://img.shields.io/badge/Type-application-informational?style=flat-square) ![AppVersion: 0.6.1](https://img.shields.io/badge/AppVersion-0.6.1-informational?style=flat-square)

Tractus-X Discovery Finder Helm Chart

**Homepage:** <https://eclipse-tractusx.github.io/>

## Source Code

* <https://github.com/eclipse-tractusx/sldt-discovery-finder>

## Requirements

| Repository | Name | Version |
|------------|------|---------|
| https://charts.bitnami.com/bitnami | postgresql | 12.12.10 |

## Values

| Key | Type | Default | Description |
|-----|------|---------|-------------|
| discoveryfinder.authentication | bool | `true` |  |
| discoveryfinder.containerPort | int | `4243` |  |
| discoveryfinder.dataSource.driverClassName | string | `"org.postgresql.Driver"` |  |
| discoveryfinder.dataSource.password | string | `nil` |  |
| discoveryfinder.dataSource.sqlInitPlatform | string | `"pg"` |  |
| discoveryfinder.dataSource.url | string | `"jdbc:postgresql://database:5432"` |  |
| discoveryfinder.dataSource.user | string | `nil` |  |
| discoveryfinder.host | string | `"localhost"` |  |
| discoveryfinder.idp.issuerUri | string | `"https://idp-url"` |  |
| discoveryfinder.idp.publicClientId | string | `"idpClientID"` |  |
| discoveryfinder.image.imagePullPolicy | string | `"IfNotPresent"` |  |
| discoveryfinder.image.registry | string | `"docker.io"` |  |
| discoveryfinder.image.repository | string | `"tractusx/sldt-discovery-finder"` |  |
| discoveryfinder.ingress.annotations | object | `{}` |  |
| discoveryfinder.ingress.className | string | `"nginx"` |  |
| discoveryfinder.ingress.enabled | bool | `false` |  |
| discoveryfinder.ingress.tls | bool | `false` |  |
| discoveryfinder.ingress.urlPrefix | string | `"/discoveryfinder"` |  |
| discoveryfinder.livenessProbe.failureThreshold | int | `3` |  |
| discoveryfinder.livenessProbe.initialDelaySeconds | int | `100` |  |
| discoveryfinder.livenessProbe.periodSeconds | int | `3` |  |
| discoveryfinder.properties.discoveryfinder | string | `nil` |  |
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

