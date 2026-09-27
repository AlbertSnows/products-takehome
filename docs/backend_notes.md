# Backend notes

## SQL injection on `name`

Not a concern for the current code. `save()` and `findAll()` go through JPA, which sends the name to
Postgres as a bound parameter. A value like `'; DROP TABLE products; --` is stored as plain text and
never runs as SQL.

It would become a risk if SQL were ever built by joining strings, for example a native query or a
custom `@Query` for search by name. Use bound parameters there.

The sources below are secondary (a tutorial site and a security knowledge base). No primary Spring or
Hibernate page was found that states this directly.

**Sources**

- [Baeldung: SQL Injection and How to Prevent It](https://www.baeldung.com/sql-injection)
- [SecureFlag: SQL Injection in Java](https://knowledge-base.secureflag.com/vulnerabilities/sql_injection/sql_injection_java.html)

## Pagination (not implemented)

`GET /products` returns every row through `findAll()`, in no defined order. That is intentional for
this exercise, since the instructions say to keep it minimal. This is how it would be added if
the list needed to scale.

- **Repository:** no change. `JpaRepository` already extends `ListPagingAndSortingRepository`, so
  `findAll(Pageable)` is available on `ProductRepository`.
- **Controller:** take a `Pageable` parameter on the `GET` handler and pass it to
  `repository.findAll(pageable)`. Spring Data fills it from query parameters: `page` (0-indexed,
  default 0), `size` (default 20), and `sort` (`property,ASC|DESC`). Example:
  `GET /products?page=0&size=20&sort=name,asc`.
- **Response shape:** Spring Data's docs advise against returning `Page` directly, because its
  implementation class is a domain type that may change. The suggested alternatives are
  `PagedModel`, or `@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)`. Either way the
  response is no longer a bare JSON array, so the frontend would have to change how it reads the
  list. Check the exact shape when implementing.
- **Stable order:** sorting by `name` alone is not stable, because names are not unique. Add `id` as
  a tiebreaker (`sort=name,asc&sort=id`) so rows do not repeat or skip across pages.

**Sources**

- [Spring Data Commons: Web support (Pageable)](https://docs.spring.io/spring-data/commons/reference/repositories/core-extensions.html)
- [Spring Data JPA: JpaRepository](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
