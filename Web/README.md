## 📚 **Web Programming Labs Overview**

This repository contains **4 web programming labs** designed to help you understand various web development techniques in **Java**, from building FastCGI and servlet-based applications to working with modern frameworks like **JavaServer Faces (JSF)** and **JAX-RS + Angular**.

---

### **1. FastCGI Server with AJAX and HTML**
🌐 **Category:** FastCGI Server, HTML, AJAX  
In this lab, you develop a **FastCGI server** in Java that determines whether a point lies inside a specified area on the coordinate plane. The server processes **HTTP requests** from an HTML form and dynamically returns results, including execution time and the current timestamp.

**Key Concepts:**  
- FastCGI server implementation using Java  
- **Apache HTTP Server** for static content  
- AJAX-based interactions between the frontend and server  
- Data validation on both client and server sides  

**Features:**  
- HTML page with an interactive form for point submission  
- Dynamic table displaying past requests and results  
- Server-side validation of input parameters  
- Response including results, timestamp, and execution time

---

### **2. Web Application with Servlets and JSP (MVC Pattern)**
🌐 **Category:** Servlets, JSP, MVC Architecture  
This lab involves creating a web application that determines whether a point lies in a specific area using **Java Servlets** and **JSP** within the **MVC design pattern**. You implement three main components: `ControllerServlet`, `AreaCheckServlet`, and a JSP page.

**Key Concepts:**  
- **Model-View-Controller (MVC)** architecture  
- Servlets for request handling and point-check logic  
- JSP for generating dynamic web content  
- JavaScript-based client-side validation  

**Features:**  
- Interactive form for point and radius input  
- Dynamic graphical element that accepts mouse clicks to determine point coordinates  
- Table displaying past results, stored using session management or application context  
- Validation to prevent submission of incorrect data  

**Deployment:**  
- Deployed on a **WildFly server**  
- All requests routed through a central controller (ControllerServlet)  

---

### **3. Web Application with JavaServer Faces (JSF)**
🌐 **Category:** JavaServer Faces, Managed Beans, PostgreSQL  
In this lab, you create a **JSF-based application** with two facelets pages: a **start page** and a **main page** for point-checking functionality. Managed beans handle application logic, while the results are stored in a **PostgreSQL database**.

**Key Concepts:**  
- Managed beans with `ApplicationScoped` configuration  
- Facelets templates for dynamic page rendering  
- Data persistence using PostgreSQL and ORM (EclipseLink)  
- Interactive components with **PrimeFaces** or **ICEFaces**  

**Features:**  
- **Start page** with dynamic clocks that update every 13 seconds  
- **Main page** with input components for point and radius  
- Dynamically updating plot to visualize point results  
- Table displaying previous checks, stored in the database  
- Navigation managed through a dedicated configuration file  

**Additional Functionality:**  
- Server-side input validation  
- Colored points on the coordinate plane, based on the result  
- Database persistence for point-check results  

---

### **4. RESTful Web Application (JAX-RS + Angular 2+)**
🌐 **Category:** REST API, JAX-RS, Angular 2+  
This lab involves creating a **RESTful web application** with a **JAX-RS backend** and an **Angular frontend**. The backend processes REST requests to validate points on the coordinate plane and interacts with a PostgreSQL database.

**Key Concepts:**  
- REST API using **JAX-RS**  
- JWT-based user authentication  
- PostgreSQL database for storing point-check results  
- Angular for a responsive, interactive frontend  

**Features:**  
- **Login and registration pages** with JWT-based authentication  
- Dynamic form for point submission and validation  
- Graphical representation of results using the canvas  
- Table displaying previous checks retrieved from the server  
- Responsive layout with desktop, tablet, and mobile views  

**Deployment:**  
- Backend runs on a **WildFly server**  
- Angular application served using **Nginx** (Dockerized deployment)  

---

## 🔧 **Technologies and Tools**
- **Java Servlets, JSP, JSF, and JAX-RS**  
- **PostgreSQL** for data persistence  
- **HTML, CSS, JavaScript, and Angular 2+** for dynamic web content  
- **Apache HTTP Server** for static content (Lab 1)  
- **WildFly** for backend deployment (Labs 2-4)  
- **Docker** for containerized deployments (Lab 4)  

---

## 🚀 **How to Use**
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/web-programming-labs.git
   cd web-programming-labs
   ```

2. Follow individual lab instructions for setting up and running each application.

---

This progression of labs will help you build a strong foundation in **web programming with Java**, from traditional servlet-based applications to modern RESTful architectures using **Angular** and **Docker**. 🌟
