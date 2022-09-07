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

public class EditProducts extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
		StoreDAO storeDAO = new StoreDAO();
		Store store = (Store) session.getAttribute("store");
//		session.setAttribute("store", store);
		resp.getWriter().write("<html><body style=\"background-image: url(imgs/stardewbackground.png); color:white;\">");
		resp.getWriter().write("<nav style=\"display:flex;\"><div style=\"display:flex;flex-direction:column\"><img src=\"https://tinyurl.com/bdebbru9\" width=\"100px\">"+customer.name+" <form method=\"get\" action=\"/P1/editAccount\"><input type=\"submit\" value=\"Edit Account\"> </form><form method=\"post\" action=\"/P1/logout\"><input type=\"submit\" value=\"Log Out\"> </form> </div></nav>");
		resp.getWriter().write("<div style=\"color:black;display:flex;align-items:center;flex-direction:column;border: 9px ridge #f4910e; background: rgb(231,165,96);background: linear-gradient(0deg, rgba(231,165,96,1) 0%, rgba(252,197,113,1) 35%, rgba(231,165,96,1) 100%);margin:0 20%;\">");
        resp.getWriter().write("<h1>Items in store</h1>");
        resp.getWriter().write("<form style=\"display:flex;gap:2rem;\" method=\"post\" action=\"/P1/adminProducts\">");
        for(Product prod : store.prods) {
        	resp.getWriter().write("<div style=\"display:flex; flex-direction: column; align-items: center;\">");
        	resp.getWriter().write("<img style=\" height: 50px; width: 50px;\" src=\""+prod.getUrl()+"\" width=\"100px\">");
        	resp.getWriter().write("<p>Name= "+prod.name+" </br>Price= $"+prod.getPrice()+" </br>Quantity= "+prod.getQuantity()+"</p>");
        	resp.getWriter().write("<input style=\" height: 35px; width= 250px; \" type=\"submit\" name=\""+prod.name+"\" value=\"Edit Product\">");
        	resp.getWriter().write("</div>");
        }
		resp.getWriter().write("</form>"); 
		resp.getWriter().write("</div>");
		resp.getWriter().write("</body></html>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductDAO productDAO = new ProductDAO();
		HttpSession session = req.getSession();
		Store store = (Store) session.getAttribute("store");
		Product product = (Product) session.getAttribute("product");
		double price;
		int quantity;
		if(req.getParameter("price").equals("")) {
			price = product.getPrice();
		} else {
			price = Double.valueOf(req.getParameter("price"));
		}
		if(req.getParameter("quantity").equals("")) {
			quantity = product.getQuantity();
		} else {
			quantity = Integer.valueOf(req.getParameter("quantity"));
		}
		product.setPrice(price);
		product.setQuantity(quantity);
		productDAO.updateInstance(product);
		for(int i=0;i<store.prods.size();i++) {
			if(store.prods.get(i).name.equals(product.name)) {
				store.prods.set(i, product);
			}
		}
		session.setAttribute("store", store);
		resp.getWriter().write("<html><body style=\"background-image: url(imgs/stardewbackground.png); color:white;\">");
		resp.getWriter().write("<div style=\"color:black;display:flex;align-items:center;flex-direction:column;border: 9px ridge #f4910e; background: rgb(231,165,96);background: linear-gradient(0deg, rgba(231,165,96,1) 0%, rgba(252,197,113,1) 35%, rgba(231,165,96,1) 100%);margin:0 20%;\">");		
		resp.getWriter().write("<p>Product Updated</p>");
		resp.getWriter().write("<form method=\"get\" action=\"/P1/editProduct\">");
		
		resp.getWriter().write("<br><input type=\"submit\" value=\"Return\">");
		
		resp.getWriter().write("</form>");
		resp.getWriter().write("</div>");
		resp.getWriter().write("</body></html>");
	}
}
