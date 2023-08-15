## Deploy using Helm and K8s
If you have a running Kubernetes cluster available, you can deploy the Discovery Finder using our Helm Chart, which is located under `charts/discoveryfinder`.
In case you don't have a running cluster, you can set up one by yourself locally, using [minikube](https://minikube.sigs.k8s.io/docs/start/).
In the following, we will use a minikube cluster for reference.

Before deploying the Discovery Finder, enable a few add-ons in your minikube cluster by running the following commands:

`minikube addons enable storage-provisioner`

`minikube addons enable default-storageclass`

`minikube addons enable ingress`

Fetch all dependencies by running `helm dep up charts/discoveryfinder`.

In order to deploy the helm chart, first create a new namespace "discovery": `kubectl create namespace discovery`.

Then run `helm install discoveryfinder -n discovery charts/discoveryfinder`. This will set up a new helm deployment in the discovery namespace. By default, the deployment contains the Discovery Finder instance itself, and a Postgresql.

Check that the two containers are running by calling `kubectl get pod -n discovery`.

To access the Discovery Finder API from the host, you need to configure the `Ingress` resource.
By default, the `Ingress` is disabled.

If you enable the `Ingress`, the Discovery Finder exposes the API on https://minikube/discovery/discoveryfinder.
For that to work, you need to append `/etc/hosts` by running `echo "minikube $(minikube ip)" | sudo tee -a /etc/hosts`.

For automated certificate generation, use and configure [cert-manager](https://cert-manager.io/).
By default, authentication is deactivated, please adjust `discoveryfinder.authentication` if needed.

## Parameters
The Helm Chart can be configured using the following parameters. For a full overview, please see the [values.yaml](./charts/discoveryfinder/values.yaml).

### Discovery Finder parameters
| Key | Type | Default                             | Description                                                                                                                                                                                                                    |
|-----|------|-------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| discoveryfinder.authentication | bool | `false`                             | Enables OAuth2 based authentication/authorization                                                                                                                                                                              |
| discoveryfinder.containerPort | int | `4243`                              | Containerport                                                                                                                                                                                                                  |
| discoveryfinder.dataSource.driverClassName | string | `"org.postgresql.Driver"`           | The driver class name for the database connection                                                                                                                                                                              |
| discoveryfinder.dataSource.password | string | `"password"`                        | Datasource password                                                                                                                                                                                                            |
| discoveryfinder.dataSource.sqlInitPlatform | string | `"pg"`                              | Datasource InitPlatform                                                                                                                                                                                                        |
| discoveryfinder.dataSource.url | string | `"jdbc:postgresql://database:5432"` | Datasource URL                                                                                                                                                                                                                 |
| discoveryfinder.dataSource.user | string | `"user"`                            | Datasource user                                                                                                                                                                                                                |
| discoveryfinder.host | string | `"localhost"`                       | This value is used by the Ingress object (if enabled) to route traffic                                                                                                                                                         |
| discoveryfinder.idp.issuerUri | string | `""`                 | The issuer URI of the OAuth2 identity provider                                                                                                                                                                                 |
| discoveryfinder.idp.publicClientId | string | `""`                     | ClientId                                                                                                                                                                                                                       |
| discoveryfinder.image.imagePullPolicy | string | `"IfNotPresent"`                    | ImagepullPolicy                                                                                                                                                                                                                |
| discoveryfinder.image.registry | string | `"ghcr.io/catenax-ng"`              | Image registry                                                                                                                                                                                                                 |
| discoveryfinder.image.repository | string | `"sldt-discovery-finder"`           | Image repository                                                                                                                                                                                                               |
| discoveryfinder.image.version | string | `""`                                | Version of image. By default the app Version from Chart.yml is used. You can overwrite the version to use an  other version of sldt-discovery-finder                                                                           |
| discoveryfinder.ingress.annotations."cert-manager.io/cluster-issuer" | string | `"selfsigned-cluster-issuer"`       |                                                                                                                                                                                                                                |
| discoveryfinder.ingress.annotations."nginx.ingress.kubernetes.io/cors-allow-credentials" | string | `"true"`                            |                                                                                                                                                                                                                                |
| discoveryfinder.ingress.annotations."nginx.ingress.kubernetes.io/enable-cors" | string | `"true"`                            |                                                                                                                                                                                                                                |
| discoveryfinder.ingress.annotations."nginx.ingress.kubernetes.io/rewrite-target" | string | `"/$2"`                             |                                                                                                                                                                                                                                |
| discoveryfinder.ingress.annotations."nginx.ingress.kubernetes.io/use-regex" | string | `"true"`                            |                                                                                                                                                                                                                                |
| discoveryfinder.ingress.annotations."nginx.ingress.kubernetes.io/x-forwarded-prefix" | string | `"/discoveryfinder"`                |                                                                                                                                                                                                                                |
| discoveryfinder.ingress.className | string | `"nginx"`                           | The Ingress class name                                                                                                                                                                                                         |
| discoveryfinder.ingress.enabled | bool | `false`                             | Configures if an Ingress resource is created                                                                                                                                                                                   |
| discoveryfinder.ingress.tls | bool | `false`                             | Configures whether the `Ingress` should include TLS configuration. In that case, a separate `Secret` (as defined by `registry.ingress.tlsSecretName`) needs to be provided manually or by using [cert-manager](https://cert-manager.io/) |
| discoveryfinder.ingress.urlPrefix | string | `"/discoveryfinder"`                | The url prefix that is used by the Ingress resource to route traffic                                                                                                                                                           |
| discoveryfinder.replicaCount | int | `1`                                 | Replica count                                                                                                                                                                                                                  |
| discoveryfinder.resources.limits.memory | string | `"1024Mi"`                          | Resources limit memory                                                                                                                                                                                                         |
| discoveryfinder.resources.requests.memory | string | `"512Mi"`                           | Resources request memory                                                                                                                                                                                                       |
| discoveryfinder.service.port | int | `8080`                              | Service port                                                                                                                                                                                                                   |
| discoveryfinder.service.type | string | `"ClusterIP"`                       | Service type                                                                                                                                                                                                                   |
### PostgreSQL parameters
| Key | Type | Default                             | Description                                                                                   |
|-----|------|-------------------------------------|-----------------------------------------------------------------------------------------------|
| enablePostgres | bool | `true`                              | If enabled, the postgreSQL instance will be run. Disable if you use your own hosted postgreSQL. |
| postgresql.auth.database | string | `"discoveryfinder"`                 | Database name                                                                                 |
| postgresql.auth.password | string | `"password"`                        | Password for authentication at the database                                                   |
| postgresql.auth.username | string | `"catenax"`                         | Username that is used to authenticate at the database                                         |
| postgresql.primary.persistence.enabled | bool | `true`                              | Persistence enabled                                                                           |
| postgresql.primary.persistence.size | string | `"50Gi"`                            | Size of persistence                                                                           |
| postgresql.service.ports.postgresql | int | `5432`                              | Size of the PersistentVolume that persists the data                                           |

### Prerequisites

- Kubernetes 1.19+
- Helm 3.2.0+
- PV provisioner support in the underlying infrastructure