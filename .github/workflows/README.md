# Setting Up a GitHub Actions Self-Hosted Runner on Ubuntu (VM/Pro)

This guide explains how to set up a self-hosted GitHub Actions runner on your Ubuntu VM (Pro) and connect it to your repository for CI/CD workflows.

---

## 1. Prerequisites

- An Ubuntu VM (physical or virtual, e.g., Ubuntu Pro)
- A GitHub repository where you want to use the runner
- `git` and `curl` installed

---

## 2. Generate Runner Registration Token

1. Go to your GitHub repository.
2. Click on **Settings** → **Actions** → **Runners**.
3. Click **New self-hosted runner**.
4. Select **Linux** as the OS.
5. Copy the commands shown in the "Download" and "Configure" steps.

---

## 3. Install the Runner on Ubuntu

Open a terminal on your Ubuntu VM and run the following (replace `<YOUR_REPO>` and `<YOUR_OWNER>` as needed):

```sh
# Create a directory for the runner
mkdir actions-runner && cd actions-runner

# Download the latest runner package (update the URL if needed)
curl -o actions-runner-linux-x64-2.316.0.tar.gz -L https://github.com/actions/runner/releases/download/v2.316.0/actions-runner-linux-x64-2.316.0.tar.gz

# Extract the installer
tar xzf ./actions-runner-linux-x64-2.316.0.tar.gz
```

---

## 4. Configure the Runner

Replace the URL and token with those provided by GitHub in your repository's runner setup page:

```sh
./config.sh --url https://github.com/<YOUR_OWNER>/<YOUR_REPO> --token <YOUR_TOKEN>
```

- Follow the prompts (you can accept defaults for most questions).

---

## 5. Start the Runner

To start the runner in the foreground:

```sh
./run.sh
```

To run the runner as a service (recommended for production): 

```sh
sudo ./svc.sh install
sudo ./svc.sh start
```

---

## 6. Verify Runner is Online

- Go to your repository's **Settings → Actions → Runners**.
- You should see your Ubuntu runner listed as **online**.

**Example:**  
![GitHub Runner Active](Jenkins_Assignment_7/hello-springboot-app/Images_and_Videos/Github_runner_active.png)

---

## 7. Use the Runner in Your Workflow

In your `.github/workflows/your-workflow.yml`, set:

```yaml
jobs:
  build-and-deploy:
    runs-on: self-hosted
    # ... your steps ...
```

---

## 8. Example: Java Docker K8s Workflow

Your workflow file (`.github/workflows/java-docker-k8s.yml`) will now use this runner to build, dockerize, and deploy your app.

---

## 9. Stopping or Removing the Runner

To stop the service:

```sh
sudo ./svc.sh stop
```

To uninstall:

```sh
sudo ./svc.sh uninstall
```

To remove the runner from GitHub, use the **Remove** button in the repository's runner settings.

---

# GitHub Actions Workflow: Java Docker K8s Deployment

This repository uses a GitHub Actions workflow (`.github/workflows/java-docker-k8s.yml`) to automate the build, Dockerization, and deployment of a Java Spring Boot application to a Kubernetes cluster running on Minikube (via a self-hosted runner on Ubuntu).

---

## What Does This Workflow Do?

Whenever you push changes to the `main` branch (specifically to files under `Jenkins_Assignment_7/hello-springboot-app/`), this workflow will:

1. **Checkout the Code**
   - Uses the latest code from your repository.

2. **Verify Minikube Status**
   - Ensures Minikube is running and ready for Kubernetes operations.

3. **Set Up Java 17**
   - Installs and configures JDK 17 using the Temurin distribution.

4. **Build the Spring Boot JAR**
   - Runs `mvn clean package` to compile and package your Java application.

5. **Configure Docker to Use Minikube**
   - Sets Docker to build images directly inside the Minikube VM, so Kubernetes can access them.

6. **Build the Docker Image**
   - Builds a Docker image for your Spring Boot app and tags it as `hello-springboot-app:latest`.

7. **Verify the Docker Image**
   - Checks that the Docker image was built and is available in the Minikube Docker registry.

8. **Delete and Recreate the Kubernetes Deployment**
   - Deletes any existing deployment named `hello-springboot-app` (ignores errors if not present).
   - Applies the `deployment.yaml` file to create/update the deployment in Minikube.

9. **Expose the Service URL**
   - Uses Minikube to print the external URL for accessing your application service.

10. **Verify Deployment and Pods**
    - Lists deployments and pods.
    - Describes the deployment and pods for debugging and verification.

---

**Example of a Successful GitHub Actions Run:**  
![Build Success GitHub Action](Jenkins_Assignment_7/hello-springboot-app/Images_and_Videos/build_success_gitAction.png)

---

## Why Use This Workflow?

- **Automates CI/CD** for your Java app on a local Kubernetes (Minikube) cluster.
- Ensures every code push is built, containerized, and deployed automatically.
- Provides immediate feedback on build, deployment, and service status.
- Uses a **self-hosted runner** so you can interact with your own Minikube and Docker environment.

---

## Prerequisites

- A self-hosted GitHub Actions runner registered on your Ubuntu VM with Minikube and Docker installed.
- Your `deployment.yaml` and application code in the correct directory.

---