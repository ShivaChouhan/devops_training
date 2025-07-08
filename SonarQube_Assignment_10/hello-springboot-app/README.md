# 🚀 Hello Spring Boot App - Jenkins Assignment 7

This project demonstrates a simple Java Spring Boot web application, containerized with Docker, and automated using Jenkins pipelines for CI/CD. The application is deployed to a Kubernetes cluster (Minikube) as part of the workflow.

## 🖥️ Virtual Machine Setup
Before starting the project, two VMs were set up using Oracle VirtualBox:
- 🖥️ **Master VM**: Acts as the Jenkins master node. Jenkins was installed and accessed via a web browser on this VM.
- 🖥️ **Slave VM**: Acts as the Jenkins agent (slave node) for running build and deployment jobs.

This master-slave setup allows distributed builds and better resource management for CI/CD pipelines.

## ⚙️ Jenkins Setup
After installing Jenkins on the master VM:
1. 🌐 Open Jenkins in your web browser (usually at `http://<master-vm-ip>:8080`).
2. 🔑 Complete the initial setup wizard:
   - Unlock Jenkins using the administrator password from `/var/lib/jenkins/secrets/initialAdminPassword`.
   - Install suggested plugins.
   - Create your first admin user.
3. 🛠️ Configure system settings as needed (e.g., tools, global credentials).
4. ➕ Add the slave VM as a Jenkins agent for distributed builds.

## 🤖 Step-by-Step: Adding and Connecting a Jenkins Slave Node (Agent)

1. **Create the Agent Node in Jenkins (Master VM):**
   - ➕ Go to **Manage Jenkins** > **Manage Nodes and Clouds**.
   - ➕ Click **New Node**.
   - 📝 Enter a name (e.g., `Slave_node`), select **Permanent Agent**, and click OK.
   - 🔢 Set the number of executors to **2**.
   - 🗂️ Enter the remote root directory as **/opt/build**.
   - 🏷️ Set the labels (e.g., `Slave_node`) to match your pipeline configuration.
   - ⚡ Choose the launch method: **Launch agent by connecting it to the controller** (JNLP/agent.jar method).
   - 💾 Save the node configuration.

2. **Prepare the Slave VM:**
   - 🗂️ Manually create the `/opt/build` directory on the slave VM:
     ```sh
     sudo mkdir -p /opt/build
     sudo chown jenkins:jenkins /opt/build
     ```
   - 📁 This directory is created at the root (`/`) of the filesystem and will be used by Jenkins for job workspaces and files.

3. **Transfer agent.jar to the Slave VM:**
   - 📥 Download `agent.jar` from the Jenkins master (controller). If the slave VM cannot download it directly, use `scp` to transfer it:
     ```sh
     # Run this command on your local machine or the master VM
     scp jenkins@<master-vm-ip>:/path/to/agent.jar jenkins@<slave-vm-ip>:/home/Downloads/agent.jar
     ```

4. **Connect the Slave Node to Jenkins:**
   - 🔗 In the Jenkins UI on the master VM, open the configuration page for your `Slave_node`.
   - 📋 Copy the Java command provided (it includes the JNLP URL and secret).
   - 💻 Paste and run this command on the slave VM to connect the agent to the Jenkins master:
     ```sh
     java -jar /home/jenkins/agent.jar -jnlpUrl http://<master-vm-ip>:8080/computer/Slave_node/jenkins-agent.jnlp -secret <secret-key> -workDir "/opt/build"
     ```
   - ✅ Once executed, the slave node will appear as "connected" in the Jenkins UI and will be ready to run jobs.

## 🐳 Docker Credentials Setup in Jenkins
To enable Jenkins to push Docker images to DockerHub, set up Docker credentials:
1. 🔐 In Jenkins, go to **Manage Jenkins** > **Manage Credentials**.
2. 🌍 Select the appropriate domain (or use "(global)")).
3. ➕ Click **Add Credentials**.
4. 👤 Choose **Username with password** as the kind.
5. 📝 Enter your DockerHub username and use a **Docker Hub access token** (not your password) for enhanced security.
6. 🏷️ Set the ID to `shivachouhan` (or the ID referenced in your Jenkinsfile).
7. 💾 Save the credentials.

Jenkins pipelines can now use these credentials to authenticate with DockerHub securely.

## 📦 Project Structure
- `HelloApplication.java`: Main Spring Boot application entry point.
- `HelloController.java`: REST controller exposing `/hello` endpoint.
- `pom.xml`: Maven build configuration.
- `Dockerfile`: Containerizes the Spring Boot app.
- `deployment.yaml`: Kubernetes manifest for deployment and service.
- `Jenkinsfile`: Jenkins pipeline for Docker build and push to DockerHub.
- `JenkinsFile_K8s`: Jenkins pipeline for building, Dockerizing, and deploying to Minikube.

## 📁 Project Folder Structure

```
hello-springboot-app/
├── deployment.yaml
├── Dockerfile
├── Jenkinsfile
├── JenkinsFile_K8s
├── pom.xml
├── README.md
├── src/
    └── main/
        └── java/
            └── com/
                └── example/
                    └── helloapp/
                        ├── HelloApplication.java
                        └── HelloController.java
```

## ⚙️ How It Works
1. 🌐 **Spring Boot App**: Exposes a `/hello` endpoint returning a greeting message.
2. 🏗️ **Build & Package**: Uses Maven to build a JAR file.
3. 🐳 **Dockerization**: Dockerfile packages the JAR into an image.
4. 🤖 **Jenkins Pipelines**:
   - `Jenkinsfile`: Builds, Dockerizes, and pushes the image to DockerHub.
   - `JenkinsFile_K8s`: Builds, Dockerizes, and deploys the app to Minikube using Kubernetes manifests.
5. ☸️ **Kubernetes Deployment**: Deploys the app and exposes it via a NodePort service.

## 📝 Usage
### 🔧 Prerequisites
- ☕ Java 17
- 🐘 Maven
- 🐳 Docker
- 🤖 Jenkins
- ☸️ Minikube & kubectl

### ▶️ Build & Run Locally
```sh
mvn clean package
java -jar target/hello-springboot-app-1.0.0.jar
```
Visit: [http://localhost:8080/hello](http://localhost:8080/hello)

### 🐳 Build Docker Image
```sh
docker build -t hello-springboot-app:latest .
```

### 🐳 Run with Docker
```sh
docker run -p 8080:8080 hello-springboot-app:latest
```

### ☸️ Deploy to Minikube
```sh
kubectl apply -f deployment.yaml
```

### 🤖 Jenkins Pipelines
- Use `Jenkinsfile` for CI/CD with DockerHub.
- Use `JenkinsFile_K8s` for CI/CD with Minikube.

## 📚 Reference: Jenkins Agent Working Directory and agent.jar

- 🗂️ The Jenkins agent (slave node) uses `/opt/build` as its remote root directory, created at the root (`/`) of the filesystem on the slave VM. All Jenkins job workspaces, build files, and temporary files for jobs running on the agent will be stored under this directory.
- 📥 When adding a new slave node (agent) in Jenkins using the "Launch agent by connecting it to the controller" method, Jenkins provides a command to run on the slave VM. This command downloads the `agent.jar` file from the Jenkins master (controller) to the slave VM, or you can transfer it using `scp` if needed.
- 🔗 The `agent.jar` file is responsible for establishing a connection between the slave node and the Jenkins master, allowing the agent to receive and execute jobs.

### Commands to see Jenkins build logs on terminal
```sh
USERNAME="ShivanandChouhan"  
API_TOKEN="11762348dd6b221028bbb14d257fd171f9"  # Replace with new token if needed

# Get Crumb
CRUMB=$(curl -s -u "$USERNAME:$API_TOKEN" \
  "http://localhost:8080/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,\":\",//crumb)")

# Fetch logs
curl -u "$USERNAME:$API_TOKEN" -H "$CRUMB" \
  "http://localhost:8080/job/k8s_build/44/consoleText"
```
- curl -u "$USERNAME:$API_TOKEN" -H "$CRUMB" \
  "http://JENKINS_URL/job/JOB_NAME/lastBuild/consoleText"

-For API_Token: Jenkins->Profile->security->API Token 

-----

# Jenkins SSH-Based Linux Slave (Agent) Setup

This guide explains how to configure a Linux machine as a Jenkins slave (agent) using **SSH key authentication**.

---

## 🔧 Step 1: Jenkins Master Setup
- Jenkins is installed on a master machine (e.g., Windows laptop).
- Access Jenkins at: `http://localhost:8080`
- Goal: Connect a Linux slave (agent) to Jenkins master.

---

## 🧩 Step 2: Create Node in Jenkins
- Go to **Manage Jenkins** → **Manage Nodes and Clouds**
- Click **New Node**
- Enter a name (e.g., `devops_test`)
- Select **Permanent Agent**, then click **Create**

---

## ⚙️ Step 3: Configure Node Details
- **Description**: (Optional)
- **Number of Executors**: Set based on CPU cores (e.g., `2`)
- **Remote Root Directory**: Directory on agent (e.g., `/opt/jenkins`)
  > Create it on slave: `sudo mkdir -p /opt/jenkins && sudo chown -R <user> /opt/jenkins`
- **Labels**: e.g., `dev`, for job targeting
- **Usage**: Leave default or customize

---

## 🚀 Step 4: Launch Method
- Choose: **Launch agent via SSH**
- Set **Host** to slave’s IP or hostname

---

## 🔐 Step 5: Generate SSH Key
On your local or Jenkins master machine:

```sh
ssh-keygen
```

## 🔐 SSH Key Generation

- Accept defaults  
- Leave passphrase **empty**

This creates:

- **Private Key**: `~/.ssh/id_rsa`  
- **Public Key**: `~/.ssh/id_rsa.pub`

---

## 🔑 Step 6: Add SSH Credentials in Jenkins

Go to:

**Manage Jenkins → Credentials → System → Global Credentials**

Add:

- **Kind**: SSH Username with private key  
- **Username**: User on slave machine (e.g., `ubuntu`, `root`)  
- **Private Key**: Paste `~/.ssh/id_rsa` content  
- **Host Key Verification Strategy**: Non-verifying (for test setup)

---

## 🖥️ Step 7: Set Up Public Key on Slave Machine

On the Linux agent:

```bash
mkdir -p ~/.ssh
nano ~/.ssh/authorized_keys
```

Paste contents of `id_rsa.pub` from Jenkins master into this file, then run:

```bash
chmod 600 ~/.ssh/authorized_keys
chmod 700 ~/.ssh
```

---

## 🔗 Step 8: Launch Agent

In Jenkins:

- Save the node configuration  
- Click **Launch agent**

Jenkins will:

- Connect via SSH  
- Push agent JAR  
- Start the slave

---

## ✅ Step 9: Verify Connection

- Node should be **online** and **idle**  
- You can now assign jobs using **labels** or **node selector**

---

## 📝 Notes

- Avoid running builds on **master**  
- Use **SSH key-based auth** for better security  
- Adjust number of **executors** based on CPU  
- For production: configure **stricter host key checking**

---

## 🏁 Success!

You now have a **Jenkins master-slave setup** with a Linux agent using **SSH authentication** 🎉
