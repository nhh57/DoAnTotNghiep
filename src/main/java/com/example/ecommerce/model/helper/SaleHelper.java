package com.example.ecommerce.model.helper;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.Sale;
import com.example.ecommerce.mvc.model.SaleResult;

public class SaleHelper {
    public SaleResult getSaleResult(Sale sale){
        String saleDateStartString=Utils.getDateString(sale.getSaleDateStart());
        SaleResult saleResult=new SaleResult();
        saleResult.setId(sale.getId());
        saleResult.setSaleDateStart(saleDateStartString);
        saleResult.setSaleDateEnd(Utils.getDateString(sale.getSaleDateEnd()));
        saleResult.setSaleTimeStart(Utils.getLocalTimeString(sale.getSaleTimeStart()));
        saleResult.setSaleTimeEnd(Utils.getLocalTimeString(sale.getSaleTimeEnd()));
        saleResult.setSaleName(sale.getSaleName());
        saleResult.setShowProduct(false);
        saleResult.setShowSale(false);
        saleResult.setDateStartShow(Utils.getDateAndMonthString(saleDateStartString));
        return saleResult;
    }
}
