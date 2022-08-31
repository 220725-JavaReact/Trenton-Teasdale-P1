package dao;

import java.util.ArrayList;

public interface DAO<T> {
	void addInstance(T newInstance);
	ArrayList<T> getAllInstances();
	T getByName(String Name);
	void updateInstance(T updatedInstance);
}
