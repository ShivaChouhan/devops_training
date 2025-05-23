# Prometheus & Grafana Monitoring on Kubernetes (Minikube)

This assignment demonstrates how I set up Prometheus and Grafana to monitor a Kubernetes cluster using Minikube and Helm.

## Prerequisites
- Minikube installed and running
- kubectl configured for your cluster
- Helm installed

---

## 1. I started Minikube
I started my local Kubernetes cluster with:

```sh
minikube start
```

---

## 2. I added the Prometheus Helm repository
I added the Prometheus Community Helm charts and updated them:

```sh
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update
```

---

## 3. I installed the Prometheus Operator Stack
I installed the kube-prometheus-stack using Helm:

```sh
helm install prometheus prometheus-community/kube-prometheus-stack \
  --namespace monitoring \
  --create-namespace \
  --wait \
  --timeout=10m
```

I saw output confirming the deployment. Then, I checked the status of the pods:

```sh
kubectl get pods -n monitoring
```

Example output:
```
NAME                                                     READY   STATUS    RESTARTS      AGE
alertmanager-prometheus-kube-prometheus-alertmanager-0   2/2     Running   2 (54m ago)   76m
prometheus-grafana-77bcfb9bdb-vzdzx                      3/3     Running   3 (54m ago)   77m
prometheus-kube-prometheus-operator-6d9d668dbd-p8hqt     1/1     Running   4             77m
prometheus-kube-state-metrics-7457555cf7-7qfmp           1/1     Running   1 (54m ago)   77m
prometheus-prometheus-kube-prometheus-prometheus-0       2/2     Running   2 (54m ago)   76m
prometheus-prometheus-node-exporter-ctb9r                1/1     Running   2 (44m ago)   77m

```

---

## 4. I accessed Grafana
Since the Grafana service is `ClusterIP` (internal), I needed to port-forward to access the UI.

First, I got the Grafana admin password:

```sh
kubectl get secrets prometheus-grafana -o jsonpath="{.data.admin-password}" | base64 -d ; echo
```

The default username is `admin` and the password is usually `prom-operator` (I verified with the command above).

I forwarded the Grafana port to my local machine:

```sh
kubectl port-forward -n monitoring svc/prometheus-grafana 3000:80
```

Then, I opened my browser and went to [http://localhost:3000](http://localhost:3000).

- **Grafana Login Page:**
  ![Grafana Login Page](Images/grafana_login.png)

I logged in with:
- **Username:** `admin`
- **Password:** `prom-operator`

- **Grafana Home Page:**
  ![Grafana Home Page](Images/grafana_home.png)
  
---
## 5. I Accessed Prometheus UI
Like Grafana, Prometheus is also exposed via a ClusterIP service. So I used kubectl port-forward to access the Prometheus UI locally.

First, I found the service:

```sh
kubectl get svc -n monitoring | grep prometheus
```
Example output:
```
prometheus-grafana                        ClusterIP   10.103.108.82    <none>        80/TCP                       77m
prometheus-kube-prometheus-alertmanager   ClusterIP   10.110.240.16    <none>        9093/TCP,8080/TCP            77m
prometheus-kube-prometheus-operator       ClusterIP   10.105.22.133    <none>        443/TCP                      77m
prometheus-kube-prometheus-prometheus     ClusterIP   10.97.7.76       <none>        9090/TCP,8080/TCP            77m
prometheus-kube-state-metrics             ClusterIP   10.108.232.88    <none>        8080/TCP                     77m
prometheus-operated                       ClusterIP   None             <none>        9090/TCP                     77m
prometheus-prometheus-node-exporter       ClusterIP   10.107.104.217   <none>        9100/TCP                     77m


```
Then, I forwarded port 9090:

```
kubectl port-forward svc/prometheus-kube-prometheus-prometheus -n monitoring 9090:9090
```
Once it was running, I opened http://localhost:9090 in my browser.

- **Prometheus Home Page:**
  ![Grafana Home Page](Images/prometheus-home.png)
---
## 6. Useful Commands I Used
- To list all services:
  ```sh
  kubectl get svc -n monitoring
  ```
- To list all deployments:
  ```sh
  kubectl get deployment -n monitoring
  ```
- To check logs for Grafana:
  ```sh
  kubectl logs <grafana-pod-name> -c grafana
  ```

---

This setup shows how I successfully deployed a robust monitoring solution for my Kubernetes cluster using Prometheus and Grafana. I can now explore cluster metrics and dashboards with ease!
