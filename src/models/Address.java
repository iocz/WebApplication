package models;

/**
 * Created by 1 on 23.06.2015.
 */
public class Address {

    private Country country;
    private String town;
    private String street;
    private int house;

    public Address() {
    }

    public Address(int house, Country country, String town, String street) {
        this.house = house;
        this.country = country;
        this.town = town;
        this.street = street;
    }

    public int getHouse() {
        return house;
    }

    public String getTown() {
        return town;
    }

    public String getStreet() {
        return street;
    }

    public Country getCountry() {
        return country;
    }
}
