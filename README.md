# 🚀 Spring Boot Microservices with Load Balancing

A complete **template microservices architecture** built using **Spring Boot**,  
**Eureka Server**, **Spring Cloud Gateway**, and **Docker**, demonstrating:

- Service discovery  
- API routing  
- Round-robin load balancing between microservice instances  

Use this project as a starting point to build full-fledged distributed backend systems.

---

## 🏷️ Tech Stack

- **Java 17**
- **Spring Boot**
- **Eureka Server (Service Discovery)**
- **Spring Cloud Gateway**
- **Spring Cloud LoadBalancer**
- **Docker & Docker Compose**

---

## 📁 Project Structure
📦 microservices-loadbalancing
 ┣ 📂 eureka-server
 ┣ 📂 api-gateway
 ┣ 📂 service-1
 ┣ 📂 service-2
 ┣ 📜 docker-compose.yml
 ┗ 📜 README.md


Each service contains:

- `src/main/java` → main application code  
- `src/main/resources/application.yml` → configuration  
- `Dockerfile` → build instructions  

---

## 🏗️ Architecture

```text
                 ┌──────────────────────────┐
                 │        Client/App        │
                 └──────────────┬───────────┘
                                │
                                ▼
                   ┌────────────────────────┐
                   │   API Gateway (8080)   │
                   │    - Routing           │
                   │    - Load Balancing    │
                   └──────────────┬────────┘
                                  │  lb://
     ┌────────────────────────────┴────────────────────────────┐
     ▼                                                         ▼
┌──────────────────────┐                          ┌──────────────────────┐
│    Service 1          │                          │     Service 2         │
│     (9001)            │                          │      (9002)           │
│   /test endpoint      │                          │    /test endpoint     │
└──────────────────────┘                          └──────────────────────┘
     ▲                                                         ▲
     └────────────────────────────┬────────────────────────────┘
                                  │   register with Eureka
                                  ▼
                     ┌─────────────────────────────┐
                     │      Eureka Server (8761)    │
                     │      Service Registry        │
                     └─────────────────────────────


## ⚙️ Features

✔ Fully containerized microservices  
✔ Automatic **service registration** via Eureka  
✔ **Zero hard-coded URLs** (Gateway + Eureka handle routing)  
✔ **Round-robin load balancing** between service instances  
✔ Easily extendable architecture  
✔ Perfect template for backend microservices projects  

---

## 🐳 Running with Docker Compose

### Start all services:

docker-compose up --build


⚠️ Important

After starting the containers,
WAIT 1–2 minutes for:

Eureka Server to fully boot

Services to register

Gateway to detect all services

Load balancer to activate

Skipping this wait will cause services to appear “not found”.

🌐 Service Ports
| Service       | Port |
| ------------- | ---- |
| Eureka Server | 8761 |
| API Gateway   | 8080 |
| Service 1     | 9001 |
| Service 2     | 9002 |

