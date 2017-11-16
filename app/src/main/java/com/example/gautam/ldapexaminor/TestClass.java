package com.example.gautam.ldapexaminor;

/**
 * Created by Gautam on 14-11-2017.
 */

public class TestClass {
    public static void main ( String[] args ) {
        String retVal;
        Search obj = new Search();
        retVal = obj.ldapMethod();
        System.out.println("\n" + "Return Value : " + retVal);
    }
}
