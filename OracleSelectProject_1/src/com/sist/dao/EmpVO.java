package com.sist.dao;
import java.util.*;
/*
 *    오라클 데이터형
 *    문자형
 *       => char(1~2000byte) => 고정바이트(설정된 메모리 크기)
 *       => varchar2(1~4000byte) => 가변 (글자수에 따라 에모리 할당)
 *       => clob => 4기가 => 가변
 *       ----------------------------------------------------String
 *    숫자
 *       => number => 8자리
 *       => number(4) => 4wkfl
 *       => number(7,2) => 7자리, 소수점 2자리
 *       ----------------------------------------------------int
 *    날짜형
 *       => DATE
 *       -----------------------------------------------------java.util.Date
 *    => 저장된 데이터를 받을 준비
 *    => 오라클 단위가 ROW(Record)
 *    
 *    => 데이터 매칭
 *       DESC table_name
 *    
이름                                      널?      유형
 ----------------------------------------- -------- ----------------------------
 EMPNO                                     NOT NULL NUMBER(4)       ==> int
 ENAME                                              VARCHAR2(10)    ==> String
 JOB                                                VARCHAR2(9)     ==> String
 MGR                                                NUMBER(4)       ==> int
 HIREDATE                                           DATE            ==> date
 SAL                                                NUMBER(7,2)     ==> int
 COMM                                               NUMBER(7,2)     ==> int
 DEPTNO                                             NUMBER(2)       ==> int
 */
//column명과 매칭
public class EmpVO {
	
	private int empno;
	private String ename;
	private String job;
	private int mgr;
	private Date hiredate;
	private int sal;
	private int comm;
	private int deptno;
	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public int getMgr() {
		return mgr;
	}
	public void setMgr(int mgr) {
		this.mgr = mgr;
	}
	public Date getHiredate() {
		return hiredate;
	}
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	public int getSal() {
		return sal;
	}
	public void setSal(int sal) {
		this.sal = sal;
	}
	public int getComm() {
		return comm;
	}
	public void setComm(int comm) {
		this.comm = comm;
	}
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	
	

}
