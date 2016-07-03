package com.softserve.edu.entity;

public class User {
    
    private Integer id;
    
    private String Name;
    
    private String login;
    
    private String password;
        
    public User() {
        super();
    }

    public User(String name, String login) {
        super();
        Name = name;
        this.login = login;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", Name=" + Name + ", login=" + login + "]";
    }
    
    
    
}
