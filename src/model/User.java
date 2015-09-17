package model;

/**
 * Created by 1 on 23.06.2015.
 */
public class User {

    private String login;
    private String pass;
    private UserData data;

    public User(UserData data, String pass, String login) {
        this.data = data;
        this.pass = pass;
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public UserData getData() {
        return data;
    }
}
