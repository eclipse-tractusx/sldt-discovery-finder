# discoveryfinder

![Version: 0.5.1](https://img.shields.io/badge/Version-0.5.1-informational?style=flat-square) ![Type: application](https://img.shields.io/badge/Type-application-informational?style=flat-square) ![AppVersion: 0.6.1](https://img.shields.io/badge/AppVersion-0.6.1-informational?style=flat-square)

**Tractus-X Discovery Finder Helm Chart** <br/>
This chart installs the Discovery Finder and its direct dependencies.

## Requirements

| Repository | Name | Version |
|------------|------|---------|
| https://charts.bitnami.com/bitnami | postgresql | 12.2.3 |

## Prerequisites
- Kubernetes 1.19+
- Helm 3.2.0+
- PV provisioner support in the underlying infrastructure

## Install
```
helm dep up charts/discoveryfinder
kubectl create namespace discovery
helm install discoveryfinder -n discovery charts/discoveryfinder
```

## Values

| Key | Type | Default | Description |
|-----|------|-------|-------------|
| discoveryfinder.authentication | bool | `true` |  |
| discoveryfinder.containerPort | int | `4243` |  |
| discoveryfinder.dataSource.driverClassName | string | `"org.postgresql.Driver"` |  |
| discoveryfinder.dataSource.password | string | `"password"` |  |
| discoveryfinder.dataSource.sqlInitPlatform | string | `"pg"` |  |
| discoveryfinder.dataSource.url | string | `"jdbc:postgresql://database:5432"` |  |
| discoveryfinder.dataSource.user | string | `"user"` |  |
| discoveryfinder.host | string | `"localhost"` |  |
| discoveryfinder.idp.issuerUri | string | `"https://idp-url"` |  |
| discoveryfinder.idp.publicClientId | string | `"idpClientID"` |  |
| discoveryfinder.image.imagePullPolicy | string | `"IfNotPresent"` |  |
| discoveryfinder.image.registry | string | `"docker.io"` |  |
| discoveryfinder.image.repository | string | `"tractusx/sldt-discovery-finder"` |  |
| discoveryfinder.ingress.annotations."cert-manager.io/cluster-issuer" | string | `"selfsigned-cluster-issuer"` |  |
| discoveryfinder.ingress.annotations."nginx.ingress.kubernetes.io/cors-allow-credentials" | string | `"true"` |  |
| discoveryfinder.ingress.annotations."nginx.ingress.kubernetes.io/enable-cors" | string | `"true"` |  |
| discoveryfinder.ingress.annotations."nginx.ingress.kubernetes.io/rewrite-target" | string | `"/$2"` |  |
| discoveryfinder.ingress.annotations."nginx.ingress.kubernetes.io/use-regex" | string | `"true"` |  |
| discoveryfinder.ingress.annotations."nginx.ingress.kubernetes.io/x-forwarded-prefix" | string | `"/discoveryfinder"` |  |
| discoveryfinder.ingress.className | string | `"nginx"` |  |
| discoveryfinder.ingress.enabled | bool | `false` |  |
| discoveryfinder.ingress.tls | bool | `false` |  |
| discoveryfinder.ingress.urlPrefix | string | `"/discoveryfinder"` |  |
| discoveryfinder.properties.discoveryfinder | string | `nil` |  |
| discoveryfinder.replicaCount | int | `1` |  |
| discoveryfinder.resources.limits.memory | string | `"1024Mi"` |  |
| discoveryfinder.resources.requests.memory | string | `"512Mi"` |  |
| discoveryfinder.service.port | int | `8080` |  |
| discoveryfinder.service.type | string | `"ClusterIP"` |  |
| enablePostgres | bool | `true` |  |
| discoveryfinder.livenessProbe.initialDelaySeconds                        | int    | `100`                                                                                                                                                        |  |
| discoveryfinder.livenessProbe.failureThreshold                        | int    | `3`                                                                                                                                                          |  |
| discoveryfinder.livenessProbe.periodSeconds                        | int    | `3`                                                                                                                                                          |  |
| discoveryfinder.readinessProbe.initialDelaySeconds                        | int    | `100`                                                                                                                                                        |  |
| discoveryfinder.readinessProbe.failureThreshold                        | int    | `3`                                                                                                                                                          |  |
| discoveryfinder.readinessProbe.periodSeconds                        | int    | `3`                                                                                                                                                          |  |
| postgresql.auth.database | string | `"discoveryfinder"` |  |
| postgresql.auth.password | string | `` |  |
| postgresql.auth.username | string | `"catenax"` |  |
| postgresql.auth.existingSecret | string | `"secret-discoveryfinder-postgres-init"` |  |

| postgresql.primary.persistence.enabled | bool | `true` |  |
| postgresql.primary.persistence.size | string | `"50Gi"` |  |
| postgresql.service.ports.postgresql | int | `5432` |  |

----------------------------------------------
Autogenerated from chart metadata using [helm-docs v1.11.0](https://github.com/norwoodj/helm-docs/releases/v1.11.0)
