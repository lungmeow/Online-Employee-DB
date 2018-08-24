package sef.impl.service.test;

import java.sql.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sef.domain.Employee;
import sef.domain.EmployeeDetail;
import sef.domain.EmployeeProjectDetail;
import sef.domain.EmployeeSkill;
import sef.domain.Project;
import sef.domain.ProjectRole;
import sef.interfaces.service.EmployeeDetailsService;
import junit.framework.TestCase;

public class EmployeeDetailsServiceImplTest extends TestCase {

	private EmployeeDetailsService detailsService;

	/** 
	 * Set up database connection
	 */
	protected void setUp() throws Exception 
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:repository-config.xml");
		detailsService = (EmployeeDetailsService)context.getBean("detailsService");
	}

	protected void tearDown() throws Exception 
	{
		super.tearDown();
	}
	
	/** 
	 * Tests the scenario when the user find the complete employee details
	 * given the employee id.
	 */	
	public void testGetEmpoyeeDetails_MatchFound() 
	
	{
		EmployeeDetail empDetail = detailsService.getEmployeeDetails(4);

		//Check employee details
		Employee emp = empDetail.getEmployee();
		assertEquals(emp.getEnterpriseID(), "john.doe");
		assertEquals(emp.getFirstName(), "John");
		assertEquals(emp.getLastName(), "Doe");
		assertEquals(emp.getMiddleInitial(), "B");
		assertEquals(emp.getLevel(), "Team Lead");
		assertEquals(emp.getWorkForce(), "Solutions");
				
		//Check project details
		List<EmployeeProjectDetail> empProjDetailList = empDetail.getProjectList();
		assertEquals(1, empProjDetailList.size());		
		
		EmployeeProjectDetail empProjDetail = empProjDetailList.get(0);

		Project proj = empProjDetail.getProject();
		assertEquals(proj.getID(), 4);
		assertEquals(proj.getName(), "Dune");
		assertEquals(proj.getClient(), "ACME Telecoms");
		assertEquals(proj.getDescription(), "Systems Integration Project for the order provisioning and order management systems");	

		//Check project roles
		List<ProjectRole> projRoles = empProjDetail.getProjectRoles();
		assertEquals(1, projRoles.size());
		
		ProjectRole projRole = projRoles.get(0);
		assertEquals(projRole.getID(), 5);
		assertEquals(projRole.getRole(), "Team Lead for the SOS team");
		assertEquals(projRole.getStartDate(), Date.valueOf("2007-04-15"));
		assertEquals(projRole.getEndDate(), Date.valueOf("2008-09-03"));
		
		//Check skills
		List<EmployeeSkill> empSkillList = empDetail.getSkillList(); 
		assertEquals(1, empSkillList.size());
		
		EmployeeSkill empSkill = empSkillList.get(0);
		assertEquals(empSkill.getID(), 1);
		assertEquals(empSkill.getName(), "J2SE");
		assertEquals(empSkill.getRating(), 2);
		assertEquals(empSkill.getDescription(), "Java programming using the standard edition");	
		
	}
	
	/** 
	 * Tests the scenario when the user did not find the employee details
	 * given the employee id.
	 */		
    public void testGetEmpoyeeDetails_NoMatchFound() 
	
	{
		EmployeeDetail empDetail = detailsService.getEmployeeDetails(100);
		
		//Check employee details
		Employee emp = empDetail.getEmployee();
		assertEquals(emp.getID(), 0);		
		assertEquals(emp.getEnterpriseID(), null);
		assertEquals(emp.getFirstName(), null);
		assertEquals(emp.getLastName(), null);
		assertEquals(emp.getMiddleInitial(), null);
		assertEquals(emp.getLevel(), null);
		assertEquals(emp.getWorkForce(), null);		

		//Check project details
		List<EmployeeProjectDetail> empProjDetailList = empDetail.getProjectList();
		assertEquals(0, empProjDetailList.size());		
		
		//Check skills
		List<EmployeeSkill> empSkillList = empDetail.getSkillList(); 
		assertEquals(0, empSkillList.size());
		
	}
    
}
