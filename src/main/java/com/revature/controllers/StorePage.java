package com.revature.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.StoreDAO;
import models.Customer;
import models.Product;
import models.Store;

@SuppressWarnings("serial")
public class StorePage extends HttpServlet{
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
        resp.getWriter().write("<form style=\"display:flex;flex-direction:column;\" method=\"post\" action=\"/P1/main\">");
        for(Product prod : store.prods) {
        	resp.getWriter().write(prod.toString());
        	resp.getWriter().write("<input type=\"submit\" name=\""+prod.name+"\" value=\"Add to cart\">");
        	resp.getWriter().write("</br></br>");
        }
		resp.getWriter().write("</form>"); 
		resp.getWriter().write("</div>");
		resp.getWriter().write("</body></html>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		resp.getWriter().write("<html><body style=\"background-image: url(imgs/stardewbackground.png); color:white;\">");
		@SuppressWarnings("unchecked")
		ArrayList<Product> cart = (ArrayList<Product>) session.getAttribute("cart");
		Store store = (Store) session.getAttribute("store");
		Customer customer = (Customer) session.getAttribute("customer");
		String name = req.getParameterNames().nextElement();
		for(int i=0;i<store.prods.size();i++) {
			if(store.prods.get(i).name.equals(name)) {
				boolean match = false;
				if(store.prods.get(i).getQuantity()==0){
					resp.getWriter().write("<p style=\"text-align:center;color:red; font-weight:600;\">no more remaining at this store</p>");
					break;
				}
				Product product = new Product(store.prods.get(i).name, store.prods.get(i).getPrice(),1,store.prods.get(i).getProductId());
				
				for(int j=0;j<cart.size();j++) {
					if(cart.get(j).name.equals(name)) {
						match = true;
						store.prods.get(i).setQuantity(store.prods.get(i).getQuantity()-1);
						cart.get(j).setQuantity(cart.get(j).getQuantity()+1);
						break;
					}
				}
				if(!match) {
					store.prods.get(i).setQuantity(store.prods.get(i).getQuantity()-1);
					cart.add(product);
				}
			}
		}
		session.setAttribute("cart", cart);
		session.setAttribute("store", store);
		resp.getWriter().write("<nav style=\"display:flex;\"><div style=\"display:flex;flex-direction:column\"><img src=\"https://tinyurl.com/bdebbru9\" width=\"100px\">"+customer.name+" <form method=\"get\" action=\"/P1/editAccount\"><input type=\"submit\" value=\"Edit Account\"> </form><form method=\"post\" action=\"/P1/logout\"><input type=\"submit\" value=\"Log Out\"> </form> </div></nav>");
		resp.getWriter().write("<div style=\"color:black;display:flex;align-items:center;flex-direction:column;border: 9px ridge #f4910e; background: rgb(231,165,96);background: linear-gradient(0deg, rgba(231,165,96,1) 0%, rgba(252,197,113,1) 35%, rgba(231,165,96,1) 100%);margin:0 20%;\">");
        resp.getWriter().write("<h1>Items in store</h1>");
        resp.getWriter().write("<form style=\"display:flex;flex-direction:column;\" method=\"post\" action=\"/P1/main\">");
        for(Product prod : store.prods) {
        	resp.getWriter().write(prod.toString());
        	resp.getWriter().write("<input type=\"submit\" name=\""+prod.name+"\" value=\"Add to cart\">");
        	resp.getWriter().write("</br></br>");
        }
        resp.getWriter().write("<h2>Items in cart</h2>");
        for(Product prod : cart) {
        	resp.getWriter().write("<p>"+prod.toString()+"</p>");
        }
		resp.getWriter().write("</form>");
        resp.getWriter().write("<form style=\"display:flex;flex-direction:column;\" method=\"post\" action=\"/P1/order\">");
        resp.getWriter().write("<input type=\"submit\" value=\"Submit Order\" >");
        resp.getWriter().write("</form>");
		resp.getWriter().write("</div>");
		resp.getWriter().write("</body></html>");
	}
}
