package com.test.hibernate6customfunctions.fuctions;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.CastFunction;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.query.sqm.function.AbstractSqmSelfRenderingFunctionDescriptor;
import org.hibernate.query.sqm.function.FunctionKind;
import org.hibernate.query.sqm.produce.function.*;
import org.hibernate.sql.ast.Clause;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.expression.CastTarget;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.type.BasicType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.spi.TypeConfiguration;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hibernate.query.sqm.produce.function.FunctionParameterType.NUMERIC;

public class CountItemsGreaterValFunction extends AbstractSqmSelfRenderingFunctionDescriptor {
    private final CastFunction castFunction;
    private final BasicType<BigDecimal> bigDecimalType;

    public CountItemsGreaterValFunction(String name, Dialect dialect, TypeConfiguration typeConfiguration) {
        super(name, FunctionKind.AGGREGATE, new ArgumentTypesValidator(StandardArgumentsValidators.exactly(2), FunctionParameterType.NUMERIC, FunctionParameterType.NUMERIC), StandardFunctionReturnTypeResolvers.invariant(typeConfiguration.getBasicTypeRegistry().resolve(StandardBasicTypes.DOUBLE)), StandardFunctionArgumentTypeResolvers.invariant(typeConfiguration, NUMERIC, NUMERIC));
        bigDecimalType = typeConfiguration.getBasicTypeRegistry().resolve(StandardBasicTypes.BIG_DECIMAL);
        castFunction = new CastFunction(dialect, dialect.getPreferredSqlTypeCodeForBoolean());
    }

    @Override
    public void render(SqlAppender sqlAppender, List<? extends SqlAstNode> sqlAstArguments, SqlAstTranslator<?> walker) {
        render(sqlAppender, sqlAstArguments, null, walker);
    }

    @Override
    public void render(SqlAppender sqlAppender, List<? extends SqlAstNode> sqlAstArguments, Predicate filter, SqlAstTranslator<?> translator) {
        final boolean caseWrapper = filter != null && !translator.supportsFilterClause();

        sqlAppender.appendSql(getName());
        sqlAppender.appendSql('(');

        final Expression first_arg = (Expression) sqlAstArguments.get(0);
        final Expression second_arg = (Expression) sqlAstArguments.get(1);

        if (caseWrapper) {
            translator.getCurrentClauseStack().push(Clause.WHERE);
            sqlAppender.appendSql("case when ");
            filter.accept(translator);
            translator.getCurrentClauseStack().pop();
            sqlAppender.appendSql(" then ");
            renderArgument(sqlAppender, translator, first_arg);
            sqlAppender.appendSql(" else null end)");
        } else {
            renderArgument(sqlAppender, translator, first_arg);
            sqlAppender.appendSql(", ");
            renderArgument(sqlAppender, translator, second_arg);
            sqlAppender.appendSql(')');
            if (filter != null) {
                translator.getCurrentClauseStack().push(Clause.WHERE);
                sqlAppender.appendSql(" filter (where ");
                filter.accept(translator);
                sqlAppender.appendSql(')');
                translator.getCurrentClauseStack().pop();
            }
        }
    }

    private void renderArgument(SqlAppender sqlAppender, SqlAstTranslator<?> translator, Expression arg) {
        final JdbcMapping sourceMapping = arg.getExpressionType().getJdbcMappings().get(0);
        if (sourceMapping.getJdbcType().isNumber()) {
            castFunction.render(sqlAppender, Arrays.asList(arg, new CastTarget(bigDecimalType)), translator);
        } else {
            arg.accept(translator);
        }
    }
}
