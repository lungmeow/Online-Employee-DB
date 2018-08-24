package sef.impl.service;

import org.apache.log4j.Logger;

import sef.controller.SearchEmployeeController;
import sef.domain.EmployeeDetail;
import sef.interfaces.repository.EmployeeRepository;
import sef.interfaces.repository.ProjectRepository;
import sef.interfaces.repository.SkillRepository;
import sef.interfaces.service.EmployeeDetailsService;

public class EmployeeDetailsServiceImpl implements EmployeeDetailsService{
	
	private EmployeeRepository empDAO;
	private ProjectRepository projectDAO;
	private SkillRepository skillDAO;
	private static Logger log = Logger.getLogger(EmployeeDetailsServiceImpl.class);

	public EmployeeDetailsServiceImpl(EmployeeRepository empDAO, ProjectRepository projectDAO, SkillRepository skillDAO){
		this.empDAO = empDAO;
		this.projectDAO = projectDAO;
		this.skillDAO = skillDAO;
	}

	@Override
	public EmployeeDetail getEmployeeDetails(long employeeID) {
		
		EmployeeDetail detail = new EmployeeDetail();
		
		detail.setEmployee(empDAO.findEmployeeByID(employeeID));
		detail.setProjectList(projectDAO.getEmployeeProjectHistory(employeeID));		
		detail.setSkillList(skillDAO.findEmployeeSkills(employeeID));
		return detail;
	}
	

}
