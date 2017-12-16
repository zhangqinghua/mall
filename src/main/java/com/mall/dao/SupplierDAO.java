package com.mall.dao;

import com.mall.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SupplierDAO extends PagingAndSortingRepository<Supplier, Long> {

}
