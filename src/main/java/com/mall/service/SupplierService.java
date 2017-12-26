package com.mall.service;

import com.mall.entity.Supplier;
import org.springframework.stereotype.Component;


@Component
public class SupplierService extends BaseService<Supplier> {

    /**
     * 删除供应商
     * 1. 如果此供应商存在报价表则不能删除
     *
     * @param aLong 供应商ID
     */
    @Override
    public void delete(Long aLong) {
        Supplier supplier = super.findOne(aLong);
        if (supplier.getGoodsSuppliers().size() != 0) {
            return;
        }
        super.delete(aLong);
    }
}
