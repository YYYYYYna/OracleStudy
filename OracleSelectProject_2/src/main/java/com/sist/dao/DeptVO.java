package com.sist.dao;
/*
 *                VO        VO => 읽기/쓰기(getter/setter)
 *     JSP에서는 핵심 클래스 => bean
 *     => 가급적이면 =>오라클 컬럼과 매칭
 *     문자 : CHAR, VARCHAR2, CLOB => String
 *     숫자 : NUMBER => int, double
 *     날짜 : DATE => java.util.Date
 *     ------------------------------> MyBatis(ORM) => VO설정시 자동처리
 */
public class DeptVO {

	private int deptno;
	private String dname, loc;
	
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
}
