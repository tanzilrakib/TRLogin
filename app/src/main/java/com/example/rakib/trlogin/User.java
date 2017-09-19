package com.example.rakib.trlogin;

/**
 * Created by RakiB on 9/19/2017.
 */

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String timestamp;

    public int getId(){
        return this.id;
    }

    public void setId(int v){
        this.id = v;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String v){
        this.name = v;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String v){
        this.email = v;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String v){
        this.password = v;
    }
    public String getTimestamp(){
        return this.timestamp;
    }

    public void setTimestamp(String v){
        this.timestamp = v;
    }

}
