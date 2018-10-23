package com.gambleton.models;

/**
 * The user is a person that is registered to the website.
 */
public class User {
    /**
     * The id of the user
     */
    private int id;

    /**
     * The username of the user, this is a unique identifier.
     */
    private String username;

    /**
     * The password of the user, used for authentication.
     */
    private String password;

    /**
     * The role of a user.
     */
    private Role role;

    /**
     * @return the username of the user, this is a unique identifier.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username, the username defines the identity of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the role of the user, mainly used for authorisation.
     */
    public Role getRole() {
        return role;
    }

    /**
     * @param role, the role of a user defines it's authorisation.
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * @return the id of the user
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id of the user
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
