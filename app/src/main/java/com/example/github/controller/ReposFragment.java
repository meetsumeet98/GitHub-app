package com.example.github.controller;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.github.R;
import com.example.github.adapters.RecyclerViewAdapter;
import com.example.github.api.API;
import com.example.github.api.Client;
import com.example.github.model.GitHubRepo;
import com.example.github.model.Username;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReposFragment extends Fragment {

    private static final String TAG = "ReposFragment";
    List<GitHubRepo> repoList;
    private Context mContext;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.repostory_fragment_layout, container, false);

        return view;
    }

    public ReposFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        API api = Client.getRetrofit().create(API.class);

        Call<List<GitHubRepo>> call = api.getRepos(Username.getUsername());

        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                repoList = response.body();
                initRecyclerView();
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                Log.d(TAG, "onFailure: Fetching failed");
            }
        });

    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: initRecyclerView called");
        recyclerView = (RecyclerView)getView().findViewById(R.id.recycler_view);
        adapter = new RecyclerViewAdapter(repoList,mContext);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: before");
        setHasOptionsMenu(true);
        Log.d(TAG, "onCreate: after");
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu: method called");

        inflater.inflate(R.menu.repomenu,menu);
        menu.clear();
        super.onCreateOptionsMenu(menu,inflater);



        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

    }

}
