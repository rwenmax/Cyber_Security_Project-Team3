package com.sparta.team3.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", nullable = false)
    private Integer id;

    @Column(name = "token", nullable = false, length = 100)
    private String token;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    private UserProfile profile;

    @Column(name = "ts_expiration")
    private Instant tsExpiration;

    public Token(String token, UserProfile profile)
    {
        this.token = token;
        this.profile = profile;
    }

    public Token() {

    }

    public Instant getTsExpiration() {
        return tsExpiration;
    }

    public void setTsExpiration(Instant tsExpiration) {
        this.tsExpiration = tsExpiration;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}