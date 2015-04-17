package com.trojanow.directmessaging.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import android.util.Log;

import com.trojanow.directmessaging.entity.Chat;
import com.trojanow.directmessaging.entity.User;
import com.trojanow.directmessaging.events.DeleteChatsResultReceiver;
import com.trojanow.directmessaging.events.ReadAllUsersIntent;
import com.trojanow.directmessaging.events.ReadAllUsersResultReceiver;
import com.trojanow.directmessaging.events.ReadChatHistoryIntent;
import com.trojanow.directmessaging.events.ReadChatHistoryResultReceiver;
import com.trojanow.directmessaging.events.SendChatIntent;
import com.trojanow.directmessaging.events.SendChatResultReceiver;

/**
 * DirectMessageService creates direct message data from the GUI
 * and transfer to the Network component.
 */
public class DirectMessageService extends IntentService {
    public static final String ACTION_READ_ALL_USERS = "com.trojanow.directmessaging.services.action.READ_ALL_USERS";
    public static final String ACTION_READ_CHAT_HISTORY = "com.trojanow.directmessaging.services.action.READ_CHAT_HISTORY";
    public static final String ACTION_DELETE_CHATS = "com.trojanow.directmessaging.services.action.DELETE_CHATS";
    public static final String ACTION_SEND_CHAT = "com.trojanow.directmessaging.services.action.SEND_CHAT";
    public static final String EXTRA_RESULT_RECEIVER = "android.os.ResultReceiver";

    /**
     * Starts this service to perform action sign up with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionReadAllUsers(Context context, String userId,
                                               ResultReceiver receiver) {
        Log.d("DirectMessageService", "startActionReadAllUsers");
        Intent intent = new Intent(context, DirectMessageService.class);
        intent.setAction(ACTION_READ_ALL_USERS);
        intent.putExtra(User.EXTRA_USER_ID, userId);
        intent.putExtra(EXTRA_RESULT_RECEIVER, receiver);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action sign up with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionReadChatHistory(Context context, String userIdFrom,
                                                  String userIdTo, ResultReceiver receiver) {
        Log.d("DirectMessageService", "startActionReadChatHistory");
        Intent intent = new Intent(context, DirectMessageService.class);
        intent.setAction(ACTION_READ_CHAT_HISTORY);
        intent.putExtra(Chat.EXTRA_USER_ID_FROM, userIdFrom);
        intent.putExtra(Chat.EXTRA_USER_ID_TO, userIdTo);
        intent.putExtra(EXTRA_RESULT_RECEIVER, receiver);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action sign up with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionDeleteChats(Context context, String userIdFrom,
                                              String userIdTo, ResultReceiver receiver) {
        Log.d("DirectMessageService", "startActionDeleteChats");
        Intent intent = new Intent(context, DirectMessageService.class);
        intent.setAction(ACTION_DELETE_CHATS);
        intent.putExtra(Chat.EXTRA_USER_ID_FROM, userIdFrom);
        intent.putExtra(Chat.EXTRA_USER_ID_TO, userIdTo);
        intent.putExtra(EXTRA_RESULT_RECEIVER, receiver);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action sign up with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionSendChat(Context context, String userIdFrom,
                                           String userIdTo, String content,
                                           ResultReceiver receiver) {
        Log.d("DirectMessageService", "startActionSendChat");
        Intent intent = new Intent(context, DirectMessageService.class);
        intent.setAction(ACTION_SEND_CHAT);
        intent.putExtra(Chat.EXTRA_USER_ID_FROM, userIdFrom);
        intent.putExtra(Chat.EXTRA_USER_ID_TO, userIdTo);
        intent.putExtra(Chat.EXTRA_CONTENT, content);
        intent.putExtra(EXTRA_RESULT_RECEIVER, receiver);
        context.startService(intent);
    }
    public DirectMessageService() {
        super("DirectMessageService");
    }

    /**
     * This method receives Intents (events) and invokes appropriate methods to handle them.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_READ_ALL_USERS.equals(action)) {
                final String userId = intent.getStringExtra(User.EXTRA_USER_ID);
                final ResultReceiver receiver =
                        intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
                handleActionReadAllUsers(userId, receiver);
            } else if (ACTION_READ_CHAT_HISTORY.equals(action)) {
                final String userIdTo = intent.getStringExtra(Chat.EXTRA_USER_ID_TO);
                final String userIdFrom = intent.getStringExtra(Chat.EXTRA_USER_ID_FROM);
                final ResultReceiver receiver =
                        intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
                handleActionReadChatHistory(userIdFrom, userIdTo, receiver);
            } else if (ACTION_DELETE_CHATS.equals(action)) {
                final String userIdTo = intent.getStringExtra(Chat.EXTRA_USER_ID_TO);
                final String userIdFrom = intent.getStringExtra(Chat.EXTRA_USER_ID_FROM);
                final ResultReceiver receiver =
                        intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
                handleActionDeleteChats(userIdFrom, userIdTo, receiver);
            } else if (ACTION_SEND_CHAT.equals(action)) {
                final String userIdTo = intent.getStringExtra(Chat.EXTRA_USER_ID_TO);
                final String userIdFrom = intent.getStringExtra(Chat.EXTRA_USER_ID_FROM);
                final String content = intent.getStringExtra(Chat.EXTRA_CONTENT);
                final ResultReceiver receiver =
                        intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
                handleActionSendChat(userIdFrom, userIdTo, content, receiver);
            }
        }
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionReadAllUsers(String userId, ResultReceiver receiver) {
        Log.d("DirectMessageService", "handleActionReadAllUsers");
        User user = new User();
        user.putUserId(userId);

        Intent intent = new ReadAllUsersIntent(
                new ReadAllUsersResultReceiver(receiver), user.toBundle());
        sendBroadcast(intent);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionReadChatHistory(String userIdFrom, String userIdTo,
                                             ResultReceiver receiver) {
        Log.d("DirectMessageService", "handleActionReadChatHistory");
        Chat chat = new Chat();
        chat.putUserIdFrom(userIdFrom);
        chat.putUserIdTo(userIdTo);
        chat.putOperation(Chat.Operation.FETCH);
        Intent intent = new ReadChatHistoryIntent(
                new ReadChatHistoryResultReceiver(receiver), chat.toBundle());
        sendBroadcast(intent);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDeleteChats(String userIdFrom, String userIdTo,
                                         ResultReceiver receiver) {
        Log.d("DirectMessageService", "handleActionDeleteChats");
        Chat chat = new Chat();
        chat.putUserIdFrom(userIdFrom);
        chat.putUserIdTo(userIdTo);
        chat.putOperation(Chat.Operation.DELETE);
        Intent intent = new ReadChatHistoryIntent(
                new DeleteChatsResultReceiver(receiver), chat.toBundle());
        sendBroadcast(intent);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSendChat(String userIdFrom, String userIdTo, String content,
                                      ResultReceiver receiver) {
        Log.d("DirectMessageService", "handleActionSendChat");
        Chat chat = new Chat();
        chat.putUserIdFrom(userIdFrom);
        chat.putUserIdTo(userIdTo);
        chat.putContent(content);

        Intent intent = new SendChatIntent(new SendChatResultReceiver(receiver), chat.toBundle());
        sendBroadcast(intent);
    }
}
