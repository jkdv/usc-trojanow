package com.trojanow.network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class HTTPSenderService extends IntentService implements SenderService {
    private static String URL_BASE = "http://trojanow.uphero.com/TrojaNow/";
    private RequestQueue requestQueue;

    /**
     * Starts this service to perform action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionJsonRequest(String action, Context context,
                                              JSONObject jsonObject, ResultReceiver receiver) {
        Log.d("HTTPSenderService", "startActionJsonRequest");
        Intent intent = new Intent(context, HTTPSenderService.class);
        intent.setAction(action);
        intent.putExtra(EXTRA_JSON_OBJECT, jsonObject.toString());
        intent.putExtra(EXTRA_RESULT_RECEIVER, receiver);
        context.startService(intent);
    }

    public HTTPSenderService() {
        super("HTTPSenderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("HTTPSenderService", "onHandleIntent");
        requestQueue = Volley.newRequestQueue(this);
        final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
        final String action = intent.getAction();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(intent.getStringExtra(EXTRA_JSON_OBJECT));
        } catch (JSONException e){
            e.printStackTrace();
        }
        if (ACTION_SIGN_IN.equals(action)) {
            handleActionSignIn(jsonObject, receiver);
        } else if (ACTION_SIGN_UP.equals(action)) {
            handleActionSignUp(jsonObject, receiver);
        } else if (ACTION_SIGN_OUT.equals(action)) {
            handleActionSignOut(jsonObject, receiver);
        } else if (ACTION_READ_RECENT_TIMELINE.equals(action)) {
            handleActionReadRecentTimeline(jsonObject, receiver);
        } else if (ACTION_READ_MY_THOUGHTS_TIMELINE.equals(action)) {
            handleActionReadMyThoughtsTimeline(jsonObject, receiver);
        } else if (ACTION_SHARE_THOUGHT.equals(action)) {
            handleActionShareThought(jsonObject, receiver);
        } else if (ACTION_UPDATE_THOUGHT.equals(action)) {
            handleActionUpdateThought(jsonObject, receiver);
        } else if (ACTION_DELETE_THOUGHT.equals(action)) {
            handleActionDeleteThought(jsonObject, receiver);
        } else if (ACTION_UPDATE_ACCOUNT.equals(action)) {
            handleActionUpdateAccount(jsonObject, receiver);
        } else if (ACTION_DEACTIVATE_ACCOUNT.equals(action)) {
            handleActionDeactivateAccount(jsonObject, receiver);
        } else if (ACTION_READ_ALL_USERS.equals(action)) {
            handleActionReadAllUsers(jsonObject, receiver);
        } else if (ACTION_READ_CHAT_HISTORY.equals(action)) {
            handleActionReadChatHistory(jsonObject, receiver);
        } else if (ACTION_DELETE_CHATS.equals(action)) {
            handleActionDeleteChats(jsonObject, receiver);
        } else if (ACTION_SEND_CHAT.equals(action)) {
            handleActionSendChat(jsonObject, receiver);
        } else if (ACTION_READ_WEATHER.equals(action)) {
            handleActionReadWeather(jsonObject, receiver);
        }
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSignIn(JSONObject jsonObject, ResultReceiver receiver) {
        Log.d("HTTPSenderService", "handleActionSignIn");
        String url = URL_BASE + "login.php";
        request(url, jsonObject, receiver);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSignUp(JSONObject jsonObject, ResultReceiver receiver) {
        Log.d("HTTPSenderService", "handleActionSignUp");
        String url = URL_BASE + "signup.php";
        request(url, jsonObject, receiver);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSignOut(JSONObject jsonObject, ResultReceiver receiver) {
        Log.d("HTTPSenderService", "handleActionSignOut");
        String url = URL_BASE + "logout.php";
        request(url, jsonObject, receiver);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionReadRecentTimeline(JSONObject jsonObject,
                                                ResultReceiver receiver) {
        Log.d("HTTPSenderService", "handleActionReadRecentTimeline");
        String url = URL_BASE + "getposts.php";
        request(url, jsonObject, receiver);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionReadMyThoughtsTimeline(JSONObject jsonObject,
                                                    ResultReceiver receiver) {
        Log.d("HTTPSenderService", "handleActionReadMyThoughtsTimeline");
        String url = URL_BASE + "myposts.php";
        request(url, jsonObject, receiver);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionShareThought(JSONObject jsonObject, ResultReceiver receiver) {
        Log.d("HTTPSenderService", "handleActionShareThought");
        String url = URL_BASE + "post.php";
        request(url, jsonObject, receiver);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdateThought(JSONObject jsonObject, ResultReceiver receiver) {
        Log.d("HTTPSenderService", "handleActionUpdateThought");
        String url = URL_BASE + "post.php";
        request(url, jsonObject, receiver);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDeleteThought(JSONObject jsonObject, ResultReceiver receiver) {
        Log.d("HTTPSenderService", "handleActionDeleteThought");
        String url = URL_BASE + "post.php";
        request(url, jsonObject, receiver);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdateAccount(JSONObject jsonObject, ResultReceiver receiver) {
        Log.d("HTTPSenderService", "handleActionUpdateAccount");
        String url = URL_BASE + "account.php";
        request(url, jsonObject, receiver);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDeactivateAccount(JSONObject jsonObject,
                                               ResultReceiver receiver) {
        Log.d("HTTPSenderService", "handleActionDeactivateAccount");
        String url = URL_BASE + "account.php";
        request(url, jsonObject, receiver);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionReadAllUsers(JSONObject jsonObject,
                                          ResultReceiver receiver) {
        Log.d("HTTPSenderService", "handleActionReadAllUsers");
        String url = URL_BASE + "getallusers.php";
        request(url, jsonObject, receiver);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionReadChatHistory(JSONObject jsonObject,
                                             ResultReceiver receiver) {
        Log.d("HTTPSenderService", "handleActionReadChatHistory");
        String url = URL_BASE + "getchats.php";
        request(url, jsonObject, receiver);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDeleteChats(JSONObject jsonObject,
                                         ResultReceiver receiver) {
        Log.d("HTTPSenderService", "handleActionDeleteChats");
        String url = URL_BASE + "getchats.php";
        request(url, jsonObject, receiver);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSendChat(JSONObject jsonObject,
                                      ResultReceiver receiver) {
        Log.d("HTTPSenderService", "handleActionSendChat");
        String url = URL_BASE + "chat.php";
        request(url, jsonObject, receiver);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionReadWeather(JSONObject jsonObject,
                                         ResultReceiver receiver) {
        Log.d("HTTPSenderService", "handleActionReadWeather");
        try {
            double lat = jsonObject.getDouble("lat");
            double lon = jsonObject.getDouble("lon");
            String url = "http://api.openweathermap.org/data/2.5/weather" +
                    "?lat=" + String.format("%.2f", lat) +
                    "&lon=" + String.format("%.2f", lon) +
                    "&units=imperial&mode=json";
            request(url, null, receiver);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send HTTP POST requests containing a JSONObject.
     * @param url
     * @param jsonObject
     * @param receiver
     */
    private void request(String url, JSONObject jsonObject, final ResultReceiver receiver) {
        Log.d("REQUEST/URL", url);
        if (jsonObject != null)
            Log.d("REQUEST/JSON", jsonObject.toString());
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        receiver.send(RESULT_ON_RESPONSE, JSONUtils.convertToBundle(response));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String msg = "Network doesn't work";
                        if (error instanceof TimeoutError) {
                            msg = "Network timeout";
                        } else if (error instanceof NoConnectionError) {
                            msg = "No network connection";
                        }
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        receiver.send(RESULT_ERROR, null);
                    }
                });

        // Add the request to the RequestQueue.
        requestQueue.add(request);
    }
}
