package com.mall.service;

import com.mall.dao.SupplierDAO;
import com.mall.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SupplierService {

    @Autowired
    private SupplierDAO dao;

    public List<Supplier> findAll() {
        return (List<Supplier>) dao.findAll();
    }

    public Page<Supplier> find(Pageable pageable) {
        return dao.findAll(pageable);
    }

    public Supplier findOne(Long id) {
        return dao.findOne(id);
    }

    public boolean save(Supplier supplier) {
        dao.save(supplier);
        return true;
    }

    public boolean delete(Long id) {
        dao.delete(id);
        return true;
    }
}
