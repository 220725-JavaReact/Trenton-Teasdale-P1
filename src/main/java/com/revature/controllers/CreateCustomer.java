package com.revature.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDAO;
import models.Customer;

@SuppressWarnings("serial")
public class CreateCustomer extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher view = req.getRequestDispatcher("createCustomer.html");

        view.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CustomerDAO customerDAO = new CustomerDAO();
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String address = req.getParameter("address");
		int number = Integer.valueOf(req.getParameter("number"));
		String password = req.getParameter("password");
		
		Customer customer = new Customer(name,address,email,number,password);
		customerDAO.addInstance(customer);
		
		resp.sendRedirect("/P1/");
	}
}
