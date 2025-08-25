# Ticketing System — Spring Boot + Next.js + PostgreSQL

A runnable starter implementing authentication (JWT), RBAC, tickets with lifecycle, comments, ratings, admin panel APIs, and basic Next.js UI.

## Quickstart

1) Start infra:
```bash
docker compose up -d
```

2) Backend:
```bash
cd ticketing-backend
./mvnw spring-boot:run
```
Backend listens on `http://localhost:8080`

3) Frontend:
```bash
cd ticketing-frontend
npm i
npm run dev
```
Frontend at `http://localhost:3000`

### Seed users
On first run, Flyway seeds roles and a few users:
- admin@demo.com / admin123 (ADMIN)
- agent@demo.com / agent123 (AGENT)
- user@demo.com  / user123  (USER)

### Env
- Postgres: localhost:5432 (ticket/ticket)
- Mailhog UI: http://localhost:8025

## Features
- JWT login/logout, refresh
- Roles: ADMIN, AGENT, USER
- Tickets: create, list, view, update, assign, lifecycle (OPEN→IN_PROGRESS→RESOLVED→CLOSED)
- Comments with timestamps
- Ratings (1-5) after resolve/close
- Search/filter by status, priority, assignee, owner, q
- Admin: manage users, force close/reassign

## Notes
- Attachments endpoints are included and save to `storage/` locally (dev). Frontend has a simple uploader on ticket page.
