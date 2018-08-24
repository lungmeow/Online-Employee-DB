package sef.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import sef.domain.Employee;
import sef.impl.service.StubSearchServiceImpl;
import sef.interfaces.repository.EmployeeRepository;

public class EmployeeDAO implements EmployeeRepository{

	private static Logger log = Logger.getLogger(EmployeeDAO.class);
	private DataSource dataSource;
	public DataSource getDataSource(){
		return this.dataSource;
	}
	public EmployeeDAO(DataSource dataSource){
		this.dataSource = dataSource;
	}
	@Override
	public List<Employee> findEmployeesByName(String firstName, String lastName) {
		// TODO Auto-generated method stub
		List<Employee> employeeList = new ArrayList<Employee>();
		Connection con=null;
		ResultSet rs=null;
		try {
			con=this.dataSource.getConnection();
			String str="select * from employee where first_name like ? and last_name like ?";
			PreparedStatement pst=con.prepareStatement(str);
			pst.setString(1,firstName+"%");
			pst.setString(2,lastName+"%");

			log.info(pst.toString());
			rs=pst.executeQuery();
			while(rs.next()){
				
				Employee emp=new Employee();
				emp.setID(rs.getLong(1));
				emp.setFirstName(rs.getString(2));
				emp.setLastName(rs.getString(3));
				emp.setMiddleInitial(rs.getString(4));
				emp.setLevel(rs.getString(5));
				emp.setWorkForce(rs.getString(6));
				emp.setEnterpriseID(rs.getString(7));
				employeeList.add(emp);
			}
			Collections.sort(employeeList,new Comparator<Employee>(){
				@Override
				public int compare(Employee emp1, Employee emp2) {
					return emp2.getFirstName().compareTo(emp1.getFirstName());
			}
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				if(con!=null){
					con.close();
				}
				if(rs!=null){
					rs.close();
				}
			}catch(SQLException e){
				log.info(e.getMessage());
				
			}finally{
				log.info("close connection successfully");
			}
			
			
		}
		return employeeList;
	}

	@Override
	public List<Employee> findEmployeesByProject(long projectID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee findEmployeeByID(long employeeID) {
		// TODO Auto-generated method stub
		Employee emp=new Employee();
		Connection con=null;
		ResultSet rs=null;
		try {
			con=this.dataSource.getConnection();
			String str="select * from employee where id=?";
			PreparedStatement pst=con.prepareStatement(str);
			pst.setInt(1, (int) employeeID);
			rs=pst.executeQuery();
			while(rs.next()){
				emp.setID(rs.getLong(1));
				emp.setFirstName(rs.getString(2));
				emp.setLastName(rs.getString(3));
				emp.setMiddleInitial(rs.getString(4));
				emp.setLevel(rs.getString(5));
				emp.setWorkForce(rs.getString(6));
				emp.setEnterpriseID(rs.getString(7));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				if(con!=null){
					con.close();
				}
				if(rs!=null){
					rs.close();
				}
			}catch(SQLException e){
				log.info(e.getMessage());
				
			}finally{
				log.info("close connection successfully");
			}
			
			
		}
		return emp;
	}
	public List<Employee> findEmployeeByAll(String firstName, String lastName,long projectID) {
		// TODO Auto-generated method stub
		List<Employee> employeeList = new ArrayList<Employee>();
		Connection con=null;
		ResultSet rs=null;
		try {
			con=this.dataSource.getConnection();
			String str="select employee.id,first_name,last_name,middle_initial,level,workforce,enterprise_id from employee,employee_project_map where employee.id=employee_id and project_id=? and first_name like ? and last_name like ?";

			PreparedStatement pst=con.prepareStatement(str);
			pst.setInt(1, (int) projectID);
				pst.setString(2,firstName+"%");
				pst.setString(3,lastName+"%");
			log.info(pst.toString());
			
			rs=pst.executeQuery();
			while(rs.next()){
				Employee emp=new Employee();
				emp.setID(rs.getLong(1));
				emp.setFirstName(rs.getString(2));
				emp.setLastName(rs.getString(3));
				emp.setMiddleInitial(rs.getString(4));
				emp.setLevel(rs.getString(5));
				emp.setWorkForce(rs.getString(6));
				emp.setEnterpriseID(rs.getString(7));
				employeeList.add(emp);
			}
			Collections.sort(employeeList,new Comparator<Employee>(){
				@Override
				public int compare(Employee emp1, Employee emp2) {
					return emp2.getFirstName().compareTo(emp1.getFirstName());
			}
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				if(con!=null){
					con.close();
				}
				if(rs!=null){
					rs.close();
				}
			}catch(SQLException e){
				log.info(e.getMessage());
				
			}finally{
				log.info("close connection successfully");
			}
			
			
		}
		return employeeList;
	}

}
