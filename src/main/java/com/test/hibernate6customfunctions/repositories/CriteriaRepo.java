package com.test.hibernate6customfunctions.repositories;

import com.test.hibernate6customfunctions.entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CriteriaRepo {
    EntityManager entityManager;
    HibernateCriteriaBuilder criteriaBuilder;

    @Autowired
    CriteriaRepo(EntityManagerFactory entityManagerFactory,
                 EntityManager entityManager) {
        this.entityManager = entityManager;
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        this.criteriaBuilder = sessionFactory.getCriteriaBuilder();
    }

    public Double getSecondMaxSalary() {
        CriteriaQuery<Double> query = criteriaBuilder.createQuery(Double.class);

        query.select(criteriaBuilder.function(
                "secondMaxSalary",
                Double.class,
                null));
        TypedQuery<Double> typedQuery = entityManager.createQuery(query);

        return typedQuery.getSingleResult();
    }

    public Long employeesWithSalaryGreater(double edge) {
        CriteriaQuery<Double> query = criteriaBuilder.createQuery(Double.class);
        Root<Employee> employee = query.from(Employee.class);

        Expression<Double> salaryExp = employee.get("salary");
        Expression<Double> edgeExp = criteriaBuilder.parameter(Double.class, "edge");

        query.select(criteriaBuilder.functionAggregate(
                "countItemsGreaterVal",
                Long.class,
                (JpaPredicate) null,
                salaryExp,
                edgeExp).asDouble());

        TypedQuery<Double> typedQuery = entityManager.createQuery(query);
        typedQuery.setParameter("edge", edge);

        return typedQuery.getSingleResult().longValue();
    }

    public Long countEmployeeSalaryBetween(double bottomEdge, double topEdge) {
        CriteriaQuery<Double> query = criteriaBuilder.createQuery(Double.class);
        Root<Employee> employee = query.from(Employee.class);

        Expression<Double> salaryExp = employee.get("salary");
        Expression<Double> bottomEdgeExp = criteriaBuilder.parameter(Double.class, "bottomEdge");
        Expression<Double> topEdgeExp = criteriaBuilder.parameter(Double.class, "topEdge");

        var filter = criteriaBuilder.lessThan(salaryExp, topEdgeExp);

        query.select(criteriaBuilder.functionAggregate(
                "countItemsGreaterVal",
                Long.class,
                filter,
                salaryExp,
                bottomEdgeExp).asDouble());

        TypedQuery<Double> typedQuery = entityManager.createQuery(query);
        typedQuery.setParameter("bottomEdge", bottomEdge);
        typedQuery.setParameter("topEdge", topEdge);

        return typedQuery.getSingleResult().longValue();
    }
}
