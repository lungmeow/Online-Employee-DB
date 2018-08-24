package sef.impl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import sef.dao.ProjectDAO;
import sef.domain.Employee;
import sef.domain.Project;
import sef.interfaces.repository.EmployeeRepository;
import sef.interfaces.repository.ProjectRepository;
import sef.interfaces.service.SearchService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class StubSearchServiceImpl implements SearchService {

	//Tip: create member variables in this class that will contain the objects 
	//passed by the Spring framework so that other methods can access the objects.

	private static Logger log = Logger.getLogger(StubSearchServiceImpl.class);
	private DataSource dataSource;
	private EmployeeRepository empDAO;
	private ProjectRepository projectDAO;
	public DataSource getDataSource(){
		return this.dataSource;
	}
	
	public StubSearchServiceImpl(EmployeeRepository empDAO,
			ProjectRepository projectDAO) {
		this.empDAO=  empDAO;
		this.projectDAO=  projectDAO;
		this.dataSource=  projectDAO.getDataSource();
        
	}

	@Override
	public List<Employee> findEmployeesByName(String firstName, String lastName) {

		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList=this.empDAO.findEmployeesByName(firstName, lastName);
		return employeeList;
	}

	@Override
	public List<Employee> findEmployeesByProject(long projectID) {
		
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList=this.projectDAO.findEmployeesByProject(projectID);
		return employeeList;
	}

	@Override
	public List<Project> listAllProjects() {
		
		List<Project> projectList = new ArrayList<Project>();
		
		projectList=this.projectDAO.listAllProjects();
		return projectList;
	}






}
