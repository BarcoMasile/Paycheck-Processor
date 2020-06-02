# Paycheck Processor

You need to add a single file "application.yml" under src/main/resources:
the content must be at least:
```yml
spring:
  profiles:
    active: @spring.profile.active@

aws:
  access-key: <AWS TEXTRACT ACCESS KEY>
  secret-key: <AWS TEXTRACT SECRET KEY>
  region: <AWS REGION>

```