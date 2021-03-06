package kr.or.bit.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.service.AdminLogin;
import kr.or.bit.service.AdminLogout;
import kr.or.bit.service.BoardList;
import kr.or.bit.service.BoardWrite;
import kr.or.bit.service.DataTable;
import kr.or.bit.service.DeptDetail;
import kr.or.bit.service.DeptList;
import kr.or.bit.service.EmpChart;
import kr.or.bit.service.EmpDelete;
import kr.or.bit.service.EmpDetail;
import kr.or.bit.service.EmpInsert;
import kr.or.bit.service.EmpList;
import kr.or.bit.service.EmpSearchDeptno;
import kr.or.bit.service.EmpSearchEmpno;
import kr.or.bit.service.EmpTotalCount;
import kr.or.bit.service.EmpUpdate;
import kr.or.bit.service.JobList;




@WebServlet("*.board")
public class FrontBoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public FrontBoardController() {
        super();
        // TODO Auto-generated constructor stub
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String requestURI = request.getRequestURI();
    	String contextPath = request.getContextPath();
    	String url_Command = requestURI.substring(contextPath.length());
	
    	Action action = null;
    	ActionForward forward = null;
    	
    	if(url_Command.equals("/BoardList.board")) {
    		//UI+로직
    		action = new BoardList();
    		forward = action.execute(request, response);
    		
    	}else if(url_Command.equals("/BoardWrite.board")) {
    		forward = new ActionForward();
    		forward.setRedirect(false);
    		forward.setPath("/WEB-INF/views/board/board_write.jsp");
    		
    	}else if(url_Command.equals("/BoardWriteOk.board")) {
    		//UI+로직
    		action = new BoardWrite();
    		forward = action.execute(request, response);
    		
    	}

    	
    	//뷰 지정하기
    	//RequestDispatcher dis = request.getRequestDispatcher(viewpage);
    	if(forward !=null) {
    		if(forward.isRedirect()) { //형식적으로 있지만 잘 안쓴다 주소가 바뀌기 때문에.
    			response.sendRedirect(forward.getPath());
    		}else { //false (모든 자원) 사용
    			//UI
    			//UI + 로직
    			//forward url 주소 변환 없이 view 내용을 받을 수 있다
    			RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
    			dis.forward(request, response);
    		}
    	}
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}






