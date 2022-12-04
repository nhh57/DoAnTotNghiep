package com.example.ecommerce.model.helper;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.Sale;
import com.example.ecommerce.mvc.model.SaleResult;

import java.util.ArrayList;
import java.util.List;

public class SaleHelper {
    public SaleResult getSaleResult(Sale sale){
        String saleDateStartString=Utils.getDateString(sale.getSaleDateStart());
        String saleDateEndString=Utils.getDateString(sale.getSaleDateEnd());
        String saleTimeStartString=Utils.getLocalTimeString(sale.getSaleTimeStart());
        String saleTimeEndString=Utils.getLocalTimeString(sale.getSaleTimeEnd());
        String saleDateTimeStartFormatVN=saleTimeStartString + " "+Utils.getDateStringVN(sale.getSaleDateStart());
        String saleDateTimeEndFormatVN=saleTimeEndString + " "+Utils.getDateStringVN(sale.getSaleDateEnd());
        String saleDateTimeStartFormatInputDateTimeLocal=saleDateStartString+"T"+saleTimeStartString;
        String saleDateTimeEndFormatInputDateTimeLocal=saleDateEndString+"T"+saleTimeEndString;
        SaleResult saleResult=new SaleResult();
        saleResult.setId(sale.getId());
        saleResult.setSaleDateStart(saleDateStartString);
        saleResult.setSaleDateEnd(saleDateEndString);
        saleResult.setSaleTimeStart(saleTimeStartString);
        saleResult.setSaleTimeEnd(saleTimeEndString);
        saleResult.setSaleName(sale.getSaleName());
        saleResult.setShowProduct(false);
        saleResult.setShowSale(false);
        saleResult.setSaleDateTimeStartFormatVN(saleDateTimeStartFormatVN);
        saleResult.setSaleDateTimeEndFormatVN(saleDateTimeEndFormatVN);
        saleResult.setSaleDateTimeStartFormatInputDateTimeLocal(saleDateTimeStartFormatInputDateTimeLocal);
        saleResult.setSaleDateTimeEndFormatInputDateTimeLocal(saleDateTimeEndFormatInputDateTimeLocal);
        saleResult.setDateStartShow(Utils.getDateAndMonthString(saleDateStartString));
        return saleResult;
    }

    public List<SaleResult> getListSaleResult(List<Sale> listSale){
        List<SaleResult> listSaleResult=new ArrayList<>();
        for(Sale sale:listSale){
            SaleResult saleResult=getSaleResult(sale);
            listSaleResult.add(saleResult);
        }
        return listSaleResult;
    }
    public int getTotalPage(int soSanPham, List<Sale> list) {
        int tongSoSanPham = list.size();
        int tongSoTrang = 1;
        float tempFloat = (float) tongSoSanPham / soSanPham;
        int tempInt = (int) tempFloat;
        if (tempFloat - tempInt > 0) {
            tongSoTrang = tempInt + 1;
        } else {
            tongSoTrang = tempInt;
        }
        return tongSoTrang;
    }
}
