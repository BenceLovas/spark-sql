package model;

public class User {

    private int id;
    private String name;
    private String password;

    private User() {
        // prevent unparameterized instantiation
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}
