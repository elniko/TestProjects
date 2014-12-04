package service;

import java.util.List;

/**
 * Created by nike on 27/11/14.
 */

public interface Service<T> {

   public void add(T entity);

   public T edit(T entity);

    public T findById(long id);

    public List<T> getAll();
}
