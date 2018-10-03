package com.gambleton.dataAccessLayer.abstraction;

import java.util.List;

/**
 * @param <E> the model what the implementation manages.
 */
public interface Context<E> {
    /**
     * @param entity what entity do you want to create?
     */
    void create(E entity);

    /**
     * @param id the id of the entity you'd like to get
     * @return the entity found by the id.
     */
    E get(int id);

    /**
     * @return all entities.
     */
    List<E> getAll();

    /**
     * @param entity the updated entity, defined by the id.
     */
    void update(E entity);

    /**
     * @param id the id of the entity you'd like to remove.
     */
    void delete(int id);
}
