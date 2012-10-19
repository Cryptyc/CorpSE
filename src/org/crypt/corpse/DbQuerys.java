package org.crypt.corpse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbQuerys {
	
	public static String getNamefromId(long id, Connection conn) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement("SELECT name FROM itemtypes WHERE id = ?");
		pstmt.setLong(1, id);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getString(1);
		} else {
			return null;
		}
	}
	

}
