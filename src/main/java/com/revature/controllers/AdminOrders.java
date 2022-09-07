package com.revature.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.OrderDAO;
import models.Customer;
import models.Order;

public class AdminOrders extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OrderDAO orderDAO = new OrderDAO();
		HttpSession session = req.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
		
		resp.getWriter().write("<html><body style=\"background-image: url(imgs/stardewbackground.png); color:white;\">");
		resp.getWriter().write("<nav style=\"display:flex;\"><div style=\"display:flex;flex-direction:column\"><img src=\"https://tinyurl.com/bdebbru9\" width=\"100px\">"+customer.name+" <form method=\"get\" action=\"/P1/editAccount\"><input type=\"submit\" value=\"Edit Account\"> </form><form method=\"post\" action=\"/P1/logout\"><input type=\"submit\" value=\"Log Out\"> </form> </div></nav>");
		resp.getWriter().write("<div style=\"color:black;display:flex;align-items:center;flex-direction:column;border: 9px ridge #f4910e; background: rgb(231,165,96);background: linear-gradient(0deg, rgba(231,165,96,1) 0%, rgba(252,197,113,1) 35%, rgba(231,165,96,1) 100%);margin:0 20%;\">");
		resp.getWriter().write("<br><br><form method=\"post\" action=\"/P1/adminOrders\"><label for=\"order number\">Search by Order Number</label><input type=\"number\" name=\"order number\"><input type=\"submit\" value=\"Search\"> </form>");
		for(Order order : orderDAO.getAllInstances()) {
        	resp.getWriter().write("<img style=\" height: 50px; width: 50px;\" src=\"https://stardewvalleywiki.com/mediawiki/images/thumb/2/2b/Bundle_Yellow.png/32px-Bundle_Yellow.png\" width=\"100px\">");
			resp.getWriter().write("<p>Order "+order.orderNumber+" Total= $"+order.totalCost+" Store= "+order.storeName+"<br>"+order.items+"</p><br><hr style=\"width:100%;\">");
		}
		resp.getWriter().write("<form method=\"get\" action=\"/P1/home\"> <input type=\"submit\" name=\"home\"+ value=\"Home\"> </form>");
		resp.getWriter().write("</div>");
		resp.getWriter().write("</body></html>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OrderDAO orderDAO = new OrderDAO();
		HttpSession session = req.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
		
		int orderNum = (Integer.valueOf(req.getParameter("order number")));
		
		resp.getWriter().write("<html><body style=\"background-image: url(imgs/stardewbackground.png); color:white;\">");
		resp.getWriter().write("<nav style=\"display:flex;\"><div style=\"display:flex;flex-direction:column\"><img src=\"https://tinyurl.com/bdebbru9\" width=\"100px\">"+customer.name+" <form method=\"get\" action=\"/P1/editAccount\"><input type=\"submit\" value=\"Edit Account\"> </form><form method=\"post\" action=\"/P1/logout\"><input type=\"submit\" value=\"Log Out\"> </form> </div></nav>");
		resp.getWriter().write("<div style=\"color:black;display:flex;align-items:center;flex-direction:column;border: 9px ridge #f4910e; background: rgb(231,165,96);background: linear-gradient(0deg, rgba(231,165,96,1) 0%, rgba(252,197,113,1) 35%, rgba(231,165,96,1) 100%);margin:0 20%;\">");
		resp.getWriter().write("Welcome "+ customer.name+"<br>");
		for(Order order : orderDAO.getAllByNumber(orderNum)) {
        	resp.getWriter().write("<img style=\" height: 50px; width: 50px;\" src=\"https://stardewvalleywiki.com/mediawiki/images/thumb/2/2b/Bundle_Yellow.png/32px-Bundle_Yellow.png\" width=\"100px\">");
			resp.getWriter().write("<p>Order "+order.orderNumber+" Total= $"+order.totalCost+" Store= "+order.storeName+"<br> Items = "+order.items+"</p><br><hr style=\"width:100%;\">");
		}
		resp.getWriter().write("<form method=\"get\" action=\"/P1/home\"> <input type=\"submit\" name=\"home\"+ value=\"Home\"> </form>");
		resp.getWriter().write("</div>");
		resp.getWriter().write("</body></html>");
	}
}
