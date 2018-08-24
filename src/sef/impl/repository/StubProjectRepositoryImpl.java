package sef.impl.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import sef.domain.Employee;
import sef.domain.Project;
import sef.domain.EmployeeProjectDetail;
import sef.domain.ProjectRole;
import sef.interfaces.repository.ProjectRepository;

import org.apache.log4j.Logger;

public class StubProjectRepositoryImpl implements ProjectRepository {

	//DataSource class encapsulates the driver, database url, username and 
	//password information.  The dataSource object is automatically created by 
	//the Spring framework and passed to the constructor therefore there's no need 
	//to instantiate the dataSource variable. A connection can be acquired by 
	//accessing the getConnection method of dataSource. 
	//
	//Tip: create member variables in this class that will contain the objects 
	//passed by the Spring framework so that other methods can access the objects.

	private static Logger log = Logger.getLogger(StubProjectRepositoryImpl.class);

	DataSource dataSource;
	public DataSource getDataSource(){
		return this.dataSource;
	}
	public StubProjectRepositoryImpl(DataSource dataSource) {
		this.dataSource=dataSource;
	}

	@Override
	public List<Project> listAllProjects() {

		List<Project> list = new ArrayList<Project>();
		try {
			Connection con=dataSource.getConnection();
			String str="select * from projects";
			PreparedStatement pst=con.prepareStatement(str);
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				Project proj=new Project();
				proj.setID(rs.getLong(1));
				proj.setName(rs.getString(2));
				proj.setDescription(rs.getString(3));
				proj.setClient(rs.getString(4));
				list.add(proj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return list;
	}

	@Override
	public List<EmployeeProjectDetail> getEmployeeProjectHistory(long employeeID) {
		
		List<EmployeeProjectDetail> detailList = new ArrayList<EmployeeProjectDetail>();
		return detailList;
	}

	@Override
	public List<ProjectRole> getEmployeeProjectRoles(long employeeID,
			long projectID) {
		
		List<ProjectRole> list = new ArrayList<ProjectRole>();
		return list;
	}

	@Override
	public List<Project> getEmployeeProjects(long employeeID) {
		
		List<Project> list = new ArrayList<Project>();
		return list;
	}
	@Override
	public List<Employee> findEmployeesByProject(long projectID) {
		// TODO Auto-generated method stub
		return null;
	}
}
