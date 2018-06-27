package pl.duncol.truckito.dao;

import java.io.Serializable;

public interface GenericDAO<T, K extends Serializable> {

	void create(T entity);
	
	T read(K id);

	T update(T entity);

	void delete(T entity);
}
