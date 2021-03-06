package com.mvc.controller;

import java.io.IOException;
import com.mvc.bean.LoginBean;
import com.mvc.dao.LoginDao;
import com.mvc.util.Hashing;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
//@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Here username and password are the names which I have given in the input box in Login.jsp page. Here I am retrieving the values entered by the user and keeping in instance variables for further use.
	
		String userName = request.getParameter("username");
		 String password1 = request.getParameter("password");
		 
		 Hashing hash_password = new Hashing();
		 LoginBean loginBean = new LoginBean(); 
		 
		 String hashed = hash_password.generateHash(password1);
		 loginBean.setUserName(userName); //setting the username and password through the loginBean object then only you can get it in future.
		 loginBean.setPassword(hashed);
		 LoginDao loginDao = new LoginDao(); //creating object for LoginDao. This class contains main logic of the application.
		 String userValidate = loginDao.authenticateUser(loginBean); //Calling authenticateUser function
		 if(userValidate.equals("User")) //If function returns success string then user will be rooted to Home page
		 {
		 HttpSession session = request.getSession();
		 session.setAttribute("userName", userName); //with setAttribute() you can define a "key" and value pair so that you can get it in future using getAttribute("key")
		 request.setAttribute("userName", userName);
		 System.out.print(userName);
		 request.getRequestDispatcher("/Home.jsp").forward(request, response);//RequestDispatcher is used to send the control to the invoked page.
		 }
		 else if(userValidate.equals("Manager"))
		 {
			 HttpSession session = request.getSession();
			 session.setAttribute("userName", userName); //with setAttribute() you can define a "key" and value pair so that you can get it in future using getAttribute("key")
			 request.setAttribute("userName", userName);
			 System.out.print(userName);
			 request.getRequestDispatcher("/manager.jsp").forward(request, response);//RequestDispatcher is used to send the control to the invoked page.
		 }
		 else
		 {
		 request.setAttribute("errMessage", userValidate); //If authenticateUser() function returnsother than SUCCESS string it will be sent to Login page again. Here the error message returned from function has been stored in a errMessage key.
		 request.getRequestDispatcher("/Login.jsp").forward(request, response);//forwarding the request
		 }
		 }
	
}
