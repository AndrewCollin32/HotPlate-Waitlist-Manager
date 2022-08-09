/*
This class is used to text people their status on the waitlist. This class is disabled to prevent people from texting random numbers.
*/
/*
package com.hotplate.hotplate;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class twilioClass {

    public static final String accountSID = "REMOVED";
    public static final String authToken = "REMOVED";

    public void warnPerson(Customer customer, String textMessage){
        Twilio.init(accountSID, authToken);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+1" + customer.getPhoneNumber()), //to
                new com.twilio.type.PhoneNumber("REMOVED"), //from
                textMessage
        ).create();
    }
}*/
