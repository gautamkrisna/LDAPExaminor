package com.example.gautam.ldapexaminor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String Search_Value = "com.example.gautam.ldapexaminor.MESSAGE";
    public static final String LDAP_Server_IP = "com.example.gautam.ldapexaminor.LDAPsrvrIP";
    public static final String LDAP_Search_DN = "com.example.gautam.ldapexaminor.LDAPsearchDN";
    public static final String LDAP_Admin_User = "com.example.gautam.ldapexaminor.LDAPadminUser";
    public static final String LDAP_Admin_Pass = "com.example.gautam.ldapexaminor.LDAPadminPass";

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
        intent.putExtra(Search_Value, strSerchUser);
        System.out.println("Main activity message search User : " + strSerchUser);

        // getting LDAP IP
        EditText edtLDAPip = findViewById(R.id.edtSrvrFQDN);
        String strLDAPip = edtLDAPip.getText().toString();
        intent.putExtra(LDAP_Server_IP,strLDAPip);
        System.out.println("Main activity message : LDAP server IP : " + strLDAPip);

        // Get LDAP admin user.
        EditText edtLDAPAdminUser = findViewById(R.id.edtAdminUser);
        String strLDAPadminUser = edtLDAPAdminUser.getText().toString();
        intent.putExtra(LDAP_Admin_User,strLDAPadminUser);
        System.out.println("Main activity message : LDAP admin user name : " + strLDAPadminUser);

        // Get LDAP admin password.
        EditText edtLDAPAdminPass = findViewById(R.id.editSUserPasswd);
        String strLDAPadminPass = edtLDAPAdminPass.getText().toString();
        intent.putExtra(LDAP_Admin_Pass,strLDAPadminPass);
        System.out.println("Main activity message : LDAP admin user pass : " + strLDAPadminPass);

        // Get search DN.
        EditText edtLDAPSearchDN = findViewById(R.id.edtSrvrBaseDN);
        String strSearchDN = edtLDAPSearchDN.getText().toString();
        intent.putExtra(LDAP_Search_DN,strSearchDN);
        System.out.println("Main activity message : LDAP Search DN : " + strSearchDN);


        startActivity(intent);
    }

}
