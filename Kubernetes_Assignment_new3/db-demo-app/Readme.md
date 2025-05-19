# db-demo-app: Kubernetes & Helm Full Stack Example

This project demonstrates a **Node.js + MongoDB** web application deployed on Kubernetes using both raw YAML manifests and a production-ready Helm chart. It is designed for beginners and intermediate users to learn Kubernetes concepts, YAML, Helm, persistent storage, and real-world deployment patterns.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Kubernetes Concepts](#kubernetes-concepts)
- [Project Structure](#project-structure)
- [Setup & Prerequisites](#setup--prerequisites)
- [Running with Raw YAML](#running-with-raw-yaml)
- [Running with Helm](#running-with-helm)
- [Automated Deployment Script](#automated-deployment-script)
- [Basic Kubernetes Commands](#basic-kubernetes-commands)
- [Troubleshooting & Tips](#troubleshooting--tips)
- [Advanced: Customizing & Scaling](#advanced-customizing--scaling)
- [References](#references)

---

## Project Overview

- **Node.js App:** Simple email storage web app using Express and Mongoose.
- **MongoDB:** Used as the backend database, deployed as a separate pod.
- **Persistent Storage:** MongoDB data is persisted using Kubernetes PV/PVC.
- **Helm Chart:** All resources are templated for easy deployment and upgrades.
- **Automation:** `deploy.sh` script for one-command install/upgrade.

---

## Kubernetes Concepts

- **Pod:** Smallest deployable unit, runs one or more containers.
- **Deployment:** Manages replica sets and rolling updates for pods.
- **Service:** Exposes pods to the network (ClusterIP, NodePort, LoadBalancer).
- **ConfigMap:** Injects configuration (like DB host/port) into pods.
- **PersistentVolume (PV):** Cluster-wide storage resource.
- **PersistentVolumeClaim (PVC):** Pod's request for storage.
- **Helm:** Kubernetes package manager for templated, repeatable deployments.

---

## Project Structure

```
db-demo-app/
├── index.js              # Node.js app source
├── index.html            # Frontend HTML
├── package.json          # Node.js dependencies
├── Dockerfile            # Container build for Node.js app
├── mongo-db.yml          # MongoDB Deployment & Service (YAML)
├── node-app.yml          # Node.js Deployment & Service (YAML)
├── mongo-config.yml      # ConfigMap for DB connection (YAML)
├── host-pv.yml           # PersistentVolume (YAML)
├── host-pvc.yml          # PersistentVolumeClaim (YAML)
├── mongo-helm/           # Helm chart for full stack deployment
│   ├── Chart.yaml
│   ├── values.yaml
│   ├── templates/
│   │   ├── node-app-deployment.yaml
│   │   ├── node-app-service.yaml
│   │   ├── mongodb-deployment.yaml
│   │   ├── mongodb-service.yaml
│   │   ├── configmap.yaml
│   │   ├── pv.yaml
│   │   ├── pvc.yaml
│   │   └── _helpers.tpl
├── deploy.sh             # Automated install/upgrade script
```

---

## Setup & Prerequisites

- **Linux OS** (recommended for Minikube)
- [Docker](https://docs.docker.com/get-docker/)
- [kubectl](https://kubernetes.io/docs/tasks/tools/)
- [minikube](https://minikube.sigs.k8s.io/docs/)
- [Helm](https://helm.sh/docs/intro/install/)

### Install Tools (Linux)

```sh
# Install kubectl
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
chmod +x kubectl
sudo mv kubectl /usr/local/bin/
kubectl version --client

# Install Minikube
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube
minikube version

# Start Minikube
minikube start
```

---

## Running with Raw YAML

1. **Apply Persistent Storage:**
   ```sh
   kubectl apply -f host-pv.yml
   kubectl apply -f host-pvc.yml
   ```

2. **Apply ConfigMap:**
   ```sh
   kubectl apply -f mongo-config.yml
   ```

3. **Deploy MongoDB:**
   ```sh
   kubectl apply -f mongo-db.yml
   ```

4. **Deploy Node.js App:**
   ```sh
   kubectl apply -f node-app.yml
   ```

5. **Access the App:**
   ```sh
   minikube service service-node-app
   ```

---

## Running with Helm

1. **Enable script execution:**
   ```sh
   chmod +x deploy.sh
   ```

2. **Deploy or upgrade everything:**
   ```sh
   ./deploy.sh
   ```

   - This script deletes old deployments, installs/upgrades the Helm release, and opens the Node.js app in your browser.

3. **Manual Helm Commands:**
   ```sh
   helm install mongo-helm ./mongo-helm
   helm upgrade mongo-helm ./mongo-helm
   helm uninstall mongo-helm
   helm list
   helm status mongo-helm
   ```

---

## Automated Deployment Script

- **File:** `deploy.sh`
- **What it does:**
  - Deletes old `mongo-app` and `node-app` deployments (if any)
  - Installs or upgrades the Helm release
  - Opens the Node.js app service in your browser

**To use:**
```sh
chmod +x deploy.sh
./deploy.sh
```

---

## Basic Kubernetes Commands

```sh
kubectl get pods
kubectl get deployments
kubectl get services
kubectl logs <pod-name>
kubectl describe pod <pod-name>
kubectl delete deployment <name>
kubectl delete service <name>
kubectl apply -f <file.yaml>
kubectl scale deployment <name> --replicas=3
kubectl rollout status deployment/<name>
kubectl set image deployment/<name> <container>=<image:tag>
kubectl rollout undo deployment/<name>
```

---

## Troubleshooting & Tips

- **Check pod logs for errors:**
  ```sh
  kubectl logs <pod-name>
  ```
- **Describe pod for detailed info:**
  ```sh
  kubectl describe pod <pod-name>
  ```
- **If service is not accessible:**
  - Ensure your Node.js app listens on the port specified in `containerPort` (default: 3000).
  - Make sure the service `targetPort` matches your app's listening port.
  - Check for MongoDB connection errors in Node.js logs.
- **PersistentVolume issues:**  
  Make sure PV and PVC are bound and available.

---

## Advanced: Customizing & Scaling

- **Change replica count:**  
  Edit `values.yaml` (`replicaCount`) or use `kubectl scale`.
- **Change Node.js or MongoDB image:**  
  Edit `values.yaml` and upgrade the Helm release.
- **Add environment variables:**  
  Use `configmap.yaml` and reference in deployment templates.
- **Scale up/down:**
  ```sh
  kubectl scale deployment node-app --replicas=3
  ```
- **Zero-downtime updates:**  
  Update image tag and run:
  ```sh
  helm upgrade mongo-helm ./mongo-helm
  ```

---

## References

- [Kubernetes Official Docs](https://kubernetes.io/docs/home/)
- [Helm Official Docs](https://helm.sh/docs/)
- [Minikube Docs](https://minikube.sigs.k8s.io/docs/)
- [Node.js Docker Best Practices](https://nodejs.org/en/docs/guides/nodejs-docker-webapp/)

---

**Happy Learning & Deploying!**