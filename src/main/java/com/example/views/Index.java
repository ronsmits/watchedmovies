/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
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

@WebServlet(urlPatterns = "/index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 8834486359025265857L;

	@EJB private Repo repo;
	@Inject private JadeEngine jadeEngine;
	
	@Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("test", "dit is een test");
        map.put("movie", repo.getList());
        String render = jadeEngine.render("index", map);
        response.getWriter().write(render);
        
        
    }
}