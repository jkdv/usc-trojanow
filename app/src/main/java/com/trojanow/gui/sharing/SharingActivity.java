package com.trojanow.gui.sharing;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.trojanow.R;
import com.trojanow.gui.entity.Thought;
import com.trojanow.gui.entity.Weather;

public class SharingActivity extends ActionBarActivity {
    private static final int RESULT_ON_RESPONSE = 1;
    private static final int MAX_CHAR = 255;

    private Thought mThought;
    private int mDefaultTextColor;

    private EditText editText;
    private ImageButton buttonWeather;
    private ToggleButton toggleAnonymous;
    private TextView textViewWordCount;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        editText = (EditText) findViewById(R.id.editText);
        mDefaultTextColor = editText.getCurrentTextColor();

        textViewWordCount = (TextView) findViewById(R.id.textViewWordCount);
        toggleAnonymous = (ToggleButton) findViewById(R.id.toggleAnonymous);
        buttonWeather = (ImageButton) findViewById(R.id.buttonWeather);

        editText.addTextChangedListener(new ThoughtTextWatcher());
        mThought = new Thought(getIntent().getExtras());
        editText.setText(mThought.getContent());

        toggleAnonymous.setChecked(mThought.getAnonymous(false));
        toggleAnonymous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onAnonyousCheckedChanged(isChecked);
            }
        });

        buttonWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readWeather();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sharing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_activity_sharing) {
            if (mThought.getOperation() == Thought.Operation.UPDATE)
                update();
            else
                share();
        }
        return super.onOptionsItemSelected(item);
    }

    private void readWeather() {
        progressDialog = ProgressDialog.show(this, "TrojaNow", "Gathering weather info");
        Intent intent = new ReadWeatherIntent(new ReadWeatherResultReceiver(), new Bundle());
        sendBroadcast(intent);
    }

    private void onAnonyousCheckedChanged(boolean isChecked) {
        if (isChecked)
            Toast.makeText(this, "Anonymous: " +
                    toggleAnonymous.getTextOn(), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Anonymous: " +
                    toggleAnonymous.getTextOff(), Toast.LENGTH_SHORT).show();
    }

    /**
     * This method sends a thought to the server.
     */
    private void share() {
        if (editText.length() > MAX_CHAR) {
            Toast.makeText(getApplicationContext(),
                    "Text needs to be shorter than 255", Toast.LENGTH_SHORT).show();
            return;
        }
        Thought thought = new Thought(mThought.toBundle());
        thought.putContent(editText.getText().toString());
        thought.putAnonymous(toggleAnonymous.isChecked());
        Intent intent = new ShareThoughtIntent(
                new ShareThoughtResultReceiver(), thought.toBundle());
        sendBroadcast(intent);
        progressDialog = ProgressDialog.show(this, "TrojaNow", "Sharing");
    }

    private void update() {
        if (editText.length() > MAX_CHAR) {
            Toast.makeText(getApplicationContext(),
                    "Text needs to be shorter than 255.", Toast.LENGTH_SHORT).show();
            return;
        }
        Thought thought = new Thought(mThought.toBundle());
        thought.putContent(editText.getText().toString());
        thought.putAnonymous(toggleAnonymous.isChecked());
        Intent intent = new UpdateThoughtIntent(
                new UpdateThoughtResultReceiver(), thought.toBundle());
        sendBroadcast(intent);
        progressDialog = ProgressDialog.show(this, "TrojaNow", "Updating");
    }

    /**
     *
     */
    private class ThoughtTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int textLength = editText.length();
            if (textLength > MAX_CHAR) {
                textViewWordCount.setTextColor(Color.RED);
            } else {
                textViewWordCount.setTextColor(mDefaultTextColor);
            }
            textViewWordCount.setText(String.valueOf(textLength));
        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    /**
     *
     */
    private class ReadWeatherResultReceiver extends ResultReceiver {
        public ReadWeatherResultReceiver() {
            super(null);
        }
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            progressDialog.setOnDismissListener(null);
            if (resultCode == RESULT_ON_RESPONSE) {
                Weather weather = new Weather(resultData);
                StringBuffer sb = new StringBuffer();
                sb.append(editText.getText()).append("\n");
                sb.append(weather.getTemp()).append(" / ");
                sb.append(weather.getDescription());
                editText.setText(sb.toString());
            } else {
                final String msg = resultData.getString("Message");
                if (msg != null) {
                    Log.d("ReadWeatherResult", msg);
                    progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            progressDialog.dismiss();
        }
    }

    /**
     *
     */
    private class ShareThoughtResultReceiver extends ResultReceiver {
        public ShareThoughtResultReceiver() {
            super(null);
        }
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            progressDialog.dismiss();
            if (resultCode == RESULT_ON_RESPONSE) {
                Thought thought = new Thought(resultData);
                Toast.makeText(
                        getApplicationContext(),
                        thought.getMessage(), Toast.LENGTH_SHORT).show();
                setResult(thought.getStatusAsInt());
                finish();
            }
        }
    }

    /**
     *
     */
    private class UpdateThoughtResultReceiver extends ResultReceiver {
        public UpdateThoughtResultReceiver() {
            super(null);
        }
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            progressDialog.dismiss();
            if (resultCode == RESULT_ON_RESPONSE) {
                Thought thought = new Thought(resultData);
                Toast.makeText(
                        getApplicationContext(),
                        thought.getMessage(), Toast.LENGTH_SHORT).show();
                setResult(thought.getStatusAsInt());
                finish();
            }
        }
    }
}
