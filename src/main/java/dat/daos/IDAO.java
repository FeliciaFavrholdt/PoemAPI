package dat.daos;

import dat.entities.Poem;

import java.util.List;

public interface IDAO<T> {
	T create(T t);
	T read(Object id);
	T update(T t);
	boolean delete(Object id);
	List<T> getAll();
	List<T> getById(T t);
}
