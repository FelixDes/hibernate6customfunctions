package com.test.hibernate6customfunctions.dialect;

import com.test.hibernate6customfunctions.fuctions.*;
import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.dialect.PostgreSQLDriverKind;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.query.spi.QueryEngine;

public class BetterPGDialect extends PostgreSQLDialect {
    public BetterPGDialect() {
        super();
    }

    public BetterPGDialect(DialectResolutionInfo info) {
        super(info);
    }

    public BetterPGDialect(DatabaseVersion version) {
        super(version);
    }

    public BetterPGDialect(DatabaseVersion version, PostgreSQLDriverKind driverKind) {
        super(version, driverKind);
    }

    @Override
    public void initializeFunctionRegistry(FunctionContributions functionContributions) {
        super.initializeFunctionRegistry(functionContributions);

        /*
         FunctionContributions contains SqmFunctionRegistry - the default storage of SQM functions.

         In the following lines we're registering custom SQM functions.
         These functions can now be used in HQL or JPQL queries that are executed against a database using the BetterPGDialect dialect.

         Note: "secondMaxSalary" - name that can be used in JPQL queries. "second_max_salary" - name of function in the database
         */
        // "Normal" function
        functionContributions.getFunctionRegistry().register("secondMaxSalary", new SecondMaxSqmFunction("second_max_salary", functionContributions.getTypeConfiguration()));
        // Aggregate function
        functionContributions.getFunctionRegistry().register("countItemsGreaterVal", new CountItemsGreaterValSqmFunction("countItemsGreaterVal", this, functionContributions.getTypeConfiguration()));
    }
}
