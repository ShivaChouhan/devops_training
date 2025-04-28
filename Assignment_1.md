# 🖥️ Jenkins Local Setup on Windows (Without Commands)
 
This guide helps you install and set up Jenkins **locally on a Windows machine** without using any terminal or command line. Perfect for beginners! ✅
 
---
 
## 📥 Step 1: Download Jenkins for Windows
 
1. Go to the official Jenkins download page:

   👉 [https://www.jenkins.io/download/](https://www.jenkins.io/download/)
 
2. Under **Windows**, click **"Download"**.
 
---
 
## 📦 Step 2: Install Jenkins
 
1. After downloading the `.msi` installer file, **double-click** to open it.

2. The Jenkins Setup Wizard will launch.

3. Follow the steps:

   - Accept license terms

   - Choose the installation path (or leave it as default)

   - Keep the default port `8080`

   - Proceed with the installation
 
4. Wait for the installation to complete.
 
---
 
## 🔐 Step 3: Unlock Jenkins
 
1. Once installed, Jenkins will open automatically in your browser:

   👉 [http://localhost:8080](http://localhost:8080)
 
2. It will ask you for an **initial admin password**.
 
3. Open **File Explorer** and navigate to:

 
4. Open the `initialAdminPassword` file and **copy** the password.
 
5. Paste it into the Jenkins setup screen to unlock.
 
---
 
## 🔌 Step 4: Install Suggested Plugins
 
1. Jenkins will ask what plugins to install.

2. Click **“Install suggested plugins”**.

3. Wait while the plugins are downloaded and installed.
 
---- Click **Save and Finish**.
 
---
 
## ✅ Jenkins is Ready!
 
Click **"Start using Jenkins"** and you’ll be taken to the Jenkins dashboard. 🎉
 
You're now ready to:
 
- Create pipelines

- Integrate with GitHub

- Automate your builds and deployments
 
## 👤 Step 5: Create Your First Admin User
 
Fill in the details:
 
- **Username**: ShivanandChouhan  

- **Password**: ------  

- **Full Name**: Shivanand Chouhan  

- **Email**: shivanand.chouhan@calsoftinc.com  
 
Click **Save and Continue**.
 
---
 
## 🌐 Step 6: Configure Jenkins URL
 
- Jenkins will auto-fill the local URL:

### 🧪 Step 7: Jenkinsfile Created on GitHub & CI/CD Pipeline Setup
After successfully setting up Jenkins, I created a simple Jenkinsfile in my GitHub repository to test the CI/CD pipeline.

🔹 Jenkinsfile Content
Here’s the content of the Jenkinsfile I added to the root of my GitHub repository:


```
pipeline {
    agent any

    stages {
        stage('assignment 1') {
            steps {
                bat 'ECHO Hello World'
            }
        }
    }
}
```
🔹 GitHub Repository
The Jenkinsfile is located in this GitHub repo:
🔗 https://github.com/ShivaChouhan/devops_training.git

🔹 CI/CD Pipeline Setup in Jenkins
To connect Jenkins with the GitHub repo:

1.Opened Jenkins dashboard → Clicked "New Item"

2.Selected Pipeline → Named it hello-world-pipeline

3.Chose "Pipeline script from SCM"

4.Selected:

5.SCM: Git

6.Repository URL: https://github.com/ShivaChouhan/devops_training.git

(If private repo: added GitHub credentials)
my repo is public so credentials do not required here

7.Set the branch to main (or whichever branch contains the Jenkinsfile)

8.Clicked Save and then "Build Now"

✅ Pipeline Output
The build was triggered, and in the Jenkins console log, I saw:

Started by user Shivanand Chouhan

Obtained JENKINS1 from git https://github.com/ShivaChouhan/devops_training.git

[Pipeline] Start of Pipeline

[Pipeline] node

Running on Jenkins in C:\ProgramData\Jenkins\.jenkins\workspace\Pipeline_Git1

[Pipeline] {

[Pipeline] stage

[Pipeline] { (Declarative: Checkout SCM)

[Pipeline] checkout

The recommended git tool is: NONE

using credential Devops-Training

Cloning the remote Git repository

Cloning repository https://github.com/ShivaChouhan/devops_training.git

 > C:\Users\Shivanand.chouhan\AppData\Local\Programs\Git\cmd\git.exe init C:\ProgramData\Jenkins\.jenkins\workspace\Pipeline_Git1 # timeout=10
Fetching upstream changes from https://github.com/ShivaChouhan/devops_training.git
 
 > C:\Users\Shivanand.chouhan\AppData\Local\Programs\Git\cmd\git.exe --version # timeout=10
 
 > git --version # 'git version 2.48.1.windows.1'
using GIT_ASKPASS to set credentials Devops-Training
 
 > C:\Users\Shivanand.chouhan\AppData\Local\Programs\Git\cmd\git.exe fetch --tags --force --progress -- https://github.com/ShivaChouhan/devops_training.git +refs/heads/*:refs/remotes/origin/* # timeout=10
 
 > C:\Users\Shivanand.chouhan\AppData\Local\Programs\Git\cmd\git.exe config remote.origin.url https://github.com/ShivaChouhan/devops_training.git # timeout=10
 
 > C:\Users\Shivanand.chouhan\AppData\Local\Programs\Git\cmd\git.exe config --add remote.origin.fetch +refs/heads/*:refs/remotes/origin/* # timeout=10
Avoid second fetch
 
 > C:\Users\Shivanand.chouhan\AppData\Local\Programs\Git\cmd\git.exe rev-parse "refs/remotes/origin/main^{commit}" # timeout=10
Checking out Revision e4b4393dcbf8c91c2c192847c75428f90530eafa (refs/remotes/origin/main)
 
 > C:\Users\Shivanand.chouhan\AppData\Local\Programs\Git\cmd\git.exe config core.sparsecheckout # timeout=10
 
 > C:\Users\Shivanand.chouhan\AppData\Local\Programs\Git\cmd\git.exe checkout -f e4b4393dcbf8c91c2c192847c75428f90530eafa # timeout=10

Commit message: "Create JENKINS1"

First time build. Skipping changelog.

[Pipeline] }

[Pipeline] // stage


[Pipeline] withEnv

[Pipeline] {

[Pipeline] stage

[Pipeline] { (Assignment 1)

[Pipeline] bat


C:\ProgramData\Jenkins\.jenkins\workspace\Pipeline_Git1>ECHO Hello World 

Hello World

[Pipeline] }

[Pipeline] // stage

[Pipeline] }

[Pipeline] // withEnv

[Pipeline] }

[Pipeline] // node

[Pipeline] End of Pipeline

Finished: SUCCESS

🎉 Boom! Jenkins successfully cloned the repo, read the Jenkinsfile, and executed it.

> And I did my first Assignment.
> 
