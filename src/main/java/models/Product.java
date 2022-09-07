package models;

import exceptions.InvalidNumberException;

public class Product {
	public String name;
	private double price;
	private int quantity;
	private int storeId;
	private int productId;
	private String url;
	
	public Product(String name, double price, int quantity, int storeId, int productId, String url) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.productId = productId;
		this.storeId = storeId;
		this.url = url;
	}
	public Product() {
		// TODO Auto-generated constructor stub
	}
	public Product(String name, double price, int quantity, int productId,String url) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.productId = productId;
		this.url = url;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		if(price <= 0 ) throw new InvalidNumberException("You cannot set this value to negative numbers or 0");
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		if(quantity < 0 ) throw new InvalidNumberException("You cannot set this value to negative numbers or below 0");
		this.quantity = quantity;
	}
	public void name(String string) {
		// TODO Auto-generated method stub
		this.name = string;
	}
	@Override
	public String toString() {
		return "Product \n[Name= " + name + ", Price= $" + price + ", Quantity= " + quantity + "]";
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
