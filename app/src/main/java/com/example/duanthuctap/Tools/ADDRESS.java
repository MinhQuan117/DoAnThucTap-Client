package com.example.duanthuctap.Tools;

import android.content.Context;


import com.example.duanthuctap.Models.Address;
import com.example.duanthuctap.Models.District;
import com.example.duanthuctap.Models.Province;
import com.example.duanthuctap.Models.Ward;

import java.util.List;

public class ADDRESS {
    public static Province province = null;
    public static District district = null;
    public static Ward ward = null;

    public static Address aDefault(Context context) {
        Address address = null;
        for (int i = 0; i < LIST.listAddress.size(); i++) {
            if (LIST.listAddress.get(i).get_id().equals(TOOLS.getDefaulAddress(context))) {
                address = LIST.listAddress.get(i);
                break;
            }
        }
        if(address==null&& !LIST.listAddress.isEmpty()){
            address = LIST.listAddress.get(0);
        }
        return address;
    }
}
