package sef.impl.service;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import sef.domain.Employee;
import sef.domain.EmployeeDetail;
import sef.domain.EmployeeProjectDetail;
import sef.domain.EmployeeSkill;
import sef.domain.Project;
import sef.domain.ProjectRole;
import sef.interfaces.repository.EmployeeRepository;
import sef.interfaces.repository.ProjectRepository;
import sef.interfaces.repository.SkillRepository;
import sef.interfaces.service.EmployeeDetailsService;

import org.apache.log4j.Logger;

public class StubEmployeeDetailsServiceImpl implements EmployeeDetailsService{
	
	//Tip: create member variables in this class that will contain the objects 
	//passed by the Spring framework so that other methods can access the objects.

	private static Logger log = Logger.getLogger(StubEmployeeDetailsServiceImpl.class);
	private EmployeeRepository empDAO;
	private ProjectRepository projectDAO;
	private SkillRepository skillDAO;
//	private SkillRepository employeeDetailDAO ;

	public StubEmployeeDetailsServiceImpl(EmployeeRepository empDAO, ProjectRepository projectDAO, SkillRepository skillDAO){
		this.empDAO=  empDAO;
		this.projectDAO=  projectDAO;
		this.skillDAO= skillDAO;
	}

	@Override
	public EmployeeDetail getEmployeeDetails(long employeeID) {
		
		EmployeeDetail detail = new EmployeeDetail();	
		Employee employee = new Employee();
		employee=empDAO.findEmployeeByID(employeeID);
		List<EmployeeProjectDetail> projectDetail = new ArrayList<EmployeeProjectDetail>();
		List<Project> projectList=new ArrayList<Project>();
		projectList=projectDAO.getEmployeeProjects(employeeID);
		for( Project project:projectList){
			List<ProjectRole> projectRoles = new ArrayList<ProjectRole>();
			projectRoles=projectDAO.getEmployeeProjectRoles(employeeID,project.getID());
			EmployeeProjectDetail empProjDetail=new EmployeeProjectDetail();
			empProjDetail.setProject(project);
			empProjDetail.setProjectRoles(projectRoles);
			projectDetail.add(empProjDetail);
			log.info("see projectDetail"+empProjDetail);
		}
		List<EmployeeSkill> skillList = new ArrayList<EmployeeSkill>();
		skillList=skillDAO.findEmployeeSkills(employeeID);
		detail.setEmployee(employee);
		detail.setProjectList(projectDetail);
		detail.setSkillList(skillList);
		return detail;
	}
}
