package com.example.neosoft.daggerdemo.app;

import android.app.Application;

import com.example.neosoft.daggerdemo.interfaces.DaggerUserComponent;
import com.example.neosoft.daggerdemo.interfaces.UserComponent;

/**
 * Created by Neosoft on 12/19/2017.
 */

public class App extends Application {

    private static UserComponent userComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        userComponent = DaggerUserComponent.builder().build();
    }

    public static UserComponent getUserComponent() {
        return userComponent;
    }
}
