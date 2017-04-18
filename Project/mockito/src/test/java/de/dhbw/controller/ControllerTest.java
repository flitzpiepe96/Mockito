
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
		// Create the mocked object...
		mockDatabase = Mockito.mock(Database.class);
		List<Customer> customers = mockCustomers();
		// ... and tell it what do do when findAllCustomers is called
		Mockito.when(mockDatabase.findAllCustomers()).thenReturn(customers);

		Controller underTest = new Controller();
		underTest.setDatabase(mockDatabase);

		// There need to be 3 customers
		assertEquals(3, underTest.getAllCustomers().size());
	}

	@Test
	public void testGettingAllCustomers2() throws Exception {
		// Automatically creates all objects annotated with @Mock and @Spy. If
		// the object is annotated with @InjectMocks, it also injects all
		// available mocks (needed for dependency injection for java beans,
		// unless you want to use setters like in this case for the database)
		MockitoAnnotations.initMocks(this);
		List<Customer> customers = mockCustomers();
		Mockito.when(mockDatabase.findAllCustomers()).thenReturn(customers);

		Controller underTest = new Controller();
		underTest.setDatabase(mockDatabase);

		assertEquals(3, underTest.getAllCustomers().size());
	}

	@Test
	public void testRecursiveGetParent1() throws Exception {
		List<Customer> customers = mockCustomers();
		Customer child = customers.get(2);

		// Mock the controller and tell him to use the method of the real object
		// when it is invoked
		Controller underTest = Mockito.mock(Controller.class);
		Mockito.when(underTest.getRootCustomer(Mockito.any())).thenCallRealMethod();

		Customer grandparent = underTest.getRootCustomer(child);

		// Check that we got the right customer
		assertEquals("Paul", grandparent.getFirstName());
		assertEquals("Schmidt", grandparent.getLastName());

		// Assert that the method was called once for every customer
		Mockito.verify(underTest).getRootCustomer(customers.get(0));
		Mockito.verify(underTest).getRootCustomer(customers.get(1));
		Mockito.verify(underTest).getRootCustomer(customers.get(2));

		// Assert that the method was called 3 times with any parameter
		Mockito.verify(underTest, Mockito.times(3)).getRootCustomer(Mockito.any());

		// Assert that the method has never been called with null parameter
		Mockito.verify(underTest, Mockito.never()).getRootCustomer(null);
	}

	@Test
	public void testRecursiveGetParent2() throws Exception {
		List<Customer> customers = mockCustomers();
		Customer child = customers.get(2);

		// Mocks return default values (null, empty Lists, ...) for every method
		// that has not explicitly been told how to behave
		// Spies always call the method of the real object, unless it is told to
		// do something else
		Controller underTest = Mockito.spy(Controller.class);

		Customer grandparent = underTest.getRootCustomer(child);

		assertEquals("Paul", grandparent.getFirstName());
		assertEquals("Schmidt", grandparent.getLastName());
		Mockito.verify(underTest).getRootCustomer(customers.get(0));
		Mockito.verify(underTest).getRootCustomer(customers.get(1));
		Mockito.verify(underTest).getRootCustomer(customers.get(2));

		Mockito.verify(underTest, Mockito.times(3)).getRootCustomer(Mockito.any());

		Mockito.verify(underTest, Mockito.never()).getRootCustomer(null);
	}

	/**
	 * Create some customers
	 * 
	 * @return List with 3 customers
	 */
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
