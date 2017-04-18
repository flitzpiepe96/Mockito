package de.dhbw.controller;

import java.util.List;

import de.dhbw.data.Customer;
import de.dhbw.data.Database;

public class Controller {

	private Database database;

	public List<Customer> getAllCustomers() {
		return database.findAllCustomers();
	}

	public Customer saveCustomer(Customer customer) {
		return database.saveCustomer(customer);
	}

	public Customer getRootCustomer(Customer customer) {
		Customer parent = customer.getParent();
		if (parent != null)
			return getRootCustomer(parent);
		return customer;
	}

	/**
	 * In a real web project this would probably be done with dependency
	 * injection
	 * 
	 * @param database
	 */
	public void setDatabase(Database database) {
		this.database = database;
	}

}
