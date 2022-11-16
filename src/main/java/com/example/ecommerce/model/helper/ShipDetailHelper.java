package com.example.ecommerce.model.helper;

import com.example.ecommerce.model.ShipDetail;
import com.example.ecommerce.model.data.ShipDetailDataModel;

public class ShipDetailHelper {
    public ShipDetailDataModel getShipDetailDataModel(ShipDetail shipDetail){
        ShipDetailDataModel shipDetailDataModel=new ShipDetailDataModel();
        shipDetailDataModel.setId(shipDetail.getId());
        shipDetailDataModel.setFullName(shipDetail.getFullName());
        shipDetailDataModel.setPhone(shipDetail.getPhone());
        shipDetailDataModel.setAddress(shipDetail.getAddress());
        return shipDetailDataModel;
    }
}
