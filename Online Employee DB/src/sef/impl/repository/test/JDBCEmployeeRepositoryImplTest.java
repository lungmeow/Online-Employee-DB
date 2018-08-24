package sef.impl.repository.test;

import junit.framework.TestCase;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sef.domain.Employee;
import sef.interfaces.repository.EmployeeRepository;

public class JDBCEmployeeRepositoryImplTest extends TestCase {
	
	private EmployeeRepository empRepository;
	

	/** 
	 * Set up database connection
	 */	
	protected void setUp() throws Exception {
		
		super.setUp();
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:repository-config.xml");	
		empRepository = (EmployeeRepository)context.getBean("employeeRep");
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}


	/** 
	 * Tests the scenario when the user finds a list of employees 
	 * containing the first and last names specified.
	 */	
	public void testFindEmployeesByName_MatchFound() {

		List<Employee> empList = empRepository.findEmployeesByName("J", "Doe");

		assertEquals(2, empList.size());
		
		assertEquals(empList.get(0).getID(), 2);
		assertEquals(empList.get(0).getEnterpriseID(), "jane.doe");
		assertEquals(empList.get(0).getFirstName(), "Jane");
		assertEquals(empList.get(0).getLastName(), "Doe");
		assertEquals(empList.get(0).getMiddleInitial(), "V");
		assertEquals(empList.get(0).getLevel(), "Senior Software Engineer");
		assertEquals(empList.get(0).getWorkForce(), "Solutions");
				
		assertEquals(empList.get(1).getID(), 4);
		assertEquals(empList.get(1).getEnterpriseID(), "john.doe");
		assertEquals(empList.get(1).getFirstName(), "John");
		assertEquals(empList.get(1).getLastName(), "Doe");
		assertEquals(empList.get(1).getMiddleInitial(), "B");
		assertEquals(empList.get(1).getLevel(), "Team Lead");
		assertEquals(empList.get(1).getWorkForce(), "Solutions");
		
	}

	/** 
	 * Tests the scenario when the user could not find an employee 
	 * with the specified first and last names.
	 */
	public void testFindEmployeesByName_NoMatchFound() {

		List<Employee> empList = empRepository.findEmployeesByName("Mike", "Gil");
		
		assertEquals(0, empList.size());
		
	}
	
	/** 
	 * Tests the scenario when the first and last names entered are null.
	 */
	public void testFindEmployeesByName_LastNameOrFirstNameIsNull() {

		List<Employee> empList = empRepository.findEmployeesByName("J", null);
		assertEquals(0, empList.size());
		
		empList = empRepository.findEmployeesByName(null, "Doe");
		assertEquals(0, empList.size());

		empList = empRepository.findEmployeesByName(null, null);
		assertEquals(0, empList.size());
	}

	/** 
	 * Tests the scenario when the user finds the employee 
	 * given the employee id.
	 */		
	public void testFindEmployeeByID_MatchFound() {
		
		Employee emp = empRepository.findEmployeeByID(1);
		
		assertEquals(emp.getID(), 1);		
		assertEquals(emp.getEnterpriseID(), "eugene.p.lozada");
		assertEquals(emp.getFirstName(), "Eugene");
		assertEquals(emp.getLastName(), "Lozada");
		assertEquals(emp.getMiddleInitial(), "P");
		assertEquals(emp.getLevel(), "Associate Manager");
		assertEquals(emp.getWorkForce(), "Solutions");
		
	}

	/** 
	 * Tests the scenario when the user did not find the employee 
	 * given the employee id.
	 */	
	public void testFindEmployeeByID_NoMatchFound() {
		
		Employee emp = empRepository.findEmployeeByID(3);
		
		assertEquals(emp.getID(), 0);		
		assertEquals(emp.getEnterpriseID(), null);
		assertEquals(emp.getFirstName(), null);
		assertEquals(emp.getLastName(), null);
		assertEquals(emp.getMiddleInitial(), null);
		assertEquals(emp.getLevel(), null);
		assertEquals(emp.getWorkForce(), null);
	}

	/** 
	 * Tests the scenario when the user finds a list of employees 
	 * given the project id.
	 */		
	public void testFindEmployeesByProject_MatchFound() {
		
		List<Employee> empList = empRepository.findEmployeesByProject(4);
		
		assertEquals(2, empList.size());
		
		assertEquals(empList.get(0).getID(), 1);		
		assertEquals(empList.get(0).getEnterpriseID(), "eugene.p.lozada");
		assertEquals(empList.get(0).getFirstName(), "Eugene");
		assertEquals(empList.get(0).getLastName(), "Lozada");
		assertEquals(empList.get(0).getMiddleInitial(), "P");
		assertEquals(empList.get(0).getLevel(), "Associate Manager");
		assertEquals(empList.get(0).getWorkForce(), "Solutions");
				
		assertEquals(empList.get(1).getID(), 4);
		assertEquals(empList.get(1).getEnterpriseID(), "john.doe");
		assertEquals(empList.get(1).getFirstName(), "John");
		assertEquals(empList.get(1).getLastName(), "Doe");
		assertEquals(empList.get(1).getMiddleInitial(), "B");
		assertEquals(empList.get(1).getLevel(), "Team Lead");
		assertEquals(empList.get(1).getWorkForce(), "Solutions");
		
	}

	/** 
	 * Tests the scenario when the user did not find an employee 
	 * given the project id.
	 */	
	public void testFindEmployeesByProject_NoMatchFound() {
		
		List<Employee> empList = empRepository.findEmployeesByProject(2);
		assertEquals(0, empList.size());
	
	}
}
