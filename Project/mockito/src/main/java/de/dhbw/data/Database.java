package de.dhbw.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This would be a real database connection in a real project
 * 
 * @author marco.herglotz
 * 
 */
public class Database {

	/**
	 * This would return all customers saved in the database
	 * 
	 * @return all customers
	 */
	public List<Customer> findAllCustomers() {
		return new ArrayList<>();
	}

	/**
	 * This would save or update the given customer in the database
	 * 
	 * @param customer
	 *            customer to be saved/updated
	 */
	public Customer saveCustomer(Customer customer) {
		// do some stuff to save the customer
		Customer updatedCustomer = new Customer(customer.getFirstName(), customer.getLastName(), customer.getAge() + 1,
				null);

		// return the updated customer
		return updatedCustomer;
	}

}
