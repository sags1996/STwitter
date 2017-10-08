package com.example.admin.stwitter;

/**
 * Created by Admin on 8/2/2017.
 */

public class DMPost {
    event e;
    public  class  event{
        message_create m;
        String type;
        public class  message_create{
            target t;
            message_data d;
            public class target{
                String recipient_id="892070359910019074";
            }
            public  class message_data{
                String text="Hii";
            }
        }
    }

}
//"event": {
//        "type": "message_create",
//        "message_create": {
//        "target": {
//        "recipient_id": "844385345234"
//        },
//        "message_data": {
//        "text": "Hello World!",
//        }
//        }
//        }
