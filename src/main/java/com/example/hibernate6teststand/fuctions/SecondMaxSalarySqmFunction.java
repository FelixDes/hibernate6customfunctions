package com.example.hibernate6teststand.fuctions;

import org.hibernate.query.sqm.function.AbstractSqmSelfRenderingFunctionDescriptor;
import org.hibernate.query.sqm.produce.function.ArgumentTypesValidator;
import org.hibernate.query.sqm.produce.function.StandardArgumentsValidators;
import org.hibernate.query.sqm.produce.function.StandardFunctionReturnTypeResolvers;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.sql.ast.tree.select.SortSpecification;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.spi.TypeConfiguration;

import java.util.Collections;
import java.util.List;

public class SecondMaxSalarySqmFunction extends AbstractSqmSelfRenderingFunctionDescriptor {
    public SecondMaxSalarySqmFunction(
            TypeConfiguration typeConfiguration
    ) {
        super(
                "secondmaxsalary",
                new ArgumentTypesValidator(StandardArgumentsValidators.exactly(0)),
                StandardFunctionReturnTypeResolvers.invariant(typeConfiguration.getBasicTypeRegistry().resolve(StandardBasicTypes.INTEGER)),
                null
        );
    }

    @Override
    public void render(
            SqlAppender sqlAppender,
            List<? extends SqlAstNode> sqlAstArguments,
            SqlAstTranslator<?> walker) {
        render(sqlAppender, sqlAstArguments, null, Collections.emptyList(), walker);
    }

    @Override
    public void render(
            SqlAppender sqlAppender,
            List<? extends SqlAstNode> sqlAstArguments,
            Predicate filter,
            SqlAstTranslator<?> walker) {
        render(sqlAppender, sqlAstArguments, filter, Collections.emptyList(), walker);
    }

    @Override
    public void render(
            SqlAppender sqlAppender,
            List<? extends SqlAstNode> sqlAstArguments,
            Predicate filter,
            List<SortSpecification> withinGroup,
            SqlAstTranslator<?> translator) {
        sqlAppender.appendSql(1233);
//        sqlAppender.appendSql('(');
//        select max(salary) from Employee where salary <> (select max(salary) from Employee)
    }
}
