package com.mall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

public class BaseService<T> implements PagingAndSortingRepository<T, Long>{

    @Autowired
    private PagingAndSortingRepository<T, Long> repository;

    @Override
    public Iterable<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public <S extends T> S save(S s) {
        return repository.save(s);
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> iterable) {
        return repository.save(iterable);
    }

    @Override
    public T findOne(Long aLong) {
        return repository.findOne(aLong);
    }

    @Override
    public boolean exists(Long aLong) {
        return repository.exists(aLong);
    }

    @Override
    public Iterable<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Iterable<T> findAll(Iterable<Long> iterable) {
        return repository.findAll(iterable);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void delete(Long aLong) {
        repository.delete(aLong);
    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }

    @Override
    public void delete(Iterable<? extends T> iterable) {
        repository.delete(iterable);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
