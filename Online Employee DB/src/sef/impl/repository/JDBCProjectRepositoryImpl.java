package sef.impl.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import sef.domain.Project;
import sef.domain.EmployeeProjectDetail;
import sef.domain.ProjectRole;
import sef.exceptions.DataAccessException;
import sef.interfaces.repository.ProjectRepository;

import org.apache.log4j.Logger;

public class JDBCProjectRepositoryImpl implements ProjectRepository {

	//DataSource class encapsulates the driver, database url, username and 
	//password information.  The dataSource object is automatically created by 
	//the Spring framework and passed to the constructor therefore there's no need 
	//to instantiate the dataSource variable. A connection can be acquired by 
	//accessing the getConnection method of dataSource. 
	
	private DataSource dataSource;
	private static Logger log = Logger
			.getLogger(JDBCProjectRepositoryImpl.class);

	public JDBCProjectRepositoryImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Project> listAllProjects() {
		String sql = "select * from PROJECTS";
		PreparedStatement statement;
		ResultSet rs = null;
		Connection conn = null;

		List<Project> list = new ArrayList<Project>();

		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(sql);
			rs = statement.executeQuery();

			while (rs.next()) {

				Project project = new Project();
				project.setID(rs.getLong("ID"));
				project.setName(rs.getString("NAME"));
				project.setClient(rs.getString("CLIENT"));
				project.setDescription(rs.getString("DESCRIPTION"));

				log.info("Adding : " + project.getName());
				list.add(project);

			}
		} catch (SQLException e) {
			throw new DataAccessException(
					"Problem accessing employee repository", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new DataAccessException(
						"Problem closing resources in employee repository", e);
			}
		}

		return list;
	}

	@Override
	public List<EmployeeProjectDetail> getEmployeeProjectHistory(long employeeID) {
		
		
		List<Project> projectList = getEmployeeProjects(employeeID);
		List<EmployeeProjectDetail> detailList = new ArrayList<EmployeeProjectDetail>();
		for(Project project : projectList){
			EmployeeProjectDetail detail = new EmployeeProjectDetail();
			detail.setProject(project);
			detail.setProjectRoles(getEmployeeProjectRoles(employeeID, project.getID()));
			detailList.add(detail);
		}
		
		return detailList;
	}

	@Override
	public List<ProjectRole> getEmployeeProjectRoles(long employeeID,
			long projectID) {
		
		String sql="select * from employee_project_map where employee_id=? and project_id=?";
		
		PreparedStatement statement;
		ResultSet rs = null;
		Connection conn = null;

		List<ProjectRole> list = new ArrayList<ProjectRole>();

		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setLong(1, employeeID);
			statement.setLong(2, projectID);
			
			rs = statement.executeQuery();

			while (rs.next()) {

				ProjectRole role = new ProjectRole();
				role.setID(rs.getLong("ID"));
				role.setRole(rs.getString("EMPLOYEE_ROLE"));
				role.setStartDate(rs.getDate("START_DATE"));
				role.setEndDate(rs.getDate("END_DATE"));
				
				log.info("Adding : " + role.getRole());
				list.add(role);

			}
		} catch (SQLException e) {
			throw new DataAccessException(
					"Problem accessing employee repository", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new DataAccessException(
						"Problem closing resources in employee repository", e);
			}
		}

		return list;
		
		
	}

	@Override
	public List<Project> getEmployeeProjects(long employeeID) {
		
		String sql="SELECT * FROM projects where id in (select project_id from employee_project_map where employee_id=?)";
		PreparedStatement statement;
		ResultSet rs = null;
		Connection conn = null;

		List<Project> list = new ArrayList<Project>();

		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setLong(1, employeeID);
			rs = statement.executeQuery();

			while (rs.next()) {

				Project project = new Project();
				project.setID(rs.getLong("ID"));
				project.setName(rs.getString("NAME"));
				project.setClient(rs.getString("CLIENT"));
				project.setDescription(rs.getString("DESCRIPTION"));

				log.info("Adding : " + project.getName());
				list.add(project);

			}
		} catch (SQLException e) {
			throw new DataAccessException(
					"Problem accessing employee repository", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new DataAccessException(
						"Problem closing resources in employee repository", e);
			}
		}

		return list;
	}



	
}
