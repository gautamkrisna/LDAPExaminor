package com.example.gautam.ldapexaminor;
/**
 * Created by Gautam on 14-11-2017.
 */
//Sample code file: var/ndk/webBuildengine/tmp/viewable_samples/f91a68eb-ad37-4526-92b1-b1938f37b871/Search.java //Warning: This code has been marked up for HTML
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
    public String ldapMethod ( String strLDAPSrvr,  //LDAP Server IP
                               String strLoginUser,  // LDAP admin user
                               String strLoginPass,  // LDAP admin user password
                               String strSearchBase,  //Example : "OU=CSLABUSERS,DC=csblr,DC=com";
                               String strSearchKey,   // Example : sAMAccountName
                               String strSearchVar)   // Example : Gautam
    {
        int ldapPort = LDAPConnection.DEFAULT_PORT;
        int searchScope = LDAPConnection.SCOPE_SUB;
        int ldapVersion  = LDAPConnection.LDAP_V3;
        String ldapHost = strLDAPSrvr;
        String loginDN  = strLoginUser ;
        String password = strLoginPass ;
        //String searchBase = "OU=CSLABUSERS,DC=csblr,DC=com";
        String searchBase = strSearchBase;
        //String searchFilter = "(sAMAccountName=Gautam)";
        String searchFilter = "("+strSearchKey+"="+strSearchVar+")";
        System.out.println("\n"+ "Class.Search - Search filter : " + searchFilter + "\n");
        String retStr = "";


        LDAPConnection lc = new LDAPConnection( 20000);

        try {

            System.out.println("\n" + "retString in the Object : "+ retStr + "\n");
            System.out.println("\n" + "Starting new LDAP Connection : " + "\n");
            lc.connect( ldapHost, ldapPort );  // connect to the server
            System.out.println("\n" + "Post Connection " + "\n");
            lc.bind( ldapVersion, loginDN, password.getBytes("UTF8") );  // bind to the server
            System.out.println("\n" + "Post bind " + "\n");
            Boolean str = lc.isConnected();
            System.out.print( "Is connected : " + str +"\n");  //Debug message
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

                System.out.println("\n"+ "class.Search - Get DN : " + nextEntry.getDN());
                retStr = retStr + nextEntry.getDN() +"\n"+"---------------------------------------------"+"\n"+"\n";
                //System.out.println("\n"+ "class.Search -  Attributes: ");
                //retStr = retStr + "\n" +("  Attributes: ");

                LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
                Iterator allAttributes = attributeSet.iterator();

                while(allAttributes.hasNext()) {

                    LDAPAttribute attribute =
                            (LDAPAttribute)allAttributes.next();

                    String attributeName = attribute.getName();
                    String attributeValue = attribute.getStringValue().trim().toUpperCase();

                    System.out.println("class.search - checkpoint    " + attributeName);
                    retStr = retStr + ( attributeName + "  :> "+"\n"+ "     " + attributeValue + "\n" + "\n");

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
            retStr = "Error: Unable to connect, Exception : " + "\n" + e.toString();
            System.out.println( retStr );
            return retStr;

        }
        catch( UnsupportedEncodingException e ) {
            retStr = "Error: Unsupported Encoding exception : " + "\n" + e.toString();
            System.out.println( retStr );
        }


        return retStr;
        //System.exit(0);
    }
}


