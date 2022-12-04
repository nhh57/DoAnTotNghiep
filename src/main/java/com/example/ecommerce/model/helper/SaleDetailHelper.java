package com.example.ecommerce.model.helper;
import com.example.ecommerce.model.SaleDetail;

import java.util.List;

public class SaleDetailHelper {
    public int getTotalPage(int soSanPham, List<SaleDetail> list) {
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
