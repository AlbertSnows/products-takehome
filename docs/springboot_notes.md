# Spring Boot notes

Spring boot is not a trivial framework to learn. This document is
meant to act as a refresher on some of the core concepts behind
how spring boot is typically used. 

## What is spring framework? 

Let's start with the framework. The Spring framework is a sophisticated handler; a wrapper around your application
software that is meant to automate a lot of the additional boilerplate necessary to get a system off the ground. 
It does this via the idea of Inversion of Control (IoC). The idea with IoC is to
remove the responsibility of management from classes, and put that responsibility at a higher level. Spring does this by utilizing a technique known as dependency
injection. These dependencies are managed by spring instead
of your system. So instead of declaring new components, you declare
the components you need, and spring manages them for you. These
components in SB are known as beans. 

**Sources**

- [Introduction to the Spring IoC Container and Beans](https://docs.spring.io/spring-framework/reference/core/beans/introduction.html)
- [Dependency Injection](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html)

## What are beans? 

A bean, as with all java things, is an object. In spring, it is an
object that is instantiated, assembled, and managed by a Spring 
"Inversion of Control" container. Here, Spring decides to control
these objects for us so we don't have to. 

**Sources**

- [Introduction to the Spring IoC Container and Beans](https://docs.spring.io/spring-framework/reference/core/beans/introduction.html)
- [Bean Overview](https://docs.spring.io/spring-framework/reference/core/beans/definition.html)

## What's this about a container? 

In Spring, our container is an object whose responsibility is to
manage the lifecycle of our other objects. It's a kind of runtime
manager. 

In our case, our container is the `ApplicationContext`. It acts
as a registry of live objects that also knows how to build them. It follows these steps: 
1) Our context derives instructions from our `Annotations` which
provides configuration metadata
2) Using these instructions, it constructs each bean, and injects
the needed dependencies
3) It holds on to the finished beans, and provides them to other
beans when requested. 

We call it a container because our beans exist inside it. Although,
I wish it had a more specific name. 

**Sources**

- [Container Overview](https://docs.spring.io/spring-framework/reference/core/beans/basics.html)
- [Classpath Scanning and Managed Components](https://docs.spring.io/spring-framework/reference/core/beans/classpath-scanning.html#beans-scanning-autodetection)

## What is dependency injection? 

Dependency injection is the idea of dependencies being handed to 
the objects that need them, rather than creating and managing them
on their own. In the case of Spring, the idea is something like, "I, as the ProductController, need something that is responsible for fetching products for me." To which Spring provides `ProductRepository`.

### Without DI

```java
class ProductController {
    private ProductRepository repo = new ProductRepository();
}
```

Problems:
- the repo needs its own dependencies populated
- the controller has access to *only* this repo
- you can't really swap it out for testing purposes

### With DI

```java
class ProductController {
    private final ProductRepository repo;

    ProductController(ProductRepository repo) {
        this.repo = repo;
    }
}
```

Here, our controller's repo is provided instead of generated. This
allows us to be more fluid with the structure of our objects. In the
case of Spring, it provides repo for the controller "automatically". 

**Sources**

- [Dependency Injection](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html)
- [Constructor-based Dependency Injection](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html#beans-constructor-injection)

## What is spring boot? 

Spring boot is an opinionated layer on top of the spring framework.
It's "batteries included" in the sense that it provides a lot of
default, sensible configurations out of the box. The intent is such
that few apps will need configurations beyond what spring boot
provides by default. Additionally, it removes several setup steps
by doing the following:

1) Starters are used to bring in libraries and matches their versions
2) It auto-configures a datasource, JPA, and MVC based on the
classpath
3) It runs tomcat (an embedded server) inside your app
4) It sources external, additional configs, via `application.properties`

Note: A starter is a dependency in build.gradle that stands in for a whole set
of libraries. For example, spring boot's `data-jpa` starter brings in the
libraries for database access. 

Put simply, Spring is the engine. Boot is the remaining, default
car parts provided to drive the car (versus setting them up 
yourself). 

**Sources**

- [Spring Boot documentation home](https://docs.spring.io/spring-boot/index.html)
- [Build Systems (starters and dependency management)](https://docs.spring.io/spring-boot/reference/using/build-systems.html)
- [Auto-configuration](https://docs.spring.io/spring-boot/reference/using/auto-configuration.html)
- [How-to: Use Another Web Server](https://docs.spring.io/spring-boot/how-to/webserver.html#howto.webserver.use-another)
- [Embedded Servlet Container Support](https://docs.spring.io/spring-boot/reference/web/servlet.html#web.servlet.embedded-container)
- [Externalized Configuration](https://docs.spring.io/spring-boot/reference/features/external-config.html)

## What are annotations?

An annotation is a label associated with a section of code. The `@` tells the
compiler that what follows is an annotation. They can also be thought of as a
form of metadata. As a part of the intent of annotations, they have no direct 
effect on the operation of the code they annotate. However, other software can
read and act on them. 

So on their own, annotations do nothing. Some other piece of software has to read the label and act on
it. Java's docs list three kinds of readers:

1. **The compiler.** `@Override` makes the compiler check that you really are overriding a parent
   method, and error out if not.
2. **Build tools.** They read annotations at compile time to generate code or files.
3. **Runtime code.** A program inspects annotations while running, using reflection (code looking
   at its own classes).

Annotations can carry values, like `@SuppressWarnings("unchecked")`, and you can stack several on
one class.

**Sources**

- [Java Tutorials: Annotations](https://docs.oracle.com/javase/tutorial/java/annotations/index.html)
- [Java Tutorials: Annotations Basics](https://docs.oracle.com/javase/tutorial/java/annotations/basics.html)
- [Java Tutorials: The Reflection API](https://docs.oracle.com/javase/tutorial/reflect/index.html)

## How does Spring use annotations?

Spring uses kind 3; it's the runtime reader. When `SpringApplication.run` starts, Spring inspects your classes,
finds the annotations, and acts on them. Spring's annotations are labels that answer questions about
each class: "manage this one," "this is a web endpoint," "this maps to a table." Without Spring
running, `@RestController` on a class does nothing. As such, a class with an annotation but no
`run` call has no effect.


**Sources**

- [Classpath Scanning: @Component and Further Stereotype Annotations](https://docs.spring.io/spring-framework/reference/core/beans/classpath-scanning.html#beans-stereotype-annotations)
- [SpringApplication](https://docs.spring.io/spring-boot/reference/features/spring-application.html)

## Tying it back to `@SpringBootApplication`

That annotation is three labels bundled into one, and each one gives Spring a different instruction
when it reads `TakehomeApplication`:

| Annotation | Instruction to Spring |
|---|---|
| `@ComponentScan` | Search this package and below, and manage any class labeled as a component (`@RestController`, `@Repository`, etc.) |
| `@EnableAutoConfiguration` | Look at what's on the classpath and set up the matching infrastructure (datasource, JPA, Tomcat) |
| `@SpringBootConfiguration` | This class holds config, and it's the root that tests and Spring use to find the app |

From this, we derive the following flow:

1. `run` starts Spring.
2. Spring reads the annotation on the class you passed in.
3. That tells it where to scan and what to auto-configure.
4. Scanning finds your other annotated classes (`@RestController` on `ProductController`, and so
   on) and manages them too.

Because of this feature, we don't need to worry about things like registering the
controller since it is automated for us. 

**Sources**

- [Using the @SpringBootApplication Annotation](https://docs.spring.io/spring-boot/reference/using/using-the-springbootapplication-annotation.html)
- [Auto-configuration](https://docs.spring.io/spring-boot/reference/using/auto-configuration.html)
- [Structuring Your Code](https://docs.spring.io/spring-boot/reference/using/structuring-your-code.html)


## @Valid

Valid asks to check an object against its rules before 
continuing. So in our case, we mark an incoming post
request as @Valid. The type is CreateProductRequest, so we 
ensure it's not blank and that the character size is 
appropriate. 

## @ExceptionHandler 

This is used with another component, e.g. `@ExceptionHandler(someexception.class)` to call a method (handleValidation) when that exception is raised. 

## Response Enity

A response entity is the entire HTTP response represented
as an object. It has status, header, and a bady. You can
use it to configure the type of response you want to provide. 

## Gotchas

### The scan boundary

Recall `@ComponentScan` from earlier. 

> Search this package and below, and manage any class labeled as a component (@RestController, @Repository, etc.)

It's important to note that our app class is under `com.products.takehome`. As 
such, it only really discovers classes in that domain. A class under
`com.other.stuff` wouldn't be picked up under this system. 

### IoC's covering

Our discussion of IoC earlier covered how spring boot puts the parts together. 
One other thing to highlight is the flow. Notably, nothing ever calls our 
controller. Instead, the embedded server (Tomcat) will listen to HTTP requests, and call our controller for us via a dispatcher. 

Spring boot uses a MVC style structure, and the mechanism that calls our
controller is known as the `DispatcherServlet`. This servlet is its own kind
of controller (known as a front controller), and needs to follow some steps:
1) find the right handler
2) call it
3) handle exception
4) yield a response

Rather than doing these steps, each is handed off to delegate components. Specifically for our case: 

- `HandlerMapping`
- `HandlerAdapter`

Our `HandlerMapping` identifies which `@Controller` is responsible for handling an
incoming request. Our `HandlerAdapter` is then responsible for invoking that 
function. 

So the flow is something like this:

`<incoming request>` -> `GET /products` -> `DispatcherServlet` -> Uses our delegate components to find the matching `@Controller` -> execute the controller

**Sources**

- [Structuring Your Code](https://docs.spring.io/spring-boot/reference/using/structuring-your-code.html)
- [DispatcherServlet](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet.html)
- [Special Bean Types](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet/special-bean-types.html)
- [Processing](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet/sequence.html)
- [Embedded Servlet Container Support](https://docs.spring.io/spring-boot/reference/web/servlet.html#web.servlet.embedded-container)
