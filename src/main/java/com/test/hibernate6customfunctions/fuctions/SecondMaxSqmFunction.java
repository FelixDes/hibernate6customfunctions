package com.test.hibernate6customfunctions.fuctions;

import org.hibernate.query.sqm.function.AbstractSqmSelfRenderingFunctionDescriptor;
import org.hibernate.query.sqm.function.FunctionKind;
import org.hibernate.query.sqm.produce.function.StandardArgumentsValidators;
import org.hibernate.query.sqm.produce.function.StandardFunctionReturnTypeResolvers;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.spi.TypeConfiguration;

import java.util.List;

public class SecondMaxSqmFunction extends AbstractSqmSelfRenderingFunctionDescriptor {

    public SecondMaxSqmFunction(String name, TypeConfiguration typeConfiguration) {
        super(
                name,
                FunctionKind.NORMAL,
                // Setting argument validation. In this case we have no args
                StandardArgumentsValidators.NO_ARGS,
                // Setting return type: numeric (mapped for Double)
                StandardFunctionReturnTypeResolvers.invariant(typeConfiguration.getBasicTypeRegistry()
                        .resolve(StandardBasicTypes.DOUBLE)),
                null
        );
    }

    @Override
    public void render(SqlAppender sqlAppender, List<? extends SqlAstNode> sqlAstArguments, SqlAstTranslator<?> walker) {
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
    }
}
