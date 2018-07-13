package com.example.neosoft.daggerdemo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.neosoft.daggerdemo.R;
import com.example.neosoft.daggerdemo.app.App;
import com.example.neosoft.daggerdemo.pojo.UserHealthDetails;
import com.example.neosoft.daggerdemo.pojo.User;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    User user;

    @Inject
    UserHealthDetails userHealthDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.getUserComponent().makeUserObject(this);
    }

    public void onButtonClick(View view) {
        Toast.makeText(this, user.getFirstName() + " " + userHealthDetails.getUserAge(), Toast.LENGTH_SHORT).show();
    }
}
