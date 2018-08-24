package sef.impl.repository.test;

import java.sql.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sef.domain.EmployeeProjectDetail;
import sef.domain.Project;
import sef.domain.ProjectRole;
import sef.interfaces.repository.ProjectRepository;
import junit.framework.TestCase;

public class JDBCProjectRepositoryImplTest extends TestCase {

	ProjectRepository projRepository;
	
	/** 
	 * Set up database connection
	 */
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:repository-config.xml");	
		projRepository = (ProjectRepository)context.getBean("projectRep");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
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
	
	/** 
	 * Tests if the listAllProjects method returns the correct project details. 
	 */		
	public void testListAllProjects() {
		
		List<Project> projList = projRepository.listAllProjects();
		
		assertEquals(3, projList.size());

		proj1(projList.get(0));
		proj3(projList.get(1));
		proj4(projList.get(2));
			
	}

	/** 
	 * Tests the scenario when the user finds the project history
	 * given the employee id.
	 */	
	public void testGetEmployeeProjectHistory_MatchFound() {
	
		List<EmployeeProjectDetail> empProjList = projRepository.getEmployeeProjectHistory(1);

		assertEquals(3, empProjList.size());
		
		proj1(empProjList.get(0).getProject());
			
		List<ProjectRole> projRole = empProjList.get(0).getProjectRoles();
		
		assertEquals(1, projRole.size());
		
		assertEquals(projRole.get(0).getID(), 2);
		assertEquals(projRole.get(0).getRole(), "Course Designer");
		assertEquals(projRole.get(0).getStartDate(), Date.valueOf("2004-08-16"));
		assertEquals(projRole.get(0).getEndDate(), Date.valueOf("2005-08-16"));
	
		proj3(empProjList.get(1).getProject());
	
		projRole = empProjList.get(1).getProjectRoles();
		
		assertEquals(1, projRole.size());
		
		assertEquals(projRole.get(0).getID(), 3);
		assertEquals(projRole.get(0).getRole(), "Team Lead for the development team");
		assertEquals(projRole.get(0).getStartDate(), Date.valueOf("2006-01-09"));
		assertEquals(projRole.get(0).getEndDate(), Date.valueOf("2007-02-13"));
		
		proj4(empProjList.get(2).getProject());

		projRole = empProjList.get(2).getProjectRoles();
		
		assertEquals(2, projRole.size());
		
		assertEquals(projRole.get(0).getID(), 4);
		assertEquals(projRole.get(0).getRole(), "Application architect for the SOS team");
		assertEquals(projRole.get(0).getStartDate(), Date.valueOf("2007-03-14"));
		assertEquals(projRole.get(0).getEndDate(), Date.valueOf("2008-09-01"));
		
		assertEquals(projRole.get(1).getID(), 6);
		assertEquals(projRole.get(1).getRole(), "SME for SOS");
		assertEquals(projRole.get(1).getStartDate(), Date.valueOf("2007-03-14"));
		assertEquals(projRole.get(1).getEndDate(), Date.valueOf("2008-09-01"));
		
	}
	
	/** 
	 * Tests the scenario when the user did not find project history
	 * given the employee id.
	 */		
	public void testGetEmployeeProjectHistory_NoMatchFound() {
	
		List<EmployeeProjectDetail> empProjList = projRepository.getEmployeeProjectHistory(3);

		assertEquals(0, empProjList.size());

	}

	/** 
	 * Tests the scenario when the user finds a list of project roles 
	 * given the employee id and project id.
	 */	
	public void testGetEmployeeProjectRoles_MatchFound() {
	
		List<ProjectRole> projRole = projRepository.getEmployeeProjectRoles(1, 3);
		
		assertEquals(1, projRole.size());
		
		assertEquals(projRole.get(0).getID(), 3);
		assertEquals(projRole.get(0).getRole(), "Team Lead for the development team");
		assertEquals(projRole.get(0).getStartDate(), Date.valueOf("2006-01-09"));
		assertEquals(projRole.get(0).getEndDate(), Date.valueOf("2007-02-13"));
		
	}

	/** 
	 * Tests the scenario when the user did not find a project role 
	 * given the employee id and project id.
	 */	
	public void testGetEmployeeProjectRoles_NoMatchFound() {
	
		List<ProjectRole> projRole = projRepository.getEmployeeProjectRoles(2, 2);
		
		assertEquals(0, projRole.size());

		projRole = projRepository.getEmployeeProjectRoles(2, 1);
		
		assertEquals(0, projRole.size());

		projRole = projRepository.getEmployeeProjectRoles(1, 2);
		
		assertEquals(0, projRole.size());
		
		projRole = projRepository.getEmployeeProjectRoles(4, 3);
		
		assertEquals(0, projRole.size());

	}
	
	/** 
	 * Tests the scenario when the user finds a list of projects 
	 * given the employee id.
	 */	
	public void testGetEmployeeProjects_MatchFound() {
		
		List<Project> empProjList = projRepository.getEmployeeProjects(4);

		assertEquals(1, empProjList.size());
		
		proj4(empProjList.get(0));
	}
	
	/** 
	 * Tests the scenario when the user did not finds a project 
	 * given the employee id.
	 */	
	public void testGetEmployeeProjects_NoMatchFound() {
		
		List<Project> empProjList = projRepository.getEmployeeProjects(3);
		
		assertEquals(0, empProjList.size());
		
	}

}
