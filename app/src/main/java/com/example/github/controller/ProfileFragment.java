package com.example.github.controller;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.github.R;
import com.example.github.api.API;
import com.example.github.api.Client;
import com.example.github.model.GitHubUser;
import com.example.github.model.Username;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";

    private ImageView avatar;
    private Button button;
    private TextView username, name , bio, repoCount, location;

    private Context mContext;
    private String avatarURL;

    public ProfileFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.profile_fragment_layout, container, false);
       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        avatar=(ImageView)getView().findViewById(R.id.avatar);
        username=(TextView)getView().findViewById(R.id.username);
        button = (Button)getView().findViewById(R.id.button);
        name=(TextView)getView().findViewById(R.id.name);
        bio=(TextView)getView().findViewById(R.id.bio);
        repoCount=(TextView)getView().findViewById(R.id.repoCount);
        location=(TextView)getView().findViewById(R.id.location);

        loadJson();

    }

    private void loadJson(){


        API api = Client.getRetrofit().create(API.class);

        Call<GitHubUser> call = api.getInfo(Username.getUsername());
        call.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                GitHubUser user = response.body();

                Log.d(TAG, "onResponse: "+user.getBio());
                Log.d(TAG, "onResponse: "+user.getName());

                if(user == null){
                    Log.d(TAG, "onResponse: empty items ");
                }
                else
                    Log.d(TAG, "onResponse: not empty");

                username.setText(user.getUsername());
                name.setText(user.getName());
                bio.setText(user.getBio());
                repoCount.setText(user.getNumberOfRepos());
                location.setText(user.getLocation());

                avatarURL = user.getAvatar();

                Glide.with(mContext)
                        .load(avatarURL)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(avatar);
            }

            @Override
            public void onFailure(Call<GitHubUser> call, Throwable t) {
                Log.d(TAG, "onFailure: Failed");
            }
        });

    }

}
