package com.example.gautam.ldapexaminor;

import android.content.Intent;
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

        // getting search user text
        EditText edtSearchUser = (EditText) findViewById(R.id.editSearchUser);
        String strSerchUser = edtSearchUser.getText().toString();

        System.out.println("Search user : "+ strSerchUser );
        intent.putExtra(EXTRA_MESSAGE, strSerchUser);
        System.out.println("Main activity message : " + strSerchUser);
        startActivity(intent);
    }

}
