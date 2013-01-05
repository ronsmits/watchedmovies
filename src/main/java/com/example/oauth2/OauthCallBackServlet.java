package com.example.oauth2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OauthCallBackServlet
 */
@WebServlet("/OauthCallBackServlet.html")
public class OauthCallBackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OauthCallBackServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println(request.getPathInfo());
    }
}
