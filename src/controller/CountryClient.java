package controller;

import models.*;

/**
 * Created by iocz on 01/10/15.
 */
public class CountryClient {
    public static void main(String[] args) throws Exception{
        javax.naming.Context initial = new javax.naming.InitialContext();

        Object objRef = initial.lookup("Beans/Country");
        CountryHome countryHome = (CountryHome)javax.rmi.PortableRemoteObject.
                narrow(objRef, CountryHome.class);
        int pkValue = (int) System.currentTimeMillis() % Integer.MAX_VALUE;
        models.Country country = countryHome.create(new Integer(pkValue), "Russia");
        country = countryHome.findByPriaryKey(new Integer(pkValue));
        System.out.println(country.getName());
    }
}
