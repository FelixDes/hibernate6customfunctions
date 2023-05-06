# Hibernate 6 custom SQM functions

## Description

This project provides several examples of how to implement custom SQM functions in Hibernate 6.2.

## Start Up

1. Run tests:
   *[Hibernate6CustomFunctionsApplicationTests.java](/src/test/java/com/test/hibernate6customfunctions/Hibernate6CustomFunctionsApplicationTests.java)*  
   *Note*: database runs in docker container provided by Testcontainers
2. Explore

## Explanation of implementing custom functions

1. Firstly, create your own SQL Dialect:
    ```java
    public class BetterPGDialect extends PostgreSQLDialect {
        // Default constructors...
    }
    ```
2. Pass this dialect to Spring Boot config (or to Hibernate config):
    ```yaml
    spring.jpa.properties:
        hibernate.dialect: com.test.hibernate6customfunctions.dialect.BetterPGDialect
    ```
3. Create your SQM function
    1. Firstly, define your function in database
       ```sql
       create or replace function second_max_salary() 
       returns numeric as 
       $$
           select max(salary) 
           from Employee 
           where salary <> (select max(salary) from Employee) 
       $$ language SQL;
       ```
    2. Create new class: SecondMaxSqmFunction
       ```java
       public class SecondMaxSqmFunction extends AbstractSqmSelfRenderingFunctionDescriptor {
           public SecondMaxSqmFunction(String name, TypeConfiguration typeConfiguration) {
               super(
                   name,
                   // Function type
                   FunctionKind.NORMAL,
                   // Setting argument validation. In this case we have no input args
                   new ArgumentTypesValidator(StandardArgumentsValidators.NO_ARGS),
                   // Setting return type: numeric (mapped for Double)
                   StandardFunctionReturnTypeResolvers.invariant(typeConfiguration.getBasicTypeRegistry()
                       .resolve(StandardBasicTypes.DOUBLE)),
                   // No arguments, so argument type resolver is null 
                   null
               );
           }
       }
       ```
    3. Override the render method
        ```java
         public class SecondMaxSqmFunction extends AbstractSqmSelfRenderingFunctionDescriptor {
            // Constructor...
            @Override
            public void render(SqlAppender sqlAppender, 
                       List<? extends SqlAstNode> sqlAstArguments, SqlAstTranslator<?> walker) {
                render(sqlAppender, sqlAstArguments, null, walker);
            }
            @Override
            public void render(
                    SqlAppender sqlAppender,
                    List<? extends SqlAstNode> sqlAstArguments,
                    Predicate filter,
                    SqlAstTranslator<?> translator) {
                // Appending function name and its parenthesis to result SQL query
                sqlAppender.appendSql(getName());
                sqlAppender.appendSql("()");
				
                // You can pass whole SQL query to sqlAppender
            }
        }
        ```
       *Note*: for implementing aggregate functions you can
       visit
       *[CountItemsGreaterValSqmFunction.java](src/main/java/com/test/hibernate6customfunctions/fuctions/CountItemsGreaterValSqmFunction.java)*
4. Register your function to sqm function registry
    ```java
    public class BetterPGDialect extends PostgreSQLDialect {
        // Default constructors...
        @Override
        public void initializeFunctionRegistry(FunctionContributions functionContributions) {
            super.initializeFunctionRegistry(functionContributions);
            
            queryEngine.getSqmFunctionRegistry().register(
                    "secondMaxSalary", // Name that can be used in JPQL queries
                    new SecondMaxSqmFunction(
                        "second_max_salary", // Name of the function in the database
                        queryEngine.getTypeConfiguration())
            );
         }
    }
    ```
    *Note*: for Hibernate 6.1 it is:
    ```java
    public class BetterPGDialect extends PostgreSQLDialect {
        // Default constructors...
        @Override
        public void initializeFunctionRegistry(QueryEngine queryEngine) {
            super.initializeFunctionRegistry(queryEngine);

            queryEngine.getSqmFunctionRegistry().register(/*...*/);
        }
    }
    ```
5. Finally, use your function.  
   Example: JPQL query in JpaRepository
   ```java
   @Repository
   public interface EmployeeRepo extends JpaRepository<Employee, UUID> {
	   @Query("select secondMaxSalary()")
	   BigDecimal getSecondMaxSalaryCustom();
   }
   ```

## License

> This project is distributed under the **MIT** license.
