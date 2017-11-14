package com.example.gautam.ldapexaminor;

/**
 * Created by Gautam on 14-11-2017.
 */

//Sample code file: var/ndk/webBuildengine/tmp/viewable_samples/f91a68eb-ad37-4526-92b1-b1938f37b871/Search.java //Warning: This code has been marked up for HTML


//import com.novell.ldap.*;



import com.novell.ldap.LDAPAttribute;

import com.novell.ldap.LDAPAttributeSet;

import com.novell.ldap.LDAPConnection;

import com.novell.ldap.LDAPEntry;

import com.novell.ldap.LDAPException;

import com.novell.ldap.LDAPSearchResults;

import com.novell.ldap.util.Base64;

import java.util.Enumeration;

import java.util.Iterator;

import java.io.UnsupportedEncodingException;



public class Search

{

    public static void main( String[] args )

    {

        int ldapPort = LDAPConnection.DEFAULT_PORT;

        int searchScope = LDAPConnection.SCOPE_SUB;

        int ldapVersion  = LDAPConnection.LDAP_V3;

        String ldapHost = "10.106.128.18";

        String loginDN  = "administrator@csblr.com";

        String password = "Citrix_123";

        String searchBase = "OU=CSLABUSERS,DC=csblr,DC=com";
        //String searchBase = "DC=csblr,DC=com";

        String searchFilter = "(sAMAccountName=Gautam)";

        LDAPConnection lc = new LDAPConnection();



        try {

            // connect to the server


            lc.connect( ldapHost, ldapPort );

            // bind to the server


            lc.bind( ldapVersion, loginDN, password.getBytes("UTF8") );

            Boolean str = lc.isConnected();
            System.out.print( "Is connected : " + str +"\n");

            LDAPSearchResults searchResults =

                    lc.search(  searchBase,
                            searchScope,
                            searchFilter,
                            null,         // return all attributes
                            false);       // return attrs and values


            /* To print out the search results,

             *   -- The first while loop goes through all the entries

             *   -- The second while loop goes through all the attributes

             *   -- The third while loop goes through all the attribute values

             */
            String srcRes = searchResults.toString();
            System.out.println( "SrcRes : " + srcRes + "\n");
            while ( searchResults.hasMore()) {

                LDAPEntry nextEntry = null;

                try {

                    nextEntry = searchResults.next();

                }

                catch(LDAPException e) {

                    System.out.println("Error: " + e.toString());



                    // Exception is thrown, go for next entry


                    if(e.getResultCode() == LDAPException.LDAP_TIMEOUT || e.getResultCode() == LDAPException.CONNECT_ERROR)

                        break;

                    else

                        continue;

                }



                System.out.println("\n" + nextEntry.getDN());

                System.out.println("  Attributes: ");



                LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();

                Iterator allAttributes = attributeSet.iterator();



                while(allAttributes.hasNext()) {

                    LDAPAttribute attribute =

                            (LDAPAttribute)allAttributes.next();

                    String attributeName = attribute.getName();



                    System.out.println("    " + attributeName);



                    Enumeration allValues = attribute.getStringValues();



                    if( allValues != null) {

                        while(allValues.hasMoreElements()) {

                            String Value = (String) allValues.nextElement();

                            if (Base64.isLDIFSafe(Value)) {

                                // is printable


                                System.out.println("      " + Value);

                            }

                            else {

                                // base64 encode and then print out


                                Value = Base64.encode(Value.getBytes());

                                System.out.println("      " + Value);

                            }

                        }

                    }

                }

            }

            // disconnect with the server


            lc.disconnect();

        }

        catch( LDAPException e ) {

            System.out.println( "Error: " + e.toString() );

        }

        catch( UnsupportedEncodingException e ) {

            System.out.println( "Error: " + e.toString() );

        }

        System.exit(0);

    }

}


