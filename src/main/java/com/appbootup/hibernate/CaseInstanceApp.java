package com.appbootup.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Hello world!
 * 
 */
public class CaseInstanceApp {
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static void main(String[] args) {
		SessionFactory sessionFactory = HibernateUtil.configureSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		// We must open a new transaction before doing anything with the DB
		Transaction tx = session.beginTransaction();

		CaseInstance case1 = new CaseInstance();
		case1.setCseStatus("open");
		case1.setCseSeverity("high");
		case1.setCseCreatedDttm(new Date(2015, 1, 1));
		session.persist(case1);
		List<CaseInstance> caseInstances = session.createCriteria(
				CaseInstance.class).list();
		for (CaseInstance caseInstance : caseInstances) {
			System.out.println("Case with id " + caseInstance.getCseId()
					+ " and status " + caseInstance.getCseStatus());
		}
		// We can now close the transaction and persist the changes
		tx.commit();
	}

}