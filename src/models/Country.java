package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
@Entity
public class Country implements Serializable, Saveable, Comparable<Country> {
    @Id
    private Integer id;
    private String name;

    public Country() {

    }

    public Country(String name) {
        this.name = name;
    }

    public Country(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        if (!name.equals(country.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int compareTo(Country country) {
        if (this.getName().charAt(0) > country.getName().charAt(0)) return 1;
        else if (this.getName().charAt(0) < country.getName().charAt(0)) return -1;
        return 0;
    }
}