package com.mall.controller;

import com.mall.entity.Goods;
import com.mall.entity.GoodsSupplier;
import com.mall.entity.Supplier;
import com.mall.service.SupplierService;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("goodsSupplier")
public class GoodsSupplierController {

    @Autowired
    private SupplierService supplierService;


    /**
     * 根据供应商到处Excel文档
     *
     * @param supplierId 供应商ID，没有则查找所有供应商
     * @return Excel 下载数据
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(Long supplierId) throws Exception {
        String filename = "产品报价表.xlsx";


        // 查找所有供应商处理，
        List<Supplier> suppliers = supplierId != null ? Arrays.asList(supplierService.findOne(supplierId)) : supplierService.findAll();


        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        Workbook workbook = createExcel(suppliers);
        workbook.write(bos);

        Resource resource = new InputStreamResource(new ByteArrayInputStream(bos.toByteArray()));
        bos.close();


        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("charset", "utf-8");
        //设置下载文件名
        filename = URLEncoder.encode(filename, "UTF-8");
        headers.add("Content-Disposition", "attachment;filename=\"" + filename + "\"");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/x-msdownload")).body(resource);
    }


    /**
     * 创建产品报价表
     *
     * @param suppliers 根据供应商来创建
     * @return
     * @throws Exception
     */
    public static Workbook createExcel(List<Supplier> suppliers) throws Exception {
        Workbook xlsx = new XSSFWorkbook();

        // 居中对齐样式
        XSSFCellStyle alignStyle = (XSSFCellStyle) xlsx.createCellStyle();
        alignStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        alignStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        // 标题居中，粗体样式
        XSSFCellStyle titleStyle = (XSSFCellStyle) xlsx.createCellStyle();
        Font ztFont = xlsx.createFont();
        ztFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleStyle.setFont(ztFont);
        titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        for (Supplier supplier : suppliers) {
            Sheet sheet = xlsx.createSheet(supplier.getName());
            sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(1, 10000);
            sheet.setColumnWidth(2, 5000);
            sheet.setColumnWidth(3, 5000);

            Row row = sheet.createRow(0);

            Cell cell = row.createCell(0);
            cell.setCellStyle(titleStyle);
            cell.setCellValue("产品编号");

            cell = row.createCell(1);
            cell.setCellStyle(titleStyle);
            cell.setCellValue("产品名称");

            cell = row.createCell(2);
            cell.setCellStyle(titleStyle);
            cell.setCellValue("进货价（分）");

            cell = row.createCell(3);
            cell.setCellStyle(titleStyle);
            cell.setCellValue("销售价（分）");

            // 如果此供应商没有产品报价则不处理
            if (supplier.getGoodsSuppliers().size() == 0) {
                continue;
            }


            for (int i = 0; i < supplier.getGoodsSuppliers().size(); i++) {
                GoodsSupplier goodsSupplier = supplier.getGoodsSuppliers().get(i);
                Goods goods = goodsSupplier.getGoods();

                row = sheet.createRow(i + 1);

                cell = row.createCell(0);
                cell.setCellStyle(alignStyle);
                cell.setCellValue(goods.getId());

                cell = row.createCell(1);
                cell.setCellStyle(alignStyle);
                cell.setCellValue(goods.getName());

                cell = row.createCell(2);
                cell.setCellStyle(alignStyle);
                cell.setCellValue(goodsSupplier.getPurchasePrice());

                cell = row.createCell(3);
                cell.setCellStyle(alignStyle);
                cell.setCellValue(goods.getSalePrice());
            }
        }
        return xlsx;
    }
}
