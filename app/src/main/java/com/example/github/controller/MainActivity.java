package com.example.github.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.github.R;
import com.example.github.api.API;
import com.example.github.api.Client;
import com.example.github.model.GitHubUser;
import com.example.github.model.Username;

import java.io.IOException;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private Button searchBtn;

    private EditText username;


    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        searchBtn = (Button) findViewById(R.id.button);

        checkNetworkConnection();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: is called");
                Username.setUsername(username.getText().toString());
                checkUser();
            }
        });
    }

   private boolean isConnected(){
       boolean connected = false;
       ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
       if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
               connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
           //we are connected to a network
           connected = true;
       }
       else
           connected = false;

    return connected;
   }

   private void checkNetworkConnection(){
        boolean connected = isConnected();

        if(!connected) {
           showAlertDialog();
       }

   }

   private void showAlertDialog(){
       final AlertDialog dialog = new AlertDialog.Builder(this)
               .setTitle("Network Error")
               .setMessage("Please connect to the internet")
               .setPositiveButton("Retry", null)
               .show();
       Button btn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(isConnected()){
                   dialog.dismiss();
               }
           }
       });
   }

   private void checkUser(){
       API api = Client.getRetrofit().create(API.class);
       Call<GitHubUser> call = api.getInfo(username.getText().toString());
       try {
           GitHubUser user = call.execute().body(); //Synchronous call


           if(user != null){
               Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
               startActivity(intent);
           }else{
               Log.d(TAG, "checkUser: user object is null");
               Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show();
           }

       }catch (IOException e){

           Log.d(TAG, "checkUser: "+e.getLocalizedMessage());
       }
       
   }


}
