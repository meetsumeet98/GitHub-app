package com.example.github.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GitHubUser {
    @SerializedName("login")
    @Expose
    private String username;

    @SerializedName("avatar_url")
    @Expose
    private String avatar;

    @SerializedName("public_repos")
    @Expose
    private String numberOfRepos;

    @SerializedName("bio")
    @Expose
    private String bio;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("location")
    @Expose
    private String location;


    public String getLocation() {
        return location;
    }

    public String getBio() {
        return bio;
    }

    public String getName() {
        return name;
    }

    public String getNumberOfRepos() {
        return numberOfRepos;
    }

    public GitHubUser(String username, String avatar, String numberOfRepos) {
        this.username = username;
        this.avatar = avatar;
        this.numberOfRepos = numberOfRepos;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }


}
