package com.appbootup.hibernate;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.PropertyProjection;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

public class CaseInstanceDataAPI {
	static SessionFactory sessionFactory = HibernateUtil
			.configureSessionFactory();
	static Session session = sessionFactory.getCurrentSession();

	@SuppressWarnings({ "unchecked", "deprecation" })
	public static void main(String[] args) {

		// We must open a new transaction before doing anything with the DB
		Transaction tx = session.beginTransaction();
		hqlTruncate("CaseInstance");

		session.persist(new CaseInstance("open", "high", new Date(2015, 1, 1),
				2015, 10));
		session.persist(new CaseInstance("closed", "high",
				new Date(2015, 1, 15), 2015, 5));
		session.persist(new CaseInstance("open", "high", new Date(2015, 2, 1),
				2015, 2));
		session.persist(new CaseInstance("open", "high", new Date(2015, 2, 15),
				2015, 10));
		session.persist(new CaseInstance("open", "medium",
				new Date(2015, 3, 1), 2015, 3));
		session.persist(new CaseInstance("open", "low", new Date(2015, 3, 15),
				2015, 5));
		session.persist(new CaseInstance("closed", "high",
				new Date(2015, 3, 15), 2015, 10));
		session.persist(new CaseInstance("new", "high", new Date(2016, 1, 1),
				2016, 15));
		session.persist(new CaseInstance("new", "medium", new Date(2016, 2, 1),
				2016, 20));
		session.persist(new CaseInstance("new", "high", new Date(2016, 3, 1),
				2016, 25));

		Criteria criteria = session.createCriteria(CaseInstance.class);

		ProjectionList projectionList = Projections.projectionList()
				.add(addSelection("cseCreatedYear"))
				.add(addSelection("cseSeverity"))
				.add(addAggregate("count", "cseCost"))
				.add(addAggregate("sum", "cseCost"));

		criteria.setProjection(projectionList).list();

		criteria.add(addWhere("cseSeverity", "%i%"));

		criteria.addOrder(Order.asc("cseCreatedYear"));

		Object[] results = criteria.list().toArray();
		for (Object result : results) {
			Object[] data = (Object[]) result;
			System.out.print(data[0]);
			System.out.print(" , " + data[1]);
			System.out.println(" , " + data[3]);
		}
		tx.commit();
	}

	static PropertyProjection addSelection(String colName) {
		PropertyProjection groupProperty = Projections.groupProperty(colName);
		return groupProperty;
	}

	static Criterion addWhere(String colName, String param) {
		SimpleExpression expression = Restrictions.like(colName, param);
		return expression;
	}

	static Projection addAggregate(String aggregate, String colName) {
		Projection projection = null;
		if ("min".equalsIgnoreCase(aggregate)) {
			projection = Projections.min(colName);
		} else if ("max".equalsIgnoreCase(aggregate)) {
			projection = Projections.max(colName);
		} else if ("sum".equalsIgnoreCase(aggregate)) {
			projection = Projections.sum(colName);
		} else if ("avg".equalsIgnoreCase(aggregate)) {
			projection = Projections.avg(colName);
		} else if ("count".equalsIgnoreCase(aggregate)) {
			projection = Projections.count(colName);
		} else if ("count_distinct".equalsIgnoreCase(aggregate)) {
			projection = Projections.countDistinct(colName);
		} else if ("count_row".equalsIgnoreCase(aggregate)) {
			projection = Projections.rowCount();
		}
		return projection;
	}

	public static int hqlTruncate(String truncateTable) {
		String hql = String.format("delete from %s", truncateTable);
		Query query = session.createQuery(hql);
		return query.executeUpdate();
	}
}