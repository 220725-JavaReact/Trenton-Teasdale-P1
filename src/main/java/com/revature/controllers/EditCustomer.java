package com.revature.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CustomerDAO;
import models.Customer;

public class EditCustomer extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().write("<html><body style=\"background-image: url(imgs/stardewbackground.png); color:white;\">");
		resp.getWriter().write("<div style=\"color:black;display:flex;align-items:center;flex-direction:column;border: 9px ridge #f4910e; background: rgb(231,165,96);background: linear-gradient(0deg, rgba(231,165,96,1) 0%, rgba(252,197,113,1) 35%, rgba(231,165,96,1) 100%);margin:0 20%;\">");		
		resp.getWriter().write("<form method=\"post\" action=\"/P1/editAccount\">");
		resp.getWriter().write("<label for=\"name\">Name</label><br>");
		resp.getWriter().write("<input type=\"text\" name=\"name\"><br><br>");
		resp.getWriter().write("<label for=\"address\">Address</label><br>");
		resp.getWriter().write("<input type=\"text\" name=\"address\"><br><br>");
		resp.getWriter().write("<label for=\"number\">Number</label><br>");
		resp.getWriter().write("<input type=\"number\" name=\"number\"><br><br>");
		resp.getWriter().write("<label for=\"password\">Password</label><br>");
		resp.getWriter().write("<input type=\"password\" name=\"password\"><br><br>");
		resp.getWriter().write("<input type=\"submit\" value=\"Update info\">");
		resp.getWriter().write("</form>");
		
		resp.getWriter().write("</div>");
		resp.getWriter().write("</body></html>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CustomerDAO customerDAO = new CustomerDAO();
		HttpSession session = req.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
		String name;
		if(!req.getParameter("name").equals("")){
			name = req.getParameter("name");
		} else {
			name = customer.name;
		}
		String email = customer.getEmail();
		String address;
		if(!req.getParameter("address").equals("")){
			address = req.getParameter("address");
		} else {
			address = customer.getAddress();
		}
		int number;
		if(!req.getParameter("number").equals("")) {
			number = Integer.valueOf(req.getParameter("number"));
		} else {
			number = customer.getNumber();
		}
		
		String password = req.getParameter("password");
		if(!req.getParameter("password").equals("")){
			password = req.getParameter("password");
		} else {
			password = customer.getPassword();
		}
		
		
		Customer updatedCustomer = new Customer(name,address,email,number,password);
		customerDAO.updateInstance(updatedCustomer);
		
		resp.sendRedirect("/P1/");
	}
}
