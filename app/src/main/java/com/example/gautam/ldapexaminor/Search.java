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
    public String ldapMethod ( )
    {
        int ldapPort = LDAPConnection.DEFAULT_PORT;
        int searchScope = LDAPConnection.SCOPE_SUB;
        int ldapVersion  = LDAPConnection.LDAP_V3;
        String ldapHost = "10.106.128.18";
        String loginDN  = "administrator@csblr.com";
        String password = "Citrix_123";
        String searchBase = "OU=CSLABUSERS,DC=csblr,DC=com";
        String searchFilter = "(sAMAccountName=Gautam)";
        String retStr = "Return message starts here";
        retStr = retStr + "\n"+"host :"+ ldapHost  + "\n";
        retStr = retStr +"Port :"+ ldapPort+ "\n";
        retStr = retStr +"Trying to connect " +"\n" ;
        retStr = retStr +"Username : " + loginDN +"\n";
        retStr = retStr +"Password : " + password + "\n";

        LDAPConnection lc = new LDAPConnection( 20000);

        try {
            retStr = retStr +"Trying to connect " +"\n" ;
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

                System.out.println("\n" + nextEntry.getDN());
                retStr = retStr + nextEntry.getDN();
                System.out.println("  Attributes: ");
                retStr = retStr + ("  Attributes: ");

                LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
                Iterator allAttributes = attributeSet.iterator();

                while(allAttributes.hasNext()) {

                    LDAPAttribute attribute =
                            (LDAPAttribute)allAttributes.next();

                    String attributeName = attribute.getName();

                    System.out.println("    " + attributeName);
                    retStr = retStr + ("    " + attributeName);

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
            System.out.println( "Error: Unable to connect, Exception " );
            System.out.println( "Error: " + e.toString() );

        }
        catch( UnsupportedEncodingException e ) {
            System.out.println( "Error: Unsupported Encoding exception" );
            System.out.println( "Error: " + e.toString() );
        }


        return retStr;
        //System.exit(0);
    }
}


