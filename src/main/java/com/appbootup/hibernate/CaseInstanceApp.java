package com.appbootup.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

public class CaseInstanceApp {
	static SessionFactory sessionFactory = HibernateUtil.configureSessionFactory();
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

		criteria.setProjection(
				Projections.projectionList()
						.add(Projections.groupProperty("cseSeverity"))
						.add(Projections.groupProperty("cseCreatedYear"))
						.add(Projections.count("cseCost"))
						.add(Projections.sum("cseCost"))).list();
		List results = criteria.list();
		Object[] array = results.toArray();
		for (Object object : array) {
			Object[] data = (Object[]) object;
			System.out.print("cseCreatedYear: " + data[1]);
			System.out.print(" , cseSeverity: " +  data[0]);
			System.out.println(" , sum of cseCost: " + data[3] );
		}

		// String sql = "SELECT * FROM CaseInstance WHERE cseId = :cse_id";
		// SQLQuery query = session.createSQLQuery(sql);
		// query.addEntity(CaseInstance.class);
		// query.setParameter("cse_id", 2);
		// List sqlResults = query.list();
		// for (Iterator iterator = sqlResults.iterator(); iterator.hasNext();) {
		// CaseInstance caseInstance = (CaseInstance) iterator.next();
		// System.out.print("cse_id: " + caseInstance.getCseId()
		// + " - cse_severity: " + caseInstance.getCseSeverity());
		// }

		String sqlFinal = "SELECT sum(cseCost), count(cseCost), cseCreatedYear, cseSeverity FROM CaseInstance GROUP BY cseCreatedYear, cseSeverity ";
		SQLQuery queryFinal = session.createSQLQuery(sqlFinal);
		List<Object[]> sqlFinalResults = queryFinal.list();
		for (Object object : sqlFinalResults) {
			Object[] data = (Object[]) object;
			System.out.print("cseCreatedYear: " + data[2]);
			System.out.print(" , cseSeverity: " + data[3]);
			System.out.println(" , sum of cseCost: " + data[0]);
		}

		String sqlFinalAgain = "SELECT sum(cseCost), count(cseCost), cseCreatedYear, cseSeverity, count(*) FROM CaseInstance GROUP BY cseSeverity, cseCreatedYear ORDER BY cseCreatedYear ";
		SQLQuery queryFinalAgain = session.createSQLQuery(sqlFinalAgain);
		List<Object[]> sqlFinalResultsAgain = queryFinalAgain.list();
		for (Object object : sqlFinalResultsAgain) {
			Object[] data = (Object[]) object;
			System.out.print("cseCreatedYear: " + data[2]);
			System.out.print(" , cseSeverity: " + data[3]);
			System.out.print(" , sum of cseCost: " + data[0]);
			System.out.println(" , count of cseCost: " + data[1]);
		}

		// List<CaseInstance> caseInstances = createCriteria.list();

		// for (CaseInstance caseInstance : caseInstances) {
		// System.out.println("Case with id " + caseInstance.getCseId()
		// + " and status " + caseInstance.getCseSeverity()
		// + " and cost " + caseInstance.getCseCost());
		// }
		// We can now close the transaction and persist the changes
		tx.commit();
	}
	
	public static int hqlTruncate(String truncateTable){
	    String hql = String.format("delete from %s",truncateTable);
	    Query query = session.createQuery(hql);
	    return query.executeUpdate();
	}
}