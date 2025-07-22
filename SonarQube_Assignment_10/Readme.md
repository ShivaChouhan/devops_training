# Commands to change java versions
```bash
sudo update-alternatives --config java
```

# How to Use SonarQube on Ubuntu 22.04 LTS

> Source: [https://docs.vultr.com/how-to-use-sonarqube-on-ubuntu-22-04-lts#5-install-sonarqube-on-ubuntu-2204](https://docs.vultr.com/how-to-use-sonarqube-on-ubuntu-22-04-lts#5-install-sonarqube-on-ubuntu-2204)

## Prerequisites

- Deploy a Ubuntu 22.04 server with at least 2GB of RAM and one vCPU core.
- Create a non-root user with sudo privileges.
- Update the server.
- A fully-qualified domain name (e.g., `sonarqube.example.com`) pointing to your server.

## 1. Configure Firewall

```bash
sudo ufw allow http
sudo ufw allow https
sudo ufw status
```

## 2. Install OpenJDK

```bash
sudo apt install openjdk-11-jdk
```

## 3. Install PostgreSQL

```bash
curl https://www.postgresql.org/media/keys/ACCC4CF8.asc | gpg --dearmor | sudo tee /etc/apt/trusted.gpg.d/apt.postgresql.org.gpg >/dev/null
sudo sh -c 'echo "deb http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list'
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl status postgresql
```

## 4. Configure PostgreSQL

```bash
sudo -u postgres psql
```

Inside the psql shell:

```sql
CREATE ROLE sonaruser WITH LOGIN ENCRYPTED PASSWORD 'your_password';
CREATE DATABASE sonarqube;
GRANT ALL PRIVILEGES ON DATABASE sonarqube to sonaruser;
\q
exit
```

## 5. Install SonarQube on Ubuntu 22.04

```bash
wget https://binaries.sonarsource.com/Distribution/sonarqube/sonarqube-9.6.1.59531.zip
unzip -q sonarqube-9.6.1.59531.zip
sudo mv sonarqube-9.6.1.59531 /opt/sonarqube
rm sonarqube-9.6.1.59531.zip
```

## 6. Create SonarQube User

```bash
sudo adduser --system --no-create-home --group --disabled-login sonarqube
sudo chown sonarqube:sonarqube /opt/sonarqube -R
```

## 7. Configure SonarQube Server

Edit SonarQube config:

```bash
sudo nano /opt/sonarqube/conf/sonar.properties
```

Uncomment and set:

```ini
sonar.jdbc.username=sonaruser
sonar.jdbc.password=your_password
sonar.jdbc.url=jdbc:postgresql://localhost:5432/sonarqube
sonar.web.javaAdditionalOpts=-server
sonar.web.host=127.0.0.1
```

Edit sysctl config:

```bash
sudo nano /etc/sysctl.conf
```

Append:

```conf
vm.max_map_count=524288
fs.file-max=131072
```

Edit limits config:

```bash
sudo nano /etc/security/limits.d/99-sonarqube.conf
```

Add:

```conf
sonarqube   -   nofile   131072
sonarqube   -   nproc    8192
```

Reboot:

```bash
sudo reboot
```

## 8. Setup Sonar Service

```bash
sudo nano /etc/systemd/system/sonarqube.service
```

Paste:

```ini
[Unit]
Description=SonarQube service
After=syslog.target network.target

[Service]
Type=forking

ExecStart=/opt/sonarqube/bin/linux-x86-64/sonar.sh start
ExecStop=/opt/sonarqube/bin/linux-x86-64/sonar.sh stop

User=sonarqube
Group=sonarqube
PermissionsStartOnly=true
Restart=always

StandardOutput=syslog
LimitNOFILE=131072
LimitNPROC=8192
TimeoutStartSec=5
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
```

Then run:

```bash
sudo systemctl start sonarqube
sudo systemctl status sonarqube
sudo systemctl enable sonarqube
```
# To check sonarqube logs

```bash
sudo cat /opt/sonarqube/logs/sonar.log
```

## Error I have faced on the setup of sonarqube

```bash
2025.07.06 13:46:08 ERROR web[][o.s.s.p.PlatformImpl] Web server startup failed
org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'jdk.internal.loader.ClassLoaders$AppClassLoader@55054057-org.sonar.server.platform.db.migration.history.MigrationHistoryTableImpl': Initialization of bean failed; nested exception is java.lang.IllegalStateException: Failed to create table schema_migrations
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:628)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
	at org.sonar.core.platform.SpringComponentContainer.startComponents(SpringComponentContainer.java:187)
	at org.sonar.server.platform.platformlevel.PlatformLevel.start(PlatformLevel.java:80)
	at org.sonar.server.platform.platformlevel.PlatformLevel2.start(PlatformLevel2.java:105)
	at org.sonar.server.platform.PlatformImpl.start(PlatformImpl.java:196)
	at org.sonar.server.platform.PlatformImpl.startLevel2Container(PlatformImpl.java:169)
	at org.sonar.server.platform.PlatformImpl.init(PlatformImpl.java:77)
	at org.sonar.server.platform.web.PlatformServletContextListener.contextInitialized(PlatformServletContextListener.java:43)
	at org.apache.catalina.core.StandardContext.listenerStart(StandardContext.java:4768)
	at org.apache.catalina.core.StandardContext.startInternal(StandardContext.java:5230)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
	at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1396)
	at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1386)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at org.apache.tomcat.util.threads.InlineExecutorService.execute(InlineExecutorService.java:75)
	at java.base/java.util.concurrent.AbstractExecutorService.submit(AbstractExecutorService.java:140)
	at org.apache.catalina.core.ContainerBase.startInternal(ContainerBase.java:919)
	at org.apache.catalina.core.StandardHost.startInternal(StandardHost.java:835)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
	at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1396)
	at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1386)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at org.apache.tomcat.util.threads.InlineExecutorService.execute(InlineExecutorService.java:75)
	at java.base/java.util.concurrent.AbstractExecutorService.submit(AbstractExecutorService.java:140)
	at org.apache.catalina.core.ContainerBase.startInternal(ContainerBase.java:919)
	at org.apache.catalina.core.StandardEngine.startInternal(StandardEngine.java:263)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
	at org.apache.catalina.core.StandardService.startInternal(StandardService.java:432)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
	at org.apache.catalina.core.StandardServer.startInternal(StandardServer.java:927)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
	at org.apache.catalina.startup.Tomcat.start(Tomcat.java:486)
	at org.sonar.server.app.EmbeddedTomcat.start(EmbeddedTomcat.java:72)
	at org.sonar.server.app.WebServer.start(WebServer.java:55)
	at org.sonar.process.ProcessEntryPoint.launch(ProcessEntryPoint.java:97)
	at org.sonar.process.ProcessEntryPoint.launch(ProcessEntryPoint.java:81)
	at org.sonar.server.app.WebServer.main(WebServer.java:104)
Caused by: java.lang.IllegalStateException: Failed to create table schema_migrations
	at org.sonar.server.platform.db.migration.history.MigrationHistoryTableImpl.start(MigrationHistoryTableImpl.java:48)
	at org.sonar.core.platform.StartableBeanPostProcessor.postProcessBeforeInitialization(StartableBeanPostProcessor.java:33)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization(AbstractAutowireCapableBeanFactory.java:440)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1796)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
	... 44 common frames omitted
Caused by: org.postgresql.util.PSQLException: ERROR: permission denied for schema public
  Position: 14
	at org.postgresql.core.v3.QueryExecutorImpl.receiveErrorResponse(QueryExecutorImpl.java:2676)
	at org.postgresql.core.v3.QueryExecutorImpl.processResults(QueryExecutorImpl.java:2366)
	at org.postgresql.core.v3.QueryExecutorImpl.execute(QueryExecutorImpl.java:356)
	at org.postgresql.jdbc.PgStatement.executeInternal(PgStatement.java:490)
	at org.postgresql.jdbc.PgStatement.execute(PgStatement.java:408)
	at org.postgresql.jdbc.PgStatement.executeWithFlags(PgStatement.java:329)
	at org.postgresql.jdbc.PgStatement.executeCachedSql(PgStatement.java:315)
	at org.postgresql.jdbc.PgStatement.executeWithFlags(PgStatement.java:291)
	at org.postgresql.jdbc.PgStatement.execute(PgStatement.java:286)
	at org.apache.commons.dbcp2.DelegatingStatement.execute(DelegatingStatement.java:193)
	at org.apache.commons.dbcp2.DelegatingStatement.execute(DelegatingStatement.java:193)
	at org.sonar.server.platform.db.migration.history.MigrationHistoryTableImpl.execute(MigrationHistoryTableImpl.java:71)
	at org.sonar.server.platform.db.migration.history.MigrationHistoryTableImpl.createTable(MigrationHistoryTableImpl.java:59)
	at org.sonar.server.platform.db.migration.history.MigrationHistoryTableImpl.start(MigrationHistoryTableImpl.java:45)
	... 48 common frames omitted
```

# Fix: Grant PostgreSQL Privileges to SonarQube User

If SonarQube fails to start due to database permission issues, follow these steps to grant the required privileges to your PostgreSQL user.

---

## 🧾 Assumptions

- PostgreSQL is running
- Database name is `sonarqube`
- SonarQube connects using PostgreSQL user `sonaruser`

---

## 🛠 Steps to Fix

### 🔹 1. Access PostgreSQL as the `postgres` superuser

```bash
sudo -u postgres psql
```

---

### 🔹 2. List all databases (to confirm the `sonarqube` DB exists)

```sql
\l
```

Look for the database named `sonarqube`.

---

### 🔹 3. Connect to the `sonarqube` database

```sql
\c sonarqube
```

---

### 🔹 4. Grant ownership and privileges to the `sonaruser`

```sql
ALTER SCHEMA public OWNER TO sonaruser;
GRANT ALL PRIVILEGES ON SCHEMA public TO sonaruser;
GRANT ALL PRIVILEGES ON DATABASE sonarqube TO sonaruser;
```

---

### 🔹 5. Exit PostgreSQL

```sql
\q
```

---

### 🔁 6. Restart SonarQube

#### If using the shell script:

```bash
cd /opt/sonarqube
./bin/linux-x86-64/sonar.sh restart
```

#### If using `systemd`:

```bash
sudo systemctl restart sonarqube
```

---

## ✅ Done!

## 2nd Error

```bash

2025.07.06 13:50:09 INFO  app[][o.s.a.SchedulerImpl] Process[ElasticSearch] is stopped
2025.07.06 13:50:09 INFO  app[][o.s.a.SchedulerImpl] Waiting for Elasticsearch to be up and running
2025.07.06 13:50:09 INFO  app[][o.s.a.SchedulerImpl] SonarQube is stopped
2025.07.06 13:50:11 INFO  app[][o.s.a.AppFileSystem] Cleaning or creating temp directory /opt/sonarqube/temp
2025.07.06 13:50:11 INFO  app[][o.s.a.es.EsSettings] Elasticsearch listening on [HTTP: 127.0.0.1:9001, TCP: 127.0.0.1:37209]
2025.07.06 13:50:11 ERROR app[][o.s.a.p.ManagedProcessHandler] Failed to launch process [ElasticSearch]
java.lang.IllegalStateException: Could not delete Elasticsearch temporary conf directory
	at org.sonar.application.ProcessLauncherImpl.pruneElasticsearchConfDirectory(ProcessLauncherImpl.java:168)
	at org.sonar.application.ProcessLauncherImpl.writeConfFiles(ProcessLauncherImpl.java:155)
	at org.sonar.application.ProcessLauncherImpl.launch(ProcessLauncherImpl.java:92)
	at org.sonar.application.SchedulerImpl.lambda$tryToStartProcess$2(SchedulerImpl.java:197)
	at org.sonar.application.process.ManagedProcessHandler.start(ManagedProcessHandler.java:76)
	at org.sonar.application.SchedulerImpl.tryToStartProcess(SchedulerImpl.java:195)
	at org.sonar.application.SchedulerImpl.tryToStartEs(SchedulerImpl.java:147)
	at org.sonar.application.SchedulerImpl.tryToStartAll(SchedulerImpl.java:139)
	at org.sonar.application.SchedulerImpl.schedule(SchedulerImpl.java:113)
	at org.sonar.application.App.start(App.java:59)
	at org.sonar.application.App.main(App.java:81)
Caused by: java.nio.file.AccessDeniedException: /opt/sonarqube/temp/conf/es
	at java.base/sun.nio.fs.UnixException.translateToIOException(UnixException.java:90)
	at java.base/sun.nio.fs.UnixException.rethrowAsIOException(UnixException.java:111)
	at java.base/sun.nio.fs.UnixException.rethrowAsIOException(UnixException.java:116)
	at java.base/sun.nio.fs.UnixFileSystemProvider.implDelete(UnixFileSystemProvider.java:249)
	at java.base/sun.nio.fs.AbstractFileSystemProvider.deleteIfExists(AbstractFileSystemProvider.java:110)
	at java.base/java.nio.file.Files.deleteIfExists(Files.java:1181)
	at org.sonar.application.ProcessLauncherImpl.pruneElasticsearchConfDirectory(ProcessLauncherImpl.java:166)
	... 10 common frames omitted

```
	

# Fix: SonarQube Fails Due to Elasticsearch Temp Directory Permission Issue

## Problem

SonarQube is failing to start due to permission issues on the Elasticsearch temporary configuration directory:

```
Caused by: java.nio.file.AccessDeniedException: /opt/sonarqube/temp/conf/es
```

## ✅ Solution

### 1. Fix Permissions

Ensure the SonarQube service user (typically `sonarqube`) has write access to the temp directory:

```bash
sudo chown -R sonarqube:sonarqube /opt/sonarqube/temp
sudo chmod -R 775 /opt/sonarqube/temp
```

### 2. Verify the SonarQube User

Ensure the systemd service runs as the correct user. Check `/etc/systemd/system/sonarqube.service` contains:

```ini
User=sonarqube
Group=sonarqube
```

### 3. Clean Up Temp Directory (Optional)

If problems persist, delete the temp configuration directory. SonarQube will recreate it on startup:

```bash
sudo rm -rf /opt/sonarqube/temp/conf/es
```

### 4. Restart SonarQube

Reset the failed systemd state and start the service again:

```bash
sudo systemctl reset-failed sonarqube
sudo systemctl start sonarqube
```

### 5. Check Logs Again

Monitor the logs to confirm that SonarQube is now starting correctly:

```bash
sudo tail -f /opt/sonarqube/logs/sonar.log
```
## ✅ Done!

SonarQube should now start successfully.	
	

# SonarQube Integration with Jenkins - Detailed Notes


## Overview
- The video explains how to integrate SonarQube with Jenkins to run code quality analysis automatically whenever a Jenkins job is executed.
- It builds on previous lectures covering SonarQube setup and overview.

## Prerequisites
- SonarQube server must be set up and running.
- Jenkins server must be set up and running.
- Both servers in the example are launched on AWS.

## Integration Steps

1. **Generate SonarQube Token**
   - Generate an authentication token on the SonarQube server.
   - This token will be used by Jenkins to authenticate with SonarQube.

2. **Install SonarQube Plugin on Jenkins**
   - Go to Jenkins → Manage Jenkins → Manage Plugins → Available.
   - Search for "SonarQube Scanner" plugin and install it without restart.

3. **Configure SonarQube Credentials in Jenkins**
   - Go to Jenkins → Manage Jenkins → Manage Credentials → Global credentials.
   - Add a new credential of type "Secret text".
   - Paste the SonarQube token as the secret text.
   - Name the credential (e.g., "sonarCube token").

4. **Add SonarQube Server in Jenkins**
   - Go to Jenkins → Manage Jenkins → Configure System.
   - Scroll to SonarQube servers section.
   - Enable environment variable checkbox.
   - Add SonarQube server details:
     - Name (e.g., "sonarCube 8.9.2").
     - URL (use private IP in production to avoid IP changes).
     - Authentication token (select the credential created earlier).

5. **Install SonarQube Scanner**
   - Either install manually on Jenkins server via SSH or
   - Use Jenkins → Manage Jenkins → Global Tool Configuration.
   - Add SonarQube Scanner with automatic installation (e.g., version 4.6.2).

6. **Create Jenkins Pipeline Job**
   - Create a new pipeline job in Jenkins.
   - Use a pipeline script that:
     - Checks out the code.
     - Builds the code using Maven (`mvn clean package`).
     - Runs SonarQube analysis using Maven sonar goal (`mvn sonar:sonar`).
   - Adjust Maven path and SonarQube scanner name as per your environment.

## Running the Job and Viewing Reports
- When the pipeline runs:
  - Code is cloned and built.
  - SonarQube analysis runs and sends results to SonarQube server.
- The analysis report shows:
  - Number of bugs, vulnerabilities, code smells, and security hotspots.
  - Detailed information on each issue with recommendations.
- Developers can review these reports to improve code quality.

## Quality Profiles and Gates
- SonarQube uses **Quality Profiles** to apply rules for code analysis.
- For Java, default profile "Sonar way" is used.
- You can create custom profiles and activate more rules.
- Quality Gates define thresholds for bugs, vulnerabilities, etc.
- If thresholds are not met, the build can be marked as failed.



# How to Monitor a Node.js Application with SonarQube

You can use SonarQube to analyze and monitor the code quality of your Node.js application. Here’s a step-by-step guide:

---

## 1. Set Up SonarQube Server

- Install SonarQube on your local machine or use a hosted instance.
- Start SonarQube and access it at [http://localhost:9000](http://localhost:9000) (default).

---

## 2. Install SonarScanner

For Node.js projects, use the [SonarScanner CLI](https://docs.sonarsource.com/sonarqube/latest/analyzing-source-code/scanners/sonarscanner/):

```sh
npm install -g sonarqube-scanner
```
or download and extract the [SonarScanner CLI](https://docs.sonarsource.com/sonarqube/latest/analyzing-source-code/scanners/sonarscanner-cli/).

---

## 3. Configure SonarQube Project

- In the SonarQube UI, create a new project and generate a project key and token.

---

## 4. Add a `sonar-project.properties` File

Create a file named `sonar-project.properties` in your project root:

```
sonar.projectKey=node-file-demo(same as token name)
sonar.projectName=Node File Demo
sonar.host.url=http://localhost:9000
sonar.login=<your_sonarqube_token>
sonar.sources=.
sonar.exclusions=node_modules/**
```
Replace `<your_sonarqube_token>` with your actual token.

---

## 5. Run SonarScanner

From your project root, run:

```sh
sonar-scanner
```
or, if you installed with npm:

```sh
npx sonarqube-scanner
```

---

## 6. View Results

- Go to your SonarQube dashboard (http://localhost:9000).
- Open your project to see code quality, bugs, vulnerabilities, and code smells.

---

## Summary

- Install and start SonarQube.
- Install SonarScanner.
- Add a `sonar-project.properties` file.
- Run `sonar-scanner` in your project directory.
- View your analysis in the SonarQube UI.


## Custom rule


## Enforcing No `System.out.println` with SonarQube (Rule: java:S106)

### 1. Find the Built-in Rule

- Go to your **SonarQube UI**.
- Navigate to **Quality Profiles > Java**.
- Search for:  
  **“Replace this use of System.out or System.err by a logger.”**  
  - **Rule key:** `java:S106`
  - **Rule name:** Standard outputs should not be used directly to log anything

---

### 2. Activate the Rule in Your Quality Profile

- If it’s not already active, **activate** this rule in your quality profile.
- Optionally, set its severity to **“Blocker”** or **“Critical”**.

---

### 3. Assign the Quality Profile to Your Project

- Go to your project in SonarQube.
- Navigate to **Project Settings > Quality Profiles**.
- Make sure your project uses the profile where you activated the rule.

---

### 4. Re-Analyze Your Project

- Run `sonar-scanner` in your Java project directory.
- SonarQube will now flag any use of `System.out.println` or `System.err.println` as an issue.

---

### **Result Example**

In your code:
```java
System.out.println("Hello this is my java application.");
```
SonarQube will report this line as a violation of rule **java:S106**.

---

## How to Assign a Custom Quality Profile to a Project

1. Go to your project in the SonarQube UI.
2. In the left sidebar, click on **Project Settings > Quality Profiles**.
3. You will see a list of languages (e.g., Java, JavaScript, etc.).
4. For **Java**, click the dropdown next to the current profile.
5. **Select your custom profile** (`custom_rule_check`) from the list.
6. The profile will now be used for this project’s Java


# Python SonarQube Jenkins Project

This project demonstrates how to set up a Python application with automated testing, coverage reporting, and SonarQube analysis. It is designed to be integrated with Jenkins for CI/CD pipelines.

---

## 📦 Project Structure

- `main.py` — Python source code
- `test_main.py` — Unit tests
- `Jenkinsfile` — Jenkins pipeline configuration
- `sonar-project.properties` — SonarQube project configuration
- `.coverage`, `coverage.xml` — Coverage reports (generated)
- `venv/` — Python virtual environment (generated)
- `.scannerwork/` — SonarQube scanner output (generated)

---

## 🚀 Setup & Usage

### 1. Install Python Virtual Environment

```bash
sudo apt install python3.12-venv
```

### 2. Create and Activate Virtual Environment

```bash
python3 -m venv venv
source venv/bin/activate
```

### 3. Install Dependencies

```bash
pip install pytest coverage
```

### 4. Run Tests and Generate Coverage Report

```bash
coverage run -m pytest
coverage report
coverage xml
```

### 5. Run SonarQube Analysis

```bash
sudo sonar-scanner
```

---

## 📝 SonarQube Configuration

Ensure your `sonar-project.properties` includes:

```properties
sonar.projectKey=my-python-jenkins-project
sonar.projectName=My Python Jenkins Project
sonar.sources=.
sonar.language=py
sonar.host.url=http://localhost:9000
sonar.login=<your_sonarqube_token>
sonar.python.coverage.reportPaths=coverage.xml
sonar.exclusions=venv/**
sonar.python.version=3.12
```

Replace `<your_sonarqube_token>` with your actual token.

---

## 🛠 Jenkins Integration

See the `Jenkinsfile` for a sample pipeline that:

- Sets up a Python virtual environment
- Installs dependencies
- Runs tests and generates coverage
- Runs SonarQube analysis

---

## 📊 View Results

- Coverage results: `coverage report` (terminal), `coverage.xml` (file)
- SonarQube dashboard: [http://localhost:9000](http://localhost:9000)

---

## 🧹 Clean Up

To remove generated files and folders:

```bash
rm -rf venv .coverage coverage.xml .scannerwork __pycache__ .pytest_cache
```

---

##