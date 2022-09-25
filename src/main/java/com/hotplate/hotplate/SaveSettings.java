package com.hotplate.hotplate;

import java.io.Serializable;

public final class SaveSettings implements Serializable {
    public String userUserName;
    public String restaurantName;
    public String username;
    public String password;
    public boolean autoLoadData;
    public String warnMessage;
    public String callMessage;

    public Boolean britishTime;

    public SaveSettings(String userUserName, String restaurantName, String username, String password, boolean autoLoadData, String warnMessage, String callMessage, Boolean britishTime){
        HotPlateApp.log.info("[Save] Saving Settings: " + userUserName + "," + restaurantName + "," + autoLoadData + "," + warnMessage + "," + callMessage + "," + username + "," + password + "," + britishTime);
        this.restaurantName = restaurantName;
        this.autoLoadData = autoLoadData;
        this.warnMessage = warnMessage;
        this.callMessage = callMessage;
        this.userUserName = userUserName;
        this.username = username;
        this.password = password;
        this.britishTime = britishTime;
    }
}
