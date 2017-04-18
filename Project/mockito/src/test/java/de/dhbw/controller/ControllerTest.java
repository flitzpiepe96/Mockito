
package de.dhbw.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import de.dhbw.data.Customer;
import de.dhbw.data.Database;

public class ControllerTest {

	@Mock
	private Database mockDatabase;

	@Test
	public void testGettingAllCustomers1() throws Exception {
		mockDatabase = Mockito.mock(Database.class);
		List<Customer> customers = mockCustomers();
		Mockito.when(mockDatabase.findAllCustomers()).thenReturn(customers);

		Controller underTest = new Controller();
		underTest.setDatabase(mockDatabase);

		assertEquals(3, underTest.getAllCustomers().size());
	}

	@Test
	public void testGettingAllCustomers2() throws Exception {
		MockitoAnnotations.initMocks(this);
		List<Customer> customers = mockCustomers();
		Mockito.when(mockDatabase.findAllCustomers()).thenReturn(customers);

		Controller underTest = new Controller();
		underTest.setDatabase(mockDatabase);

		assertEquals(3, underTest.getAllCustomers().size());
	}

	@Test
	public void testRecursiveGetParent() throws Exception {
		List<Customer> customers = mockCustomers();
		Customer child = customers.get(2);

		Controller underTest = Mockito.mock(Controller.class);
		Mockito.when(underTest.getRootCustomer(Mockito.any())).thenCallRealMethod();

		Customer grandparent = underTest.getRootCustomer(child);

		assertEquals("Paul", grandparent.getFirstName());
		assertEquals("Schmidt", grandparent.getLastName());
		Mockito.verify(underTest).getRootCustomer(customers.get(0));
		Mockito.verify(underTest).getRootCustomer(customers.get(1));
		Mockito.verify(underTest).getRootCustomer(customers.get(2));

		Mockito.verify(underTest, Mockito.times(3)).getRootCustomer(Mockito.any());

		Mockito.verify(underTest, Mockito.never()).getRootCustomer(null);
	}

	private List<Customer> mockCustomers() {
		List<Customer> result = new ArrayList<>();
		Customer grandparent = new Customer("Paul", "Schmidt", 75, null);
		Customer parent = new Customer("Max", "Mustermann", 40, grandparent);
		Customer child = new Customer("Lisa", "Mueller", 25, parent);
		result.add(grandparent);
		result.add(parent);
		result.add(child);
		return result;
	}
}
