# 🚆 Train Booking System (Java Backend Project)

A console-based **Train Booking System** built using Java that simulates core functionalities of real-world railway reservation systems like IRCTC.

This project demonstrates **backend engineering concepts**, including authentication, data persistence, and layered architecture.

---

## 📌 Features

* 🔐 User Signup & Login (BCrypt Password Hashing)
* 🔍 Search trains by source & destination
* 🪑 Seat availability & booking system
* 🎫 Ticket generation with complete details
* 📄 Booking history (Fetch user tickets)
* ❌ Ticket cancellation
* 💾 JSON-based data persistence (No database used)

---

## 🧱 Project Architecture

This project follows a **Layered Architecture**:

```
User (Console Input)
        ↓
App.java (Controller Layer)
        ↓
Service Layer (Business Logic)
        ↓
Entity Layer (Data Models)
        ↓
JSON Files (user.json, train.json)
```

---

## 📁 Project Structure

```
ticket.booking
│
├── App.java                  # Main application (entry point)
│
├── entities/
│   ├── User.java             # User model
│   ├── Train.java            # Train model
│   ├── Ticket.java           # Ticket model
│
├── service/
│   ├── UserBookingService.java   # Handles booking logic
│   ├── TrainService.java         # Train operations
│
├── util/
│   ├── UserServiceUtil.java      # Password hashing (BCrypt)
│
├── localDb/
│   ├── user.json            # User data storage
│   ├── train.json           # Train data storage
```

---

## ⚙️ Technologies Used

* Java (Core + OOP)
* Gradle
* Jackson (JSON Processing)
* BCrypt (Password Security)
* IntelliJ IDEA

---

## 🔄 How It Works

1. User signs up or logs in
2. System authenticates using BCrypt
3. User searches trains by route
4. Selects a train
5. Books a seat
6. Ticket is generated and stored
7. Data is persisted in JSON files

---

##🚨 Important Concepts You Learned

This project covers:

✔ OOP (classes, objects)
✔ Collections (List, Map)
✔ File handling
✔ JSON processing
✔ Exception handling
✔ State management
✔ Layered architecture
✔ Authentication
✔ Real-world workflow

## ▶️ How to Run

1. Clone the repository:

   ```
   git clone https://github.com/Anupam4625f/train-booking-system.git
   ```

2. Open in IntelliJ IDEA

3. Run:

   ```
   App.java
   ```

---

## 📌 Sample Output

```
Ticket ID: 1349a23c...
User : anupam
| Train: NANDANKANAN EXP
| From: midnapore
| To: puri
| Date: 2026-04-22
```

---

## 🧠 Key Concepts Demonstrated

* Object-Oriented Programming (OOP)
* Collections (List, Map)
* File Handling & JSON Serialization
* Authentication using BCrypt
* Layered Architecture Design
* State Management

---

## 🚀 Future Improvements

* Convert to Spring Boot REST API
* Integrate MySQL database
* Add JWT Authentication
* Build frontend (React/Flutter)
* Add seat selection UI

---

## 👨‍💻 Author

**Anupam Jana**

* 📧 Email: techsayan4625@gmail.com
* 🔗 LinkedIn: https://www.linkedin.com/in/anupam-jana2005/
* 💻 GitHub: https://github.com/Anupam4625f

---

## ⭐ If you like this project

Give it a ⭐ on GitHub and share feedback!
