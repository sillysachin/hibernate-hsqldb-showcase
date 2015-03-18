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
public class UserApp {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		SessionFactory sessionFactory = HibernateUtil.configureSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		// We must open a new transaction before doing anything with the DB
		Transaction tx = session.beginTransaction();

		User john = new User("John");
		john.setAge(25);
		User mike = new User("Mike");

		session.persist(john);
		session.persist(mike);

		Product screen = new Product("Screen");
		screen.setDescription("1080p HD Screen");
		Product keyboard = new Product("Keyboard");

		session.persist(screen);
		session.persist(keyboard);

		Sale johnBoughtScreen = new Sale(john, screen, new Date());
		Sale johnBoughtKeyboard = new Sale(john, keyboard, new Date());
		Sale mikeBoughtKeyboard = new Sale(mike, keyboard, new Date());

		session.persist(johnBoughtScreen);
		session.persist(johnBoughtKeyboard);
		session.persist(mikeBoughtKeyboard);

		List<User> users = session.createCriteria(User.class).list();
		for (User user : users) {
			System.out.println("User with id " + user.getId() + " and name "
					+ user.getName());
		}

		List<Product> products = session.createCriteria(Product.class).list();
		for (Product product : products) {
			System.out.println("Product with id " + product.getId()
					+ " and name " + product.getName());
		}

		List<Sale> sales = session.createCriteria(Sale.class).list();
		for (Sale sale : sales) {
			System.out.println("Sale with id " + sale.getId() + " user "
					+ sale.getUser().getName() + "(" + sale.getUser().getId()
					+ ")" + " product " + sale.getProduct().getName() + "("
					+ sale.getProduct().getId() + ")");
		}

		// We can now close the transaction and persist the changes
		tx.commit();
	}

}