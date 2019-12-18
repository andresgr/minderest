package com.asgr.minderest.service;

import com.asgr.minderest.model.BaseEntity;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.Optional;

public abstract class BaseCrudService<E extends BaseEntity, D extends CrudRepository<E, Long>>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    protected abstract D repository();

    public void create(E entity) {
        repository().save(entity);
    }

    public E save(E entity) {
        return repository().save(entity);
    }

    public void delete(E entity) {
        maybeFind(entity.getId()).ifPresent(repository()::delete);
    }

    public long count() {
        return repository().count();
    }

    public Optional<E> maybeFind(Long id) {
        return repository().findById(id);
    }

    public Iterable<E> findAll() {
        return repository().findAll();
    }

}
