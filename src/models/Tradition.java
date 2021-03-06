package models;

public class Tradition implements Saveable{

    private Integer id;
    private Holiday holiday;
    private Country country;
    private String description;

    public Tradition() {

    }

    public Tradition(Holiday holiday, Country country) {
        this.holiday = holiday;
        this.country = country;
        this.description = "";
    }

    public Tradition(Holiday holiday, Country country, String description) {
        this.holiday = holiday;
        this.country = country;
        this.description = description;
    }

    public Tradition(Integer id, Holiday holiday, Country country,
                     String description) {
        this.id = id;
        this.holiday = holiday;
        this.country = country;
        this.description = description;
    }

    public void setHoliday(Holiday holiday) {
        this.holiday = holiday;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.description;
    }

    public Holiday getHoliday() {
        return this.holiday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tradition tradition = (Tradition) o;

        if (!country.equals(tradition.country)) return false;
        if (!description.equals(tradition.description)) return false;
        if (!holiday.equals(tradition.holiday)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = holiday.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    public Country getCountry() { return this.country;}

    public String toString() {
        return String.format("%30s%20s",this.holiday.getName(),this.country.getName());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}