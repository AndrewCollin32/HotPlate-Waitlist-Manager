package com.hotplate.hotplate;

import java.io.Serializable;
import java.util.ArrayList;

public class SaveData implements Serializable {

    public String userName;
    public String restaurantName;
    public String pin;
    public ArrayList<Customer> customersData;
    public boolean autoLoadData;
    public String warnMessage;
    public String callMessage;

    public SaveData(ArrayList<Customer> customersData, String userName, String restaurantName, String pin, boolean autoLoadData, String warnMessage, String callMessage){
        this.customersData = customersData;
        this.userName = userName;
        this.restaurantName = restaurantName;
        this.pin = pin;
        this.autoLoadData = autoLoadData;
        this.warnMessage = warnMessage;
        this.callMessage = callMessage;
    }
}
