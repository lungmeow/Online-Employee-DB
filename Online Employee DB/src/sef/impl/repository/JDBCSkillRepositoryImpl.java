package sef.impl.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import sef.domain.EmployeeSkill;
import sef.domain.Project;
import sef.exceptions.DataAccessException;
import sef.interfaces.repository.SkillRepository;

import org.apache.log4j.Logger;

public class JDBCSkillRepositoryImpl implements SkillRepository{
	
	//DataSource class encapsulates the driver, database url, username and 
	//password information.  The dataSource object is automatically created by 
	//the Spring framework and passed to the constructor therefore there's no need 
	//to instantiate the dataSource variable. A connection can be acquired by 
	//accessing the getConnection method of dataSource. 
		
	private DataSource dataSource;
	private static Logger log = Logger.getLogger(JDBCSkillRepositoryImpl.class);
	
	public JDBCSkillRepositoryImpl(DataSource dataSource){
		this.dataSource = dataSource;
	}

	@Override
	public List<EmployeeSkill> findEmployeeSkills(long employeeID) {
		String sql = "SELECT  s.*, e.rating FROM skill s, employee_skill_map e where e.employee_ID=? and s.ID= e.skill_ID";
		PreparedStatement statement;
		ResultSet rs = null;
		Connection conn = null;

		List<EmployeeSkill> list = new ArrayList<EmployeeSkill>();

		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setLong(1, employeeID);
			rs = statement.executeQuery();

			while (rs.next()) {

				EmployeeSkill skill = new EmployeeSkill();
				skill.setID(rs.getLong("ID"));
				skill.setName(rs.getString("NAME"));
				skill.setDescription(rs.getString("DESCRIPTION"));
				skill.setRating(rs.getInt("RATING"));	
				list.add(skill);
				log.info("Adding skill: "  + skill.getName());
					
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
