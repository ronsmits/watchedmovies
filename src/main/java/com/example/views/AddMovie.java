package com.example.views;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.ejb.Repo;
import com.example.utils.JadeEngine;

/**
 * Servlet implementation class AddMovie
 */
@WebServlet("/addmovie")
public class AddMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       @EJB private Repo repo;
       @Inject private JadeEngine jadeEngine;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMovie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String render = jadeEngine.render("addmovie", map);
		response.getWriter().write(render);
	}
}
