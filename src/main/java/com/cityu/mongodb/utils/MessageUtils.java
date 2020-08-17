package com.cityu.mongodb.utils;

import com.cityu.mongodb.constants.Message;

public class MessageUtils<T> {
    private MessageUtils() {}

    public static <T> Message returnSuccessMsg(String msg, Object... args) {
        return returnSuccessMsgWithContent(null, msg, args);
    }

    public static <T> Message returnSuccessMsgWithContent(T content, String msg, Object... args) {
        return returnMsg(true, content, msg, args);
    }

    public static <T> Message returnErrorMsg(String msg, Object... args) {
        return returnErrorMsgWithContent(null, msg, args);
    }

    public static <T> Message returnErrorMsgWithContent(T content, String msg, Object... args) {
        return returnMsg(false, content, msg, args);
    }

    private static <T> Message returnMsg(boolean success, T content, String msg, Object... args) {
        Message<T> m = new Message<>();
        m.setSuccess(success);
        m.setMsg(String.format(msg, args));
        m.setContent(content);

        return m;
    }

}
