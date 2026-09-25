# Products Take-Home

## Start backend

Start Postgres first, from the repo root:

```
docker compose up -d
```

Then start the app, from `backend/`:

```
./gradlew bootRun
```

The API listens on `http://localhost:8080`. Stop it with Ctrl+C.

If the app fails with "Connection to localhost:5432 refused", Postgres is not running.

To wipe the database and rerun `db/init.sql`, from the repo root:

```
docker compose down -v
docker compose up -d
```
