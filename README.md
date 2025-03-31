# BlogSphere

BlogSphere is a backend service for a blog application, designed to efficiently and securely handle content management using **Java Spring Boot**.

## 🚀 Features

- **User Authentication:** JWT-based authentication for stateless security
- **Password Security:** Bcrypt (12 rounds) for hashing 🔒
- **Role-Based Access Control (RBAC):** Manage user roles and permissions
- **ORM:** Java Persistence API (JPA) for database interactions
- **API Documentation:** Swagger for easy API exploration
- **Deployment:** Hosted on AWS Elastic Beanstalk

## 🛠 Tech Stack

- **Backend:** Spring Boot
- **Database:** PostgreSQL / MySQL
- **ORM:** Java Persistence API (JPA)
- **Authentication:** JWT-based authentication
- **Password Security:** Bcrypt hashing
- **API Documentation:** Swagger
- **Deployment:** AWS Elastic Beanstalk

## 📌 Installation & Setup

### Prerequisites:
- Java 21
- Maven
- PostgreSQL / MySQL

### Steps to Run Locally:
```bash
# Clone the repository
git clone https://github.com/hanshulll/blog-app-apis.git
cd blog-app-apis

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### Environment Variables:
Create a `application.properties` file with following configs:
```
server.port=9090
#db configuration
spring.datasource.url=jdbc:mysql://localhost:3306/blog_app_apis
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

## 📖 API Documentation
Once the application is running, API documentation is available at:
```
http://localhost:9090/swagger-ui/index.html
```

## 🏆 Challenges Faced
- Handling circular references in **Spring Data JPA**, which caused stack overflow errors.
- Deciding the best approach for **storing images** without using AWS S3.
- Staying consistent in completing the project without strict deadlines.

## 📚 Key Learnings
✅ Importance of **consistency** in personal projects.
✅ Handling **bidirectional relationships** in JPA using `@JsonIgnore`, `@JsonManagedReference`, `@JsonBackReference`.
✅ Exploring different **image storage** strategies in databases and cloud storage.

## 🏗 Future Improvements
- Implementing **S3 storage** for image handling.
- Adding **unit tests** and integration tests.
- Enhancing **performance optimizations**.

## 🤝 Contributing
Contributions are welcome! Feel free to fork the repo and submit a pull request.

## 📩 Contact
For queries, reach out on **LinkedIn**: [LinkedIn](https://www.linkedin.com/in/hanshul-chandel/)

---

Made with ❤️ by Hanshul 🚀
