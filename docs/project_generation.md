# How the project was generated

The `backend/` skeleton was generated with [Spring Initializr](https://start.spring.io), using its
`starter.zip` endpoint from the command line:

```
curl https://start.spring.io/starter.zip \
  -d type=gradle-project -d language=java -d bootVersion=4.1.1 \
  -d baseDir=backend -d groupId=com.products -d artifactId=takehome \
  -d name=takehome -d packageName=com.products.takehome -d javaVersion=25 \
  -d dependencies=web,data-jpa,postgresql,devtools \
  -o backend.zip
unzip backend.zip -d backend && rm backend.zip
```

Generated: `build.gradle`, `settings.gradle`, the Gradle wrapper, `TakehomeApplication.java`, the
`contextLoads` test, `application.properties`, and `.gitignore`. 
