package com.petmaru.member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.petmaru.member.model.service.MemberService;
import com.petmaru.member.model.vo.MemberVo;

/**
 * Servlet implementation class MemberMypageUpdateServlet
 */
@WebServlet("/mypageupdate")
public class MemberMypageUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberMypageUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/member/mypageupdate.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset-UTF-8");
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		//String deleteid = request.getParameter("id");
		String pwd = request.getParameter("pwd");		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String gender = request.getParameter("gender");
		String regdate = request.getParameter("regdate");
		int point = Integer.parseInt(request.getParameter("point"));			
		MemberService service = new MemberService();
		//MemberVo m = new MemberVo(id,name,pwd, phone, address, gender, point,email);
		System.out.println("????????? ???????????? " + id + email+ pwd + address);
		if(id!= null) {
			String deleteid = (String) request.getAttribute("deleteid");	
		int update = service.updateMember(id,name,pwd, phone, address, gender, point,email);
		System.out.println("???????????????");
		System.out.println("???????????? ??????????????? ????????? ?????????" + update);
		System.out.println("?????????????????? : "+ deleteid);
		}
		String deleteid = (String) request.getAttribute("deleteid");
		System.out.println("?????????????????? : "+ deleteid);
		response.sendRedirect("/Petmaru/");
		// request.getRequestDispatcher("/WEB-INF/member/mypageupdate.jsp").forward(request, response);
		
	}
	

}
