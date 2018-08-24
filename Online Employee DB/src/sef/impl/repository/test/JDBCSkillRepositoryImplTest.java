package sef.impl.repository.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sef.domain.EmployeeSkill;
import sef.interfaces.repository.SkillRepository;
import junit.framework.TestCase;

public class JDBCSkillRepositoryImplTest extends TestCase {

	SkillRepository skillRepository;
	
	/** 
	 * Set up database connection
	 */
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:repository-config.xml");	
		skillRepository = (SkillRepository)context.getBean("skillRep");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/** 
	 * Tests the scenario when the user finds a list of skills 
	 * given the employee id.
	 */		
	public void testFindEmployeeSkills_MatchFound() {
		List<EmployeeSkill> empSkillList = skillRepository.findEmployeeSkills(2); 

		assertEquals(1, empSkillList.size());

		assertEquals(empSkillList.get(0).getID(), 1);
		assertEquals(empSkillList.get(0).getName(), "J2SE");
		assertEquals(empSkillList.get(0).getRating(), 1);
		assertEquals(empSkillList.get(0).getDescription(), "Java programming using the standard edition");	

	}
	
	/** 
	 * Tests the scenario when the user did not find a skill 
	 * given the employee id.
	 */		
	public void testFindEmployeeSkills_NoMatchFound() {

		List<EmployeeSkill> empSkillList = skillRepository.findEmployeeSkills(100);
		
		assertEquals(0, empSkillList.size());

	}
}
