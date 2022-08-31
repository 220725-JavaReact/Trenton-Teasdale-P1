package models;

import java.util.ArrayList;
import java.util.regex.Pattern;

import exceptions.InvalidEmailException;

public class Customer {
	public String name;
	private String address;
	private String email;
	private int number;
	private String password;
	public ArrayList<Order> orders = new ArrayList<>();
	public Customer() {
	
	}
	public Customer(String string, String address, String email, int number,String password) {
		// TODO Auto-generated constructor stub
		this.name=string;
		this.setAddress(address);
		this.setEmail(email);
		this.setNumber(number);
		this.setPassword(password);
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
		Pattern pattern = Pattern.compile(regex);
		if(!pattern.matcher(email).matches()) throw new InvalidEmailException("Invalid Email - try again");
			this.email = email;
		
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public void addStr(Order str) {
		this.orders.add(str);
	}
	@Override
	public String toString() {
		return "customer [name=" + name + ", address=" + address + ", email=" + email + ", number=" + number + ", orders=" +orders+"]";
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
