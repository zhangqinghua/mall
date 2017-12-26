package com.mall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

public class BaseService<T> {

    @Autowired
    private PagingAndSortingRepository<T, Long> repository;

    public Iterable<T> findAll(Sort sort) throws Exception {
        return repository.findAll(sort);
    }

    public Page<T> findAll(Pageable pageable) throws Exception {
        return repository.findAll(pageable);
    }

    public <S extends T> S save(S s) throws Exception {
        return repository.save(s);
    }

    public <S extends T> Iterable<S> save(Iterable<S> iterable) throws Exception {
        return repository.save(iterable);
    }

    public T findOne(Long aLong) throws Exception {
        return repository.findOne(aLong);
    }

    public boolean exists(Long aLong) throws Exception {
        return repository.exists(aLong);
    }

    public Iterable<T> findAll() throws Exception {
        return repository.findAll();
    }

    public Iterable<T> findAll(Iterable<Long> iterable) throws Exception {
        return repository.findAll(iterable);
    }

    public long count() throws Exception {
        return repository.count();
    }

    public void delete(Long aLong) throws Exception {
        repository.delete(aLong);
    }

    public void delete(T t) throws Exception {
        repository.delete(t);
    }

    public void delete(Iterable<? extends T> iterable) throws Exception {
        repository.delete(iterable);
    }

    public void deleteAll() throws Exception {
        repository.deleteAll();
    }
}
