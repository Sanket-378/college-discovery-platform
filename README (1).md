# CampusCompass — College Discovery Platform

**CampusCompass** is a full-stack college discovery and recommendation platform designed to help students explore colleges, compare options, view courses, read student reviews, and find colleges that match their entrance-exam rank.

## 🌐 Live Demo

- Frontend: https://sanket-378.github.io/college-discovery-platform/
- Backend API: https://college-finder-api-amw0.onrender.com

The frontend is deployed on GitHub Pages and the Spring Boot backend is deployed on Render.

---

## ✨ Features

### College Discovery
- Search colleges by keyword
- Filter by city and state
- Filter by college type and course
- Filter by minimum/maximum rating
- Filter by minimum/maximum fees
- Pagination
- Sorting
- Ascending/descending ordering

### College Profiles
Each college has a dedicated profile containing:
- Name and location
- Description
- College type
- Ownership
- Established year
- Accreditation
- Average rating
- Courses
- Degree level and specialization
- Course fees
- Student reviews

### College Predictor
Students can enter:
- Entrance exam
- Rank
- Category
- Preferred location

Results are grouped into:
- 🟢 Good Chances
- 🟡 Moderate Chances
- 🔴 Ambitious

Each result can link to the complete college profile.

> The predictor is a transparent rule-based/cutoff-based estimation feature for demonstration and decision support. It is not an official admission guarantee.

### College Comparison
Students can select colleges and compare them side by side.

Comparison includes:
- College name
- Location
- Rating
- College type
- Ownership
- Established year
- NIRF rank
- Review count

The application supports up to four colleges for comparison.

### Student Reviews & Ratings
Students can submit:
- Name
- Rating
- Review title
- Review text

College profiles display available reviews and average rating information.

---

## 🏗️ Architecture

```text
                    Student
                       |
                       v
              +------------------+
              | CampusCompass UI |
              | GitHub Pages     |
              +--------+---------+
                       |
                    REST API
                       |
                       v
              +------------------+
              | Spring Boot API  |
              | Render           |
              +--------+---------+
                       |
                       v
              +------------------+
              |   PostgreSQL     |
              +------------------+
                       |
                       v
                    Flyway
              Database Migrations
```

---

## 🛠️ Technology Stack

### Frontend
- HTML5
- CSS3
- JavaScript (ES Modules)
- Fetch API
- Hash-based client-side routing
- Custom build scripts

### Backend
- Java 21
- Spring Boot 4.1.1
- Spring Web
- Spring Data JPA
- Spring Validation
- Hibernate
- Apache Tomcat

### Database
- PostgreSQL
- Flyway

### Deployment
- GitHub
- GitHub Actions
- GitHub Pages
- Render

---

## 📁 Project Structure

```text
college-discovery-platform/
│
├── .github/
│   └── workflows/
│       └── deploy.yml
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/collegefinder/
│   │   │   │   ├── config/
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── entity/
│   │   │   │   ├── repository/
│   │   │   │   └── service/
│   │   │   └── resources/
│   │   │       └── db/migration/
│   │   └── test/
│   └── pom.xml
│
├── database/
├── docs/
│
└── frontend/
    ├── public/
    ├── src/
    │   ├── main.js
    │   ├── api.js
    │   └── pages/
    │       └── Home.js
    ├── index.html
    ├── package.json
    ├── build.mjs
    └── dev-server.mjs
```

---

# 🔌 REST API

Production base URL:

```text
https://college-finder-api-amw0.onrender.com
```

## Colleges

### List/search colleges

```http
GET /api/colleges
```

Example:

```text
/api/colleges?page=0&size=12&sort=name&direction=asc
```

Supported parameters include:

```text
search
city
state
collegeType
course
minRating
maxRating
minFees
maxFees
page
size
sort
direction
```

### College details

```http
GET /api/colleges/{id}
```

### Courses

```http
GET /api/colleges/{id}/courses
```

### Compare colleges

```http
GET /api/colleges/compare?ids=1,2,3
```

### Reviews

```http
GET /api/colleges/{id}/reviews
```

### Rating

```http
GET /api/colleges/{id}/rating
```

### Create review

```http
POST /api/colleges/{id}/reviews
```

Example request:

```json
{
  "name": "Student Name",
  "rating": 5,
  "title": "Great college",
  "review": "Good academic environment and facilities."
}
```

---

## 🎯 Predictor API

```http
POST /api/predictor
```

Example:

```json
{
  "exam": "JEE Main",
  "rank": 30000,
  "category": "Open",
  "location": "Maharashtra"
}
```

The predictor response is organized into:

```text
goodChances
moderateChances
ambitious
```

---

# 🚀 Running Locally

## Prerequisites

Install:

- Java 21
- Maven
- Node.js
- npm
- PostgreSQL
- Git

Verify:

```bash
java -version
mvn -version
node -v
npm -v
psql --version
```

---

## 🗄️ Database Setup

Create the database:

```sql
CREATE DATABASE college_finder;
```

Configure:

```text
backend/src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/college_finder
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
```

Do not commit real credentials to GitHub.

Flyway manages database migrations when the backend starts.

---

## ▶️ Start Backend

```bash
cd backend
mvn spring-boot:run
```

For a typical local configuration:

```text
http://localhost:8080
```

If port 8080 is already occupied, use another port:

```properties
server.port=8081
```

---

## ▶️ Start Frontend

Open another terminal:

```bash
cd frontend
npm install
npm run dev
```

The development frontend normally runs on:

```text
http://localhost:5173
```

---

# 🔗 Frontend API Configuration

The frontend API helper supports a configurable API base URL.

It uses:

```javascript
window.__COLLEGE_API_BASE_URL__
```

and falls back to the deployed backend URL:

```text
https://college-finder-api-amw0.onrender.com
```

This allows the frontend to communicate with either a local or deployed backend.

---

# 🧭 Frontend Routes

The application uses hash-based routing for GitHub Pages compatibility.

```text
#/
#/discover
#/predictor
#/compare
#/college/{id}
```

Example:

```text
#/college/6
```

opens the profile for college ID 6.

---

# 🏭 Build

From the frontend directory:

```bash
npm run build
```

The production build is generated using:

```text
frontend/build.mjs
```

---

# 🚢 Deployment

## Frontend — GitHub Pages

The frontend is deployed through GitHub Actions.

```text
Git Push
   ↓
GitHub Actions
   ↓
npm build
   ↓
GitHub Pages
   ↓
Live Frontend
```

Workflow:

```text
.github/workflows/deploy.yml
```

Live site:

```text
https://sanket-378.github.io/college-discovery-platform/
```

GitHub Pages should be configured to use GitHub Actions as its deployment source.

## Backend — Render

The Spring Boot backend is deployed as a Render web service.

Backend:

```text
https://college-finder-api-amw0.onrender.com
```

Production database credentials should be supplied through Render environment variables rather than committed to the repository.

---

# 🔐 CORS

Because the frontend and backend use different domains, the backend must allow the production frontend origin:

```text
https://sanket-378.github.io
```

Local development origins can also be allowed:

```text
http://localhost:5173
http://127.0.0.1:5173
```

CORS is configured centrally in the Spring Boot backend.

---

# 🧪 Verification

The backend has been verified for the major application operations:

- College listing
- Search
- Filtering
- Sorting
- Pagination
- College details
- Courses
- Comparison
- Reviews
- Ratings
- Predictor

The frontend supports:

- Home page
- Discovery
- College profile navigation
- Predictor
- Predictor result cards
- Comparison
- Courses
- Reviews

---

# 📊 User Flow

```text
Open CampusCompass
        ↓
Enter exam + rank
        ↓
College Predictor
        ↓
Good / Moderate / Ambitious results
        ↓
Open college profile
        ↓
View courses
        ↓
View rating & reviews
        ↓
Compare colleges
        ↓
Make an informed decision
```

Discovery flow:

```text
Discover Colleges
        ↓
Search / Filter
        ↓
Select colleges
        ↓
Compare
        ↓
Open profile
        ↓
Explore courses and reviews
```

---

# 🧠 Predictor Approach

The predictor uses transparent cutoff-based/rule-based matching.

```text
Entrance Exam
      +
Rank
      +
Category
      +
Preferred Location
      ↓
Available college cutoff data
      ↓
Matching logic
      ↓
Good / Moderate / Ambitious
```

The approach is intentionally understandable rather than presenting an unexplained prediction score.

---

# ⚠️ Disclaimer

CampusCompass is an educational/internship project.

Prediction results are estimates based on the application's available data and matching logic.

Actual admission depends on factors including:

- Admission year
- Entrance examination
- Category
- Counselling round
- Seat availability
- Course/branch
- Official cutoff
- College and government admission policies

Users should verify final admission information using official sources.

---

# 🔒 Security

Never commit:

```text
Database passwords
API keys
Access tokens
Production credentials
Private configuration
```

Use environment variables for production secrets.

---

# 🛠️ Troubleshooting

### `npm run build` cannot find `package.json`

Run the command from:

```text
frontend/
```

because the frontend contains:

```text
frontend/package.json
```

GitHub Actions must also use the correct frontend working directory.

### Frontend cannot connect to backend

Check:

1. Render service is running.
2. API base URL is correct.
3. Backend CORS allows the GitHub Pages origin.
4. Browser DevTools Network/Console.
5. Render logs.

### GitHub Pages deployment fails

Check:

- Pages is enabled.
- Pages uses GitHub Actions.
- `.github/workflows/deploy.yml` exists.
- Frontend build succeeds.
- The workflow uploads the correct build directory.

### College profile opens with `undefined`

The route must contain a valid college ID:

```text
#/college/6
```

not:

```text
#/college/undefined
```

The predictor response must contain the college identifier expected by the frontend.

### Backend does not start

Check:

- PostgreSQL connection
- Database credentials
- Flyway migrations
- Application port
- Render environment variables

If port 8080 is occupied locally, configure another port.

---

# 🧩 Backend Architecture

The backend follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

### Controllers
Handle HTTP requests and responses.

### Services
Contain application/business logic.

### Repositories
Handle database persistence using Spring Data JPA.

### DTOs
Represent API request and response models.

### Entities
Represent database records.

---

# 🗃️ Database Migrations

Flyway is used to manage schema migrations.

Migration files are stored under:

```text
backend/src/main/resources/db/migration/
```

This makes database schema changes reproducible between environments.

---

# 🔮 Future Improvements

Potential future enhancements:

- Larger and regularly updated college dataset
- More entrance examinations
- Branch-specific predictions
- Historical cutoff trends
- Advanced recommendation scoring
- Authentication and student profiles
- Saved colleges
- Personalized college lists
- More detailed statistics
- College images
- Official college website links
- Improved mobile experience
- More automated integration tests
- Production monitoring

---

# 🎯 Project Objective

CampusCompass aims to simplify college research by bringing important decision-making features into one platform:

- College discovery
- Search and filtering
- Rank-based prediction
- College profiles
- Courses
- Ratings
- Student reviews
- College comparison

Instead of switching between multiple tools, students can use one platform to explore and evaluate their options.

---

# 👨‍💻 Author

**Sanket Khatakale**

GitHub: https://github.com/Sanket-378

Repository: https://github.com/Sanket-378/college-discovery-platform

---

## ⭐ CampusCompass

**Find the right college. Build your future.**
