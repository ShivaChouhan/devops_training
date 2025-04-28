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
        stage('Hello World') {
            steps {
                echo 'Hello, World from Jenkins!'
            }
        }
    }
}
```
🔹 GitHub Repository
The Jenkinsfile is located in this GitHub repo:
🔗 https://github.com/Raj123-dev/Devops_Journey_2k25

🔹 CI/CD Pipeline Setup in Jenkins
To connect Jenkins with the GitHub repo:

1.Opened Jenkins dashboard → Clicked "New Item"

2.Selected Pipeline → Named it hello-world-pipeline

3.Chose "Pipeline script from SCM"

4.Selected:

5.SCM: Git

6.Repository URL: https://github.com/Raj123-dev/Devops_Journey_2k25.git

(If private repo: added GitHub credentials)
my repo is public so credentials do not required here

7.Set the branch to main (or whichever branch contains the Jenkinsfile)

8.Clicked Save and then "Build Now"

✅ Pipeline Output
The build was triggered, and in the Jenkins console log, I saw:

Started by user Raj Kashyap

Obtained Jenkinsfile from git https://github.com/Raj123-dev/Devops_Journey_2k25.git

[Pipeline] Start of Pipeline

[Pipeline] node

Running on Jenkins in /var/lib/jenkins/workspace/New_pipeline_helloworld

[Pipeline] {

[Pipeline] stage

[Pipeline] { (Declarative: Checkout SCM)

[Pipeline] checkout

Selected Git installation does not exist. Using Default

The recommended git tool is: NONE

No credentials specified

 > git rev-parse --resolve-git-dir /var/lib/jenkins/workspace/New_pipeline_helloworld/.git # timeout=10
Fetching changes from the remote Git repository
 
 > git config remote.origin.url https://github.com/Raj123-dev/Devops_Journey_2k25.git # timeout=10
Fetching upstream changes from https://github.com/Raj123-dev/Devops_Journey_2k25.git
 
 > git --version # timeout=10
 
 > git --version # 'git version 2.43.0'
 
 > git fetch --tags --force --progress -- https://github.com/Raj123-dev/Devops_Journey_2k25.git +refs/heads/*:refs/remotes/origin/* # timeout=10
 
 > git rev-parse refs/remotes/origin/main^{commit} # timeout=10

Checking out Revision 0d37d105fccd8d6b8fd377c93834656e835bd9c6 (refs/remotes/origin/main)

 > git config core.sparsecheckout # timeout=10
 
 > git checkout -f 0d37d105fccd8d6b8fd377c93834656e835bd9c6 # timeout=10

 > git rev-list --no-walk 6b8b6a2e695a455d22ea1f0f438af4a05fceaca2 # timeout=10

[Pipeline] }

[Pipeline] // stage

[Pipeline] withEnv

[Pipeline] {

[Pipeline] stage

[Pipeline] { (Hello World)

[Pipeline] echo

Hello, World from Jenkins!

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
