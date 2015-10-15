package beans;

import models.Country;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public interface CountryWorking {
    ResultSet selectCountries() throws SQLException;
    ResultSet insertCountry(String countryName) throws SQLException;
    void updateCountry(int id, String countryName) throws SQLException;
    ResultSet getCountries() throws SQLException;
    ResultSet getCountryTraditions(String countryName, int userId) throws SQLException;
    void saveCountryXML(List<Country> countries, String direct) throws IOException;
}
