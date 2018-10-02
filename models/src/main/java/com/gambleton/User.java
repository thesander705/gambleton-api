package com.gambleton;

/**
 * The user is a person that is registered to the website.
 */
public class User {
    /**
     * The username of the user, this is a unique identifier.
     */
    private String username;

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
}
