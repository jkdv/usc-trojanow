package com.trojanow.network;

/**
 * Created by heetae on 4/10/15.
 */
interface SenderService {
    static final String ACTION_SIGN_IN = "com.trojanow.network.services.action.SIGN_IN";
    static final String ACTION_SIGN_UP = "com.trojanow.network.services.action.SIGN_UP";
    static final String ACTION_SIGN_OUT = "com.trojanow.network.services.action.SIGN_OUT";
    static final String ACTION_READ_RECENT_TIMELINE = "com.trojanow.network.services.action.READ_RECENT_TIMELINE";
    static final String ACTION_READ_MY_THOUGHTS_TIMELINE = "com.trojanow.network.services.action.READ_MY_TIMELINE";
    static final String ACTION_SHARE_THOUGHT = "com.trojanow.network.services.action.SHARE_THOUGHT";
    static final String ACTION_DELETE_THOUGHT = "com.trojanow.network.services.action.DELETE_THOUGHT";
    static final String ACTION_UPDATE_THOUGHT = "com.trojanow.network.services.action.UPDATE_THOUGHT";
    static final String ACTION_UPDATE_ACCOUNT = "com.trojanow.network.services.action.UPDATE_ACCOUNT";
    static final String ACTION_DEACTIVATE_ACCOUNT = "com.trojanow.network.services.action.DEACTIVATE_ACCOUNT";
    static final String ACTION_READ_ALL_USERS = "com.trojanow.network.services.action.READ_ALL_USERS";
    static final String ACTION_READ_CHAT_HISTORY = "com.trojanow.network.services.action.READ_CHAT_HISTORY";
    static final String ACTION_SEND_CHAT = "com.trojanow.network.services.action.SEND_CHAT";
    static final String ACTION_DELETE_CHATS = "com.trojanow.network.services.action.DELETE_CHATS";
    static final String ACTION_READ_WEATHER = "com.trojanow.network.services.action.READ_WEATHER";

    static final String EXTRA_RESULT_RECEIVER = "android.os.ResultReceiver";
    static final String EXTRA_JSON_OBJECT = "com.trojanow.network.services.extra.JSON_OBJECT";

    static final int RESULT_ERROR = -1;
    static final int RESULT_ON_RESPONSE = 1;
}
