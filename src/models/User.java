package models;

/**
 * Created by 1 on 23.06.2015.
 */
public class User {

    private Integer id;
    private String login;
    private String pass;
    //TODO add userData.
    //private UserData data;

    public User(String pass, String login) {
        //this.data = data;
        this.pass = pass;
        this.login = login;
    }

    public User(Integer id, String login, String pass) {
        this.id = id;
        this.login = login;
        this.pass = pass;
    }

    public Integer getId(){
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    //public UserData getData() {
        //return data;
    //}
}
