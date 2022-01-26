package com.sparta.team3.entities;

import javax.persistence.*;

@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", nullable = false)
    private Integer id;

    @Column(name = "profile_username", nullable = false, length = 50)
    private String profileUsername;

    @Column(name = "profile_password", nullable = false, length = 500)
    private String profilePassword;

    public String getProfilePassword() {
        return profilePassword;
    }

    public void setProfilePassword(String profilePassword) {
        this.profilePassword = profilePassword;
    }

    public String getProfileUsername() {
        return profileUsername;
    }

    public void setProfileUsername(String profileUsername) {
        this.profileUsername = profileUsername;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}