package com.sist.dao;

/*
 *    조인
 *     => 2개 이상의 테이블을 연결해서 하나의 테이블처럼 필요한 데이터를
 *     => 데이터 추출에 목적
 *     => 종류
 *        INNER JOIN
 *           => EQUI_JOIN (=)
 *           => NON_EQUI_JOIN (논리연산자,BETWEEN)
 *        OUTER JOIN
 *           => NULL값 처리 => INNER JOIN을 보완
 *           => LEFT
 *           => RIGHT
 */

import java.util.*;
import java.sql.*;

public class EmpDAO {
	
	//오라클 연결
	private Connection conn;
	
	//SQL문장 전송=>결과값
	private PreparedStatement ps;
	
	//오라클 연결 => URL주소
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	//1. 드라이버 등록 : 1번만 사용 / 주로 생성자에서 사용
	public EmpDAO()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//thin / oci
			//thin => 연결만 하는 역할(무료)
			//oci => 드라이버에 오라클 기능(유료)
			
			//Class.forName => 클래스명으로 메모리 할당(스프링,마이바티스 라이브러리에서 주로 사용)
			//==> 리플렉션~~ => 메모리 할당 => 클래스 등록(XML)
		}catch(Exception ex) {}
	}
	
	//2. 오라클 연결
	public void getConnection()
	{
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
			//오라클에 전송하는 명령어 => conn hr happy
		}catch(Exception ex) {}
		
	}

	//3. 오라클 닫기
	public void disConnection()
	{
		try {
			if(ps!=null) ps.close();
			//OutputStream / BufferedReader
			if(conn!=null) conn.close();
			//Socket
		}catch(Exception ex) {}
	}
	////////////////////////////////////////////////////////////////여기가지 필수과정
	/*
	 *   DAO => 원래 한개의 테이블만 제어할수 있게 만든다
	 *          회원가입, 영화, 게시판, 음악, 맛집, 레시피 각각 하나씩 만들어야함 
	 *   Service => 근데! 간혹가다 통합해서 아용할때도 있음
	 *              게시판+댓글, 찜하기+좋아요 처럼 비슷한 카테고리의 항목을 묶어두기
	 */
	//기능 => 사원의 정보 => 급여등급, 부서명, 근무지
	//JOIN => emp=dept, emp=salgrade
	
	public ArrayList<EmpVO> empAllData()
	{
		//emp, dept, salgrade 정보를 한번에 모아서 => 전송
		ArrayList<EmpVO> list=new ArrayList<EmpVO>();
		
		try {
			getConnection();
			/*
			String sql="SELECT empno,ename,job,hiredate, "
					+"sal,emp.deptno,dname,loc,grade "
					+"FROM emp,dept,salgrade "
					+"WHERE emp.deptno=dept.deptno "
					+"AND sal BETWEEN losal AND hisal";
					*/
			String sql="SELECT empno,ename,job,hiredate, "
					+"sal,emp.deptno,dname,loc,grade "
					+"FROM emp JOIN dept "
					+"ON emp.deptno=dept.deptno "
					+"JOIN salgrade "
					+"ON sal BETWEEN losal AND hisal";
			//전송
			ps=conn.prepareStatement(sql);
			//실행
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				EmpVO vo=new EmpVO();
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setHiredate(rs.getDate(4));
				vo.setSal(rs.getInt(5));
				vo.setDept(rs.getInt(6));
				vo.getDvo().setDname(rs.getString(7));
				vo.getDvo().setLoc(rs.getString(8));
				vo.getSvo().setGrade(rs.getInt(9));
				
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		
		return list;
	}
	
	//서브쿼리 연습
	/*
	 *  subQuery : SQL문장 여러개를 한개로 모아서 한번에 처리
	 *  MainQuery = (SubQuery)
	 *  종류
	 *   = WHERE 뒤에 조건 
	 *     => 단일행 서브쿼리
	 *        비교연산자(=, !=, <, >, <=, >=)
	 *     => 다중행 서브쿼리
	 *        IN, ANY, ALL
	 *   = 컬럼 대신 사용 SELECT 뒤에 ==> 스칼라 서브쿼리
	 *   = 테이블 대신 사용 FROM 뒤에 ==> 인라인뷰
	 */
	// KING이 있는 부서에서 근무하는 사원의 사번, 이름, 부서명, 부서번호, 근무지, 입사일
	public ArrayList<EmpVO> subqueryEmpData()
	{
		ArrayList<EmpVO> list=new ArrayList<EmpVO>();
		try {
			// 서브쿼리가 먼저 실행 => () 괄호 잊으면 안됨!
			// 서브쿼리에서는 Order By는 사용할 수 없다.
			getConnection();
			String sql="SELECT empno, ename, dname, loc, hiredate,sal "
					+"FROM emp,dept "
					+"WHERE emp.deptno=dept.deptno "
					+"AND emp.deptno=(SELECT deptno FROM emp WHERE ename='KING')";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				EmpVO vo=new EmpVO();
				
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.getDvo().setDname(rs.getString(3));
				vo.getDvo().setLoc(rs.getString(4));
				vo.setHiredate(rs.getDate(5));
				vo.setSal(rs.getInt(6));
				
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return list;
	}
	//다중행 서브쿼리 => IN => 이름중에 A를 포함하고 있는..
	public ArrayList<EmpVO> subqueryInEmpListData()
	{
		ArrayList<EmpVO> list=new ArrayList<EmpVO>();
		try {
			getConnection();
			String sql="SELECT empno,ename,dname,loc,hiredate,sal "
					+"FROM emp JOIN dept "
					+"ON emp.deptno=dept.deptno "
					+"AND emp.deptno IN(SELECT DISTINCT deptno FROM emp WHERE ename LIKE '%A%') "
					+"ORDER BY emp.deptno ASC";
			
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{

				EmpVO vo=new EmpVO();
				
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.getDvo().setDname(rs.getString(3));
				vo.getDvo().setLoc(rs.getString(4));
				vo.setHiredate(rs.getDate(5));
				vo.setSal(rs.getInt(6));
				
				list.add(vo);
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return list;
	}
}
