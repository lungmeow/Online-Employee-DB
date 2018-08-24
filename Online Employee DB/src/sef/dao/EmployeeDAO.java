package sef.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
			String str="select * from employee where ";
			if(firstName.length()!=0&&lastName.length()!=0){
				str+="first_name like ? and last_name like ?";
			}else if(firstName.length()==0){
				str+="last_name like ?";
			}else if(lastName.length()==0){
				str+="first_name like ?";
			}
			
			PreparedStatement pst=con.prepareStatement(str);
			if(firstName.length()!=0&&lastName.length()!=0){
				pst.setString(1,firstName+"%");
				pst.setString(2,lastName+"%");
			}else if(firstName.length()==0){
				pst.setString(1,lastName+"%");
			}else if(lastName.length()==0){
				pst.setString(1,firstName+"%");
			}
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
			con.close();
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
		return null;
	}

}
