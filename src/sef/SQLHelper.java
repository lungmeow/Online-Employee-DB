package sef;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;


public class SQLHelper {
	private static Logger log = Logger.getLogger(SQLHelper.class);
	private static DataSource dataSource;
	public DataSource getDataSource(){
		return dataSource;
	}
	public  SQLHelper(DataSource dataSource) {
		super();
		SQLHelper.dataSource = dataSource;
	}
	public static Connection getConnection(){
		Connection con=null;
		try {
			con=dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	public static void close(Connection con){
		try {
			if(con!=null){
				con.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void clean(ResultSet rs){
		try {
			if(rs!=null){
				rs.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void free(Connection con,ResultSet rs, PreparedStatement pst){
		if(con!=null){
			try{
				con.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(rs!=null){
			try{
				rs.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(pst!=null){
			try{
				pst.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
