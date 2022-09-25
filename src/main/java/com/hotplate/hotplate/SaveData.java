package com.hotplate.hotplate;

import java.io.Serializable;
import java.util.ArrayList;

public final class SaveData implements Serializable {
    public ArrayList<Customer> customersData;
    public SaveData(ArrayList<Customer> customersData){
        this.customersData = customersData;

    }
}
