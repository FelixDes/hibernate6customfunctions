package com.test.hibernate6customfunctions.dialect;

import com.test.hibernate6customfunctions.fuctions.CountItemsGreaterValFunction;
import com.test.hibernate6customfunctions.fuctions.SecondMaxSqmFunction;
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

        queryEngine.getSqmFunctionRegistry().register("secondMaxSalary", new SecondMaxSqmFunction("secondMaxSalary", queryEngine.getTypeConfiguration()));
        queryEngine.getSqmFunctionRegistry().register("countItemsGreaterVal", new CountItemsGreaterValFunction("countItemsGreaterVal", this,  queryEngine.getTypeConfiguration()));
    }
}