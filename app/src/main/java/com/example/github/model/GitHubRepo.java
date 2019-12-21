package com.example.github.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GitHubRepo {
    @SerializedName("name")
    private String name;

    @SerializedName("html_url")
    private String repo_urls;

    public String getRepo_urls() {
        return repo_urls;
    }

    public GitHubRepo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
