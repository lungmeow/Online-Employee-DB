package sef.impl.service;

import java.util.ArrayList;
import java.util.List;

import sef.domain.Employee;
import sef.domain.Project;
import sef.interfaces.repository.EmployeeRepository;
import sef.interfaces.repository.ProjectRepository;
import sef.interfaces.service.SearchService;

import org.apache.log4j.Logger;

public class SearchServiceImpl implements SearchService {

	private EmployeeRepository empDAO;
	private ProjectRepository projectDAO;


	private static Logger log = Logger.getLogger(SearchServiceImpl.class);

	public SearchServiceImpl(EmployeeRepository empDAO,
			ProjectRepository projectDAO) {
		this.empDAO = empDAO;
		this.projectDAO = projectDAO;
		
	}

	@Override
	public List<Employee> findEmployeesByName(String firstName, String lastName) {

		List<Employee> employeeList = new ArrayList<Employee>();

		if (firstName == null || lastName == null) {
			// return empty list
			log.info("Invalid search parameters:" + firstName + "," + lastName);
			return employeeList;
		}
		employeeList = empDAO.findEmployeesByName(firstName, lastName);
		return employeeList;
	}

	@Override
	public List<Employee> findEmployeesByProject(long projectID) {
		List<Employee> employeeList = empDAO.findEmployeesByProject(projectID);
		return employeeList;
	}

	

	@Override
	public List<Project> listAllProjects() {
		log.info("Returning all projects in the repository");
		List<Project> projectList = projectDAO.listAllProjects();
		return projectList;
	}






}
