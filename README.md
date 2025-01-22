# ABCIgnite

# Overview
**ABC Ignite** is a Software-as-a-Service full solution for workout clubs/gyms (and groups of clubs) which allows business owners to manage their courses, classes, members, memberships, etc.

This project is a gym booking class management system built using **Spring Boot 2.7.x** and **Java 8**. It provides functionalities to:

1. Create and manage classes.
2. Book classes with validations for date and capacity.
3. Search bookings by member name and/or date range.

The project emphasizes simplicity, modularity, and thorough testing.

---

## Pre-requisites
Ensure the following tools are installed:

- **Java Development Kit (JDK 8)** and set environment variables accordingly:
  ```bash
  java -version
  ```
- **Apache Maven (3.6 or higher):**
  ```bash
  mvn -v
  ```
- **Git:**
  ```bash
  git --version
  ```

---

## Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-repo/class-booking-system.git
   ```
2. **Build the project:**
   ```bash
   mvn clean install
   ```
3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

---

## Key Design and Implementation Highlights

### 1. Scalable and Clean Architecture
- The application follows a clean architecture with well-defined layers:
  - **Controller Layer:** Handles API requests and responses.
  - **Service Layer:** Implements the business logic.
  - **Repository Layer:** Manages database operations.
- Ensures separation of concerns, making the system maintainable and scalable.

### 2. Object-Oriented Programming (OOP)
- Employed OOP principles such as:
  - **Encapsulation:** Ensures data integrity by restricting direct access to fields.
  - **Abstraction:** Hides implementation details and exposes only necessary functionalities.
  - **Polymorphism:** Simplifies code modifications and enhances flexibility.
  - **Inheritance:** Reduces redundancy by sharing common functionalities across components.

### 3. Testing
- Ensures code quality and functionality with:
  - **JUnit:** For unit testing.
  - **Mockito:** For mocking dependencies and testing isolated components.

---

## API Endpoints

### 1. Create a Class
- **Endpoint:** `POST /api/classes`
- **Request Body:**
  ```json
  {
    "name": "Yoga",
    "startDate": "2025-01-20",
    "endDate": "2025-02-01",
    "startTime": "10:00",
    "duration": 60,
    "capacity": 10
  }
  ```
- **Response:** `201 Created`

### 2. Book a Class
- **Endpoint:** `POST /api/bookings`
- **Request Body:**
  ```json
  {
    "memberName": "John Doe",
    "className": "Yoga",
    "participationDate": "2025-01-25"
  }
  ```
- **Response:** `Booking Successful`

### 3. Search Bookings
- **Endpoint:** `GET /api/bookings`
- **Query Parameters:**
  - `memberName=Yaheya Farooqui`
  - `startDate=2025-01-20`
  - `endDate=2025-01-30`
- **Response:**
  ```json
  [
    {
      "memberName": "Yaheya Farooqui",
      "className": "Yoga",
      "participationDate": "2025-01-25"
    }
  ]
  ```

---

## Thought Process

- **Storage:** Used `ConcurrentHashMap` for in-memory storage as the assignment didn’t require database persistence.
- **Modular Validations:** Refactored validations for reusability and maintainability.
- **Testing:** Focused on core functionality with unit tests to cover edge cases.

---

## Future Improvements

1. Add database persistence for scalability.
2. Introduce user authentication and role-based access control.
3. Improve error handling with detailed error codes.
4. Add integration tests to validate end-to-end API behavior.
