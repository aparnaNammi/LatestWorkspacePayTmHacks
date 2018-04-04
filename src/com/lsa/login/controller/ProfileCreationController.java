package com.lsa.login.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.db.LifeSaviorDAO;
import com.pojo.UserProfileDetails;

public class ProfileCreationController extends HttpServlet{
	final static Logger logger = Logger.getLogger(ProfileCreationController.class.getName());
	public ProfileCreationController() {
		super();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		UserProfileDetails userProfileDetails = new UserProfileDetails();
		userProfileDetails.setFirstName(req.getParameter("fname"));
		userProfileDetails.setLastName(req.getParameter("lname"));
		userProfileDetails.setMiddleName(req.getParameter("mname"));
		userProfileDetails.setFatherName(req.getParameter("fathername"));
		userProfileDetails.setMobileNum(req.getParameter("mobNum"));
		userProfileDetails.setAge(req.getParameter("age"));
		userProfileDetails.setGender(req.getParameter("gender"));
		userProfileDetails.setAadharNum(req.getParameter("aadharNum"));
		userProfileDetails.setCurrentAddress(req.getParameter("address"));
		userProfileDetails.setEmergencyContactName1(req.getParameter("contactName1"));
		userProfileDetails.setEmergencyContactNum1(req.getParameter("contactNum1"));
		userProfileDetails.setEmergencyContactName2(req.getParameter("contactName2"));
		userProfileDetails.setEmergencyContactNum2(req.getParameter("contactNum2"));
		userProfileDetails.setEmergencyContactName3(req.getParameter("contactName3"));
		userProfileDetails.setEmergencyContactNum3(req.getParameter("contactNum3"));
		userProfileDetails.setFamilyDoctorName(req.getParameter("familyDoctorName"));
		userProfileDetails.setFamilyDocNum(req.getParameter("familyDoctorNum"));
		userProfileDetails.setCriticalIllness(req.getParameter("crtiticalIllness"));
		userProfileDetails.setHistoricHealthEvents(req.getParameter("historicLifeEvents"));
		userProfileDetails.setFamilyMedicalBackground(req.getParameter("familyMedBackground"));
		/*WriteToExcel writeToExcel = new WriteToExcel();
		writeToExcel.writeUserProfileToExcel(LSSUtil.getPath(), userDetails);*/
		LifeSaviorDAO connectToDB = new LifeSaviorDAO();
		try {
			connectToDB.insertData(userProfileDetails);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.setAttribute("userDetails", userProfileDetails);
		RequestDispatcher rd = req.getRequestDispatcher("/home.jsp");
		rd.forward(req, resp);
	}
}
