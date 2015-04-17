package com.trojanow.gui.account;

/**
 * Created by heetae on 4/16/15.
 */
public class SignedInAccount {
    private static SignedInAccount ourInstance = new SignedInAccount();
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String contact;
    private String email;

    public static SignedInAccount getInstance() {
        return ourInstance;
    }

    private SignedInAccount() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static void removeInstance() {
        ourInstance = null;
    }
}
