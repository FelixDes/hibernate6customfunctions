package com.example.hibernate6teststand.fuctions;

import com.example.hibernate6teststand.entities.Employee;
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

public class SecondMaxSqmFunction extends AbstractSqmSelfRenderingFunctionDescriptor {
    private TypeConfiguration typeConfiguration;

    public SecondMaxSqmFunction(
            String name,
            TypeConfiguration typeConfiguration
    ) {
        super(
                name,
                new ArgumentTypesValidator(StandardArgumentsValidators.exactly(0)),
                StandardFunctionReturnTypeResolvers.invariant(typeConfiguration.getBasicTypeRegistry().resolve(StandardBasicTypes.BIG_INTEGER)),
                null
        );
        this.typeConfiguration = typeConfiguration;
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
//        String e = typeConfiguration.getJavaTypeRegistry().resolveEntityTypeDescriptor(Employee.class).getRecommendedJdbcType(typeConfiguration.getCurrentBaseSqlTypeIndicators()).getFriendlyName();

        sqlAppender.appendSql("max(salary) from Employee where salary <> (select max(salary) from Employee)");
    }
}
