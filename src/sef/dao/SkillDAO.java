package sef.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import sef.domain.EmployeeSkill;
import sef.interfaces.repository.SkillRepository;

public class SkillDAO implements SkillRepository{
	private static Logger log = Logger.getLogger(SkillDAO.class);
	private DataSource dataSource;
	public DataSource getDataSource(){
		return this.dataSource;
	}
	public SkillDAO(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	@Override
	public List<EmployeeSkill> findEmployeeSkills(long employeeID) {
		List<EmployeeSkill> empSkillList = new ArrayList<EmployeeSkill>();
		Connection con=null;
		ResultSet rs=null;
		try {
			con=this.dataSource.getConnection();String str="select skill_id,name,description,rating from skill,employee_skill_map where employee_id=employee_skill_map.id and employee_skill_map.skill_id = skill.id and employee_id=?";
			PreparedStatement pst=con.prepareStatement(str);
			pst.setInt(1, (int) employeeID);
			rs=pst.executeQuery();
			while(rs.next()){
				EmployeeSkill skill=new EmployeeSkill();
				skill.setID(rs.getLong("skill_id"));
				skill.setName(rs.getString("name"));
				skill.setDescription(rs.getString("description"));
				skill.setRating(rs.getInt("rating"));
				
				empSkillList.add(skill);
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
		
		return empSkillList;
	}

}
