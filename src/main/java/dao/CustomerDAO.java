package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Customer;
import util.ConnectionFactory;

public class CustomerDAO implements DAO<Customer>{

	@Override
	public void addInstance(Customer newInstance) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "Insert into customers (email, customer_name, address,customer_number,password) values (?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newInstance.getEmail());
			pstmt.setString(2, newInstance.name);
			pstmt.setString(3, newInstance.getAddress());
			pstmt.setInt(4, newInstance.getNumber());
			pstmt.setString(5, newInstance.getPassword());
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public ArrayList<Customer> getAllInstances() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getByName(String name) {
		OrderDAO orderDAO = new OrderDAO();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "select * from customers where email = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			Customer customer = new Customer();
			while(rs.next()) {
				customer.name= rs.getString("customer_name");
				customer.setAddress(rs.getString("address"));
				customer.setNumber(rs.getInt("customer_number"));
				customer.setEmail(rs.getString("email"));
				customer.setPassword(rs.getString("password"));
			}
			customer.orders = orderDAO.getAllByName(customer.getEmail());
			return customer;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void updateInstance(Customer updatedInstance) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "update customers set customer_name = ?, address = ?, customer_number = ?,password = ? where email = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, updatedInstance.name);
			pstmt.setString(2, updatedInstance.getAddress());
			pstmt.setInt(3, updatedInstance.getNumber());
			pstmt.setString(4, updatedInstance.getPassword());
			pstmt.setString(5, updatedInstance.getEmail());
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
