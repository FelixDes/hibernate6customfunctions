# Hibernate 6 custom SQM functions

## Description

This project provides several examples of how to implement custom SQM functions in Hibernate 6.

## Start Up

1. Run db container by...
    - executing *./run.sh*
    - or manually: `cd ./db; docker-compose pull; docker-compose up --build -d`
2. Run tests: *./src/test/java/com/test/hibernate6customfunctions*
3. Explore

## Explanation of implementing custom functions

1. Firstly create your own SQL Dialect:
   ```java
   public class BetterPGDialect extends PostgreSQLDialect {
      //default constructors...
   }
   ```
2. Define this dialect in hibernate config (or in spring boot config):
   ```yaml
   spring.jpa.properties.hibernate.dialect: com.test.hibernate6customfunctions.dialect.BetterPGDialect
   ```
3. Create your new SQM function
4. To be continued
   ```java
   @Override
   public void initializeFunctionRegistry(QueryEngine queryEngine) {
        super.initializeFunctionRegistry(queryEngine);

        queryEngine.getSqmFunctionRegistry().register("secondMaxSalary", new SecondMaxSqmFunction("secondMaxSalary", queryEngine.getTypeConfiguration()));
   }
   ```