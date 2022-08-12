package com.hotplate.hotplate;

import java.io.Serializable;

public class SaveSettings implements Serializable {
    public String userName;
    public String restaurantName;
    public String pin;
    public boolean autoLoadData;
    public String warnMessage;
    public String callMessage;

    public Boolean britishTime;

    public SaveSettings(String userName, String restaurantName, String pin, boolean autoLoadData, String warnMessage, String callMessage, Boolean britishTime){
        this.userName = userName;
        this.restaurantName = restaurantName;
        this.autoLoadData = autoLoadData;
        this.warnMessage = warnMessage;
        this.callMessage = callMessage;
        this.pin = pin;
        this.britishTime = britishTime;
    }
}
