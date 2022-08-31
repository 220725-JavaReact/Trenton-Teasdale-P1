package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Order;
import models.Product;
import util.ConnectionFactory;

public class OrderDAO implements DAO<Order>{
//	DAO<Product> productDAO = new ProductDBDAO();

	@Override
	public void addInstance(Order newInstance) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "Insert into orders (order_id, customer_name, store_name,total_cost,storeId,email) values (?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, newInstance.orderNumber);
			pstmt.setString(2, newInstance.customerName);
			pstmt.setString(3,newInstance.storeName);
			pstmt.setDouble(4, newInstance.totalCost);
			pstmt.setInt(5, newInstance.storeId);
			pstmt.setString(6, newInstance.email);
			pstmt.execute();
			for(Product item : newInstance.items) {
				String query2 = "Insert into line_items (order_id,product_id,quantity) values (?,?,?)";
				PreparedStatement pstmt2 = conn.prepareStatement(query2);
				pstmt2.setInt(1, newInstance.orderNumber);
				pstmt2.setInt(2, item.getProductId());
				pstmt2.setInt(3, item.getQuantity());
				pstmt2.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public ArrayList<Order> getAllInstances() {
		ProductDAO productDAO = new ProductDAO();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			ArrayList<Order> orders = new ArrayList<Order>();
			String query = "select * from line_items li \n"
					+ "inner join products p on p.productId = li.product_id\n"
					+ "inner join orders o on o.order_id = li.order_id";
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Boolean duplicate = false;
				Order order = new Order();
				order.orderNumber = rs.getInt("order_id");
				order.storeName =rs.getString("store_name");
				order.customerName = rs.getString("customer_name");
				order.totalCost = rs.getDouble("total_cost");
				order.storeId = rs.getInt("storeid");
				order.items.addAll(((ProductDAO) productDAO).getAllByOrderId(order.orderNumber));
				for(Order ord : orders) {
					if(order.orderNumber == ord.orderNumber) {
						duplicate = true;
					}
				}
				if(duplicate == false) {
					orders.add(order);
				} else {
					continue;
				}
			}
			return orders;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Order getByName(String name) {
		return null;
	}

	@Override
	public void updateInstance(Order updatedInstance) {
		// TODO Auto-generated method stub
		
	}
	public ArrayList<Order> getAllByName(String name){
		ProductDAO productDAO = new ProductDAO();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			ArrayList<Order> orders = new ArrayList<Order>();
			String query = "select * from line_items li \n"
					+ "inner join products p on p.productId = li.product_id\n"
					+ "inner join orders o on o.order_id = li.order_id where email =?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Boolean duplicate = false;
				Order order = new Order();
				order.orderNumber = rs.getInt("order_id");
				order.storeName =rs.getString("store_name");
				order.customerName = rs.getString("customer_name");
				order.totalCost = rs.getDouble("total_cost");
				order.storeId = rs.getInt("storeid");
				order.items.addAll(((ProductDAO) productDAO).getAllByOrderId(order.orderNumber));
				for(Order ord : orders) {
					if(order.orderNumber == ord.orderNumber) {
						duplicate = true;
					}
				}
				if(duplicate == false) {
					orders.add(order);
				} else {
					continue;
				}
			}
			return orders;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<Order> getAllByNumber(int orderNum){
		ProductDAO productDAO = new ProductDAO();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			ArrayList<Order> orders = new ArrayList<Order>();
			String query = "select * from line_items li \n"
					+ "inner join products p on p.productId = li.product_id\n"
					+ "inner join orders o on o.order_id = li.order_id where o.order_id =?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, orderNum);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Boolean duplicate = false;
				Order order = new Order();
				order.orderNumber = rs.getInt("order_id");
				order.storeName =rs.getString("store_name");
				order.customerName = rs.getString("customer_name");
				order.totalCost = rs.getDouble("total_cost");
				order.storeId = rs.getInt("storeid");
				order.items.addAll(((ProductDAO) productDAO).getAllByOrderId(order.orderNumber));
				for(Order ord : orders) {
					if(order.orderNumber == ord.orderNumber) {
						duplicate = true;
					}
				}
				if(duplicate == false) {
					orders.add(order);
				} else {
					continue;
				}
			}
			return orders;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
