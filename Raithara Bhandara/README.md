# Raithara Bhandara — Servlets + JDBC (MySQL)

A simple, interview-ready project that connects **farmers** with **nearby cold storage facilities** to increase crop shelf life.

- **Stack:** Core Java, **Servlets (HttpServlet)**, **JDBC**, MySQL, Maven
- **No Spring / No Hibernate / No Swagger** — exactly as requested
- **Packaging:** WAR. Deploy on **Apache Tomcat 9+**
- **Auth:** Minimal login via `HttpServlet` using a `users` table (role-based: FARMER/ADMIN)
- **UI:** Basic HTML + CSS only (no JSP)

## Project Structure

```
raithara-bhandara/
├─ sql/
│  ├─ create_tables.sql
│  └─ sample_data.sql
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ com/rb/
│  │  │     ├─ model/ (POJOs)
│  │  │     ├─ dao/   (JDBC DAOs)
│  │  │     ├─ util/  (DB + password helpers)
│  │  │     └─ servlet/ (HttpServlets)
│  │  ├─ resources/
│  │  │  └─ db.properties
│  │  └─ webapp/
│  │     ├─ assets/styles.css
│  │     ├─ index.html
│  │     ├─ login.html
│  │     ├─ register.html
│  │     ├─ addCrop.html
│  │     ├─ searchStorage.html
│  │     └─ WEB-INF/web.xml
├─ pom.xml
└─ README.md
```

## Database Schema (MySQL)

The schema implements the exact entities you described:

- **farmers** — farmer details
- **crops** — crops uploaded by farmers
- **cold_storage** — storage facilities
- **bookings** — mapping between crops and storage with status
- **users** (optional but used for login) — username/password/role

> Create the database and tables:

```bash
mysql -u root -p < sql/create_tables.sql
mysql -u root -p < sql/sample_data.sql   # optional seed
```

## Local Setup

1. **MySQL**: Update `src/main/resources/db.properties` with your credentials.
2. **Build**: `mvn clean package`
3. **Deploy**: Copy `target/raithara-bhandara.war` to `<TOMCAT_HOME>/webapps/`
4. **Run**: Start Tomcat and open: <http://localhost:8080/raithara-bhandara/>

### Default Login (from sample data)

- **Farmer**: `farmer1` / `password123`
- **Admin**:  `admin` / `admin123`

## Features

- Farmer **registers** and **logs in**
- Farmer **adds crops**
- Farmer **searches cold storages** by location
- Farmer **books** selected storage for a chosen crop
  - Capacity check: sum of **booked crop quantities** vs **storage capacity**
- Simple **dashboard** rendering via Servlets (no JSP)

## Notes

- Passwords are stored as **SHA-256 hashes**.
- Minimal HTML/CSS provided for demo during interview.
- Keep things simple: No JSP/Thymeleaf, no Spring/Hibernate.

---

© 2025 Raithara Bhandara Demo
