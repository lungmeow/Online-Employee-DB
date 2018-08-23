package sef.impl.service.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sef.domain.Employee;
import sef.domain.Project;
import sef.interfaces.service.SearchService;
import junit.framework.TestCase;

public class SearchServiceImplTest extends TestCase {

	private SearchService searchService;

	/** 
	 * Set up database connection
	 */	
	protected void setUp() throws Exception 
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:repository-config.xml");
		searchService = (SearchService)context.getBean("searchService");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/** 
	 * Tests the scenario when the user finds a list of employees 
	 * containing the first and last names specified.
	 */	
	public final void testFindEmployeesByName_MatchFound() 
	{
		List<Employee> empList = searchService.findEmployeesByName("Eugene","Lozada");
		
		assertEquals(1, empList.size());
		
		assertEquals(empList.get(0).getFirstName(),"Eugene");
		assertEquals(empList.get(0).getLastName(),"Lozada");
		assertEquals(empList.get(0).getEnterpriseID(),"eugene.p.lozada");
		assertEquals(empList.get(0).getLevel(),"Associate Manager");
		assertEquals(empList.get(0).getWorkForce(),"Solutions");
	}

	/** 
	 * Tests the scenario when the user could not find an employee 
	 * with the specified first and last names.
	 */
	public final void testFindEmployeesByName_NoMatchFound() 
	{
		List<Employee> empList = searchService.findEmployeesByName("Ice","Tomas");
		assertEquals(0, empList.size());		
	}

	/** 
	 * Tests the scenario when the first and last names entered are null.
	 */	
	public final void testFindEmployeesByName_LastNameOrFirstNameIsNull() 
	{
		List<Employee> empList = searchService.findEmployeesByName("Eugene", null);
		assertEquals(0, empList.size());		
		
		empList = searchService.findEmployeesByName(null, "Lozada");
		assertEquals(0, empList.size());		

		empList = searchService.findEmployeesByName(null, null);
		assertEquals(0, empList.size());		
		
	}

	/** 
	 * Tests the scenario when the user finds a list of employees 
	 * given the project id.
	 */	
	public final void testFindEmployeesByProject_MatchFound() 
	{
		List<Employee> empList = searchService.findEmployeesByProject(1);
		
		assertEquals(empList.get(0).getFirstName(),"Eugene");
		assertEquals(empList.get(0).getLastName(),"Lozada");
		assertEquals(empList.get(0).getEnterpriseID(),"eugene.p.lozada");
		assertEquals(empList.get(0).getLevel(),"Associate Manager");
		assertEquals(empList.get(0).getWorkForce(),"Solutions");
	}

	/** 
	 * Tests the scenario when the user did not find an employee 
	 * given the project id.
	 */
	public final void testFindEmployeesByProject_NoMatchFound() 
	{
		List<Employee> empList = searchService.findEmployeesByProject(100);
		assertEquals(0, empList.size());		
		
	}

	/** 
	 * Tests if the listAllProjects method returns the correct project details. 
	 */		
	public final void testListAllProjects() 
	{
		List<Project> projList = searchService.listAllProjects();
		
		assertEquals(3, projList.size());

		proj1(projList.get(0));
		proj3(projList.get(1));
		proj4(projList.get(2));
	}

	
	/** 
	 * Checks the project details of project id equal to 1 
	 */		
	public void proj1(Project proj)
	{
		assertEquals(proj.getID(), 1);
		assertEquals(proj.getName(), "Phase Line Banana");
		assertEquals(proj.getClient(), "Accenture");
		assertEquals(proj.getDescription(), "Development of a boot-camp training course for entry level Java developers");	
	}
	
	/** 
	 * Checks the project details of project id equal to 3 
	 */		
	public void proj3(Project proj)
	{
		assertEquals(proj.getID(), 3);
		assertEquals(proj.getName(), "Project Kimura");
		assertEquals(proj.getClient(), "ACME  Solutions");
		assertEquals(proj.getDescription(), "Port the front-end of a legacy mainframe application from green-screens to Java Swing");
	}
	
	/** 
	 * Checks the project details of project id equal to 4 
	 */		
	public void proj4(Project proj)
	{
		assertEquals(proj.getID(), 4);
		assertEquals(proj.getName(), "Dune");
		assertEquals(proj.getClient(), "ACME Telecoms");
		assertEquals(proj.getDescription(), "Systems Integration Project for the order provisioning and order management systems");	
	}
	
}
