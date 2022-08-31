package models;

import java.util.ArrayList;

public class Store {
	public String name;
	public int address;
	private int id;
	public ArrayList<Product> prods = new ArrayList<>();
	public ArrayList<Order> orders = new ArrayList<>();
	
	public Store() {
		
	}
	public Store(int int1, String string, int int2) {
		// TODO Auto-generated constructor stub
		this.id = int1;
		this.name = string;
		this.address = int2;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void addProduct(Product item) {
		this.prods.add(item);
	}
	@Override
	public String toString() {
		return "Store [name=" + name + ", address=" + address +"]";
	}
//	public static void storeList(DAO<Store> storeDAO) {
//		int i =1;
//		for(Store store: storeDAO.getAllInstances()) {
//			System.out.println("["+i+"] "+ store);
//			i++;
//		}
//		System.out.println("[x] Exit out");
//	}
//	public static Store storeMenu(Store store) {		
//				System.out.println("---------------------");
//				System.out.println("Please select a product to add to cart or X to return");
//				for(int i=0; i<store.prods.size();i++) {
//					System.out.print("["+i+"] " +store.prods.get(i)+"\n");
//				}
//				System.out.println("[c] Submit order");
//				System.out.println("[x] Cancel and exit");
//				return store;
//	}
}