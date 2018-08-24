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
import sef.domain.EmployeeSkill;
import sef.domain.Project;
import sef.interfaces.repository.SkillRepository;

public class EmployeeDetailDAO implements SkillRepository{
	private static Logger log = Logger.getLogger(EmployeeDetailDAO.class);
	private DataSource dataSource;
	public DataSource getDataSource(){
		return this.dataSource;
	}
	public EmployeeDetailDAO(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	@Override
	public List<EmployeeSkill> findEmployeeSkills(long employeeID) {
		// TODO Auto-generated method stub
		
		List<EmployeeSkill> empSkillList = new ArrayList<EmployeeSkill>();
		Connection con=null;
		ResultSet rs=null;
		try {
			con=this.dataSource.getConnection();
//			String str="select employee_skill_map.id,employee_id,skill_id,rating,first_name,last_name,middle_initial,level,workforce,enterprise_id,name,description from employee_skill_map,employee,skill where employee_id=employee.id and skill.id=skill_id and employee_id=?";
			String str="select skill_id,name,description,rating from skill,employee,employee_skill_map where employee_id=employee_skill_map.id and employee_skill_map.skill_id = skill.id and employee_id=?";
			PreparedStatement pst=con.prepareStatement(str);
			pst.setInt(1, (int) employeeID);
			rs=pst.executeQuery();
			while(rs.next()){
				EmployeeSkill skill=new EmployeeSkill();
				skill.setID(rs.getLong("skill_id"));
				skill.setName(rs.getString("name"));
				skill.setDescription(rs.getString("description"));
				skill.setRating(rs.getInt("rating"));
//				Employee emp=new Employee();
//				emp.setID(rs.getLong("employee_id"));
//				emp.setFirstName(rs.getString("first_name"));
//				emp.setLastName(rs.getString("last_name"));
//				emp.setLevel(rs.getString("level"));
//				emp.setMiddleInitial(rs.getString("middle_initial"));
//				emp.setWorkForce(rs.getString("workforce"));
//				emp.setEnterpriseID(rs.getString("enterprise_id"));
				
				empSkillList.add(skill);
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
		
		return empSkillList;
	}


	
}
