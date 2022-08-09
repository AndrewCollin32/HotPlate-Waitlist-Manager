/*
This class is used to text people their status on the waitlist. This class is disabled to prevent people from texting random numbers.
*/
/*
package com.hotplate.hotplate;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class twilioClass {

    public static final String accountSID = "ACdcfa5249495f638b9b41de5e59f87c98";
    public static final String authToken = "f0676df49a1499e491441e48fa1023ae";

    public static void warnPerson(Customer customer, String textMessage){
        Twilio.init(accountSID, authToken);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+16198182891"), //to
                new com.twilio.type.PhoneNumber("+14172125590"), //from
                textMessage
        ).create();
    }
}
*/