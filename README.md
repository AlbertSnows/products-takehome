# Products Take-Home

A small Products app: a React page that lists products and creates new ones, a Spring Boot API,
and a Postgres table.

To run everything in containers, from the repo root:

```
docker compose up --build
```

Then open `http://localhost:3000`. The sections below cover running each part on its own.

## Prerequisites

- Docker with Compose. This is all you need to run the whole system.
- To run the backend or frontend from source, also install:
  - JDK 25 (the Gradle wrapper is included, so Gradle itself is not needed)
  - Node 22.12 or newer

## Database setup

Postgres 16 runs in Docker. `db/init.sql` creates the table:

```sql
CREATE TABLE IF NOT EXISTS products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL
)
```

Compose mounts that file into the Postgres image's `/docker-entrypoint-initdb.d/`, and the image
runs it on the first start with an empty data volume. The user, password, and database are all
`products`, on port 5432.

Start only Postgres, from the repo root. A plain `docker compose up` also starts the backend and
frontend containers, and the backend container would take port 8080.

```
docker compose up -d db
```

To wipe the database and rerun `db/init.sql`:

```
docker compose down -v
docker compose up -d db
```

## Start backend

With Postgres running, from `backend/`:

```
./gradlew bootRun
```

The API listens on `http://localhost:8080`. Stop it with Ctrl+C.

- `GET /products` lists the products.
- `POST /products` with `{"name": "P1"}` creates one and returns it with status 201.

If the app fails with "Connection to localhost:5432 refused", Postgres is not running. See
Database setup.

Tests, from `backend/`. Two of the four test classes use the real database, so start Postgres
first:

```
./gradlew test
```
Note: Some tests will not work unless the db is up. 

## Start frontend

With Docker, the frontend is already served at `http://localhost:3000`.

To run it from source, with the backend running on port 8080, from `frontend/`:

```
npm install
npm run dev
```

The page is at `http://localhost:5173`. Tests, from `frontend/`:

```
npm run test:run
```

## Verify

1. Run `docker compose up --build` and open `http://localhost:3000`. If you are running the
   frontend from source instead, open `http://localhost:5173`.
2. Type `P1` into the text box and click **Add**. The page shows "Created".
3. Click **Refresh**. `P1` appears in the list.

## Notes

- No deviations from the preferred stack: React, Java Spring Boot, and Postgres.
- Versions: Spring Boot 4.1.1 on JDK 25, React 19, Vite 8.
- Ports: 3000 (frontend in Docker), 5173 (frontend dev server), 8080 (API), 5432 (Postgres).
- The frontend calls the API directly at `http://localhost:8080`, and the backend allows the
  origins `http://localhost:3000` and `http://localhost:5173` (CORS).
- A name is required, is trimmed, and can be at most 255 characters. An invalid request returns
  status 400.
- The `init.sql` mount in `docker-compose.yml` ends in `:z`, which relabels the file for SELinux
  hosts such as Fedora. Compose ignores it elsewhere.

## How you built it

- I generated the skeletons with Spring Initializr (backend) and `create-vite` (frontend), and ran
  Postgres in Docker Compose.
- On AI: I used Claude Code as a pair. It explained concepts with sources, reviewed my code and tests,
  helped debug, and wrote scaffolding such as empty test files and drafts of some docs. I wrote
  the application code and the tests.
- What I would do differently: use a migration tool such as Flyway instead of a one-time init
  script, add pagination, use a data-fetching library instead of `fetch` inside an effect, and use
  Testcontainers so the integration test does not need the compose database.
