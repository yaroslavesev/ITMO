## 📚 **Java Core Labs Overview**

This repository contains **8 labs** designed to enhance your understanding of core Java concepts, from mathematical operations and object-oriented programming (OOP) to client-server communication and graphical user interfaces.

---

### **1. Laba1: Mathematical Calculations**
🔢 **Category:** Basic Programming and Mathematical Operations  
In this lab, you generate arrays of random numbers and perform complex mathematical operations using trigonometric and exponential functions. The results are formatted and printed to the console.

**Key Concepts:**  
- Arrays, loops, and conditional statements  
- Mathematical functions from the `Math` library  
- String formatting for output display

---

### **2. Pokémon Battle Simulation**
🦸 **Category:** Object-Oriented Programming (OOP)  
This lab involves creating custom classes for Pokémon and their attacks. Using inheritance, you implement a Pokémon battle simulation, with each Pokémon having unique stats and moves.

**Key Concepts:**  
- Inheritance and method overriding  
- Abstract classes and preventing further inheritance  
- Handling battle logic and damage calculation

---

### **3-4. Object-Oriented Modeling and Scenario Simulation**
🎭 **Category:** Object-Oriented Design and UML  
You design an object model with characters and items based on a given scenario. The program simulates interactions and changes in the state of objects while demonstrating core OOP principles.

**Key Concepts:**  
- UML diagrams and object modeling  
- Abstract classes, interfaces, and enums  
- Handling exceptions and using records  
- SOLID design principles and polymorphism

---

### **5. Console-Based Collection Manager**
📂 **Category:** Data Structures and File I/O  
This lab involves creating an interactive console-based application that manages a collection of `Person` objects stored in a LinkedList. The program reads data from an XML file and allows various operations, such as adding, updating, and sorting records.

**Key Concepts:**  
- LinkedList for dynamic data storage  
- Serialization and deserialization of XML data  
- BufferedReader and OutputStreamWriter for file I/O  
- Command-line argument handling

**Supported Commands:**  
As shown in the screenshot, commands include: `add`, `remove`, `update`, `show`, `save`, and more.

---

### **6. Client-Server Architecture**
🌐 **Category:** Network Programming and Client-Server Communication  
This lab extends the collection manager from **Lab 5** into a client-server application. The server manages the collection and processes requests, while the client sends commands and displays responses.

**Key Concepts:**  
- TCP communication using Java’s Socket API  
- Serialization of commands and objects  
- Non-blocking I/O with network channels  
- Stream API for data processing  
- Handling server downtime and retries  

**Modules:**
- Request reception  
- Command execution  
- Sending responses back to the client  

---

### **7. Multi-Threaded Server with Database Support**
💾 **Category:** Multi-Threading and Database Integration  
This lab replaces the file-based storage with a PostgreSQL database. Each user can manage their own objects, and commands are executed in a multi-threaded server environment to handle concurrent requests.

**Key Concepts:**  
- PostgreSQL integration using JDBC  
- SHA-384 hashing for password security  
- Multi-threaded request handling  
- User-based access control and object ownership  
- Fixed thread pools for efficient processing  

---

### **8. JavaFX Graphical User Interface (GUI)**
🖥️ **Category:** GUI Development  
In this lab, the console client from **Lab 7** is replaced by a JavaFX-based GUI. The interface allows users to log in, view, and manipulate objects through tables and interactive visualizations.

**Key Concepts:**  
- JavaFX for building a modern GUI  
- Localization for multiple languages (Russian, Icelandic, Croatian, Spanish)  
- Table views with sorting and filtering using Stream API  
- Dynamic object visualization using graphics primitives  
- Live updates and animations for real-time changes  

---

## 🔧 **Technologies and Tools**
- **Java Core**  
- **PostgreSQL**  
- **JavaFX**  
- **TCP Sockets**  
- **Multi-threading**  
- **Serialization**  
- **File I/O (XML)**  

---

## 🚀 **How to Use**
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/java-core-labs.git
   cd java-core-labs
   ```

2. Run individual labs according to their instructions. For GUI-based labs, ensure you have the required JavaFX setup.

---

This structured progression through the labs ensures a deep understanding of Java programming concepts, preparing you for both academic and real-world development challenges. 🌟
