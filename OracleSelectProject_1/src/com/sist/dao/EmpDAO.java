package com.sist.dao;
/*
 *      연동하는 부분이 이곳임
 *      브라우저 =====> 자바 =======> 오라클
 *              요청       SQL전송    |SQL문장 실행
 *              전송 ==========   결과값받기
 *               |결과값을 받아서 화면 출력 (HTML)
 *            **오라클 SQL문장과 자바에서 전송받는 SQL문장이 다른것이 있다
 *                          95%동일 5%가 다름
 *                                 -------
 *                                 LIKE문장이 약간 틀림
 *      1) 연결(송수신) => 드라이버 설정
 *                      -----클래스형으로 만들어져 있음
 *                           드라이버설치=클래스메모리할당
 *         Class.forName("oracle.jdbc.driver.OracleDriver")     
 *                       ---------------------------------
 *                          ojdbc8.jar
 *      2) 오라클 연결
 *         Connection conn=DriverManager,
 *                         getConnection(URL,username,password)
 *                                              hr      happy
 *         URL : jdbc:업체명:드라이버타입:@IP:PORT:데이버베이스명
 *               jdbc:oracle:thin:@localhost:1521:XE
 *         
 *      3) SQL문장을 전송
 *         PreparedStatement ps=
 *               conn.prepareStatement(SQL문장);
 *         SQL문장 => SELECT...
 *         
 *      4) 오라클에서 실행된 데이터를 받는다
 *         ResultSet rs=ps.excuteQuery()
 *         =>        --   실행된 결과를 메모리에 저장
 *         SELECT 
 *         ResultSet
 *         ENAME                JOB
-------------------- ------------------
KING                 PRESIDENT  |커서이동 => next()
BLAKE                MANAGER
CLARK                MANAGER
JONES                MANAGER
MARTIN               SALESMAN
ALLEN                SALESMAN
TURNER               SALESMAN
JAMES                CLERK
WARD                 SALESMAN
FORD                 ANALYST
SMITH                CLERK
SCOTT                ANALYST
ADAMS                CLERK
MILLER               CLERK    | 커서이동 => previous()
|커서가 여기에 존재
 *  ==> Order by를 사용해서 데이터를 읽어온다 => next()를 주로 사용  
 *  while(rs.next())
 *  {
 *     => VO에 값을 채운다                            
 *  }
 *  => 끝남과 동시에 읽을 데이터가 없는 상태
 *  rs.close()
 *  ps.close()
 *  conn.close() ==> 종료
 *  --------------------코딩하는 패턴이 1개 ==> 이 패턴만 외우면 데이터 베이스 사용 문제 없음!
 *                                     ==> SQL문장을 얼마나 잘 만드느냐가 중요함~~
 *          
 *          
 *          ==> 반복구간(연결/닫기) 존재 => 이걸 메소드화 시키려함~~
 *          
 *          SELECT : 데이터 읽기 (검색) 
 *             => 형식
 *                SELECT * | column1, column2...
 *                FROM table_name명
 *                [
 *                   WHERE 조건문 (연산자)
 *                   GROUP BY 컬럼|함수 => 그룹
 *                   HAVING 그룹에 대한 조건 ===> 반드시 GROUP BY가 있는 경우에 사용가능
 *                   ORDER BY 컬럼(정렬대상), 번호 + ASC/DESC
 *                ]
 *  
 *              
 */
import java.util.*; //DATE쓰려구 가져옴
import java.sql.*; //Connection , PreparedStatement, ResultSet
public class EmpDAO {
	
	//0-1. 연결 객체 선언 => Connection
	private Connection conn;
	
	//0-2. SQL문장 송수신
	private PreparedStatement ps; //=read/write의 역할

	//0-3. 오라클 연결시 오라클주소(IP)가져오기
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	//1. 드라이버 등록
	// 한번만 수행 => 보통(생성자)
	public EmpDAO()
	{
		try {
			//대소문자 구분 주의
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//메모리 할당 => 클래스명으로 메모리 할당이 가능 => 리플렉션형태
			
		}catch(Exception ex){}
	}
	
	//2. 오라클 연결 => SQLPlus를 연결
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
			//=conn은 아이디 비번을 보내는 역할
			
		}catch(Exception ex) {}
	}
	
	//3. 오라클 해제
	public void disConnection()
	{
		try
		{
			if(ps!=null)
			{
				ps.close();
			}
			if(conn!=null)
			{
				conn.close();
			}
		}catch(Exception ex) {}
	}
	
	//4. 기능 수행 => 이부분에 메소드가 많음 
	//**테이블 한개당 VO,DAO가 하나씩 생성되는걸 잊지 말기
	public void empListData() //=emp에서 데이터 출력하는 메소드
	{
		//사번,이름,입사일,직위,급여
		try {
			//1. 오라클 연결 = 아까 메소드 만들어 둔거!!
			getConnection();
			//2. SQL문장 제작
			String sql="SELECT empno,ename,job,hiredate,sal "+"FROM emp";
			//3. SQL문장을 오라클 전송
			ps=conn.prepareStatement(sql);
			//4. 결과값을 받는다
			ResultSet rs=ps.executeQuery();
			//5. 결과값을 출력
			/*
			 *      no    name   sex    regdate
			 *      1     홍길동   남자    23/11/13   //while문 한번에 이 한줄을 읽어오는거임
			 *      |       |      |       |rs.getDate(4)
			 *      |       |      |rs.getString(3)
			 *      |       |rs.getString(2)
			 *      |rs.getInt(1)
			 */
			while(rs.next())
			{
				System.out.println(
						rs.getInt(1)+" "
						+rs.getString(2)+" "
						+rs.getString(3)+" "
						+rs.getDate(4)+" "
						+rs.getInt(5));
			}
			rs.close();
		}catch(Exception ex) 
		{
			//오류 확인
			ex.printStackTrace();
		}
		finally
		{
			//닫기
			disConnection();
		}
	}
	
	//사원의 이름, 직위, 급여, 입사일, 성과금 => 성과금이 없는 사원의 목록을 출력
	public void empNotCommListDate() {
		try {
			getConnection();
			
			String sql="SELECT ename, job, sal, hiredate, comm "
					+"FROM emp WHERE comm IS NULL OR comm=0";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				System.out.println(
						rs.getString(1)+" "
						+rs.getString(2)+" "
						+rs.getInt(3)+" "
						+rs.getDate(4)+" "
						+rs.getInt(5)+" ");
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
	}
	
	//사원 이름, 직위, 급여, 입사일, 성과금, (성과금있는 사원 0은 제거)
	public void empCommListData()
	{
		try {
			getConnection();
			
			String sql="SELECT ename, job, sal, hiredate, comm "
					+"FROM emp WHERE NOT (comm IS NULL OR comm=0)";
			
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				System.out.println(
						rs.getString(1)+" "
								+rs.getString(2)+" "
								+rs.getInt(3)+" "
								+rs.getDate(4)+" "
								+rs.getInt(5)+" ");
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
	}
	
	//(사용자 입력값)이 포함된 사원의 이름, 직위, 입사일, 급여 + 이름순으로 정렬
	public void empFindData(String ename)
	{
		try
		{
			getConnection();
			
			String sql="SELECT ename,job,hiredate,sal "
					+"FROM emp "
					+"WHERE ename LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			
			// => ?에 값을 채운후에 실행 요청
			ps.setString(1, ename);
			
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				System.out.println(
						rs.getString(1)+" "
						+rs.getString(2)+" "
						+rs.getDate(3)+" "
						+rs.getInt(4));
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
	}
	
	//rpad 이용
	public void empRpadDate()
	{
		try {
			getConnection();
			
			String sql="SELECT ename, RPAD(SUBSTR(ename,1,2),LENGTH(ename),'*') "
					+"FROM emp";
			
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				System.out.println(rs.getString(1)+" "
						+rs.getString(2));
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
	}
	
	public void empSalInfoData()
	{
		try
		{
			getConnection();
			
			String sql="SELECT ename,ROUND(MONTHS_BETWEEN(SYSDATE,hiredate)),"
					+"TO_CHAR(sal,'$999,999'), TO_CHAR(sal*12,'$999,999'), TO_CHAR(sal+NVL(comm,0),'$999,999'),"
					+"TO_CHAR(HIREDATE,'yyyy-mm-dd HH24:mi:ss') "
					+"FROM emp";
			
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				System.out.println(
						rs.getString(1)+" "
						+rs.getInt(2)+" "
						+rs.getString(3)+" "
						+rs.getString(4)+" "
						+rs.getString(5)+" "
						+rs.getString(6));
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
	}
	
	public void empGroupByData()
	{
		try {
			getConnection();
			
			String sql="SELECT TO_CHAR(hiredate,'yyyy'), count(*), sum(sal),\r\n"
					+ "AVG(sal), MAX(sal), MIN(sal) "
					+"FROM emp "
					+"GROUP BY TO_CHAR(HIREDATE,'yyyy') "
					+"ORDER BY 1 ASC";
			
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				System.out.println(
						rs.getString(1)+" "
						+rs.getInt(2)+" "
						+rs.getInt(3)+" "
						+rs.getDouble(4)+" "
						+rs.getInt(5)+" "
						+rs.getInt(6));
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
	}

	//서브쿼리를 사용하지 않았을때
	public void subqueryNotData()
	{
		try {
			getConnection();
			
			String sql="SELECT ROUND(AVG(sal)) "
					+"FROM emp";
			
			ps=conn.prepareStatement(sql);
			//sql문장은 1개만 사용이 가능!!
			
			ResultSet rs=ps.executeQuery();
			rs.next();//커서의 위치 변경
			int avg=rs.getInt(1);
			rs.close();
			
			sql="SELECT ename, job, hiredate, sal "
					+"FROM emp "
					+"WHERE sal<?";
			
			ps=conn.prepareStatement(sql);
			ps.setInt(1, avg);
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				System.out.println(rs.getString(1)+" "
						+rs.getString(2)+" "
						+rs.getDate(3)+" "
						+rs.getInt(4));
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
	}
	
	//서브쿼리를 사용한 경우
	public void subqueryData()
	{
		try {
			getConnection();
			
			String sql="SELECT ename, job, hiredate, sal "
					+"FROM emp "
					+"WHERE sal<(SELECT ROUND(AVG(sal)) FROM emp)";
			
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				System.out.println(rs.getString(1)+" "
						+rs.getString(2)+" "
						+rs.getDate(3)+" "
						+rs.getInt(4));
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
	}
}
