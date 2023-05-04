package com.test.hibernate6customfunctions.dialect;

import com.test.hibernate6customfunctions.fuctions.*;
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
    public void initializeFunctionRegistry(QueryEngine queryEngine) {
        super.initializeFunctionRegistry(queryEngine);

        /*
         QueryEngine contains SqmFunctionRegistry - the default storage of SQM functions.

         In the following lines we're registering custom SQM functions.
         These functions can now be used in HQL or JPQL queries that are executed against a database using the BetterPGDialect dialect.

         Note: "secondMaxSalary" - name that can be used in JPQL queries. "second_max_salary" - name of function in the database
         */
        // "Normal" function
        queryEngine.getSqmFunctionRegistry().register("secondMaxSalary", new SecondMaxSqmFunction("second_max_salary", queryEngine.getTypeConfiguration()));
        // Aggregate function
        queryEngine.getSqmFunctionRegistry().register("countItemsGreaterVal", new CountItemsGreaterValSqmFunction("countItemsGreaterVal", this, queryEngine.getTypeConfiguration()));;
    }
}
