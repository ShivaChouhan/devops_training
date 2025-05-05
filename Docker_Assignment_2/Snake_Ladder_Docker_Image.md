# Snake & Ladder Game Dockerization - Complete Procedure

This document outlines the step-by-step procedure to Dockerize a Java Swing-based Snake & Ladder game, starting from uploading the project to GitHub, setting up the environment on a VirtualBox Ubuntu instance, and finally creating and running the Docker image.

---

## 📁 Project Setup

1. **Initial Java Project**:
   - You had a Java Swing GUI-based Snake & Ladder game with a main file: `SnakeLadder.java`.
   - The application also included GIF or image assets used in the GUI.

2. **GitHub Upload**:
   - The project was pushed to GitHub under the repository `devops_training`.
   - Inside the repository, the Java application was placed under `Docker_Assignment_2/`.
   - You included a custom Dockerfile named `Docker_File_SnakeLadder` in this directory.

---

## 🐧 VirtualBox Ubuntu Setup

1. **Ubuntu Virtual Machine**:
   - You created a virtual machine using VirtualBox and installed Ubuntu.
   - Required packages like `git` and `docker` were installed.

2. **Clone Git Repository**:
   ```bash
   git clone https://github.com/ShivaChouhan/devops_training.git
   cd devops_training/Docker_Assignment_2
   ```

3. **Check Dockerfile**:
   - The custom Dockerfile `Docker_File_SnakeLadder` was used in the build command explicitly and not renamed.

---

## 🐳 Dockerization Process

### 🔧 Docker_File_SnakeLadder Used
```Dockerfile
# Use a slim JDK image with apt support
FROM openjdk:17-jdk-slim

# Install GUI-related libraries
RUN apt-get update && apt-get install -y \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libgtk2.0-0 \
    && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy local files into container
COPY . /app

# Compile the Java code
RUN javac SnakeLadder.java

# Configure the display environment variable
ENV DISPLAY=:0

# Run the game
CMD ["java", "SnakeLadder"]
```

### 🛠️ Build the Docker Image
```bash
cd devops_training/Docker_Assignment_2
sudo docker build -f Docker_File_SnakeLadder -t snake-ladder-game .
```

> **Note**: You encountered an error earlier because you were using `openjdk:17` (which lacked `apt-get`). Switching to `openjdk:17-jdk-slim` fixed this issue.

---

## ▶️ Running the Docker Container

To run a GUI-based Java app from the container:
```bash
sudo docker run -e DISPLAY=$DISPLAY -v /tmp/.X11-unix:/tmp/.X11-unix --rm snake-ladder-game
```

If you're using VirtualBox, ensure:
- X11 forwarding is enabled.
- You have `xhost +` permissions (or configure access control securely).

---

## 🧪 Modifying & Rebuilding
If you make changes to the `SnakeLadder.java` file, you must rebuild the Docker image:
```bash
sudo docker build -t snake-ladder-game .
```

The old image will be replaced unless you tag it differently.

---

## 📦 Sharing Docker Image
To share the image with another machine:
1. **Save the image**:
```bash
sudo docker save -o snake_ladder_game.tar snake-ladder-game
```
2. **Transfer the file** (via USB, scp, etc.):
3. **Load on another machine**:
```bash
sudo docker load -i snake_ladder_game.tar
```
> The receiver machine must also have Docker installed.

---

## ❓ Common Issues Faced
- `apt-get: command not found`: Caused by using a base image without APT support.
- GIFs not displaying: Usually due to missing GUI libraries or improper X11 forwarding.
- Docker daemon permission issues: Resolved by using `sudo` or configuring Docker group.

---

## ✅ Summary
You successfully:
- Uploaded your Java project to GitHub.
- Cloned it into a VirtualBox Ubuntu environment.
- Wrote and fixed a Dockerfile.
- Built and ran a Docker image with GUI support.
- Shared the image for use on other systems.

This demonstrates a full DevOps lifecycle for containerizing and sharing a Java GUI application using Docker.

---

> For any future builds, just modify the Java files and rebuild the image with Docker as shown above.

