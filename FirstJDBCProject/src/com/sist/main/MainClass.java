package com.sist.main;
import java.sql.*;

public class MainClass {
	public static void main(String[] args) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//핵심 => 자바 ( 12장 )
			Class.forName("oracle.jdbc.driver.OracleDriver");//오라클 연결 => 드라이버 설정
			System.out.println("드라이버로딩성공");
			// 오라클 연결 connect hr/happy
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			// 네트워크 => ip/port XE=> 데이터베이스 이름
			// 파일 / 폴더
			// 테이블 / 데이터베이스 => book,emp,dept(테이블)
			String id = "hr";
			String pwd = "happy";
			
			/////////////내가임의추가한내용//////////////Connection con = DriverManager.getConnection(url, user, password);
	        /////////////내가임의추가한내용//////////////System.out.println("연결성공:"+con);
			
			conn = DriverManager.getConnection(url,id,pwd);// 연결
			String sql = "SELECT ename,hiredate,job FROM emp";
			ps = conn.prepareStatement(sql);//쿼리문 송신
			rs = ps.executeQuery();//쿼리문 수신
			while(rs.next()) {
				System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}