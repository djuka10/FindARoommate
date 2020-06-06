package rs.ac.uns.ftn.findaroommate.FindARoommateServer.service;

import java.util.List;

public interface ServiceInterface<T> {
	List<T> getAll();
	T save(T object);
	T getOne(Integer id);
	void delete(T object);
	void deleteById(Integer id);
}
