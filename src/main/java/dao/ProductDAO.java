package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.ConnectionFactory;

import models.Product;

public class ProductDAO implements DAO<Product>{

	@Override
	public void addInstance(Product newInstance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Product> getAllInstances() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getByName(String name) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "Select * from products where product_name = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return new Product(rs.getString("product_name"),rs.getDouble("price"),rs.getInt("quantity"),rs.getInt("productId"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateInstance(Product updatedInstance) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "update products set quantity = ?, price = ? where productId = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, updatedInstance.getQuantity());
			pstmt.setDouble(2, updatedInstance.getPrice());
			pstmt.setInt(3, updatedInstance.getProductId());
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public ArrayList<Product> getAllByOrderId(int order_id){
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			ArrayList<Product> products = new ArrayList<Product>();
			String query = "select * from line_items li\n"
					+ "inner join products p on p.productId = li.product_id\n"
					+ "inner join orders o on o.order_id = li.order_id where o.order_id =?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, order_id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				boolean duplicate = false;
				Product product = new Product();
				product.name = rs.getString("product_name");
				product.setPrice(rs.getDouble("price"));
				product.setQuantity(rs.getInt("quantity"));
				product.setStoreId(rs.getInt("storeId"));
				product.setProductId(rs.getInt("productId"));
				for(Product prod: products) {
					if(prod.name.equals(product.name)) {
						duplicate = true;
						prod.setQuantity(prod.getQuantity()+1);
						prod.setPrice(product.getPrice()*prod.getQuantity());
					}
				}
				if(duplicate == false) {
					products.add(product);
				} else {
					continue;
				}
				
			}
			return products;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
