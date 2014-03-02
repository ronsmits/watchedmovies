package org.ronsmits.watchedmovies.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ContextViewer
 */
@WebServlet("/ContextViewer")
public class ContextViewer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContextViewer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.write("<html><head></head><body>");
		try {
			Context context = new InitialContext();
			NamingEnumeration<NameClassPair> list = context.list("java:global/watchedmovies");
			while (list.hasMore()){
				NameClassPair next = list.next();
				System.out.println(next.getName() +" "+ next.getClassName());
				out.write(next.getName() +" "+ next.getClassName()+"<br/>");
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			out.write("error "+ e.getMessage());
		} finally {
			out.write("</body></html>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
