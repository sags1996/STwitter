package com.example.admin.stwitter;

import java.util.ArrayList;

/**
 * Created by Admin on 8/1/2017.
 */

public class DirectMessagesID {
    ArrayList<EEvents> events;
    public static class EEvents{
       mc message_create;
        String id;
        public static class mc{
            md message_data;
            public static class md{
                 String text;
            }
        }

    }
}
