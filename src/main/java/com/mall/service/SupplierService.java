package com.mall.service;

import com.mall.dao.SupplierDAO;
import com.mall.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


@Component
public class SupplierService {

    @Autowired
    private SupplierDAO supplierDAO;

    public Page<Supplier> find(Pageable pageable) {
        return supplierDAO.findAll(pageable);
    }


    public boolean save(Supplier supplier) {
        supplierDAO.save(supplier);
        return true;
    }

    public boolean delete(Long id) {
        supplierDAO.delete(id);
        return true;
    }
}
