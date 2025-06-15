package com.example.chat_26_5.dto;

public class ChatResponse {
    public Choice[] choices;

    public static class Choice {
        public Message message;

        public static class Message {
            public String role;
            public String content;
        }
    }
}
