package com.sist.main;

import java.util.*;
import com.sist.dao.*;

public class EmpMain {
	public static void main(String[] args) {
		
		EmpDAO dao=new EmpDAO();
		
		//dao.empListData();
		
		//dao.empNotCommListDate();
		
		//dao.empCommListData();
		
		/*
		Scanner scan=new Scanner(System.in);
		System.out.print("검색어 입력:");
		String ename=scan.next();
		dao.empFindData(ename.toUpperCase());
		*/
		
		//dao.empRpadDate();
		
		//dao.empSalInfoData();
		
		//dao.empGroupByData();
		
		//서브쿼리사용X
		//dao.subqueryNotData();
		//서브쿼리사용O
		dao.subqueryData();
	}

}
