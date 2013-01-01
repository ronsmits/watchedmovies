package com.example;

import java.io.IOException;
import java.util.HashMap;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.utils.JadeEngine;

@WebServlet("/index.html")
public class Index extends HttpServlet{
	private static final long serialVersionUID = -8428721746001855011L;

	@Inject private JadeEngine engine;
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getWriter().write(engine.render("index", new HashMap<String, Object>()));
	}
}
