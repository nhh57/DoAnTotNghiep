package com.example.ecommerce.model.helper;

import com.example.ecommerce.model.ShipDetail;
import com.example.ecommerce.model.data.ShipDetailDataModel;

import java.util.Optional;

public class ShipDetailHelper {
    public ShipDetailDataModel getShipDetailDataModel(ShipDetail shipDetail){
        ShipDetailDataModel shipDetailDataModel=new ShipDetailDataModel();
        shipDetailDataModel.setId(shipDetail.getId());
        shipDetailDataModel.setFullName(shipDetail.getFullName());
        shipDetailDataModel.setPhone(shipDetail.getPhone());
        shipDetailDataModel.setAddress(shipDetail.getAddress());
        return shipDetailDataModel;
    }
    public String getAddress(Optional<String> province, Optional<String> district, Optional<String> ward, Optional<String> addressMore){
        String address="";
        if(addressMore.isPresent() && !addressMore.get().equals("")){
            address+=addressMore.get();
        }
        if(ward.isPresent() && !ward.get().equals("")){
            address+=", "+ward.get();
        }
        if(district.isPresent() && !district.get().equals("")){
            address+=", "+district.get();
        }
        if(province.isPresent() && !province.get().equals("")){
            address+=", "+province.get();
        }
        return address;
    }
}
