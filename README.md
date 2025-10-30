# 🧩 Distributed Architecture Workshop

## Overview

In this workshop, students will design and implement a **distributed system** that demonstrates **replication**, **load balancing**, and **dynamic service discovery**. The system is composed of multiple backend nodes that maintain a **replicated data structure**, a **load balancer** responsible for routing client requests, and an **asynchronous JavaScript client** that interacts with the system in real time.

---

## 🏗️ Architecture

The system consists of the following main components:

### 1. Asynchronous Client (Frontend)
- Implemented in **JavaScript (HTML + JS)**.
- Sends asynchronous HTTP requests (`POST`) to the load balancer’s API endpoint.
- Allows users to register their names, which are stored along with a timestamp in the distributed backend system.

### 2. Load Balancer
- Built with **Spring Boot**.
- Distributes client requests across backend nodes using a **Round-Robin** load-balancing strategy.
- Maintains a **Server Registry**, where backend nodes register themselves dynamically.
- Ensures fault tolerance by only routing traffic to currently active backend instances.

### 3. Backend Nodes
- Each backend is a **Spring Boot service** running in its own JVM.
- Stores key-value pairs (e.g., `{ name, timestamp }`) inside a **Replicated HashMap**.
- Synchronizes state automatically with other backends, so all instances maintain consistent data.
- New nodes joining the cluster receive a **state transfer** from existing ones, ensuring system consistency.

---

## ⚙️ Execution Flow

1. **Startup phase:**
   - Backend nodes start and **register themselves** with the Load Balancer via the Server Registry.
   - Each node initializes its local **Replicated HashMap** and connects to other nodes for state synchronization.

2. **Client interaction:**
   - The client submits a name asynchronously (via POST request).
   - The Load Balancer selects a backend node (Round Robin) and forwards the request.
   - The selected backend stores the data locally and replicates it across all nodes.

3. **Consistency:**
   - Each backend maintains a synchronized copy of the HashMap.
   - If a node goes down, others continue serving requests seamlessly.
   - When a new node joins, it performs **state transfer** to catch up with the cluster.

---

## 🧠 Learning Objectives

By completing this workshop, students will:

- Understand the fundamentals of **distributed architectures**.
- Implement **replicated data structures** using group communication.
- Build a **load balancer** with **round-robin routing**.
- Develop a **service discovery mechanism** for dynamic backend registration.
- Explore **fault tolerance** and **state synchronization** in distributed systems.
- Integrate **asynchronous clients** with distributed backends.

---

## 🖥️ Technologies Used

- **Java 17+**
- **Spring Boot**
- **HTTP / REST**
- **JGroups (for replication and state transfer)**
- **JavaScript (Fetch API for asynchronous client)**
- **Docker / EC2 (optional for deployment)**

---

## 🧪 Example Flow

1. Start multiple backend nodes:
   ```bash
   docker run -d -p 8080:8080 -e SPRING_PORT=8080  -it --rm --network host andresariz88/simplechat
   docker run -d -p 8081:8081 -e SPRING_PORT=8081  -it --rm --network host andresariz88/simplechat
   docker run -d -p 8081:8081 -e SPRING_PORT=8082  -it --rm --network host andresariz88/simplechat
   ```

2. Start the load balancer:
   ```bash
   docker run -d -p 8080:8080 -it --rm --network host andresariz88/chat-load-balancer
   ```

3. Open the web client and send a name (e.g., `Andrés`):
   - The request hits the load balancer.
   - The load balancer forwards it to one backend node.
   - The name appears replicated across all backends.

---

## 📦 Deliverables

- Source code for:
  - Load Balancer
  - Backend Nodes (with replicated HashMap)
  - Asynchronous Web Client
- README documentation (this file)
- Evidence of system running with multiple nodes (screenshots or demo)

---

## 📚 Summary

This workshop combines **concepts of distributed computing, load balancing, and replication** in a practical, hands-on environment.  
Students gain real experience building fault-tolerant, scalable systems capable of maintaining state consistency across multiple JVMs.

---
