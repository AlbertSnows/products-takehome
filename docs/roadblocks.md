# Roadblocks

Problems hit while building this project, with the cause and the fix for each.

## Project generation

**1. Initializr returns a 500**
- Cause: `bootVersion=3.5.0` is below the supported range (4.0.0 or newer), and the `.RELEASE` suffix is no longer accepted.
- Fix: a bare version, `bootVersion=4.1.1`.


## Docker on Fedora Silverblue

**2. The `products` table was never created** (log: `init.sql: Permission denied`)
- Cause: SELinux blocked the container from reading the bind-mounted `init.sql`. Postgres only runs init scripts on an empty data directory, so it skipped them on every later start.
- Fix: add `:z` to the mount, then `docker compose down -v` and start again.

## Frontend and API


**3. The browser shows "Failed to fetch"**
- Cause: CORS. The page (port 5173) and the API (port 8080) are different origins, and the backend sent no `Access-Control-Allow-Origin` header. JavaScript only sees a generic error, and the detail is in the browser console.
- Fix: `@CrossOrigin(origins=...)` on the controller, later extended with `http://localhost:3000` for the Docker frontend.

**4. A blank name returned 500**
- Cause: the request reached `save()` and Hibernate rejected it. After adding validation it still returned 500, because `@Valid` was on the method and not on the `@RequestBody` parameter.
- Fix: put `@Valid` on the parameter, which gives a 400.


## Sources

- [Docker: bind mounts, SELinux label](https://docs.docker.com/engine/storage/bind-mounts/)
- [MDN: CORS errors](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS/Errors)
- [Spring MVC: @RequestBody](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-methods/requestbody.html)