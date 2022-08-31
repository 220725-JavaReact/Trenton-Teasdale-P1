package com.revature.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProductDAO;
import dao.StoreDAO;
import models.Customer;
import models.Product;
import models.Store;

public class AdminProducts extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String name = req.getParameterNames().nextElement();
		Customer customer = (Customer) session.getAttribute("customer");
		StoreDAO storeDAO = new StoreDAO();
		ArrayList<Product> cart = new ArrayList<>();
		Store store = storeDAO.getByName(name);
		session.setAttribute("store", store);
		session.setAttribute("cart", cart);
		resp.getWriter().write("<html><body style=\"background-image: url(imgs/stardewbackground.png); color:white;\">");
		resp.getWriter().write("<nav style=\"display:flex;\"><div style=\"display:flex;flex-direction:column\"><img src=\"https://tinyurl.com/bdebbru9\" width=\"100px\">"+customer.name+" <form method=\"get\" action=\"/P1/editAccount\"><input type=\"submit\" value=\"Edit Account\"> </form><form method=\"post\" action=\"/P1/logout\"><input type=\"submit\" value=\"Log Out\"> </form> </div></nav>");
		resp.getWriter().write("<div style=\"color:black;display:flex;align-items:center;flex-direction:column;border: 9px ridge #f4910e; background: rgb(231,165,96);background: linear-gradient(0deg, rgba(231,165,96,1) 0%, rgba(252,197,113,1) 35%, rgba(231,165,96,1) 100%);margin:0 20%;\">");
        resp.getWriter().write("<h1>Items in store</h1>");
        resp.getWriter().write("<form style=\"display:flex;flex-direction:column;\" method=\"post\" action=\"/P1/adminProducts\">");
        for(Product prod : store.prods) {
        	resp.getWriter().write(prod.toString());
        	resp.getWriter().write("<input type=\"submit\" name=\""+prod.name+"\" value=\"Edit Product\">");
        	resp.getWriter().write("</br></br>");
        }
		resp.getWriter().write("</form>"); 
		resp.getWriter().write("</div>");
		resp.getWriter().write("</body></html>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String name = req.getParameterNames().nextElement();
		ProductDAO productDAO = new ProductDAO();
		Product product = productDAO.getByName(name);
		session.setAttribute("product", product);
		
		
		resp.getWriter().write("<html><body style=\"background-image: url(imgs/stardewbackground.png); color:white;\">");
		resp.getWriter().write("<div style=\"color:black;display:flex;align-items:center;flex-direction:column;border: 9px ridge #f4910e; background: rgb(231,165,96);background: linear-gradient(0deg, rgba(231,165,96,1) 0%, rgba(252,197,113,1) 35%, rgba(231,165,96,1) 100%);margin:0 20%;\">");		
		resp.getWriter().write("<p>Update "+product.name+"Current Price: $"+product.getPrice()+" Remaining Quantity: "+product.getQuantity()+ "</p>");
		resp.getWriter().write("<form method=\"post\" action=\"/P1/editProduct\">");
		resp.getWriter().write("<label for=\"price\">Price</label> ");
		resp.getWriter().write("<input type=\"number\" name=\"price\">");
		resp.getWriter().write("<label for=\"quantity\">Quantity</label> ");
		resp.getWriter().write("<input type=\"number\" name=\"quantity\">");
		resp.getWriter().write("<input type=\"submit\" vaule=\"Update Product\">");
		resp.getWriter().write("</form>");
		resp.getWriter().write("</div>");
		resp.getWriter().write("</body></html>");
	}
}