package models;

public class UserData {

    private String name;
    private String surname;
    private Address address;
    private FamilyStatus status;

    public UserData(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.address = new Address();
        this.status = FamilyStatus.NONE;
    }

    public UserData(String name, String surname, Address address, FamilyStatus status) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Address getAddress() {
        return address;
    }

    public FamilyStatus getStatus() {
        return status;
    }
}
