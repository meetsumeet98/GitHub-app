package com.example.github.api;

import com.example.github.model.GitHubRepo;
import com.example.github.model.GitHubUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {
    public static final String BASE_URL = "https://api.github.com";

    @GET("/users/{username}/repos")
    Call<List<GitHubRepo>> getRepos(@Path("username") String username);

    @GET("/users/{username}")
    Call<GitHubUser> getInfo(@Path("username") String username);

}
