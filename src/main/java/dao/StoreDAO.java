package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.Product;
import models.Store;
import util.ConnectionFactory;

public class StoreDAO implements DAO<Store>{

	@Override
	public void addInstance(Store newInstance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Store> getAllInstances() {
		ArrayList<Store> stores = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "Select * from stores";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				stores.add(new Store(rs.getInt("Id"),rs.getString("store_name"),rs.getInt("address")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stores;
	}

	@Override
	public Store getByName(String name) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "select * from store_products sp \n"
					+ "inner join products p on p.productId = sp.productId\n"
					+ "inner join stores s on s.id = sp.storeId where s.store_name = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			Store store = new Store();
			while(rs.next()) {
					store.name= rs.getString("store_name");
					store.address = rs.getInt("address");
					store.setId(rs.getInt("Id"));
				store.addProduct(new Product(rs.getString("product_name"),rs.getDouble("price"),rs.getInt("quantity"),rs.getInt("storeId"),rs.getInt("productId"),rs.getString("url"))); 
			}
			return store;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateInstance(Store updatedInstance) {
		// TODO Auto-generated method stub
		
	}

}
