package com.alipay.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.util.JdbcUtil;
/**
 * @version 时间：2018年6月12日 上午9:45:23
 *
 */
public class AccountsServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(JdbcUtil.class.getName());
	public AccountsServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("执行AccountsServlet:");
		response.setContentType("text/html;charset-UTF-8");
		PrintWriter out = response.getWriter();
//		String u_id = ((HashMap)request.getSession().getAttribute("map")).get("U_ID").toString();
		out.print(true);
		out.flush();
		out.close();
	}
	public void init() throws ServletException {
	}
}
