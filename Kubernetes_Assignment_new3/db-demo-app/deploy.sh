#!/bin/bash

RELEASE="mongo-helm"
CHART_DIR="./mongo-helm"

# Delete existing deployments if they exist
kubectl delete deployment mongo-app --ignore-not-found
kubectl delete deployment node-app --ignore-not-found

# Check if the release exists
if helm status "$RELEASE" > /dev/null 2>&1; then
  echo "Helm release '$RELEASE' exists. Upgrading..."
  helm upgrade "$RELEASE" "$CHART_DIR"
else
  echo "Helm release '$RELEASE' does not exist. Installing..."
  helm install "$RELEASE" "$CHART_DIR"
fi

# Open the Node.js app service in the browser (minikube tunnel)
minikube service node-app-service

#for unistalling helm release :- helm uninstall mongo-helm