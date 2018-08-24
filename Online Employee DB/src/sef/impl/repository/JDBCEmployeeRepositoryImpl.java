package sef.impl.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import sef.domain.Employee;
import sef.exceptions.DataAccessException;
import sef.interfaces.repository.EmployeeRepository;

import org.apache.log4j.Logger;

public class JDBCEmployeeRepositoryImpl implements EmployeeRepository {

	//DataSource class encapsulates the driver, database url, username and 
	//password information.  The dataSource object is automatically created by 
	//the Spring framework and passed to the constructor therefore there's no need 
	//to instantiate the dataSource variable. A connection can be acquired by 
	//accessing the getConnection method of dataSource. 
	
	private DataSource dataSource;
	private static Logger log = Logger.getLogger(JDBCEmployeeRepositoryImpl.class);


	public JDBCEmployeeRepositoryImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Employee> findEmployeesByName(String firstName, String lastName) {
		
		String sql = "select * from EMPLOYEE where upper(FIRST_NAME) like ? and upper(LAST_NAME) like ?";
		PreparedStatement statement;
		ResultSet rs=null;
		Connection conn=null;
		
		List<Employee> list = new ArrayList<Employee>();
		
		if(firstName == null || lastName == null){
			//return empty list
			log.info("Invalid search parameters:" + firstName + "," + lastName);
			return list;
		}
		
		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setString(1, "%" + firstName.toUpperCase() + "%");
			statement.setString(2, "%" + lastName.toUpperCase() + "%");
			rs = statement.executeQuery();
			list = new ArrayList<Employee>();
			while (rs.next()) {
				Employee employee = new Employee();
				employee.setID(rs.getLong("ID"));
				employee.setFirstName(rs.getString("FIRST_NAME"));
				employee.setMiddleInitial(rs.getString("MIDDLE_INITIAL"));
				employee.setLastName(rs.getString("LAST_NAME"));
				employee.setWorkForce(rs.getString("WORKFORCE"));
				employee.setLevel(rs.getString("LEVEL"));
				employee.setEnterpriseID(rs.getString("ENTERPRISE_ID"));
				log.info("Adding " + employee.getEnterpriseID());
				list.add(employee);	
			}
		} catch (SQLException e) {
			throw new DataAccessException("Problem accessing employee repository",e);
		}finally{
			try{
				if(rs!= null){
					rs.close();
				}
				if(conn != null){
					conn.close();
				}
			}
			catch(SQLException e){
				throw new DataAccessException("Problem closing resources in employee repository",e);
			}
		}

		return list;
	}

	@Override
	public Employee findEmployeeByID(long employeeID) {
		
		String sql = "select * from EMPLOYEE where ID=?";
		PreparedStatement statement;
		ResultSet rs=null;
		Connection conn=null;
		Employee employee = new Employee();
		
		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setLong(1, employeeID);
			rs = statement.executeQuery();
		
			if(rs.next()) {
				employee.setID(rs.getLong("ID"));
				employee.setFirstName(rs.getString("FIRST_NAME"));
				employee.setMiddleInitial(rs.getString("MIDDLE_INITIAL"));
				employee.setLastName(rs.getString("LAST_NAME"));
				employee.setWorkForce(rs.getString("WORKFORCE"));
				employee.setLevel(rs.getString("LEVEL"));
				employee.setEnterpriseID(rs.getString("ENTERPRISE_ID"));
				log.info("Adding " + employee.getEnterpriseID());
			}
		} catch (SQLException e) {
			throw new DataAccessException("Problem accessing employee repository",e);
		}finally{
			try{
				if(rs!= null){
					rs.close();
				}
				if(conn != null){
					conn.close();
				}
			}
			catch(SQLException e){
				throw new DataAccessException("Problem closing resources in employee repository",e);
			}
		}
		return employee;
	}

	@Override
	public List<Employee> findEmployeesByProject(long projectID) {
		String sql = "SELECT * FROM employee where id in (select employee_id from employee_project_map e where project_id=?);";
		PreparedStatement statement;
		ResultSet rs=null;
		Connection conn=null;
		
		List<Employee> list = new ArrayList<Employee>();
		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setLong(1,projectID);
			rs = statement.executeQuery();
			list = new ArrayList<Employee>();
			while (rs.next()) {
				Employee employee = new Employee();
				employee.setID(rs.getLong("ID"));
				employee.setFirstName(rs.getString("FIRST_NAME"));
				employee.setMiddleInitial(rs.getString("MIDDLE_INITIAL"));
				employee.setLastName(rs.getString("LAST_NAME"));
				employee.setWorkForce(rs.getString("WORKFORCE"));
				employee.setLevel(rs.getString("LEVEL"));
				employee.setEnterpriseID(rs.getString("ENTERPRISE_ID"));
				log.info("Adding " + employee.getEnterpriseID());
				list.add(employee);	
			}
		} catch (SQLException e) {
			throw new DataAccessException("Problem accessing employee repository",e);
		}finally{
			try{
				if(rs!= null){
					rs.close();
				}
				if(conn != null){
					conn.close();
				}
			}
			catch(SQLException e){
				throw new DataAccessException("Problem closing resources in employee repository",e);
			}
		}

		return list;
	}


}
