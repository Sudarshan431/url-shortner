# URL Shortener Backend 🚀

A Spring Boot backend application that converts long URLs into short links and redirects users.

## Features
- URL shortening
- Unique short code generation
- Redirect using HTTP 302
- JWT-based security
- REST APIs

## Tech Stack
- Java
- Spring Boot
- Spring Security (JWT)
- MySQL
- JPA / Hibernate

## API Endpoints

### Shorten URL
POST /api/url/shorten

Request:
{
"originalUrl": "https://google.com"
}

Response:
{
"shortUrl": "http://localhost:8080/abc123"
}

### Redirect
GET /api/url/{shortCode}

## How to Run
1. Clone repo
2. Run Spring Boot application
3. Use Postman to test APIs

---

## Future Improvements
- Custom alias
- Expiry links
- Click analytics
- Redis caching