# mongo-helm

This Helm chart deploys a Node.js application and a MongoDB database on Kubernetes. It includes persistent storage for MongoDB and exposes the Node.js app as a service.  
A helper shell script (`deploy.sh`) is provided to automate installation and upgrades.

---

## Directory Structure

```
mongo-helm/
├── Chart.yaml
├── values.yaml
├── templates/
│   ├── _helpers.tpl
│   ├── configmap.yaml
│   ├── mongodb-deployment.yaml
│   ├── mongodb-service.yaml
│   ├── node-app-deployment.yaml
│   ├── node-app-service.yaml
│   ├── pv.yaml
│   ├── pvc.yaml
│   └── tests/
│       └── test-connection.yaml
deploy.sh
```

---

## Prerequisites

- [Helm](https://helm.sh/) installed
- [kubectl](https://kubernetes.io/docs/tasks/tools/) installed and configured
- [minikube](https://minikube.sigs.k8s.io/) or a running Kubernetes cluster

---

## Usage

### 1. Enable Shell Script Execution

```sh
chmod +x deploy.sh
```

### 2. Deploy or Upgrade the Chart

```sh
./deploy.sh
```
- This script will:
  - Delete any existing `mongo-app` and `node-app` deployments
  - Install or upgrade the Helm release (`mongo-helm`)
  - Open the Node.js app service in your browser using `minikube service node-app-service`

---

## Helm Basic Commands

### Install the Chart

```sh
helm install mongo-helm ./mongo-helm
```

### Upgrade the Chart

```sh
helm upgrade mongo-helm ./mongo-helm
```

### Uninstall the Release

```sh
helm uninstall mongo-helm
```

### List All Releases

```sh
helm list
```

### Show Release Status

```sh
helm status mongo-helm
```

### Dry Run (Preview Changes)

```sh
helm install --dry-run --debug mongo-helm ./mongo-helm
```

### Lint the Chart

```sh
helm lint ./mongo-helm
```

---

## Accessing the Node.js App

After deployment, the script will automatically open the Node.js app in your browser.  
If you want to access it manually:

```sh
minikube service node-app-service
```

---

## Notes

- Make sure your Node.js app listens on the port specified by `containerPort` in `values.yaml`.
- Persistent storage is provisioned for MongoDB using a PersistentVolume and PersistentVolumeClaim.
- Configuration for MongoDB connection is managed via a ConfigMap.

---

## Uninstalling Everything

To remove the Helm release and all associated resources:

```sh
helm uninstall mongo-helm
```

---

**Happy Helm Deploying!**