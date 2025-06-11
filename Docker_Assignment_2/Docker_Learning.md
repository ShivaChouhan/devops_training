🐳 Docker Kya Hai?
Docker ek open platform hai jo developers ko applications ko build, ship (bhejne), aur run karne mein madad karta hai.

Ye aapko aapki application ko infrastructure se alag karne ki suvidha deta hai — jisse aap software ko fast aur easily deliver kar sakte ho.

🧱 Docker Platform – Simple Words Mein
Docker applications ko containers ke form mein run karta hai. Ek container ek lightweight, isolated environment hota hai jisme aapki application safely run karti hai.

Containers independent hote hain — host machine pe jo installed hai uspar depend nahi karte.

Aap containers share bhi kar sakte ho — aur har baar same environment milega.

⚙️ Docker Platform Se Aap Kya Kar Sakte Ho?
🚀 Fast & Consistent Application Delivery:
Developers apni code local machine pe likhte hain.

Docker containers ke through testing aur delivery easy ho jaati hai.

Agar koi bug aata hai, woh fix karke same container ko dubara test & deploy kar sakte hain.

📦 Responsive Deployment & Scaling:
Docker containers portable hote hain: local laptop, cloud, ya hybrid environment — sab jageh run kar sakte hain.

Real-time mein scale up/down karna bahut aasaan ho jaata hai.

💸 Run More on Same Hardware:
Docker lightweight hai, toh VM (virtual machines) ke mukable zyada containers ek hi machine pe chala sakte ho.

Resources ka better use hota hai.

🧠 Docker Architecture

![image](https://github.com/user-attachments/assets/7cfa9b18-c65b-4bad-b293-25b969635f26)


1️⃣ Docker Engine (Client-Server Architecture)
✅ Components:
Docker Daemon (dockerd):

Background mein chalta hai.

Containers, images, volumes ko manage karta hai.

Docker Client (docker):

Command line interface hai.

Commands like docker run, docker build, docker ps, etc. yahan se diye jaate hain.

REST API:

Client aur Daemon ke beech communication handle karta hai.

2️⃣ Docker Desktop:
Windows, Mac, Linux ke liye GUI-based app.

Isme docker engine, client, Docker Compose, Kubernetes sab included hota hai.

3️⃣ Docker Registries
Docker images ko store karne ka platform.

Public registry = Docker Hub

Private registries bhi bana sakte ho.

🧭 Commands:
bash
Copy
Edit
docker pull ubuntu         # image download
docker push myrepo/image   # image upload
4️⃣ Docker Objects
🖼️ Images:
Ek read-only template hoti hai jisme app aur dependencies hoti hain.

Example: Ubuntu base + Apache + aapki app.

📦 Containers:
Images ka live, runnable version.

Aap run, stop, delete, ya modify kar sakte ho.

Har container isolated hota hai.

Example command:

bash
Copy
Edit
docker run -it ubuntu /bin/bash
⚙️ Under the Hood (Technology)
Docker Linux namespaces aur cgroups ka use karta hai:

Har container ka apna isolated network, storage, aur process space hota hai.

Isse secure aur independent environment milta hai.

✅ Summary
Feature	Explanation
Docker	Application containers ko run karne ka tool
Image	App ka blueprint ya base file
Container	Image ka live instance
Registry	Jahan images store hoti hain (e.g., Docker Hub)
Docker Engine	Containers ko manage karta hai
Docker Compose	Multiple container apps ke liye

Building and running Docker containers

🐳 Building and Running Docker Containers on Ubuntu

🔰 1. Basic Setup
Before using Docker, make sure it is installed and running:

bash

Copy

Edit

sudo apt update

sudo apt install docker.io -y

sudo systemctl start docker

sudo systemctl enable docker

sudo docker --version  # Verify installation

🛠️ 2. Building a Docker Image

Navigate to your project directory:


bash

Copy

Edit

cd ~/path/to/project

Dockerfile Example:


dockerfile

Copy

Edit

# Use a base image

FROM openjdk:17-jdk-slim



# Set the working directory

WORKDIR /app



# Copy the jar file to the container

COPY SnakeLadder.jar /app


# Run the application

CMD ["java", "-jar", "SnakeLadder.jar"]

Build the Docker image:


bash

Copy

Edit

sudo docker build -t snake-ladder-game -f Docker_File_SnakeLadder .

-t snake-ladder-game: Tags the image as snake-ladder-game.


-f Docker_File_SnakeLadder: Specifies the custom Dockerfile.



.: Uses the current directory as build context.


🚀 3. Running a Docker Container

Basic Run:


bash

Copy

Edit

sudo docker run -it --rm snake-ladder-game

-it: Runs the container interactively.



--rm: Removes the container after it stops.


Running in Detached Mode:



bash

Copy

Edit

sudo docker run -d --name snake-ladder snake-ladder-game

-d: Run in the background (detached).



--name snake-ladder: Assigns a custom name to the container.



Accessing the Container:


bash

Copy

Edit

sudo docker exec -it snake-ladder bash

Opens a shell inside the running container.


📝 4. Viewing and Managing Containers

List running containers:


bash

Copy

Edit

sudo docker ps

List all containers (including stopped):


bash

Copy

Edit

sudo docker ps -a

Check container logs:


bash

Copy

Edit

sudo docker logs snake-ladder

Stop a running container:



bash

Copy

Edit

sudo docker stop snake-ladder

Restart a container:


bash

Copy

Edit

sudo docker restart snake-ladder

Remove a container:


bash

Copy

Edit

sudo docker rm snake-ladder

🗑️ 5. Cleaning Up Docker Resources

Remove the image:


bash

Copy

Edit

sudo docker rmi snake-ladder-game

Remove all stopped containers:


bash

Copy

Edit

sudo docker container prune

Remove unused images:


bash

Copy

Edit

sudo docker image prune

Complete cleanup (containers, images, networks, cache):


bash

Copy

Edit

sudo docker system prune


🐳 Docker Commands on Ubuntu (Basic → Advanced)

🔰 Basic Commands

Command	Description

sudo docker images	List all Docker images available locally.

sudo docker ps	List running Docker containers.

sudo docker ps -a	List all containers, including stopped ones.

sudo docker container prune	Remove all stopped containers (asks for confirmation).

sudo docker image prune	Remove dangling (unused) images to free up space.

sudo docker system prune	Clean up unused containers, networks, images, and cache (asks for confirmation).


⚙️ Intermediate Commands

Command	Description

cd ~/path/to/project	Navigate to your project folder (like Docker_Assignment_2).

sudo docker build -t snake-ladder-game -f Docker_File_SnakeLadder .	Build an image called snake-ladder-game using your custom Dockerfile.

sudo docker run -it --rm snake-ladder-game	Run the container interactively and delete it once it exits.

sudo docker run -it snake-ladder-game	Run the container interactively and keep it after it exits (useful for debugging).


🚀 Advanced Commands

Command	Description

sudo docker rmi snake-ladder-game	Remove the snake-ladder-game image. Useful for rebuilding cleanly.

sudo docker exec -it <container_id_or_name> bash	Access the shell of a running container (if needed for debugging).

sudo docker logs <container_id_or_name>	View logs from a container.


# Use Docker Volumes and Networks in a Sample Application

Docker Volumes (Persistent Storage)

Kya Hai?


Containers temporary hote hain, agar aap data permanently save karna chahte ho, toh volumes use karein.

Kaise Use Karein?


bash

docker run -d -v /data --name my-container my-image

Ya host machine se mount karein:


bash

docker run -d -v /host/folder:/container/folder my-image

Docker Networks (Multi-Container Communication)

Kya Hai?

Agar aapke paas multiple containers hain (e.g., web app + database), toh unhe network se connect karna padega.

Kaise Banayein?


bash

docker network create my-network

Phir containers ko us network se attach karein:

bash

docker run -d --network my-network --name my-db mysql

docker run -d --network my-network --name my-app my-web-app

# Docker network example

For these 2 images
1. shivachouhan/snake-ladder-game   mongo_app_02   fed3bba54732   2 weeks ago    1.15GB
2. mongo                            4.4.18         f0bbeaaea8c3   2 years ago    438MB


1.Create a Docker Network

docker network create my-app-net

2.docker run -d --name mongo --network my-app-net -p 27017:27017 mongo:4.4.18

docker run -d: Runs the container in detached (background) mode.

--name mongo: Names the container mongo.

--network my-app-net: Connects the container to the custom Docker network my-app-net.

-p 27017:27017: Maps port 27017 on your host to port 27017 in the container (MongoDB’s default port).

mongo:4.4.18: Uses the official MongoDB image, version 4.4.18.

Result:

Starts a MongoDB server accessible to other containers on my-app-net as mongo, and to your host on port 27017.
 
 3. Run Node.js App Container

docker run -d --name node-app --network my-app-net -p 3000:3000   -e MONGO_HOST=mongo   -e MONGO_PORT=27017   shivachouhan/snake-ladder-game:mongo_app_02


docker run -d: Runs the container in detached mode.

--name node-app: Names the container node-app.

--network my-app-net: Connects the container to the same custom Docker network.

-p 3000:3000: Maps port 3000 on your host to port 3000 in the container (your Node.js app’s port).

-e MONGO_HOST=mongo: Sets the environment variable MONGO_HOST to mongo (the name of the MongoDB container).

-e MONGO_PORT=27017: Sets the environment variable MONGO_PORT to 27017.

shivachouhan/snake-ladder-game:mongo_app_02: Uses your Node.js app image.

Result:

Starts your Node.js app, which connects to MongoDB at mongo:27017 (using Docker’s internal DNS), and exposes the app on http://localhost:3000.


