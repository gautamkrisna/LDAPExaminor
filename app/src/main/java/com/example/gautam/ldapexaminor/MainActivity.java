package com.example.gautam.ldapexaminor;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.gautam.ldapexaminor.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /** Called when the Retrivew button is clicked */

    public void sendMessage ( View view ) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText6);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        System.out.println("Main activity message : " + message);
        startActivity(intent);
    }


}
