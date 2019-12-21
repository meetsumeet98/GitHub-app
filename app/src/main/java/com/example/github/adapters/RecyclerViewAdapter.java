package com.example.github.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.github.R;
import com.example.github.model.GitHubRepo;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {
    private static final String TAG = "RecyclerViewAdapter";

    private List<GitHubRepo> mRepoList;

    private List<GitHubRepo> mRepoListFull;

    public RecyclerViewAdapter(List<GitHubRepo> mRepoList, Context mContext) {
        this.mRepoList = mRepoList;
        this.mContext = mContext;
        mRepoListFull= new ArrayList<>(mRepoList);
    }


    private Context mContext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: list item called");
        holder.mRepoName.setText(mRepoList.get(position).getName());


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mRepoList.get(position).getRepo_urls()));
                mContext.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+mRepoList.size());
        return mRepoList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<GitHubRepo> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(mRepoListFull);
            }
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(GitHubRepo repo : mRepoListFull){
                    if(repo.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(repo);
                    }
                }
            }

            FilterResults result = new FilterResults();
            result.values = filteredList;
            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mRepoList.clear();
            mRepoList.addAll((List)filterResults.values);
            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mRepoName;
        private LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRepoName = (TextView)itemView.findViewById(R.id.repo_name);
            parentLayout = (LinearLayout) itemView.findViewById(R.id.parent_layout);
        }
    }


}
