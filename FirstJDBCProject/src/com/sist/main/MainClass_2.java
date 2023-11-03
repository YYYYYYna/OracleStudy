package com.sist.main;
/*
 *     자바
 *     ---
 *     프로그램의 핵심 : 데이터 관리
 *      변수,배열,클래스,파일,오라클,XML,JSON
 *      => 데이터 베이스
 *      => 머신러닝/딥러닝
 *         => 데이터수집,데이터전처리,분석
 *     
 *     프로젝트
 *     ------
 *     => 벤치마킹(아이템)
 *        => 예약(예매),추천,결제
 *        -------------------
 *          | =>출력할 데이터가 가장 먼저 필요함~~(구현을 해야하니까)
 *     => 요구사항 분석
 *        ---------- 필요한 데이터(크롤링)
 *     => 오라클에 저장하기(데이터베이스설계)
 *     => 화면UI
 *     => 구현
 *     => 테스트
 *     => 배포(발표) ==> 호스팅(AWS)
 *     
 *     클래스 : 관련된 데이터를 묶는다 => VO
 *             => 캡슐화 => 변수의은닉화(private)
 *               => 다른클래스나 브라우저에서 접근이 안됨
 *               => 사용 => 읽기/쓰기 => getter/setter
 *            => 사용자 정의 데이터
 *               => CRUD => 액션
 *                  => 메소드로만 만든다
 *                  => ~Manager
 *                  => ~DAO
 *            관련된 기능을 묶는다
 *            => 핵심=재사용(상속/**포함)
 *            => 관련된 클래스가 여러개 있는 경우***=>인터페이스의 정의로 대답해야함!!!
 *              => 인터페이스를 이용해서 1개로 제어
 *                 List list=new ArrayList()
 *                      list=new Vector()
 *                      list=new LinkedList()
 *              => 객체란?
 *                 Object:현실이나 비현실 세계를 단순화하는것
 *              *** 여러개의 데이터
 *                  변수/배열/클래스 => 한개의 이름으로 제어
 *     
 *     예)
 *     emp => 사원정보
 *     =>empno : 사번 => int
 *     =>ename : 이름 => String
 *     =>job   : 직위 => String
 *     =>hiredate : 입사일 => Date
 *     =>sal   : 급여 => int
 *     =>comm : 성과금 => int
 *     =>mgr : 사수의 사번 => int
 *     =>deptno : 부서번호 => int
 *     
 *     리턴형이 1개 밖에 없기 때문에
 *     <기존의 하던 방식> 처럼 이렇게 하나씩 다 만들거나...
 *     => public int getEmopno()
 *        public String getEname()
 *        public String getJob()
 *        public Date getHiredate()
 *        public int getSal()
 *        public int getComm()
 *        public int getMgr()
 *        public int getDeptno()
 *        : 이렇게 데이터를 한 8개를 만들어서 보내야함....
 *        : 데이터가 많을수록 속도가 느려지고 불편해짐
 *     
 *     리턴형이 1개 밖에 없기 때문에
 *     <VO를 통해 하나로 묶어서 효율적으로 사용가능>
 *     => public EmpVo getEmpData()
 *     
 *          
 *          **react는 데이터 관리가 근간임
 *          **비행기 게임에서 비행기는 "움직이는"게 아니라 기존위치를 삭제하고 해당 좌표점에 다시 그려주는거임
 *          
 *        
 */
public class MainClass_2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
