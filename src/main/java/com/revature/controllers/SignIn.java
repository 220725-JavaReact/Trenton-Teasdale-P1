package com.revature.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CustomerDAO;
import dao.StoreDAO;
import models.Customer;
import models.Store;

@SuppressWarnings("serial")
public class SignIn extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher view = req.getRequestDispatcher("signIn.html");

        view.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CustomerDAO customerDAO = new CustomerDAO();
		StoreDAO storeDAO = new StoreDAO();
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		Customer customer = customerDAO.getByName(email);
		if(customer.getPassword() != null) {
			if(customer.getPassword().equals(password)) {
				HttpSession session = req.getSession();
				
				session.setAttribute("customer", customer);
				resp.getWriter().write("<html><body style=\"background-image: url(imgs/stardewbackground.png); color:white;\">");
				resp.getWriter().write("<nav style=\"display:flex;\"><div style=\"display:flex;flex-direction:column\"><img src=\"https://tinyurl.com/bdebbru9\" width=\"100px\">"+customer.name+" <form method=\"get\" action=\"/P1/editAccount\"><input type=\"submit\" value=\"Edit Account\"> </form><form method=\"post\" action=\"/P1/logout\"><input type=\"submit\" value=\"Log Out\"> </form> </div></nav>");
				resp.getWriter().write("<div style=\"color:black;display:flex;align-items:center;flex-direction:column;border: 9px ridge #f4910e; background: rgb(231,165,96);background: linear-gradient(0deg, rgba(231,165,96,1) 0%, rgba(252,197,113,1) 35%, rgba(231,165,96,1) 100%);margin:0 20%;\">");
				resp.getWriter().write("<p>Welcome to Joja Mart</p><br>");
				if(email.equals("admin@joja.com")) {
//					resp.getWriter().write("<form method=\"get\" action=\"/P1/adminProducts\"> <input type=\"submit\" name=\"orders\"+ value=\"Stores\"> </form>");
					for(Store store : storeDAO.getAllInstances()) {
						resp.getWriter().write("<form method=\"get\" action=\"/P1/adminProducts\"> <input type=\"submit\" name=\""+store.name+"\"+ value=\""+store.name+"\"> </form>");
					}
					resp.getWriter().write("<form method=\"get\" action=\"/P1/adminOrders\"> <input type=\"submit\" name=\"orders\"+ value=\"Orders\"> </form>");
				} else {
					for(Store store : storeDAO.getAllInstances()) {
						resp.getWriter().write("<form method=\"get\" action=\"/P1/main\"> <input type=\"submit\" name=\""+store.name+"\"+ value=\""+store.name+"\"> </form>");
					}
					if(customer.orders.size() >0) {
						resp.getWriter().write("<form method=\"get\" action=\"/P1/order\"> <input type=\"submit\" name=\"orders\"+ value=\"Previous Orders\"> </form>");
					}
				}

				
				resp.getWriter().write("</div>");
				resp.getWriter().write("</body></html>");
			}
			 else {
					resp.getWriter().write("incorrect email - password combonation");
				}
		} else {
			resp.getWriter().write("incorrect email - password combonation");
		}
	}
}
