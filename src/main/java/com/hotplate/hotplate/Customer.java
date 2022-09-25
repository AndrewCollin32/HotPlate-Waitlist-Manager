package com.hotplate.hotplate;

import java.io.Serializable;

public final class Customer implements Serializable {

    private String name;
    private String partySize;
    private String timeWaited;
    private String phoneNumber;

    public Customer (String name, String partySize, String time, String phoneNumber){
        HotPlateApp.log.info("[Creation] New Customer Data Created: " + name + "," + partySize + "," + time + "," + phoneNumber);
        this.name = name;
        this.partySize = partySize;
        this.timeWaited = time;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartySize() {
        return partySize;
    }

    public void setPartySize(String partySize) {
        this.partySize = partySize;
    }

    public String getTimeWaited() {
        return timeWaited;
    }

    public void setTimeWaited(String timeWaited) {
        this.timeWaited = timeWaited;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
