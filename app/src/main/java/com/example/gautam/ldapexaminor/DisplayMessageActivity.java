package com.example.gautam.ldapexaminor;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {
    public static String retVal, strSearchUser, strLDPServer, strLDPadmin, strLDPadminPass, strLDPSearchString, strSearchLDPSearchDN = "sAMAccountName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the intent that started this activity and extract the string.
        // Get search value via intent
        Intent intent = getIntent();

        strSearchUser = intent.getStringExtra( MainActivity.Search_Value );    //getting search value.
        System.out.println("Display activity : get SearchUser : " +strSearchUser + "\n" );

        strLDPServer = intent.getStringExtra( MainActivity.LDAP_Server_IP) ;  //getting LDAP Server IP
        System.out.println("Display activity : get LDAP server ip : " +strLDPServer + "\n" );

        strLDPadmin = intent.getStringExtra(MainActivity.LDAP_Admin_User);  //getting LDAP admin user name.
        System.out.println("Display activity : get LDAP admin user : " + strLDPadmin + "\n" );

        strLDPadminPass = intent.getStringExtra(MainActivity.LDAP_Admin_Pass); //getting LDAP admin user password.
        System.out.println("Display activity : get LDAP admin user pass : " + strLDPadminPass + "\n" );

        strLDPSearchString = intent.getStringExtra(MainActivity.LDAP_Search_DN); //getting LDAP search string
        System.out.println("Display activity : get Searchg DN : " + strLDPSearchString + "\n" );


        // Calling Async task to execute LDAP attributes.
        System.out.println("Creating Async task: " + "\n" );
        retAttrAsyncTsk objAsyncTsk = new retAttrAsyncTsk();
        objAsyncTsk.execute();

        //Capture the layout's Text View and set the string as its text
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
            System.out.print("\n"+ "Search user variable in the Async activity : " +DisplayMessageActivity.strSearchUser + "\n");

            DisplayMessageActivity.retVal = obj.ldapMethod(DisplayMessageActivity.strLDPServer,
                                                            DisplayMessageActivity.strLDPadmin,
                                                            DisplayMessageActivity.strLDPadminPass,
                                                            DisplayMessageActivity.strLDPSearchString,
                                                            DisplayMessageActivity.strSearchLDPSearchDN,
                                                            DisplayMessageActivity.strSearchUser);
            System.out.println("\n" + "LDAP return Value : " + DisplayMessageActivity.retVal + "\n");

            return DisplayMessageActivity.retVal;
            //return "Test Text";
        }
    }
}
