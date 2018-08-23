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
import sef.domain.EmployeeProjectDetail;
import sef.domain.Project;
import sef.domain.ProjectRole;
import sef.impl.service.StubSearchServiceImpl;
import sef.interfaces.repository.ProjectRepository;

public class ProjectDAO implements ProjectRepository {
	private static Logger log = Logger.getLogger(ProjectDAO.class);
	private DataSource dataSource;
	public DataSource getDataSource(){
		return this.dataSource;
	}
	public ProjectDAO(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	@Override
	public List<Project> listAllProjects() {
		// TODO Auto-generated method stub
		List<Project> projectList = new ArrayList<Project>();
		Connection con=null;
		ResultSet rs=null;
		try {
			con=this.dataSource.getConnection();
			String str="select * from projects";
			PreparedStatement pst=con.prepareStatement(str);
			rs=pst.executeQuery();
			while(rs.next()){
				Project proj=new Project();
				proj.setID(rs.getLong(1));
				proj.setName(rs.getString(2));
				proj.setDescription(rs.getString(3));
				proj.setClient(rs.getString(4));
				projectList.add(proj);
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
		return projectList;
	}
	@Override
	public List<Project> getEmployeeProjects(long employeeID) {
		// TODO Auto-generated method stub
		List<Project> projectList = new ArrayList<Project>();
		Connection con=null;
		ResultSet rs=null;
		try {
			con=this.dataSource.getConnection();
			String str="select * from projects where id in (select * from employee_project_map where employee_id=?)";
			PreparedStatement pst=con.prepareStatement(str);
			pst.setInt(1, (int) employeeID);
			rs=pst.executeQuery();
			while(rs.next()){
				Project proj=new Project();
				proj.setID(rs.getLong(1));
				proj.setName(rs.getString(2));
				proj.setDescription(rs.getString(3));
				proj.setClient(rs.getString(4));
				projectList.add(proj);
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
		return projectList;
	}
	@Override
	public List<ProjectRole> getEmployeeProjectRoles(long employeeID,
			long projectID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<EmployeeProjectDetail> getEmployeeProjectHistory(long employeeID) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Employee> findEmployeesByProject(long projectID){
		List<Employee> employeeList = new ArrayList<Employee>();
		Connection con=null;
		ResultSet rs=null;
		try {
			log.info(projectID);
			con=this.dataSource.getConnection();
			String str="select * from employee where id in(select employee_id from employee_project_map where project_id = ?)";
			PreparedStatement pst=con.prepareStatement(str);
			pst.setInt(1, (int) projectID);
			rs=pst.executeQuery();
			
			while(rs.next()){
				log.info(rs.getLong(1));
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
}
