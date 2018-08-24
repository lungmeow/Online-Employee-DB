package sef.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import sef.jdbc.JDBCUtils;

public abstract class AbstractDAO {
	private static Logger log = Logger.getLogger(AbstractDAO.class);
	protected Object find(String sql, Object[] params){
		Object obj=null;
		ResultSet rs=null;
		PreparedStatement pst=null;
		Connection con=null;
		try{
			con=JDBCUtils.getConnection();
			pst = con.prepareStatement(sql);
			if(params!=null && params.length>0){
				for(int i=0;i<params.length;i++){
					pst.setObject(i+1, params[i]);
				}
			}
			log.info("show pst:"+pst);
			rs = pst.executeQuery();
//			while(rs.next()){
				obj=rowMapper(rs);
//			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JDBCUtils.free(con, rs, pst);
		}
		return obj;
	}
	protected abstract Object rowMapper(ResultSet rs) throws SQLException;
}
