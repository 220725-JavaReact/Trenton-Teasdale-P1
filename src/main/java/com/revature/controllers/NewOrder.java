package com.revature.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CustomerDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import dao.StoreDAO;
import models.Customer;
import models.Order;
import models.Product;
import models.Store;

@SuppressWarnings("serial")
public class NewOrder extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OrderDAO orderDAO = new OrderDAO();
		HttpSession session = req.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
		
		resp.getWriter().write("<html><body style=\"background-image: url(imgs/stardewbackground.png); color:white;\">");
		resp.getWriter().write("<nav style=\"display:flex;\"><div style=\"display:flex;flex-direction:column\"><img src=\"https://tinyurl.com/bdebbru9\" width=\"100px\">"+customer.name+" <form method=\"get\" action=\"/P1/editAccount\"><input type=\"submit\" value=\"Edit Account\"> </form><form method=\"post\" action=\"/P1/logout\"><input type=\"submit\" value=\"Log Out\"> </form> </div></nav>");
		resp.getWriter().write("<div style=\"color:black;display:flex;align-items:center;flex-direction:column;border: 9px ridge #f4910e; background: rgb(231,165,96);background: linear-gradient(0deg, rgba(231,165,96,1) 0%, rgba(252,197,113,1) 35%, rgba(231,165,96,1) 100%);margin:0 20%;\">");
		resp.getWriter().write("Welcome "+ customer.name+"<br>");
		for(Order order : orderDAO.getAllByName(customer.getEmail())) {
			resp.getWriter().write("<p>Order "+order.orderNumber+" Total= $"+order.totalCost+" Store= "+order.storeName+"<br> Items = "+order.items+"</p><br><hr style=\"width:100%;\">");
		}
		resp.getWriter().write("<form method=\"get\" action=\"/P1/signIn\"> <input type=\"submit\" name=\"home\"+ value=\"Home\"> </form>");
		resp.getWriter().write("</div>");
		resp.getWriter().write("</body></html>");
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductDAO productDAO = new ProductDAO();
		OrderDAO orderDAO = new OrderDAO();
		StoreDAO storeDAO =new StoreDAO();
		HttpSession session = req.getSession();
		Store store = (Store) session.getAttribute("store");
		@SuppressWarnings("unchecked")
		ArrayList<Product> cart = (ArrayList<Product>) session.getAttribute("cart");
		Customer customer = (Customer) session.getAttribute("customer");
		
		double cost =0;
		for(Product product : cart) {
			cost += product.getPrice() * product.getQuantity();
		}
		Random random = new Random();
		Order order = new Order(random.nextInt(10000),cost,customer.name,store.name,store.getId(),customer.getEmail(),cart);
		for(Product p : store.prods) {
			productDAO.updateInstance(p);
		}
		orderDAO.addInstance(order);
		resp.getWriter().write("<html><body style=\"background-image: url(imgs/stardewbackground.png); color:white;\">");
		resp.getWriter().write("<nav style=\"display:flex;\"><div style=\"display:flex;flex-direction:column\"><img src=\"https://tinyurl.com/bdebbru9\" width=\"100px\">"+customer.name+" <form method=\"get\" action=\"/P1/editAccount\"><input type=\"submit\" value=\"Edit Account\"> </form><form method=\"post\" action=\"/P1/logout\"><input type=\"submit\" value=\"Log Out\"> </form> </div></nav>");
		resp.getWriter().write("<p style=\"text-align:center;color:green; font-weight:600;\">Order submitted</p>");
		resp.getWriter().write("<div style=\"color:black;display:flex;align-items:center;flex-direction:column;border: 9px ridge #f4910e; background: rgb(231,165,96);background: linear-gradient(0deg, rgba(231,165,96,1) 0%, rgba(252,197,113,1) 35%, rgba(231,165,96,1) 100%);margin:0 20%;\">");
		resp.getWriter().write("<p>Welcome to Joja Mart</p><br>");
		for(Store stores : storeDAO.getAllInstances()) {
			
			resp.getWriter().write("<form method=\"get\" action=\"/P1/main\"> <input type=\"submit\" name=\""+stores.name+"\"+ value=\""+stores.name+"\"> </form>");
		}
		if(customer.orders.size() >0) {
			resp.getWriter().write("<form method=\"get\" action=\"/P1/order\"> <input type=\"submit\" name=\"orders\"+ value=\"Previous Orders\"> </form>");
		}
		resp.getWriter().write("</div>");
		resp.getWriter().write("</body></html>");
	}
}
