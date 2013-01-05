package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.ejb.UserRepo;
import com.example.model.User;
import com.example.utils.JadeEngine;

@WebServlet("/index.html")
public class Index extends HttpServlet{
	private static final long serialVersionUID = -8428721746001855011L;

	@Inject private JadeEngine engine;
	@EJB private UserRepo repo;
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String,Object> map = new HashMap<String, Object>();
		List<User> users = new ArrayList<User>();
		String[] parameterValues = request.getParameterValues("id");
		if (parameterValues!=null && parameterValues.length>0) {
			System.out.println("id is "+parameterValues[0]);
			User user = repo.findUser(parameterValues[0]);
			users.add(user);
			System.out.println("user is "+ user);
			map.put("name", user.getUsername());
			map.put("picture", user.getPictureurl());
		}
		response.getWriter().write(engine.render("index", map));
	}
}
