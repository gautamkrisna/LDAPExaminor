package com.example.gautam.ldapexaminor;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {
    public static String retVal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        // Get the intent that started this activity and extract the string.
        Intent intent = getIntent();
        String message = intent.getStringExtra( MainActivity.EXTRA_MESSAGE );
        System.out.println("Display activity message " );

        System.out.println("Creating Async task: " );

        //Capture the layout's Text View and set the string as its text


        // Calling Async task to execute LDAP attributes.
        retAttrAsyncTsk objAsyncTsk = new retAttrAsyncTsk();
        objAsyncTsk.execute();

        // Setting text
        TextView textView = findViewById(R.id.txtLDAPAttributes);
        textView.setText(retVal);

    }



    // Class to retreivew LDAP attributes.
    public class retAttrAsyncTsk extends AsyncTask< String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            System.out.println("\n"+"Inside the Async Task"+"\n");
            //Retreviging LDAP attributes
            //String retVal; To be deleted.
            System.out.println("\n" + "Starting new Object in AsyncTask in the background : " + "\n");
            Search obj = new Search();
            System.out.println("\n" + "new Object created in the Back ground" + "\n");

            DisplayMessageActivity.retVal = obj.ldapMethod();
            System.out.println("\n" + "LDAP return Value : " + DisplayMessageActivity.retVal + "\n");

            return DisplayMessageActivity.retVal;
            //return "Test Text";
        }
    }
}
