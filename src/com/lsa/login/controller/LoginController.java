package com.lsa.login.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.db.LifeSaviorDAO;
import com.email.SendEmailToEmergencyService;
import com.pojo.UserProfileDetails;

public class LoginController extends HttpServlet {

	final static Logger logger = Logger.getLogger(LoginController.class
			.getName());
	private static final long serialVersionUID = 1L;

	public LoginController() {
		super();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userName = req.getParameter("uname");
		String pass = req.getParameter("psw");
		RequestDispatcher rd = null;

		/*
		 * UserProfileDetails userDetails = null; ReadExcel readExcelData = new
		 * ReadExcel(); userDetails = readExcelData.readExcelData(userName,
		 * LSSUtil.getPath());
		 */

		LifeSaviorDAO connectToDB = new LifeSaviorDAO();
		UserProfileDetails userProfileDetails = null;
		try {
			userProfileDetails = connectToDB.selectData(userName);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.setAttribute("userDetails", userProfileDetails);
		rd = req.getRequestDispatcher("/home.jsp");
		rd.forward(req, resp);
	}
}
